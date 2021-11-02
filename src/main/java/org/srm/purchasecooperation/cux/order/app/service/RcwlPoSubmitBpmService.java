package org.srm.purchasecooperation.cux.order.app.service;


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

}
