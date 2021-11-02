package org.srm.purchasecooperation.cux.order.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoSubmitBpmService;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;

/**
 * @author pengxu.zhi@hand-china.com
 * @desc ...
 * @date 2021-11-02 15:26:14
 */
@Service
public class RcwlPoHeaderBpmServiceImpl implements RcwlPoSubmitBpmService {

    @Autowired
    private PoHeaderMapper poHeaderMapper;

    @Override
    public void rcwlSubmitBpmSuccessed(Long tenantId, String poNum, String procInstID, String newProcURL) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);
        //SODR.PO_STATUS
        poHeaderCondition.setAttributeVarchar36(procInstID);
        poHeaderCondition.setAttributeVarchar37(newProcURL);
        poHeader.setStatusCode("SUBMITTED");
        poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode","attributeVarchar36","attributeVarchar37"});
    }

    @Override
    public void rcwlSubmitBpmApproved(Long tenantId, String poNum) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);
        //SODR.PO_STATUS
        poHeader.setStatusCode("APPROVED");
        poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode"});
    }

    @Override
    public void rcwlSubmitBpmReject(Long tenantId, String poNum) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);
        //SODR.PO_STATUS
        poHeader.setStatusCode("REJECTED");
        poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode"});
    }

}
