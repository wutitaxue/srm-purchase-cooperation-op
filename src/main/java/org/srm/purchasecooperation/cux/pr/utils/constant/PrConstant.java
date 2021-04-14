package org.srm.purchasecooperation.cux.pr.utils.constant;

/**
 * @description: 常量接口
 * @author:yuanping.zhang
 * @createTime:2021/3/26 17:10
 * @version:1.0
 */
public interface PrConstant {
    /**
     * SPUC.PR_DEMAND_TYPE 申请类型值集
     *       标准申请     STANDARD
     *       项目申请    PROJECT
     *       紧急申请    EMERGENCY
     *       零星申请    SPORADIC
     *       计划申请  PLAN
     */
    interface PrType{
        String PR_TYPE_STANDARD = "STANDARD";
        String PR_TYPE_PROJECT = "PROJECT";
        String PR_TYPE_EMERGENCY = "EMERGENCY";
        String PR_TYPE_SPORADIC = "SPORADIC";
        String PR_TYPE_PLAN = "PLAN";
    }
}
