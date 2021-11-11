package org.srm.purchasecooperation.cux.po.itf.app.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.boot.platform.lov.feign.LovFeignClient;
import org.hzero.core.helper.LanguageHelper;
import org.hzero.core.message.MessageAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoHeader;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoLine;
import org.srm.purchasecooperation.cux.po.domain.repository.RcwlSodrHzpoHeaderRepository;
import org.srm.purchasecooperation.cux.po.domain.repository.RcwlSodrHzpoLineRepository;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlSkuInfoDTO;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlSodrHzpoHeaderDTO;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlSodrHzpoLineDTO;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlSodrHzpoReturnDTO;
import org.srm.purchasecooperation.cux.po.itf.app.service.RcwlSodrHzpoItfService;
import org.srm.purchasecooperation.cux.po.itf.domain.repository.RcwlSodrHzpoItfRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 16:16
 * @Description:
 */
@Service
public class RcwlSodrHzpoItfServiceImpl implements RcwlSodrHzpoItfService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlSodrHzpoItfServiceImpl.class);
    @Autowired
    private RcwlSodrHzpoItfRepository rcwlSodrHzpoItfRepository;
    @Autowired
    private RcwlSodrHzpoHeaderRepository rcwlSodrHzpoHeaderRepository;
    @Autowired
    private RcwlSodrHzpoLineRepository rcwlSodrHzpoLineRepository;
    @Autowired
    LovFeignClient lovFeignClient;
    @Override
    public RcwlSodrHzpoReturnDTO handleItfData(RcwlSodrHzpoHeaderDTO itfData) {
        //校验头数据
        //Tenant_id：hpfm_group表中core_flag为1的tanant_id
        Long tenantId = rcwlSodrHzpoItfRepository.getTenantId();
        itfData.setTenantId(tenantId);
        //判断接口传入的状态代码是否在系统中存在
        boolean existsFlag = false;
        //unified_social_code：关联sslm_supplier_basic中unified_social_code，校验必须存在
        Long existsCount = rcwlSodrHzpoItfRepository.checkUnifiedSocialCode(tenantId,itfData.getUnifiedSocialCode());
        Assert.isTrue(!existsCount.equals(0L), MessageAccessor.getMessage("supplier.not.exist", LanguageHelper.locale()).desc());

        //校验行数据
        List<RcwlSodrHzpoLineDTO> rcwlSodrHzpoLineDTOList = itfData.getData();
        List<String> skuCodes = new ArrayList<>();
        rcwlSodrHzpoLineDTOList.forEach(poLine ->{
            skuCodes.add(poLine.getSkuNo());
            Long noCount = rcwlSodrHzpoItfRepository.checkSkuNode(tenantId,poLine.getSkuNo());
            Assert.isTrue(!noCount.equals(0L),MessageAccessor.getMessage("smpc.error.sku.not.exists",LanguageHelper.locale()).desc());
        });
        //根据skuCode查找商品名称和品类
        List<RcwlSkuInfoDTO> skuInfos = rcwlSodrHzpoItfRepository.querySkuInfo(tenantId,skuCodes);
        skuInfos.forEach(skuInfo -> {
            rcwlSodrHzpoLineDTOList.forEach(itfInfo->{
                if(itfInfo.getSkuNo().equals(skuInfo.getSkuCode())){
                    LOGGER.info("接口中的数据:{}",itfInfo.toString());
                    LOGGER.info("数据库中的数据:{}",skuInfo.toString());
                    //报文中的商品名称/品类如果不为空并且与数据库中查询到的值不一样的话则报错
                    Assert.isTrue(itfInfo.getSkuName() == null || itfInfo.getSkuName().equals(skuInfo.getSkuName()),MessageAccessor.getMessage("small.name.of.the.product.is.inconsistent",LanguageHelper.locale()).desc());
                    Assert.isTrue(itfInfo.getSkuCategoryCode() == null || itfInfo.getSkuCategoryCode().equals(skuInfo.getCategoryCode()),MessageAccessor.getMessage("error.item_category_not_exists",LanguageHelper.locale()).desc());
                    itfInfo.setSkuName(skuInfo.getSkuName());
                    itfInfo.setSkuCategoryCode(skuInfo.getCategoryCode());
                }
            });
        });
        return createOrUpdatePo(tenantId,itfData);
    }

    RcwlSodrHzpoReturnDTO createOrUpdatePo(Long tenantId,RcwlSodrHzpoHeaderDTO itfData){
        //查找头信息，查到就更新，否则新增
        List<RcwlSodrHzpoLine> addList = new ArrayList<>();
        List<RcwlSodrHzpoLine> updateList = new ArrayList<>();
        RcwlSodrHzpoHeader headQueryRecord = new RcwlSodrHzpoHeader();
        headQueryRecord.setTenantId(tenantId);
        headQueryRecord.setPoNum(itfData.getPoNum());
        RcwlSodrHzpoHeader poHeader = rcwlSodrHzpoHeaderRepository.selectOne(headQueryRecord);
        Boolean poNewFlag = poHeader == null;
        //新建订单
        //RcwlSodrHzpoHeader poHeader = new RcwlSodrHzpoHeader();
        if(poNewFlag){
            RcwlSodrHzpoHeader newPoHeader = new RcwlSodrHzpoHeader();
            BeanUtils.copyProperties(itfData,newPoHeader);
            rcwlSodrHzpoHeaderRepository.insertSelective(newPoHeader);
            LOGGER.info("生成的单据:{}",newPoHeader.toString());
            Long poHeaderId = newPoHeader.getPoHeaderId();
            itfData.getData().forEach(line->{
                line.setTenantId(tenantId);
                line.setPoHeaderId(poHeaderId);
                RcwlSodrHzpoLine addLine = new RcwlSodrHzpoLine();
                BeanUtils.copyProperties(line,addLine);
                addList.add(addLine);
            });
            if(CollectionUtils.isNotEmpty(addList)){
                rcwlSodrHzpoLineRepository.batchInsert(addList);
            }
           return RcwlSodrHzpoReturnDTO.success();
        }
        else{
            itfData.setPoHeaderId(poHeader.getPoHeaderId());
            itfData.setObjectVersionNumber(poHeader.getObjectVersionNumber());
            //更新订单
            BeanUtils.copyProperties(itfData,poHeader);
            rcwlSodrHzpoHeaderRepository.updateByPrimaryKeySelective(poHeader);
            //检查行数据
            RcwlSodrHzpoLine lineQueryRecord = new RcwlSodrHzpoLine();
            itfData.getData().forEach(line->{
                lineQueryRecord.setTenantId(tenantId);
                lineQueryRecord.setPoHeaderId(poHeader.getPoHeaderId());
                lineQueryRecord.setLineNum(line.getLineNum());
                RcwlSodrHzpoLine selectLine = rcwlSodrHzpoLineRepository.selectOne(lineQueryRecord);
                Boolean lineNewFlag = selectLine == null;
                RcwlSodrHzpoLine poLine = new RcwlSodrHzpoLine();

                if(lineNewFlag){
                    BeanUtils.copyProperties(line,poLine);
                    //新增行
                    poLine.setTenantId(tenantId);
                    poLine.setPoHeaderId(poHeader.getPoHeaderId());
                    addList.add(poLine);
                }else{
                    LOGGER.info("line:{}",line);
                    line.setPoHeaderId(selectLine.getPoHeaderId());
                    line.setPoLineId(selectLine.getPoLineId());
                    line.setObjectVersionNumber(selectLine.getObjectVersionNumber());
                    line.setTenantId(selectLine.getTenantId());
                    BeanUtils.copyProperties(line,poLine);
                    //更新行
                    updateList.add(poLine);
                }
            });
            if(CollectionUtils.isNotEmpty(addList)){
                rcwlSodrHzpoLineRepository.batchInsert(addList);
            }
            if(CollectionUtils.isNotEmpty(updateList)){
                LOGGER.info("poLine:{}",updateList);
                rcwlSodrHzpoLineRepository.batchUpdateByPrimaryKey(updateList);
            }
            return RcwlSodrHzpoReturnDTO.success();
        }
    }

}
