package org.srm.purchasecooperation.cux.asn.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.asn.domain.repository.RcwlAsnHeaderRepository;
import org.srm.purchasecooperation.cux.asn.domain.vo.RcwlAsnHeaderVO;
import org.srm.purchasecooperation.cux.asn.infra.mapper.RcwlAsnHeaderMapper;
import org.srm.purchasecooperation.asn.infra.repository.impl.AsnHeaderRepositoryImpl;
import org.srm.web.annotation.Tenant;

@Component
@Tenant("SRM-RCWL")
public class RcwlAsnHeaderRepositoryImpl extends AsnHeaderRepositoryImpl implements RcwlAsnHeaderRepository {

    @Autowired
    private RcwlAsnHeaderMapper rcwlAsnHeaderMapper;


       public RcwlAsnHeaderVO selectRcwlAsnHeaderByHeaderId(Long asnHeaderId) {
        RcwlAsnHeaderVO rcwlAsnHeaderVO = this.rcwlAsnHeaderMapper.selectRcwlAsnHeaderByHeaderId(asnHeaderId);
        if (rcwlAsnHeaderVO == null) {
            rcwlAsnHeaderVO = new RcwlAsnHeaderVO();
        }

        return rcwlAsnHeaderVO;
    }
}
