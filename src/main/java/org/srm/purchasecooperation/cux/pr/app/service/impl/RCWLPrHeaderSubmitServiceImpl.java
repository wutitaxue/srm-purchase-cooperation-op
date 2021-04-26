package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.hzero.boot.customize.service.CustomizeClient;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.workflow.WorkflowClient;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.redis.RedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.srm.boot.event.service.sender.EventSender;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.group.GroupApproveHelper;
import org.srm.boot.platform.message.MessageHelper;
import org.srm.purchasecooperation.asn.infra.utils.SitfJsonConvertUtil;
import org.srm.purchasecooperation.budget.app.service.BudgetService;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrHeaderSubmitService;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrHeaderSubmitService;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.finance.infra.mapper.CompanyMapper;
import org.srm.purchasecooperation.order.api.dto.ItemListDTO;
import org.srm.purchasecooperation.order.app.service.PoCreateRuleService;
import org.srm.purchasecooperation.order.app.service.PoHeaderService;
import org.srm.purchasecooperation.order.app.service.PoLineLocationService;
import org.srm.purchasecooperation.order.app.service.PoLineService;
import org.srm.purchasecooperation.order.domain.repository.ChangePoRepository;
import org.srm.purchasecooperation.order.domain.repository.OrderTypeRepository;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.service.PoDomainService;
import org.srm.purchasecooperation.order.domain.service.PoHeaderDomainService;
import org.srm.purchasecooperation.order.domain.service.PoPriceLibDomainService;
import org.srm.purchasecooperation.order.infra.feign.SitfRemoteService;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.pr.app.service.*;
import org.srm.purchasecooperation.pr.app.service.impl.PrHeaderServiceImpl;
import org.srm.purchasecooperation.pr.app.service.impl.PrLineServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.*;
import org.srm.purchasecooperation.pr.domain.service.PurchaseRequestDomainService;
import org.srm.purchasecooperation.pr.domain.vo.PrHeaderVO;
import org.srm.purchasecooperation.pr.infra.feign.HpfmRemoteService;
import org.srm.purchasecooperation.pr.infra.feign.HwfpRemoteService;
import org.srm.purchasecooperation.pr.infra.feign.ScecRemoteService;
import org.srm.purchasecooperation.pr.infra.feign.SmalPrRemoteService;
import org.srm.purchasecooperation.pr.infra.mapper.PrHeaderMapper;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.web.annotation.Tenant;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/21 14:14
 */
