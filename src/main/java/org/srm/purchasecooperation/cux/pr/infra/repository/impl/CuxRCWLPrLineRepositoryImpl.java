package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.hzero.core.base.AopProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.cux.pr.infra.mapper.CuxRCWLPrLineMapper;
import org.srm.purchasecooperation.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.pr.app.service.PrLineService;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.purchasecooperation.pr.infra.repository.impl.PrLineRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.util.List;

@Component
@Tenant(RCWLConstants.TENANT_CODE)
public class CuxRCWLPrLineRepositoryImpl extends PrLineRepositoryImpl implements AopProxy<PrLineService> {

    @Autowired
    private CuxRCWLPrLineMapper cuxRCWLPrLineMapper;


    @Override
    public List<PrLineVO> pageAssignList(PrLineDTO prLineDTO) {
        return this.cuxRCWLPrLineMapper.pageAssignList(prLineDTO);
    }


}
