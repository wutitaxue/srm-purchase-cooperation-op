package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlMyPoHeaderMapper;
import org.srm.purchasecooperation.order.api.dto.PoHeaderDetailDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.infra.repository.impl.PoHeaderRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.util.HashSet;
import java.util.Set;

@Component
@Tenant("SRM-RCWL")
public class RcwlMyPoHeaderRepositoryImpl extends PoHeaderRepositoryImpl {

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
            return this.rcwlMyPoHeaderMapper.rcwlSelectPoHeader(poHeader);
        });
    }

    @Override
    public PoHeaderDetailDTO selectHeaderdetail(Long tenantId, Long poHeaderId) {
        PoHeaderDetailDTO poHeaderDetailDTO = this.rcwlMyPoHeaderMapper.rcwlSelectHeaderdetail(tenantId, poHeaderId);
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
}
