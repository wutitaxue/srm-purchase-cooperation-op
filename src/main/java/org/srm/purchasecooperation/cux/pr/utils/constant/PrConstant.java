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
     * 标准申请     STANDARD
     * 项目申请    PROJECT
     * 紧急申请    EMERGENCY
     * 零星申请    SPORADIC
     * 计划申请  PLAN
     */
    interface PrType {
        String PR_TYPE_STANDARD = "STANDARD";
        String PR_TYPE_PROJECT = "PROJECT";
        String PR_TYPE_EMERGENCY = "EMERGENCY";
        String PR_TYPE_SPORADIC = "SPORADIC";
        String PR_TYPE_PLAN = "PLAN";
    }

    interface BpmCodes {
        String REQ_SRC_SYS_CODE = "RCWL_BPM_REQSRCSYS";
        String REQ_TAR_CODE = "RCWL_BPM_REQTARSYS";
        String REQ_URL_CODE = "RCWL_BPM_URLIP";
        String ZYUN_URL_CODE = "RCWL_PR_TO_BPM_URL";

        String PR_STAGING = "SCUX.RCWL.PR_STAGING";
        String JH_BIDDING = "SCUX.RCWL.SCEC.JH_BIDDING";
        String PR_FORMAT = "SCUX.RCWL.PR_FORMAT";

        String BUDGET_ACCOUNT = "SMDM.BUDGET_ACCOUNT";
        String COST_CENTER = "SPRM.COST_CENTER";
        String WBS = "SMDM.WBS";
    }

    interface PrSourcePlatform {
        String SRM = "SRM";
        String SHOP = "SHOP";
        String ERP = "ERP";
        String CATALOGUE = "CATALOGUE";
        String E_COMMERCE = "E-COMMERCE";
    }

    interface ProcessCode {
        String SPUC_ERP_SUBMIT_DOC = "SPUC_ERP_SUBMIT_DOC";
        String SPUC_CATALOG_SUBMIT_DOC = "SPUC_CATALOG_SUBMIT_DOC";
        String SPUC_EC_SUBMIT_DOC = "SPUC_EC_SUBMIT_DOC";
        String SPUC_SRM_SUBMIT_DOC = "SPUC_SRM_SUBMIT_DOC";
    }

    interface ApprovalCode {
        String WFL = "WFL";
    }
}