@Component
public class RCWLPrHeaderSubmitServiceImpl  {
//    @Autowired
//    private PrHeaderRepository prHeaderRepository;
//    @Autowired
//    private PrLineRepository prLineRepository;
//    @Autowired
//    private PrActionService prActionService;
//    @Autowired
//    private PrActionRepository prActionRepository;
//    @Autowired
//    private CustomizeSettingHelper customizeSettingHelper;
//    @Autowired
//    private PrApprovalRuleService prApprovalRuleService;
//    @Autowired
//    private CodeRuleBuilder codeRuleBuilder;
//    @Autowired
//    private PrLineService prLineService;
//
//    @Autowired
//    private ScecRemoteService scecRemoteService;
//    @Autowired
//    private SitfJsonConvertUtil sitfJsonConvertUitl;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private PrPoHeaderEsService prPoHeaderEsService;
//    @Autowired
//    private PoHeaderService poHeaderService;
//    @Autowired
//    private MessageSource messageSource;
//    @Autowired
//    private PurchaseRequestDomainService purchaseRequestDomainService;
//    @Autowired
//    private PrSyncConfigService prSyncConfigService;
//    @Autowired
//    private PrHeaderMapper prHeaderMapper;
//    @Autowired
//    private PrLineMapper prLineMapper;
//    @Autowired
//    private PoLineLocationService poLineLocationService;
//    @Autowired
//    private PrSyncConfigRepository prSyncConfigRepository;
//    @Autowired
//    private HpfmRemoteService hpfmRemoteService;
//    @Autowired
//    private MessageHelper messageHelper;
//    @Autowired
//    private WorkflowClient workflowClient;
//    @Autowired
//    private MessageClient messageClient;
//    @Autowired
//    private PrLineAssignRepository prLineAssignRepository;
//    @Autowired
//    private SmalPrRemoteService smalPrRemoteService;
//    @Autowired
//    private PrChangeConfigRepository prChangeConfigRepository;
//    @Autowired
//    private PoCreateRuleService poCreateRuleService;
//    @Autowired
//    private OrderTypeRepository orderTypeRepository;
//    @Autowired
//    private PrTypeRepository prTypeRepository;
//    @Autowired
//    private PoLineRepository poLineRepository;
//    @Autowired
//    private GroupApproveHelper groupApproveHelper;
//    @Autowired
//    private ItemRepository itemRepository;
//    @Autowired
//    private PrSubmitConfigRepository prSubmitConfigRepository;
//    @Autowired
//    private PrHeaderEsService prHeaderEsService;
//    @Autowired
//    private PoHeaderRepository poHeaderRepository;
//    @Autowired
//    private HwfpRemoteService hwfpRemoteService;
//    @Autowired
//    private PoLineService poLineService;
//    @Autowired
//    private PrLineServiceImpl prLineServiceImpl;
//    @Autowired
//    private CompanyMapper companyMapper;
//    @Autowired
//    private PoPriceLibDomainService poPriceLibDomainService;
//    @Autowired
//    private PrBudgetRepository prBudgetRepository;
//    @Autowired
//    private BudgetService budgetService;
//    @Autowired
//    private PrBudgetService prBudgetService;
//    @Autowired
//    private CustomizeClient customizeClient;
//    @Autowired
//    private PrLineSupplierRepository prLineSupplierRepository;
//    @Autowired
//    private PoDomainService poDomainService;
//    @Autowired
//    private ChangePoRepository changePoRepository;
//    @Autowired
//    private Validator validator;
//    @Autowired
//    private PoHeaderDomainService poHeaderDomainService;
//    @Autowired
//    private RCWLPrItfService rcwlPrItfService;
//    private static final Logger LOGGER = LoggerFactory.getLogger(PrHeaderServiceImpl.class);
//    @Value("${service.home-url}")
//    private String homeUrl;
//    @Value("${service.reject-url}")
//    private String rejectUrl;
//    @Value("${service.pr-non-erp-url}")
//    private String prNonErpUrl;
//    @Value("${service.pr-erp-url}")
//    private String prErpUrl;
//    @Value("${service.demand-query-detail}")
//    private String demandQueryDetailUrl;
//
//    public RCWLPrHeaderSubmitServiceImpl() {
//    }
//
//    @Override
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    @SagaStart
//    public PrHeader singletonSubmit(Long tenantId, PrHeader prHeader) {
//        this.checkUnit(prHeader);
//        if (CollectionUtils.isEmpty(prHeader.getPrLineList())) {
//            return prHeader;
//        } else {
//            prHeader = this.updatePrHeader(prHeader);
//            //保存完之后触发接口
//            try {
//                this.rcwlPrItfService.invokeBudgetOccupy(prHeader,tenantId);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            prHeader.validateSubmitForBatch(this.prHeaderRepository, this.prLineRepository, this.customizeSettingHelper, this.customizeClient);
//            return ((PrHeaderService)this).submit(tenantId, prHeader);
//        }
//    }
//    private void checkUnit(PrHeader prHeader) {
//        Iterator var2 = prHeader.getPrLineList().iterator();
//
//        while(var2.hasNext()) {
//            PrLine prLine = (PrLine)var2.next();
//            Integer flag = prLine.getUnitFlag();
//            if (prLine.getItemId() != null && BaseConstants.Flag.YES.equals(flag) && prHeader.getPrSourcePlatform().equals("SRM")) {
//                List<ItemListDTO> content = this.prLineMapper.relatePurchasePriceItemList(prHeader.getTenantId(), prLine.getItemCode(), prLine.getItemName(), (String)null, (Long)null, (Long)null, prHeader.getCompanyId());
//                if (content != null && content.size() > 0) {
//                    if (((ItemListDTO)content.get(0)).getOrderUomName() != null) {
//                        if (!((ItemListDTO)content.get(0)).getOrderUomId().equals(prLine.getUomId())) {
//                            throw new CommonException("error.pr.item_unit_error", new Object[0]);
//                        }
//                    } else if (!((ItemListDTO)content.get(0)).getPrimaryUomId().equals(prLine.getUomId())) {
//                        throw new CommonException("error.pr.item_unit_error", new Object[0]);
//                    }
//                }
//            }
//        }
//
//    }
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public PrHeader updatePrHeader(PrHeader prHeader) {
//        LOGGER.debug("12705 updatePrHeader ====用户信息:{},采购申请=:{}", DetailsHelper.getUserDetails(), JSON.toJSONString(Arrays.asList(prHeader)));
//        this.validatePrCancel(prHeader);
//        this.checkUnit(prHeader);
//        prHeader.validInvoiceDetail();
//        prHeader.createValidateNonNull();
//        prHeader.validUniqueIndex(this.prHeaderRepository);
//        prHeader.setLocalCurrency(this.prHeaderRepository.selectPrLocalCurrencyCode(prHeader.getTenantId(), prHeader.getCompanyId()));
//        prHeader.setPrLineList(this.prLineService.updatePrLines(prHeader));
//        prHeader.batchMaintainDateAndCountAmount(this.prLineRepository);
//        PrHeaderVO oldPrHeaderVO;
//        PrHeaderVO newPrHeaderVO;
//        if (prHeader.sourcePlatformIsRule()) {
//            oldPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
//            prHeader.setPreviousPrStatusCode(oldPrHeaderVO.getPrStatusCode());
//            if (prHeader.getRequestedBy() == null) {
//                prHeader.setRequestedBy(oldPrHeaderVO.getRequestedBy());
//            }
//
//            this.prHeaderRepository.updateOptional(prHeader, new String[]{"purchaseAgentId", "title", "contactTelNum", "approvedRemark", "invoiceAddressId", "invoiceContactName", "invoiceTelNum", "receiverEmailAddress", "invoiceMethodCode", "remark", "invoiceTypeCode", "invoiceTitleTypeCode", "invoiceDetailTypeCode", "ouId", "purchaseOrgId", "paymentMethodCode", "paymentMethodName", "invoiceAddress", "unitId", "unitName", "invoiceTypeName", "invoiceMethodName", "invoiceTitleTypeName", "invoiceTitle", "invoiceDetailTypeName", "expenseUnitId", "parentUnitId", "expenseUnitName", "invoiceCompanyId", "accepterUserId", "inventoryId", "categoryId", "techGuidanceFlag", "techDirectorUserId", "prTypeId", "requestedBy", "prRequestedName", "previousPrStatusCode", "localCurrencyNoTaxSum", "localCurrencyTaxSum", "localCurrency", "originalCurrency"});
//            newPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
//            this.prActionService.headerDataChangeDetection(oldPrHeaderVO, newPrHeaderVO);
//        } else {
//            oldPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
//            prHeader.setPreviousPrStatusCode(oldPrHeaderVO.getPrStatusCode());
//            this.prHeaderRepository.updateByPrimaryKeySelective(prHeader);
//            this.prHeaderRepository.updateOptional(prHeader, PrHeader.SRM_UPDATE_FIELD_LIST);
//            newPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
//            this.prActionService.headerDataChangeDetection(oldPrHeaderVO, newPrHeaderVO);
//        }
//
//        return prHeader;
//    }
//    public void validatePrCancel(PrHeader prHeader) {
//        PrHeader oldPrHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(prHeader.getPrHeaderId());
//        if ("CANCELLED".equals(oldPrHeader.getCancelStatusCode())) {
//            throw new CommonException("error.pr_cancelled_non_operationl", new Object[]{oldPrHeader.getDisplayPrNum()});
//        }
//    }
}
