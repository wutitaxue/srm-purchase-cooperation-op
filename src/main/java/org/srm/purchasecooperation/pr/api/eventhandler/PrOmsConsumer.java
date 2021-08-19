//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.srm.purchasecooperation.pr.api.eventhandler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.core.redis.RedisHelper;
import org.hzero.core.util.ValidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.srm.boot.event.service.sender.EventSender;
import org.srm.mq.annotation.Consumer;
import org.srm.mq.service.MessageData;
import org.srm.mq.service.consumer.MessageConsumer;
import org.srm.purchasecooperation.common.api.dto.TenantDTO;
import org.srm.purchasecooperation.order.api.dto.UserCacheDTO;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.pr.api.dto.MallBatchCreatePrDTO;
import org.srm.purchasecooperation.pr.api.dto.MallBatchCreateShopPrDTO;
import org.srm.purchasecooperation.pr.api.dto.PrCallBackDTO;
import org.srm.purchasecooperation.pr.api.dto.PrHeaderCreateDTO;
import org.srm.purchasecooperation.pr.api.dto.PrMessageDTO;
import org.srm.purchasecooperation.pr.api.dto.PrOmsDTO;
import org.srm.purchasecooperation.pr.api.dto.ShopPrHeaderCreateDTO;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.infra.constant.PrMessageEnum;
import org.srm.purchasecooperation.pr.infra.utils.PrMqUtil;

