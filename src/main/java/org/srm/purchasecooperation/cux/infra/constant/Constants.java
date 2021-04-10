package org.srm.purchasecooperation.cux.infra.constant;

/**
 * @author 15640
 */
public class Constants {


    private Constants() {
    }
    /**
     * 采购计划状态
     */
    public static class PlanHeaderState {
        public static final String NEW = "NEW";
        public static final String NOT = "NOT";
        public static final String ALREADY = "ALREADY";
        public static final String CANCEL = "CANCEL";
        private PlanHeaderState() {
        }
    }
    /**
     * 编码规则
     */
    public static class  Encoding{
        /**
         * 采购计划编号
         */
        public static final String SPRM_PLAN_HEADER_NUM = "SPRM.PLAN_HEADER";
    }
    public static class ExpertConstants {
        public static final String USER_ID = "userId";
        public static final String ORGANIZATION_ID = "organizationId";
        public static final String SOURCE_HEADER_ID = "sourceHeaderId";
        public static final String SOURCE_FROM = "sourceFrom";
        public static final String SEQUENCE_NUM = "SEQUENCE_NUM";
        public static final String EXPERT_STATUS = "expertStatus";
        public static final String TEAM = "team";
        public static final String LEADER_FLAG = "leaderFlag";
        public static final String EVALUATE_LEADER_FLAG = "evaluateLeaderFlag";
        public static final String CURRENT_SEQUENCE_NUM = "currentSequenceNum";

        private ExpertConstants() {
        }
    }

    public static class QuotationTemplateLatestFlag {
        public static final String LATEST = "1";
        public static final String HISTORY = "0";

        private QuotationTemplateLatestFlag() {
        }
    }

    public static class QuotationTemplateStatus {
        public static final String NEW = "NEW";
        public static final String RELEASED = "RELEASED";
        public static final String UPDATED = "UPDATED";

        private QuotationTemplateStatus() {
        }
    }

    public static class ResponseStatus {
        public static final String SUCCESS = "SUCCESS";
        public static final String ERROR = "ERROR";

        private ResponseStatus() {
        }
    }

    public static class MessageCodeConstants {
        public static final String RFX_RELEASE = "SSRC.RFX.RELEASE.STATION";
        public static final String RFX_RELEASE_FASTBIDDING = "SSRC.RFX.RELEASE.FASTBIDDING";
        public static final String RFX_CHECK_SUPPLIER = "SSRC.RFX_CHECK_SUPPLIER";
        public static final String RFX_ROUND_QUOTATION = "SSRC.RFX_ROUND_QUOTATION";
        public static final String QUOTATION = "SSRC.SUPPLIER_QUOTATION";
        public static final String BARGAIN = "SSRC.ENQUIRY_BARGAIN";
        public static final String BARGAIN_ALL = "SSRC.BARGAIN_ALL";
        public static final String BARGAIN_OFFLINE = "SSRC.BARGAIN_OFFLINE";
        public static final String TIME_ADJUSTED_EMAIL = "SSRC.TIME_ADJUSTED";
        public static final String SING_IN = "SSRC.RFX.SING_IN";
        public static final String QUOTATION_START = "SSRC.RFX.QUOTATION_START";
        public static final String ESTIMATED_TIME_ADJUSTED = "SSRC.ESTIMATED_TIME_ADJUSTED";
        public static final String RFX_CLOSE = "SSRC.RFX_CLOSE";
        public static final String RFX_RESUME = "SSRC.RFX_RESUME";
        public static final String RFX_PAUSE = "SSRC.RFX_PAUSE";
        public static final String RFX_PW = "SSRC.RFX_PASSWORD";
        public static final String RFX_NONE_PW = "SSRC.RFX_NONE_PASSWORD";
        public static final String RFX_EVALUATE = "SSRC.RFX_EVALUATE";
        public static final String RFX_EVALUATE_AGAIN = "SSRC.RFX_EVALUATE_AGAIN";
        public static final String RFX_EVALUATE_SUMMARY = "SSRC.RFX_EVALUATE_SUMMARY";
        public static final String RFX_PRE_EVALUATION = "SSRC.RFX_PRE_EVALUATION";
        public static final String RFX_CLARIFY_NOTIFY_LEADER = "SSRC.RFX_CLARIFY_NOTIFY_LEADER";
        public static final String RFX_CLARIFY_NOTIFY_EXPERT = "SSRC.RFX_CLARIFY_NOTIFY_EXPERT";
        public static final String RFX_CLARIFY_NOTIFY_SUPPLIER = "SSRC.RFX_CLARIFY_NOTIFY_SUP";
        public static final String RFX_CLARIFY_SUPPLIER = "SSRC.RFX_CLARIFY_SUPPLIER";
        public static final String SSRC_PRICE_CLARIFY_NOTIFY = "SSRC_PRICE_CLARIFY_NOTIFY";
        public static final String RFX_QUOTATION_END_SUPPLIER = "SSRC.RFX_QUO_END_SUPPLIER";
        public static final String RFX_QUOTATION_END_BUYER = "SSRC.RFX_QUOTATION_END_BUYER";
        public static final String RFX_LACK_QUOTED = "SSRC.RFX_LACK_QUOTED";
        public static final String RFX_EXPERT_CHECK = "SSRC.RFX_EXPERT_CHECK";
        public static final String BID_WRITE_ABILITY_NOTICE = "SSRC.BID_WRITE_ABILITY_NOTICE";
        public static final String RFX_WRITE_ABILITY_NOTICE = "SSRC.RFX_WRITE_ABILITY_NOTICE";
        public static final String RFX_ABANDON = "SSRC.RFX_ABANDON";

