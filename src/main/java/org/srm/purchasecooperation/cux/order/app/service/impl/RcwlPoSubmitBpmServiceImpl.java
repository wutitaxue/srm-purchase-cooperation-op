package org.srm.purchasecooperation.cux.order.app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.ResponseUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.srm.boot.event.service.sender.EventSender;
import org.srm.purchasecooperation.common.utils.ConfigCenterUtils;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoBudgetItfService;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoSubmitBpmService;
import org.srm.purchasecooperation.order.api.dto.MallOrderConfirmDTO;
import org.srm.purchasecooperation.order.api.dto.MallOrderConfirmLineMessageDTO;
import org.srm.purchasecooperation.order.api.dto.MallOrderConfirmMessageDTO;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.api.dto.PoMessageDetailDTO;
import org.srm.purchasecooperation.order.app.service.PoApproveRuleService;
import org.srm.purchasecooperation.order.app.service.PoHeaderService;
import org.srm.purchasecooperation.order.app.service.PoProcessActionService;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.entity.PoStatusSyncMallRecord;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.service.PoHeaderSendApplyMqService;
import org.srm.purchasecooperation.order.domain.vo.PoDocVO;
import org.srm.purchasecooperation.order.infra.constant.PoConstants;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.order.infra.utils.OrderMessageUtil;
import org.srm.purchasecooperation.order.infra.utils.PoApproveRuleEnum;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.infra.feign.ScecRemoteService;
import org.srm.purchasecooperation.sinv.app.service.SinvRcvTrxHeaderService;
import org.srm.purchasecooperation.utils.service.EventSendTranService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author pengxu.zhi@hand-china.com
 * @desc ...
 * @date 2021-11-02 15:26:14
 */
@Slf4j
@Service
public class RcwlPoSubmitBpmServiceImpl implements RcwlPoSubmitBpmService {

    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private RcwlPoBudgetItfService rcwlPoBudgetItfService;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private PoApproveRuleService poApproveRuleService;
    @Autowired
    private PoHeaderService poHeaderService;
    @Autowired
    private PoHeaderSendApplyMqService poHeaderSendApplyMqService;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private PoLineLocationRepository poLineLocationRepository;
    @Autowired
    @Lazy
    private SinvRcvTrxHeaderService sinvRcvTrxHeaderService;
    @Autowired
    private PoProcessActionService poProcessActionService;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private ScecRemoteService scecRemoteService;
    @Autowired
    private EventSendTranService eventsendTranService;

