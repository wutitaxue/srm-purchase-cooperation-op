package org.srm.purchasecooperation.cux.acp.infra.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 13:53
 * @version:1.0
 */
public class RCWLAcpConstant {
    public final static String TENANT_NUMBER = "SRM-RCWL";
    /*大象云的URL前缀*/
    public final static String URL = "http://10.199.201.99:9000/bx2th/bxsdk/web/authorize?data=";

    /*大象云的URL前缀*/
    public final static String SRCSTR = "POST/bx2th/openApi/invoice/dii?";

    /*大象云的授权ID*/
    public final static String APPSECID = "d4bf814c02abb801a2a2b6742a6d140a";


    /*大象云的授权*/
    public final static String APPSECKEY = "116837c1750110f87f285feb2148ad2c";

    /*供应商*/
    public final static String CGXT_SUPPLIER = "CGXT_SUPPLIER";

    /*融创内部员工*/
    public final static String CGXT_SUNAC = "CGXT_SUNAC";

    /*.供方形式发票*/
    public final static String INVOICE_TYPE_VENDOR = "01";

    /*购方形式发票*/
    public final static String INVOICE_TYPE_COMPANY = "02";

    /*付款申请单*/
    public final static String INVOICE_TYPE_PAY = "03";

    /**
     * 错误类型定义
     */
    public static class ERROR {
        /*单据类型错误*/
        public final static String INVOICE_TYPE_ERROR = "单据类型错误";

        /*发票来源错误*/
        public final static String SYSTEM_SOURCE_ERROR = "来源错误";

        //        发票税务行为0
        public final static String INVOCIE_TAX_LINE_ERROR = "发票税务行为0";
    }

    /**
     * 同步大象慧云的单据状态
     * 0：未审批
     * 1：审批中
     * 2：审批完成
     * 3：审批驳回
     * 4：已删除
     */
    public static class AcpInvoiceStatus {
        public static final Map<String, String> status = new HashMap<String, String>();

        static {
            status.put("NEW", "0");
            status.put("SUBMITTED", "1");
            status.put("APPROVED", "2");
            status.put("RETURN_TO_APPROVE", "3");
            status.put("CANCELLED", "4");
            status.put("RETURN_TO_SUBMIT", "3");
            status.put("RETURN_TO_VENDOR", "3");
        }

    }

    /**
     * 大象的globalInfo数据项说明
     */
    public static class AcpInvoiceGlobalInfo {
        /*大象云的应用标识*/
        public final static String APPID = "BXSDK";

        public final static String VERSION = "v1.0";

        public final static String INTERFACECODE = "APPROVAL_STATE_UPDATE";
    }

    /**
     * 大象的接口定义
     */
    public static class AcpInvoiceElephantInterfaceInfo{
        //命名空间
        public final static String NAMESPACE = "SRM-RCWL";
        //服务编码
        public final static String SERVERCODE = "RCWL.ACP.INVOICE.ELEPHANT";
        //接口编码
        public final static String INSERFACECODE = "RCWL.ACP.INVOICE.ELEPHANT";

    }

}