@Consumer(
        code = "smodr-message-topic",
        type = "config",
        condition = "SPRM"
)
public class PrOmsConsumer implements MessageConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrOmsConsumer.class);
    @Autowired
    private PrHeaderService prHeaderService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private Validator validator;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private RedisHelper redisHelper;

    public PrOmsConsumer() {
    }

    public void messageHandle(MessageData messageData) {
        Integer errorFlag = Flag.NO;
        LOGGER.info("PrOmsConsumer PrOmsConsumerStart : {}", messageData);

        try {
            JavaType javaType = this.objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{PrMessageDTO.class});
            List<PrMessageDTO> prMessageDTOS = (List)this.objectMapper.readValue(messageData.getData(), javaType);
            PrMessageDTO prMessageDTO = (PrMessageDTO)prMessageDTOS.get(0);
            this.assemblyUserDetail(prMessageDTO);
            String var6 = prMessageDTO.getType();
            byte var7 = -1;
            switch(var6.hashCode()) {
                case -1441109769:
                    if (var6.equals("OMS_ORDER_CREATE")) {
                        var7 = 0;
                    }
                    break;
                case -439965454:
                    if (var6.equals("ORDER_CANCEL_BY_EC")) {
                        var7 = 4;
                    }
                    break;
                case -332782621:
                    if (var6.equals("MANUAL_REQUEST_CANCEL")) {
                        var7 = 5;
                    }
                    break;
                case -317352347:
                    if (var6.equals("MANUAL_REQUEST_CREATE")) {
                        var7 = 1;
                    }
                    break;
                case -257842197:
                    if (var6.equals("PR_CREATE_CALLBACK")) {
                        var7 = 6;
                    }
                    break;
                case 221443650:
                    if (var6.equals("PREEMPT_CANCEL_CALLBACK_FAILED")) {
                        var7 = 3;
                    }
                    break;
                case 1789206142:
                    if (var6.equals("PREEMPT_CANCEL_CALLBACK_SUCCESS")) {
                        var7 = 2;
                    }
            }

            switch(var7) {
                case 0:
                    this.omsOrderCreate(prMessageDTO);
                    return;
                case 1:
                    this.manualRequestCreate(prMessageDTO);
                    return;
                case 2:
                    this.preemptCancelCallbackSuccess(prMessageDTO);
                    return;
                case 3:
                    this.preemptCancelCallbackFailed(prMessageDTO);
                    return;
                case 4:
                    this.orderCancelByEc(prMessageDTO);
                    return;
                case 5:
                    this.manualRequestCancel(prMessageDTO);
                    return;
                case 6:
                    this.prCreateCallback(prMessageDTO);
                    return;
                default:
                    throw new CommonException("sprm.pr_oms_mq_type_mismatch", new Object[]{prMessageDTO.getType()});
            }
        } catch (Exception var11) {
            LOGGER.debug("PrOmsConsumer PrOmsConsumerError ，json: \n {} \n 错误信息: \n {} ", messageData.getData(), var11.getMessage());
            LOGGER.error("stackTrace:", var11);
            errorFlag = Flag.YES;
        } finally {
            SecurityContextHolder.clearContext();
        }

        if (Flag.YES.equals(errorFlag)) {
            throw new CommonException(errorFlag.toString(), new Object[0]);
        }
    }

    private void omsOrderCreate(PrMessageDTO prMessageDTO) {
        LOGGER.info("omsOrderCreate 开始初始化商城采购申请\n{}", prMessageDTO);
        String errorMsg = "failed";
        Object prHeaders = new ArrayList();

        try {
            MallBatchCreatePrDTO createPrDTO = (MallBatchCreatePrDTO)this.objectMapper.convertValue(prMessageDTO.getData(), new TypeReference<MallBatchCreatePrDTO>() {
            });
            List<PrHeaderCreateDTO> prHeaderCreateDTOList = createPrDTO.getPrHeaderCreateDTOList();
            ValidUtils.valid(this.validator, prHeaderCreateDTOList, new Class[0]);
            prHeaders = this.prHeaderService.batchCreatePrWholeOrder(prHeaderCreateDTOList, createPrDTO.getLotNum(), prMessageDTO.getTenantId());
        } catch (Exception var8) {
            LOGGER.debug("omsOrderCreate fail 数据读取失败，json: \n {} \n 错误信息: \n {} ", prMessageDTO.getData(), var8.getMessage());
            LOGGER.error("stackTrace:", var8);
            errorMsg = var8.getMessage();
        }

        if (CollectionUtils.isEmpty((Collection)prHeaders)) {
            String msg = errorMsg;
            MallBatchCreatePrDTO createPrDTO = (MallBatchCreatePrDTO)this.objectMapper.convertValue(prMessageDTO.getData(), new TypeReference<MallBatchCreatePrDTO>() {
            });
            List<PrHeaderCreateDTO> prHeaderCreateDTOList = createPrDTO.getPrHeaderCreateDTOList();
            List<PrCallBackDTO> prCallBackDTOS = (List)prHeaderCreateDTOList.stream().map((prHeaderCreateDTO) -> {
                return new PrCallBackDTO(createPrDTO.getTenantId(), createPrDTO.getUserId(), prHeaderCreateDTO.getMallOrderNum(), createPrDTO.getLotNum(), msg);
            }).collect(Collectors.toList());
            LOGGER.info("Mall Purchase Requisit Callback：{}", JSONObject.toJSONString(prCallBackDTOS));
            PrMqUtil.sendMessage(DetailsHelper.getUserDetails().getTenantId(), PrMessageEnum.PR_CALLBACK_FAILED, prCallBackDTOS);
        }

    }

    private void manualRequestCreate(PrMessageDTO prMessageDTO) {
        LOGGER.info("manualRequestCreate init mall manual purchase requisit\n{}", prMessageDTO);
        MallBatchCreateShopPrDTO createPrDTO = (MallBatchCreateShopPrDTO)this.objectMapper.convertValue(prMessageDTO.getData(), new TypeReference<MallBatchCreateShopPrDTO>() {
        });
        LOGGER.info("mall manual purchase requisit create list:\n{}", createPrDTO);
        List<ShopPrHeaderCreateDTO> prHeaderCreateDTOList = createPrDTO.getPrHeaderCreateDTOList();
        this.prHeaderService.batchCreateShopPr(prHeaderCreateDTOList, DetailsHelper.getUserDetails());
    }

    private void preemptCancelCallbackSuccess(PrMessageDTO prMessageDTO) {
        LOGGER.info("开始初始化商城订单取消回传\n{}", prMessageDTO);

        try {
            List<PrOmsDTO> prOmsDTOList = (List)this.objectMapper.convertValue(prMessageDTO.getData(), new TypeReference<List<PrOmsDTO>>() {
            });
            PrOmsDTO prOmsDTO = (PrOmsDTO)prOmsDTOList.get(0);
            LOGGER.info("商城订单取消回传消息列表:\n preemptCancelCallbackSuccess {}", prOmsDTO);
            LOGGER.info("商城订单取消回传==========流程变量是：" + DetailsHelper.getUserDetails().toString());
            Set<String> prNums = this.prHeaderRepository.selectPrNumsByMallOrderNumsAndTenantId(prOmsDTO.getOrderCode(), prMessageDTO.getTenantId());
            LOGGER.info("商城订单取消回传===状态：preemptCancelCallbackSuccess,采购申请编号：" + prNums.toString());
        } catch (Exception var5) {
            LOGGER.debug("商城订单取消回传，数据读取失败，json: \n {} \n 错误信息: \n {} ", prMessageDTO.getData(), var5.getMessage());
            LOGGER.error("stackTrace:", var5);
        }

    }

    private void preemptCancelCallbackFailed(PrMessageDTO prMessageDTO) {
        LOGGER.info("开始初始化商城订单取消回传\n{}", prMessageDTO);

        try {
            List<PrOmsDTO> prOmsDTOList = (List)this.objectMapper.convertValue(prMessageDTO.getData(), new TypeReference<List<PrOmsDTO>>() {
            });
            PrOmsDTO prOmsDTO = (PrOmsDTO)prOmsDTOList.get(0);
            LOGGER.info("商城订单取消回传消息列表:\n preemptCancelCallbackFailed {}", prOmsDTO);
            LOGGER.info("商城订单取消回传==========流程变量是：" + DetailsHelper.getUserDetails().toString());
            Set<String> prNums = this.prHeaderRepository.selectPrNumsByMallOrderNumsAndTenantId(prOmsDTO.getOrderCode(), prMessageDTO.getTenantId());
            LOGGER.info("商城订单取消回传===状态：preemptCancelCallbackFailed,采购申请编号：" + prNums.toString());
        } catch (Exception var5) {
            LOGGER.debug("商城订单取消回传，数据读取失败，json: \n {} \n 错误信息: \n {} ", prMessageDTO.getData(), var5.getMessage());
            LOGGER.error("stackTrace:", var5);
        }

    }

    private void orderCancelByEc(PrMessageDTO prMessageDTO) {
        LOGGER.info("begin ec expired cancel handler : {}", prMessageDTO);
        List<PrOmsDTO> prOmsDTOs = (List<PrOmsDTO>)this.objectMapper.convertValue(prMessageDTO.getData(), new TypeReference<List<PrOmsDTO>>() {
        });
//        PrOmsDTO prOmsDTO = prOmsDTOs.get(0);
        LOGGER.info("电商超期/按钮取消==========流程变量是：" + DetailsHelper.getUserDetails().toString());
        prOmsDTOs.forEach(prOmsDTO->{
            Set<String> prNums = this.prHeaderRepository.selectPrNumsByMallOrderNumsAndTenantId(prOmsDTO.getOrderCode(), prMessageDTO.getTenantId());
            //设置上下文租户 走二开流程
            CustomUserDetails userDetails = DetailsHelper.getUserDetails();
            userDetails.setTenantNum("SRM-RCWL");
            DetailsHelper.setCustomUserDetails(userDetails);
            LOGGER.info("电商超期/按钮取消==========二开：" + DetailsHelper.getUserDetails().toString());
            this.prHeaderService.cancelElectricityPurchasingExpired(prMessageDTO.getTenantId(), prNums);
        });

    }

    private void manualRequestCancel(PrMessageDTO prMessageDTO) {
        LOGGER.info("manualRequestCancel init mall manual purchase cancle\n{} ", prMessageDTO);

        try {
            PrOmsDTO prOmsDTO = (PrOmsDTO)this.objectMapper.convertValue(prMessageDTO.getData(), new TypeReference<PrOmsDTO>() {
            });
            LOGGER.info("mall manual purchase requisit cancel list:\n{}", prOmsDTO);
            PrHeader prHeader = this.prHeaderRepository.selectPrHeaderByPrNumAndTenantId(prOmsDTO.getManualRequestNum(), prMessageDTO.getTenantId());
            this.prHeaderService.batchCancelShopPr(prMessageDTO.getTenantId(), prHeader);
        } catch (Exception var4) {
            PrCallBackDTO prCallBackDTO = new PrCallBackDTO(prMessageDTO.getTenantId(), prMessageDTO.getUserId(), Flag.NO, var4.getMessage());
            LOGGER.debug("数据读取失败,json:\n {} \n 错误信息: \n {}", prMessageDTO.getData(), var4.getMessage());
            LOGGER.error("stackTrace:", var4);
            PrMqUtil.sendMessage(prMessageDTO.getTenantId(), PrMessageEnum.MANUAL_REQUEST_CANCEL_CALLBACK, prCallBackDTO);
        }

    }

    private void assemblyUserDetail(PrMessageDTO prMessageDTO) {
        Long tenantId = prMessageDTO.getTenantId();
        Long organizationId = prMessageDTO.getTenantId();
        CustomUserDetails userDetail = new CustomUserDetails("1", "1");
        if (tenantId != null && tenantId != 0L) {
            userDetail.setTenantId(tenantId);
            userDetail.setOrganizationId(organizationId);
            userDetail.setUserId(prMessageDTO.getUserId());
            TenantDTO tenantDTO = this.poHeaderMapper.selectTenantById(tenantId);
            userDetail.setTenantNum(tenantDTO.getTenantNum());
            UserCacheDTO userCacheDTO = (UserCacheDTO)this.redisHelper.fromJson(this.redisHelper.hshGet("hiam:user", String.valueOf(prMessageDTO.getUserId())), UserCacheDTO.class);
            userDetail.setLanguage(!Objects.isNull(userCacheDTO) && !StringUtils.isBlank(userCacheDTO.getLanguage()) ? userCacheDTO.getLanguage() : BaseConstants.DEFAULT_LOCALE_STR);
            DetailsHelper.setCustomUserDetails(userDetail);
        } else {
            throw new CommonException("error.tenant_id_is_null_or_equal_zero", new Object[]{tenantId});
        }
    }

    private void prCreateCallback(PrMessageDTO prMessageDTO) {
        LOGGER.info("prCreateCallback init mall \n{} ", prMessageDTO);
        List<PrHeader> prHeaderList = (List)this.objectMapper.convertValue(prMessageDTO.getData(), new TypeReference<List<PrHeader>>() {
        });
        LOGGER.info("prCreateCallback list:\n{}", JSONObject.toJSONString(prHeaderList));
        this.prHeaderService.autoSubmitFromMq(prHeaderList, prMessageDTO.getTenantId());
    }
}