    @Override
    public void rcwlSubmitBpmSuccessed(Long tenantId, String poNum, String procInstID, String newProcURL) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);
        //SODR.PO_STATUS
        poHeader.setAttributeVarchar36(procInstID);
        poHeader.setAttributeVarchar37(newProcURL);
        poHeader.setStatusCode("SUBMITTED");
        poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode","attributeVarchar36","attributeVarchar37"});
    }

    @Override
    public void rcwlSubmitBpmApproved(Long tenantId, String poNum) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);
        this.approveProcess(tenantId, poHeader, 1);
    }

    @Override
    public void approveProcess(Long tenantId, PoHeader poHeader, Integer mqFlag) {
        //获取自动发布配置
        String manualPublicFlag = this.poHeaderRepository.getPoConfigCodeValue(tenantId, poHeader.getPoHeaderId(), "SITE.SPUC.PO.MANUAL_PUBLISH");
        log.info("订单自动发布配置信息：{}",manualPublicFlag);

        //非电商商城数据
        if (!"E-COMMERCE".equals(poHeader.getPoSourcePlatform())){
            poHeader.setApprovedFlag(1);
            poHeader.setApprovedDate(new Date());
            if (StringUtils.isNotEmpty(manualPublicFlag) && "1".equals(manualPublicFlag)) {
                poHeader.setStatusCode("APPROVED");
            } else {
                poHeader.setStatusCode("PUBLISHED");
            }
            poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode","approvedFlag","approvedDate"});
        }else {
            //电商商城订单自动发布确认
            List<PoLineLocation> lineLocationList = this.poLineLocationRepository.select("poHeaderId", poHeader.getPoHeaderId());
            this.publishOrderForECommerce(poHeader);
            String promiseDeliveryDateNotNullFlag = String.valueOf(this.poApproveRuleService.selectPoApproveRule(DetailsHelper.getUserDetails().getTenantId(), PoApproveRuleEnum.COMMITTED_DELIVERY_DATE.name()).isPromiseDeliveryDateNotNull());
            if (String.valueOf(BaseConstants.Flag.YES).equals(promiseDeliveryDateNotNullFlag)) {
                lineLocationList.forEach((lineLocation) -> {
                    lineLocation.setPromiseDeliveryDate(lineLocation.getNeedByDate());
                });
            }

            try {
                PoDocVO poDocVO = this.poHeaderRepository.selectPoDocVO(poHeader.getPoHeaderId(), "PUBLISHED");
                this.eventSender.fireEvent("PO", "SORD_PO", PoConstants.DEFAULT_TENANT_ID, "PUBLISHED", Collections.singletonList(poDocVO));
            } catch (Exception var9) {
                log.error("send poDocVO has failed: ", var9);
            }

            poHeader = this.confirmOrderForECommerce(poHeader, lineLocationList);
            PoDTO poDTO = new PoDTO();
            BeanUtils.copyProperties(poHeader, poDTO);
            if (Integer.valueOf(1).equals(mqFlag)){
                this.poHeaderSendApplyMqService.sendApplyMq(poDTO.getPoHeaderId(), poDTO.getTenantId(), "UPDATE");
            }
        }
    }




    @SneakyThrows
    @Override
    public void rcwlSubmitBpmReject(Long tenantId, String poNum) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);

        PoDTO poDTO = new PoDTO();
        BeanUtils.copyProperties(poHeader,poDTO);
        //调用占预算接口释放预算，占用标识（01占用，02释放），当前释放逻辑：占用金额固定为0，清空占用金额
        rcwlPoBudgetItfService.invokeBudgetOccupy(poDTO, poDTO.getTenantId(), "02");

        //SODR.PO_STATUS
        poHeader.setStatusCode("REJECTED");
        //释放失败返回错误消息，成功则更新占用标识为0
        poHeader.setAttributeTinyint1(0);
        poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode", "attributeTinyint1"});
    }


    private PoHeader publishOrderForECommerce(PoHeader poHeader) {
        poHeader.setReleasedDate(new Date());
        poHeader.setReleasedFlag(BaseConstants.Flag.YES);
        poHeader.setStatusCode("PUBLISHED");
        if ("E-COMMERCE".equals(poHeader.getPoSourcePlatform())) {
            this.sinvRcvTrxHeaderService.checkEcPoExistAsnStrategy(poHeader.getTenantId(), poHeader.getPoHeaderId());
        }

        this.poProcessActionService.insert(poHeader.getPoHeaderId(), "PUBLISH");
        this.poHeaderRepository.updateOptional(poHeader, new String[]{"statusCode", "releasedDate", "releasedFlag"});

        CustomUserDetails userDetail = DetailsHelper.getUserDetails();
        this.sendMessageByType(userDetail, poHeader.getPoHeaderId(), "PUBLISH");
        return poHeader;
    }

    protected PoHeader confirmOrderForECommerce(PoHeader orderHeader, List<PoLineLocation> poLineLocationList) {
        PrHeader header = new PrHeader();
        header.setPrHeaderId(orderHeader.getPrHeaderId());
        PrHeader prHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(header);
        Assert.notNull(prHeader, "error.data_not_exists");
        PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(orderHeader);
        if (ConfigCenterUtils.enableOrderCenterFlag(poHeader.getTenantId()) && Objects.isNull(poHeader.getOriginalPoHeaderId())) {
            String byKey = orderHeader.getPoHeaderId() + "-" + orderHeader.getTenantId();
            String type = "ORDER_RELEASE";
            OrderMessageUtil.sendOMSMessage(orderHeader.getTenantId(), type, this.buildMallOrderConfirmMessageDTO(poHeader, poLineLocationList, true), byKey);
            this.sendMessageByType(DetailsHelper.getUserDetails(), poHeader.getPoHeaderId(), "CONFIRMED");
            return poHeader;
        } else {
            Map<Long, List<PoLineLocation>> map = (Map)poLineLocationList.stream().collect(Collectors.groupingBy(PoLineLocation::getPoLineId));
            List<PoLine> poLineList = this.poLineRepository.selectByCondition(Condition.builder(PoLine.class).andWhere(Sqls.custom().andIn("poLineId", map.keySet())).build());
            List<MallOrderConfirmDTO> mallOrderConfirmDTOList = new ArrayList();
            poLineList.forEach((poLine) -> {
                MallOrderConfirmDTO mallOrderConfirm = new MallOrderConfirmDTO();
                mallOrderConfirm.setMallOrderNum(prHeader.getMallOrderNum());
                mallOrderConfirm.setPoHeaderId(poHeader.getPoHeaderId());
                mallOrderConfirm.setPoNum(poHeader.getPoNum());
                mallOrderConfirm.setProductId(poLine.getProductId());
                mallOrderConfirm.setProductNum(poLine.getProductNum());
                mallOrderConfirm.setPoLineLocationId(((PoLineLocation)((List)map.get(poLine.getPoLineId())).get(0)).getPoLineLocationId());
                mallOrderConfirm.setDisplayPoNum(poHeader.getDisplayPoNum());
                mallOrderConfirm.setSrmOrderEntryCode(poLine.getDisplayLineNum());
                mallOrderConfirm.setSrmOrderEntryId(poLine.getPoLineId());
                mallOrderConfirmDTOList.add(mallOrderConfirm);
                log.info("order confirm poNum：{}, PoLineLocationId：{}, mallOrderConfirmDTOList：{}", new Object[]{poHeader.getPoNum(), ((PoLineLocation)((List)map.get(poLine.getPoLineId())).get(0)).getPoLineLocationId(), mallOrderConfirmDTOList});
            });
            ResponseEntity<String> confirmResult = this.scecRemoteService.confirmOrder(poHeader.getTenantId(), mallOrderConfirmDTOList);
            log.debug("===>confirmOrderForECommerce poHeader : {}, confirmResult : {}", poHeader, confirmResult);
            if (!confirmResult.getStatusCode().is2xxSuccessful()) {
                log.error("内部调用接口失败，请重试");
                throw new CommonException("内部调用接口失败，请重试", new Object[0]);
            } else {
                String resultStr = (String) ResponseUtils.getResponse(confirmResult, new TypeReference<String>() {
                }, (httpStatus, response) -> {
                    throw new CommonException(httpStatus.value() + response, new Object[0]);
                }, (exceptionResponse) -> {
                    throw new CommonException(exceptionResponse.getCode(), new Object[]{exceptionResponse.getMessage()});
                });
                log.debug("===>confirmOrderForECommerce ResponseEntity<String> : {}", resultStr);
                CustomUserDetails userDetail = DetailsHelper.getUserDetails();
                this.sendMessageByType(userDetail, poHeader.getPoHeaderId(), "CONFIRMED");
                return poHeader;
            }
        }
    }

    private void sendMessageByType(CustomUserDetails userDetail, Long poHeaderId, String operateType) {
        log.debug("24497====[sendMessageByType] poHeaderId is:{}", poHeaderId);
        log.debug("24497====[sendMessageByType] userDetails is:{}", userDetail.toString());
        if (userDetail.getTenantId() == null || userDetail.getTenantId() == 0L) {
            PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeaderId);
            userDetail.setTenantId(poHeader.getTenantId());
        }

        List<PoMessageDetailDTO> poMessageDetailDTOS = new ArrayList();
        log.debug("===>sendMessageByType operateType : {}", operateType);
        PoMessageDetailDTO poMessageDetailDTO;
        if ("CONFIRMED".equals(operateType)) {
            poMessageDetailDTO = new PoMessageDetailDTO(poHeaderId, userDetail.getUserId(), userDetail.getTenantId(), userDetail.getOrganizationId(), "CONFIRMED", "ORDER");
            poMessageDetailDTOS.add(poMessageDetailDTO);

            try {
                this.eventsendTranService.fireEvent("PO", "SODR_PO_MESSAGE", PoConstants.DEFAULT_TENANT_ID, "CONFIRMED", poMessageDetailDTOS);
            } catch (Exception var9) {
                log.error("send poMessageDetailDTOS has failed: ", var9);
            }
        } else if ("PUBLISH".equals(operateType)) {
            poMessageDetailDTO = new PoMessageDetailDTO(poHeaderId, userDetail.getUserId(), userDetail.getTenantId(), userDetail.getOrganizationId(), "PUBLISH", "ORDER");
            poMessageDetailDTOS.add(poMessageDetailDTO);

            try {
                this.eventsendTranService.fireEvent("PO", "SODR_PO_MESSAGE", PoConstants.DEFAULT_TENANT_ID, "PUBLISHED", poMessageDetailDTOS);
            } catch (Exception var8) {
                log.error("send poMessageDetailDTOS has failed: ", var8);
            }
        } else if ("DELIVERY_DATE_SUBMIT".equals(operateType)) {
            poMessageDetailDTO = new PoMessageDetailDTO(poHeaderId, userDetail.getUserId(), userDetail.getTenantId(), userDetail.getOrganizationId(), "DELIVERY_DATE_SUBMIT", "ORDER");
            poMessageDetailDTOS.add(poMessageDetailDTO);

            try {
                this.eventSender.fireEvent("PO", "SODR_PO_MESSAGE", PoConstants.DEFAULT_TENANT_ID, "DELIVERY_DATE_SUBMIT", poMessageDetailDTOS);
            } catch (Exception var7) {
                log.error("send poMessageDetailDTOS has failed: ", var7);
            }
        }

    }

    protected List<MallOrderConfirmMessageDTO> buildMallOrderConfirmMessageDTO(PoHeader poHeader, List<PoLineLocation> poLineLocationList, boolean isEcOrder) {
        PrHeader header = new PrHeader();
        header.setPrHeaderId(poHeader.getPrHeaderId());
        PrHeader prHeader = null;
        if (isEcOrder) {
            prHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(header);
            Assert.notNull(prHeader, "error.data_not_exists");
        }

        Date now = new Date();
        Map<Long, List<PoLineLocation>> map = (Map)poLineLocationList.stream().collect(Collectors.groupingBy(PoLineLocation::getPoLineId));
        List<PoLine> poLineList = this.poLineRepository.selectByCondition(Condition.builder(PoLine.class).andWhere(Sqls.custom().andIn("poLineId", map.keySet())).build());
        Map<Long, String> mallOrderNumMap = null;
        if (!isEcOrder) {
            Set<Long> prHeaderIdSet = (Set)poLineList.stream().map(PoLine::getPrHeaderId).collect(Collectors.toSet());
            String prHeaderIds = StringUtils.join(prHeaderIdSet, ",");
            mallOrderNumMap = (Map)this.prHeaderRepository.selectByIds(prHeaderIds).stream().collect(Collectors.toMap(PrHeader::getPrHeaderId, PrHeader::getMallOrderNum));
        }

        List<MallOrderConfirmLineMessageDTO> mallOrderConfirmDTOList = new ArrayList();
        Iterator var16 = poLineList.iterator();

        while(var16.hasNext()) {
            PoLine poLine = (PoLine)var16.next();
            MallOrderConfirmLineMessageDTO mallOrderConfirm = new MallOrderConfirmLineMessageDTO();
            if (!isEcOrder) {
                String orderCode = (String)mallOrderNumMap.get(poLine.getPrHeaderId());
                Assert.notNull(orderCode, "error.data_invalid");
                mallOrderConfirm.setOrderCode(orderCode);
            }

            mallOrderConfirm.setSrmOrderId(poHeader.getPoHeaderId());
            mallOrderConfirm.setSrmOrderCode(poHeader.getPoNum());
            mallOrderConfirm.setSrmDisplayOrderCode(poHeader.getDisplayPoNum());
            mallOrderConfirm.setSkuId(poLine.getProductId());
            mallOrderConfirm.setSkuCode(poLine.getProductNum());
            mallOrderConfirm.setSkuName(poLine.getProductName());
            mallOrderConfirm.setItemId(poLine.getItemId());
            mallOrderConfirm.setItemCode(poLine.getItemCode());
            mallOrderConfirm.setItemName(poLine.getItemName());
            mallOrderConfirm.setFreightFlag(poLine.getFreightLineFlag());
            mallOrderConfirm.setSrmPoLineLocationId(((PoLineLocation)((List)map.get(poLine.getPoLineId())).get(0)).getPoLineLocationId());
            mallOrderConfirm.setQuantity(poLine.getQuantity());
            mallOrderConfirm.setSrmOrderEntryCode(poLine.getDisplayLineNum());
            mallOrderConfirm.setSrmOrderEntryId(poLine.getPoLineId());
            mallOrderConfirmDTOList.add(mallOrderConfirm);
        }

        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        if (isEcOrder) {
            MallOrderConfirmMessageDTO messageDTO = new MallOrderConfirmMessageDTO();
            messageDTO.setConfirmTime(now);
            messageDTO.setUserId(userDetails.getUserId());
            messageDTO.setTenantId(poHeader.getTenantId());
            messageDTO.setOrderCode(prHeader.getMallOrderNum());
            messageDTO.setProductDTOList(mallOrderConfirmDTOList);
            log.info("ec messageDTO {}", messageDTO);
            return Collections.singletonList(messageDTO);
        } else {
            Map<String, List<MallOrderConfirmLineMessageDTO>> orderMap = (Map)mallOrderConfirmDTOList.stream().collect(Collectors.groupingBy(MallOrderConfirmLineMessageDTO::getOrderCode));
            List<MallOrderConfirmMessageDTO> messageDTOList = new ArrayList();
            orderMap.forEach((orderCodex, lineList) -> {
                MallOrderConfirmMessageDTO messageDTO = new MallOrderConfirmMessageDTO();
                messageDTO.setConfirmTime(now);
                messageDTO.setUserId(userDetails.getUserId());
                messageDTO.setTenantId(poHeader.getTenantId());
                messageDTO.setOrderCode(orderCodex);
                messageDTO.setSupplierConfirmedDate(now);
                messageDTO.setReceiptType(poHeader.getReceiptType());
                messageDTO.setProductDTOList(lineList);
                messageDTOList.add(messageDTO);
            });
            log.info("catalog po messageDTOList {}", messageDTOList);
            return messageDTOList;
        }
    }

}
