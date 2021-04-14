package org.srm.purchasecooperation.cux.order.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.purchasecooperation.cux.pr.utils.constant.PrConstant;
import org.srm.purchasecooperation.finance.api.dto.CompanyDTO;
import org.srm.purchasecooperation.finance.infra.mapper.CompanyMapper;
import org.srm.purchasecooperation.order.api.dto.PoHeaderAccordingToLineOfReferenceDTO;
import org.srm.purchasecooperation.order.app.service.impl.PoLineServiceImpl;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.vo.PoHeaderAccordingToLineOfReferenceVO;
import org.srm.purchasecooperation.pr.domain.entity.PrLineSupplier;
import org.srm.purchasecooperation.pr.domain.repository.PrLineSupplierRepository;
import org.srm.web.annotation.Tenant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/4/14 16:29
 * @version:1.0
 */
@Tenant
@Service
public class RcwlPoLineServiceImpl extends PoLineServiceImpl {
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private PrLineSupplierRepository prLineSupplierRepository;
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public Page<PoHeaderAccordingToLineOfReferenceVO> selectAccordingToLineOfReference(PageRequest pageRequest, PoHeaderAccordingToLineOfReferenceDTO poHeaderAccordingToLineOfReferenceDTO) {
        boolean flag = String.valueOf(BaseConstants.Flag.YES).equals(this.customizeSettingHelper.queryBySettingCode(poHeaderAccordingToLineOfReferenceDTO.getTenantId(), "010903"));
        if (flag) {
            return new Page();
        } else {
            poHeaderAccordingToLineOfReferenceDTO.assignFilter(this.customizeSettingHelper);
            poHeaderAccordingToLineOfReferenceDTO.executionStrategyFilter(this.customizeSettingHelper);
            poHeaderAccordingToLineOfReferenceDTO.setCurrentDate(new Date());
            Page<PoHeaderAccordingToLineOfReferenceVO> page = PageHelper.doPageAndSort(pageRequest, () -> {
                return  this.poLineRepository.selectAccordingToLineOfReference(poHeaderAccordingToLineOfReferenceDTO);
            });
            List<PoHeaderAccordingToLineOfReferenceVO> content = page.getContent();
            List<PoHeaderAccordingToLineOfReferenceVO> list = new ArrayList<>();
            content.forEach(e->{
                //当申请类型为“标准申请”STANDARD“项目申请”PROJECT“紧急申请”EMERGENCY“零星申请”SPORADIC  时,剩余可下单数量不等于本次下单数量时，这条数据不显示。
                Set<String> prTypeCodes = new TreeSet<>();
                prTypeCodes.add(PrConstant.PrType.PR_TYPE_STANDARD);
                prTypeCodes.add(PrConstant.PrType.PR_TYPE_EMERGENCY);
                prTypeCodes.add(PrConstant.PrType.PR_TYPE_PROJECT);
                prTypeCodes.add(PrConstant.PrType.PR_TYPE_SPORADIC);
                String prTypeCode = ObjectUtils.isEmpty(e.getPrTypeCode()) ? "" : e.getPrTypeCode();
                if (prTypeCodes.contains(prTypeCode)){
                    if(e.getRestPoQuantity().compareTo(e.getThisOrderQuantity())==1){
                        list.add(e);
                    }
                }else{
                    list.add(e);
                }
            });
            page.setContent(list);
            this.queryDefaultSupplier(poHeaderAccordingToLineOfReferenceDTO, content);
            List<Long> prLineIds = (List) content.stream().map(PoHeaderAccordingToLineOfReferenceVO::getPrLineId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(prLineIds)) {
                Page p = new Page();
                p.setContent(Collections.emptyList());
                return p;
            } else {
                List<PrLineSupplier> prLineSuppliers = this.prLineSupplierRepository.selectByCondition(Condition.builder(PrLineSupplier.class).andWhere(Sqls.custom().andIn("prLineId", prLineIds)).build());
                if (CollectionUtils.isEmpty(prLineSuppliers)) {
                    return page;
                } else {
                    Map<Long, List<PrLineSupplier>> supplierMap = (Map) prLineSuppliers.stream().collect(Collectors.groupingBy(PrLineSupplier::getPrLineId));
                    page.forEach((v) -> {
                        List<PrLineSupplier> suppliers = (List) supplierMap.get(v.getPrLineId());
                        if (!CollectionUtils.isEmpty(suppliers)) {
                            List<CompanyDTO> companyDTOS = this.companyMapper.queryCompanyCode(v.getPrLineId());
                            String supplierCode = (String) companyDTOS.stream().map((company) -> {
                                return StringUtils.isNotBlank(company.getSupplierCode()) ? company.getSupplierCode() : company.getCompanyCode();
                            }).distinct().collect(Collectors.joining(","));
                            v.setSupplierCode(supplierCode);
                            v.setSupplierList(suppliers);
                        }
                    });
                    Integer sapFlag = poHeaderAccordingToLineOfReferenceDTO.getSapFlag();
                    List<PoHeaderAccordingToLineOfReferenceVO> sapList = new ArrayList();
                    List<PoHeaderAccordingToLineOfReferenceVO> noSapList = new ArrayList();
                    page.forEach((prLine) -> {
                        String attributeVarchar1 = prLine.getAttributeVarchar1();
                        if (StringUtils.isNotBlank(attributeVarchar1)) {
                            sapList.add(prLine);
                        } else {
                            noSapList.add(prLine);
                        }

                    });
                    if (Objects.isNull(sapFlag)) {
                        return page;
                    } else {
                        if (sapFlag == 1) {
                            page.setContent(sapList);
                        } else if (sapFlag == 0) {
                            page.setContent(noSapList);
                        }

                        return page;
                    }
                }
            }
        }
    }
}
