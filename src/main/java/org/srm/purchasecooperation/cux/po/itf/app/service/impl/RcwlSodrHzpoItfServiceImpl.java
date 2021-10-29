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
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlSodrHzpoHeaderDTO;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlSodrHzpoLineDTO;
import org.srm.purchasecooperation.cux.po.itf.app.service.RcwlSodrHzpoItfService;
import org.srm.purchasecooperation.cux.po.itf.domain.repository.RcwlSodrHzpoItfRepository;

import java.util.ArrayList;
import java.util.List;

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
    public RcwlSodrHzpoHeader handleItfData(RcwlSodrHzpoHeaderDTO itfData) {
        //校验头数据
        //Tenant_id：hpfm_group表中core_flag为1的tanant_id
        Long tenantId = rcwlSodrHzpoItfRepository.getTenantId();
        itfData.setTenantId(tenantId);
        //判断接口传入的状态代码是否在系统中存在
        boolean existsFlag = false;
        List<LovValueDTO> lovValueDTOLists = lovFeignClient.queryLovValue("SCUX_RCWL_HZPO_STATUS",tenantId);
        for(LovValueDTO lovValueDTOList:lovValueDTOLists ){
            if(lovValueDTOList.getValue().equals(itfData.getStatusCode())){
                existsFlag = true;
            }
        }
        Assert.isTrue(existsFlag,MessageAccessor.getMessage("error.sodrhzpo_status_error", LanguageHelper.locale()).desc());

        //unified_social_code：关联sslm_supplier_basic中unified_social_code，校验必须存在
        Long existsCount = rcwlSodrHzpoItfRepository.checkUnifiedSocialCode(tenantId,itfData.getUnifiedSocialCode());
        Assert.isTrue(!existsCount.equals(0L), MessageAccessor.getMessage("supplier.not.exist", LanguageHelper.locale()).desc());

        //校验行数据
        List<RcwlSodrHzpoLineDTO> rcwlSodrHzpoLineDTOList = itfData.getData();
        rcwlSodrHzpoLineDTOList.forEach(poLine ->{
            Long categoryCodeCount = rcwlSodrHzpoItfRepository.checkSkuCategoryCode(tenantId,poLine.getSkuCategoryCode());
            Long noCount = rcwlSodrHzpoItfRepository.checkSkuNode(tenantId,poLine.getSkuCategoryCode());
            Assert.isTrue(!categoryCodeCount.equals(0L), MessageAccessor.getMessage("error.product.category.does.not.exist" , LanguageHelper.locale()).desc());
            Assert.isTrue(!noCount.equals(0L),MessageAccessor.getMessage("smpc.error.sku.not.exists",LanguageHelper.locale()).desc());

        });
        return createOrUpdatePo(tenantId,itfData);
    }

    RcwlSodrHzpoHeader createOrUpdatePo(Long tenantId,RcwlSodrHzpoHeaderDTO itfData){
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
           return newPoHeader;
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
                Boolean lineNewFlag = selectLine.getPoLineId() == null;
                RcwlSodrHzpoLine poLine = new RcwlSodrHzpoLine();

                if(lineNewFlag){
                    BeanUtils.copyProperties(line,poLine);
                    //新增行
                    poLine.setTenantId(tenantId);
                    poLine.setPoHeaderId(poHeader.getPoHeaderId());
                    addList.add(poLine);
                }else{
                    line.setPoHeaderId(selectLine.getPoHeaderId());
                    line.setPoLineId(selectLine.getPoLineId());
                    line.setObjectVersionNumber(selectLine.getObjectVersionNumber());
                    BeanUtils.copyProperties(line,poLine);
                    //更新行
                    updateList.add(poLine);
                }
            });
            if(CollectionUtils.isNotEmpty(addList)){
                rcwlSodrHzpoLineRepository.batchInsert(addList);
            }
            if(CollectionUtils.isNotEmpty(updateList)){
                rcwlSodrHzpoLineRepository.batchUpdateByPrimaryKey(updateList);
            }
            return poHeader;
        }
    }

}
