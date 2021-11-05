package org.srm.purchasecooperation.cux.pr.infra.constant;


public class RCWLConstants {
    private RCWLConstants() {
    }

    /**
     * 租户编码
     */
    public static final String TENANT_CODE = "SRM-RCWL";


    /**
     * 接口初始化字段值
     */
    public static class InterfaceInitValue {
        public final static String TYPE = "billdata";
        public final static String DEFINE_CODE = "KNYSZY";
        public final static String OPEN_ID = "X19kZWZhdWx0X3RlbmFudF9fI0NHR1gjY2dneA==JqYbz==";
        public final static String NAMESPACE = "SRM-RCWL";
        public final static String SERVER_CODE = "SRM-RCWL";
        public final static String INTERFACE_CODE = "RCWL_BUDGET_TOKEN_GET";
        public final static String CODE = "0";
    }
    /**
     * bpm审批回传标识
     */
    public static class BPMApproveFlag{
       public final static String APPROVED = "1";
       public final static String REJECTED = "0";
    }
    /**
     * 通用
     */
    public static class Common{
        public final static Integer IS  = 1;
    }
}
