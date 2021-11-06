package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlMyPoHeaderMapper;
import org.srm.purchasecooperation.order.api.dto.PoHeaderDetailDTO;
import org.srm.purchasecooperation.order.api.dto.PoHeaderSingleReferenceDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.vo.PoHeaderSingleReferenceVO;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.order.infra.repository.impl.PoHeaderRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Tenant("SRM-RCWL")
public class RcwlMyPoHeaderRepositoryImpl extends PoHeaderRepositoryImpl {

    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private RcwlMyPoHeaderMapper rcwlMyPoHeaderMapper;

    @Override
    public Page<PoHeader> pagePoHeader(PoHeader poHeader, PageRequest pageRequest) {
        poHeader.setNowDate(LocalDate.now().toDate());
        Set<String> statusSet = new HashSet();
        if (poHeader.getConfirmedFlag() != null && poHeader.getConfirmedFlag() == 1 && poHeader.getEvaluationFlag() != null && poHeader.getEvaluationFlag() == 0) {
            ((Set)statusSet).add("CLOSED");
            ((Set)statusSet).add("CONFIRMED");
        } else if (poHeader.getStatusCodes() != null) {
            statusSet = poHeader.getStatusCodes();
        } else if (!StringUtils.isEmpty(poHeader.getStatusCode())) {
            ((Set)statusSet).add(poHeader.getStatusCode());
        }

        poHeader.setStatusCodes((Set)statusSet);
        return PageHelper.doPageAndSort(pageRequest, () -> {
            Page<PoHeader> page = (Page<PoHeader>) poHeaderMapper.selectPoHeader(poHeader);
            List<PoHeader> collect = page.stream().map(m -> {
                m.setAttributeVarchar40(rcwlMyPoHeaderMapper.rcwlSelect(m.getPoHeaderId()));
                return m;
            }).collect(Collectors.toList());
            page.setContent(collect);
            return page;
        });
    }

    @Override
    public PoHeaderDetailDTO selectHeaderdetail(Long tenantId, Long poHeaderId) {
//        PoHeaderDetailDTO poHeaderDetailDTO = this.poHeaderMapper.selectHeaderdetail(tenantId, poHeaderId);
        PoHeaderDetailDTO poHeaderDetailDTO = this.rcwlMyPoHeaderMapper.rcwlSelectHeaderdetail(tenantId, poHeaderId);
        poHeaderDetailDTO.setAttributeVarchar40(rcwlMyPoHeaderMapper.rcwlSelect(poHeaderDetailDTO.getPoHeaderId()));
        PoHeaderDetailDTO poHeaderDetailDTO1 = this.selectHeaderdetailAdress(tenantId, poHeaderId);
        if (poHeaderDetailDTO1 != null) {
            if (StringUtils.isNotEmpty(poHeaderDetailDTO1.getShipToLocContName())) {
                poHeaderDetailDTO.setShipToLocContName(poHeaderDetailDTO1.getShipToLocContName());
            }

            if (StringUtils.isNotEmpty(poHeaderDetailDTO1.getShipToLocTelNum())) {
                poHeaderDetailDTO.setShipToLocTelNum(poHeaderDetailDTO1.getShipToLocTelNum());
            }
        }

        return poHeaderDetailDTO;
    }

    @Override
    @ProcessLovValue
    public List<PoHeaderSingleReferenceVO> queryReferPrHeaderSummary(Long tenantId, PoHeaderSingleReferenceDTO referenceDTO) {
        return this.rcwlMyPoHeaderMapper.selectPrHeader(tenantId, referenceDTO);
    }
}
