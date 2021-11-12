package org.srm.purchasecooperation.cux.order.app.service;


import org.srm.purchasecooperation.order.domain.entity.PoHeader;

public interface RcwlPoSubmitBpmService {

    /**
     * 提交成功
     * @param tenantId
     * @param poNum
     */
    void rcwlSubmitBpmSuccessed(Long tenantId, String poNum, String procInstID, String newProcURL);

    /**
     * 审批拒绝
     * @param tenantId
     * @param poNum
     */
    void rcwlSubmitBpmReject(Long tenantId, String poNum);

    /**
     * 审批通过
     * @param tenantId
     * @param poNum
     */
    void rcwlSubmitBpmApproved(Long tenantId, String poNum);

    /**
     * 审批通过发布确认
     * @param tenantId
     * @param poHeader
     */
    void approveProcess(Long tenantId, PoHeader poHeader);

}
