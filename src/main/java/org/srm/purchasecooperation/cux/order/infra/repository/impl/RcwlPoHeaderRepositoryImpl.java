package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlPoHeaderMapper;
import org.srm.purchasecooperation.order.api.dto.PoHeaderDetailDTO;
import org.srm.purchasecooperation.order.infra.repository.impl.PoHeaderRepositoryImpl;
import org.srm.web.annotation.Tenant;

@Component
@Tenant("SRM-RCWL")
public class RcwlPoHeaderRepositoryImpl extends PoHeaderRepositoryImpl {

    @Autowired
    private RcwlPoHeaderMapper rcwlPoHeaderMapper;

    @Override
    public PoHeaderDetailDTO selectHeaderdetail(Long tenantId, Long poHeaderId) {
        PoHeaderDetailDTO poHeaderDetailDTO = this.rcwlPoHeaderMapper.rcwlSelectHeaderdetail(tenantId, poHeaderId);
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