        private MessageCodeConstants() {
        }
    }

    public static class MessageConstants {
        public static final String COMPANY_ID = "companyId";
        public static final String ORGANIZATION_ID = "organizationId";
        public static final String RFX_HEADER_ID = "rfxHeaderId";
        public static final String SOURCE_FROM = "sourceFrom";
        public static final String SOURCE_HEADER_ID = "sourceHeaderId";
        public static final String COMPANY_NAME = "companyName";
        public static final String RFX_NUMBER = "rfxNumber";
        public static final String RFX_TITLE = "rfxTitle";
        public static final String RFX_START_TIME = "rfxStartTime";
        public static final String ESTIMATED_START_TIME = "estimatedStartTime";
        public static final String RFX_CHECK_SUBMIT_TIME = "rfxCheckSubmitTime";
        public static final String PREQUAL_CLOSING_DATE = "prequalCloseDate";
        public static final String FEEDBACK_END_TIME = "feedEndTime";
        public static final String ADJUST_TIME_REASON = "adjustTimeReason";
        public static final String TERMINATED_REASON = "terminatedReason";
        public static final String REASON = "reason";
        public static final String PW = "password";
        public static final String USER_ID = "userId";
        public static final String TEAM = "team";
        public static final String SUPPLIER_TENANT_ID = "supplierTenantId";
        public static final String SUPPLIER_COMPANY_ID = "supplierCompanyId";
        public static final String RFX_PARTICIPATE_URL = "rfxParticipateUrl";
        public static final String RFX_PARTICIPATE_LINK = "<a href = \"http://";
        public static final String CURRENT_TIME = "currentTime";
        public static final String SOURCE_ADDRESS = "sourceAddress";
        public static final String RFX_QUOTATION_ADDRESS = "rfxQuotationAddress";
        public static final String TIME_REMAINING = "timeRemaining";
        public static final String SENT_MESSAGE_KEY = "sentMessageKey";
        public static final String QUOTATION_QUANTITY = "quotationQuantity";
        public static final String RFX_STATION_URL = "rfxStationUrl";
        public static final String MIN_QUOTED_SUPPLIER = "minQuotedSupplier";
        public static final String SUPPLIER_INFOS = "supplierInfos";
        public static final String SOURCE_TYPE = "sourceType";
        public static final String SUPPLIER_COMPANY_NAME = "supplierCompanyName";
        public static final String ABANDON_REMARK = "abandonRemark";
        public static final String PURCHASE_NAME = "purchaseName";
        public static final String PURCHASE_EMAIL = "purchaseEmail";
        public static final String PURCHASE_PHONE = "purchasePhone";
        public static final String QUOTATION_END_DATE = "quotationEndDate";

        private MessageConstants() {
        }
    }

    public static class SourceMethod {
        public static final String INVITE = "INVITE";
        public static final String OPEN = "OPEN";
        public static final String ALL_OPEN = "ALL_OPEN";

