package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.exception.CommonException;
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
import org.springframework.transaction.annotation.Transactional;
import org.srm.boot.event.service.sender.EventSender;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.group.GroupApproveHelper;
import org.srm.boot.platform.message.MessageHelper;
import org.srm.purchasecooperation.asn.infra.utils.SitfJsonConvertUtil;
import org.srm.purchasecooperation.budget.app.service.BudgetService;
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
import org.srm.purchasecooperation.pr.infra.feign.HpfmRemoteService;
import org.srm.purchasecooperation.pr.infra.feign.HwfpRemoteService;
import org.srm.purchasecooperation.pr.infra.feign.ScecRemoteService;
import org.srm.purchasecooperation.pr.infra.feign.SmalPrRemoteService;
import org.srm.purchasecooperation.pr.infra.mapper.PrHeaderMapper;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.web.annotation.Tenant;

import javax.validation.Validator;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/21 14:14
 */
@Tenant("SRM-RCWL")
public class RCWLPrHeaderServiceImpl extends PrHeaderServiceImpl {
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private PrActionService prActionService;
    @Autowired
    private PrActionRepository prActionRepository;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private PrApprovalRuleService prApprovalRuleService;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private PrLineService prLineService;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private SitfRemoteService sitfRemoteService;
    @Autowired
    private ScecRemoteService scecRemoteService;
    @Autowired
    private SitfJsonConvertUtil sitfJsonConvertUitl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PrPoHeaderEsService prPoHeaderEsService;
    @Autowired
    private PoHeaderService poHeaderService;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PurchaseRequestDomainService purchaseRequestDomainService;
    @Autowired
    private PrSyncConfigService prSyncConfigService;
    @Autowired
    private PrHeaderMapper prHeaderMapper;
    @Autowired
    private PrLineMapper prLineMapper;
    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private PoLineLocationService poLineLocationService;
    @Autowired
    private PrSyncConfigRepository prSyncConfigRepository;
    @Autowired
    private HpfmRemoteService hpfmRemoteService;
    @Autowired
    private MessageHelper messageHelper;
    @Autowired
    private WorkflowClient workflowClient;
    @Autowired
    private MessageClient messageClient;
    @Autowired
    private PrLineAssignRepository prLineAssignRepository;
    @Autowired
    private SmalPrRemoteService smalPrRemoteService;
    @Autowired
    private PrChangeConfigRepository prChangeConfigRepository;
    @Autowired
    private PoCreateRuleService poCreateRuleService;
    @Autowired
    private OrderTypeRepository orderTypeRepository;
    @Autowired
    private PrTypeRepository prTypeRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private GroupApproveHelper groupApproveHelper;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PrSubmitConfigRepository prSubmitConfigRepository;
    @Autowired
    private PrHeaderEsService prHeaderEsService;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private HwfpRemoteService hwfpRemoteService;
    @Autowired
    private PoLineService poLineService;
    @Autowired
    private PrLineServiceImpl prLineServiceImpl;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private PoPriceLibDomainService poPriceLibDomainService;
    @Autowired
    private PrBudgetRepository prBudgetRepository;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private PrBudgetService prBudgetService;
    @Autowired
    private CustomizeClient customizeClient;
    @Autowired
    private PrLineSupplierRepository prLineSupplierRepository;
    @Autowired
    private PoDomainService poDomainService;
    @Autowired
    private ChangePoRepository changePoRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private PoHeaderDomainService poHeaderDomainService;
    @Autowired
    private RCWLPrItfService rcwlPrItfService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PrHeaderServiceImpl.class);
    @Value("${service.home-url}")
    private String homeUrl;
    @Value("${service.reject-url}")
    private String rejectUrl;
    @Value("${service.pr-non-erp-url}")
    private String prNonErpUrl;
    @Value("${service.pr-erp-url}")
    private String prErpUrl;
    @Value("${service.demand-query-detail}")
    private String demandQueryDetailUrl;

    public RCWLPrHeaderServiceImpl() {
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @SagaStart
    public PrHeader singletonSubmit(Long tenantId, PrHeader prHeader) {
        this.checkUnit(prHeader);
        if (CollectionUtils.isEmpty(prHeader.getPrLineList())) {
            return prHeader;
        } else {
            prHeader = this.updatePrHeader(prHeader);
            //保存完之后触发接口
            try {
                this.rcwlPrItfService.invokeBudgetOccupy(prHeader,tenantId);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            prHeader.validateSubmitForBatch(this.prHeaderRepository, this.prLineRepository, this.customizeSettingHelper, this.customizeClient);
            return ((PrHeaderService)this.self()).submit(tenantId, prHeader);
        }
    }
    private void checkUnit(PrHeader prHeader) {
        Iterator var2 = prHeader.getPrLineList().iterator();

        while(var2.hasNext()) {
            PrLine prLine = (PrLine)var2.next();
            Integer flag = prLine.getUnitFlag();
            if (prLine.getItemId() != null && BaseConstants.Flag.YES.equals(flag) && prHeader.getPrSourcePlatform().equals("SRM")) {
                List<ItemListDTO> content = this.prLineMapper.relatePurchasePriceItemList(prHeader.getTenantId(), prLine.getItemCode(), prLine.getItemName(), (String)null, (Long)null, (Long)null, prHeader.getCompanyId());
                if (content != null && content.size() > 0) {
                    if (((ItemListDTO)content.get(0)).getOrderUomName() != null) {
                        if (!((ItemListDTO)content.get(0)).getOrderUomId().equals(prLine.getUomId())) {
                            throw new CommonException("error.pr.item_unit_error", new Object[0]);
                        }
                    } else if (!((ItemListDTO)content.get(0)).getPrimaryUomId().equals(prLine.getUomId())) {
                        throw new CommonException("error.pr.item_unit_error", new Object[0]);
                    }
                }
            }
        }

    }
}