        private SourceMethod() {
        }
    }

    public static class RfxStatus {
        public static final String IN_QUOTATION = "IN_QUOTATION";

        private RfxStatus() {
        }
    }

    public static class DefaultFlagValue {
        public static final Integer DEFAULT_PWD_FLAG = 0;
        public static final Integer PASSWORD_FLAG = 1;
        public static final Integer DEFAULT_OPENED_FLAG = 0;
        public static final Integer OPENED_FLAG = 1;
        public static final Integer DEFAULT_SENT_FLAG = 0;
        public static final Integer DEFAULT_SEALED_QUOTATION_FLAG = 0;
        public static final Integer EALED_QUOTATION_FLAG = 1;
        public static final Integer DEFAULT_PRETRIAL_FLAG = 0;
        public static final Integer PRETRIAL_FLAG = 1;
        public static final Integer DEFAULT_FINISHED_FLAG = 0;
        public static final Integer FINISHED_FLAG = 1;
        public static final Integer DEFAULT_FLAG = 0;
        public static final Integer FLAG = 1;

        private DefaultFlagValue() {
        }
    }

    public static class PriceAssistantConfig {
        public PriceAssistantConfig() {
        }

        public static class SupplierConfigItem {
            public static final String PAYMENT_METHOD = "PAYMENT_METHOD";
            public static final String PAYMENT_TERM = "PAYMENT_TERM";
            public static final String CURRENCY_CODE = "CURRENCY_CODE";
            public static final String BID_COUNT_TOTAL = "BID_COUNT_TOTAL";
            public static final String BID_AMOUNT_TOTAL = "BID_AMOUNT_TOTAL";
            public static final String CONTRACT_PERSON = "CONTRACT_PERSON";
            public static final String CONTRACT_METHOD = "CONTRACT_METHOD";
            public static final String ADDRESS = "ADDRESS";
            public static final String MAIN_CATEGORY = "MAIN_CATEGORY";

            public SupplierConfigItem() {
            }
        }

        public static class QuotationConfigItem {
            public static final String NET_PRICE = "NET_PRICE";
            public static final String BASE_NET_PRICE = "BASE_NET_PRICE";
            public static final String TAX_RATE = "TAX_RATE";
            public static final String TAX_PRICE = "TAX_PRICE";
            public static final String BASE_TAX_QUOTATION_PRICE = "BASE_TAX_QUOTATION_PRICE";
            public static final String EXCHANGE_RATE = "EXCHANGE_RATE";
            public static final String FREIGHT_AMOUNT = "FREIGHT_AMOUNT";
            public static final String CURRENT_QUOTATION_QUANTITY = "CURRENT_QUOTATION_QUANTITY";
            public static final String NET_LINE_PRICE = "NET_LINE_PRICE";
            public static final String LINE_PRICE = "LINE_PRICE";
            public static final String MIN_PURCHASE_QUANTITY = "MIN_PURCHASE_QUANTITY";
            public static final String MIN_PACKAGE_QUANTITY = "MIN_PACKAGE_QUANTITY";
            public static final String VALID_EXPIRY_DATE_FROM = "VALID_EXPIRY_DATE_FROM";
            public static final String VALID_EXPIRY_DATE_TO = "VALID_EXPIRY_DATE_TO";
            public static final String DELIVERY_CYCLE = "DELIVERY_CYCLE";
            public static final String PAYMENT_TERM = "PAYMENT_TERM";
            public static final String MIN_HISTORY_PRICE = "MIN_HISTORY_PRICE";
            public static final String MAX_HISTORY_PRICE = "MAX_HISTORY_PRICE";
            public static final String AVG_HISTORY_PRICE = "AVG_HISTORY_PRICE";
            public static final String FREIGHT_LINE_PRICE = "FREIGHT_LINE_PRICE";

            public QuotationConfigItem() {
            }
        }

        public static class ConfigType {
            public static final String QUOTATION = "QUOTATION";
            public static final String SUPPLIER = "SUPPLIER";

            public ConfigType() {
            }
        }
    }

    public static class ErrorCode {
        public static final String CONFIG_CONTENT_ERROR = "error.config_content";
        public static final String CANCEL_EXIST = "已取消的采购计划不能再次取消";
        private ErrorCode() {
        }
    }
}