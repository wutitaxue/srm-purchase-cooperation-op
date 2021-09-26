package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.alibaba.fastjson.JSON;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.srm.boot.adaptor.client.AdaptorTaskHelper;
import org.srm.boot.adaptor.client.exception.TaskNotExistException;
import org.srm.boot.adaptor.client.result.TaskResultBox;
import org.srm.common.TenantInfoHelper;
import org.srm.purchasecooperation.asn.infra.utils.CopyUtils;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrheaderService;
import org.srm.purchasecooperation.cux.pr.utils.constant.PrConstant;
import org.srm.purchasecooperation.order.api.dto.ItemListDTO;
import org.srm.purchasecooperation.pr.app.service.PrActionService;
import org.srm.purchasecooperation.pr.app.service.PrLineService;
import org.srm.purchasecooperation.pr.app.service.impl.PrHeaderServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.pr.domain.vo.PrHeaderVO;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.web.annotation.Tenant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/3/26 16:19
 * @version:1.0
 */
@Tenant
@Service
public class RcwlPrHeaderServiceImpl extends PrHeaderServiceImpl implements RcwlPrheaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPrHeaderServiceImpl.class);
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrLineService prLineService;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private PrActionService prActionService;
    @Autowired
    private PrLineMapper prLineMapper;

    private static final String LOG_MSG_USER = " updatePrHeader ====用户信息:{},采购申请=:{}";
    private static final String LOG_MSG_SPUC_PR_HEADER_UPDATE_AMOUNT = "============SPUC_PR_HEADER_UPDATE_AMOUNT-TaskNotExistException=============={}";
    private static final String PR_TASK_CODE = "SPUC_PR_HEADER_UPDATE";
    private static final String[] HEADER_UPDATE_OPTIONAL = new String[]{"purchaseAgentId", "title", "contactTelNum", "approvedRemark",
            "invoiceAddressId", "invoiceContactName", "invoiceTelNum", "receiverEmailAddress", "invoiceMethodCode",
            "remark", "invoiceTypeCode", "invoiceTitleTypeCode", "invoiceDetailTypeCode", "ouId", "purchaseOrgId", "paymentMethodCode", "paymentMethodName",
            "invoiceAddress", "unitId", "unitName", "invoiceTypeName", "invoiceMethodName", "invoiceTitleTypeName", "invoiceTitle", "invoiceDetailTypeName",
            "expenseUnitId", "parentUnitId", "expenseUnitName", "invoiceCompanyId", "accepterUserId", "inventoryId", "categoryId", "techGuidanceFlag", "techDirectorUserId",
            "prTypeId", "requestedBy", "prRequestedName", "previousPrStatusCode", "localCurrencyNoTaxSum", "localCurrencyTaxSum", "localCurrency", "originalCurrency"};

    /**
     * 融创采购申请更新二开接口
     *
     * @param prHeader
     * @return
     */
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @Override
    public PrHeader updatePrHeader(PrHeader prHeader) {
        LOGGER.debug(LOG_MSG_USER, DetailsHelper.getUserDetails(), JSON.toJSONString(Arrays.asList(prHeader)));
        if (CollectionUtils.isNotEmpty(prHeader.getPrLineList())){
            prHeader.getPrLineList().forEach(line -> line.setIsPushAssetsFlag(line.getAttributeVarchar11()));
        }
        this.validatePrCancel(prHeader);
        this.checkUnit(prHeader);
        prHeader.validInvoiceDetail();
        prHeader.createValidateNonNull();
        prHeader.validUniqueIndex(this.prHeaderRepository);
        prHeader.setLocalCurrency(this.prHeaderRepository.selectPrLocalCurrencyCode(prHeader.getTenantId(), prHeader.getCompanyId()));
        prHeader.setPrLineList(this.prLineService.updatePrLines(prHeader));
        String tenantNum = TenantInfoHelper.selectByTenantId(prHeader.getTenantId()).getTenantNum();

        try {
            TaskResultBox taskResultBox = AdaptorTaskHelper.executeAdaptorTask(PR_TASK_CODE, tenantNum, prHeader);
            PrHeader header = (PrHeader) taskResultBox.get(0, PrHeader.class);
            BeanUtils.copyProperties(header, prHeader, CopyUtils.getNullPropertyNames(header));
        } catch (TaskNotExistException var5) {
            LOGGER.info(LOG_MSG_SPUC_PR_HEADER_UPDATE_AMOUNT, new Object[]{tenantNum, var5.getMessage(), var5.getStackTrace()});
        }
        //当申请类型为“计划申请” PLAN时 项目申请”PROJECT，预算总金额必填，申请行“预估单价（含税）”字段非必填，同时取消，行金额相加=申请总额的逻辑。
        if (!PrConstant.PrType.PR_TYPE_PLAN.equals(prHeader.getPrTypeCode()) &&
                !PrConstant.PrType.PR_TYPE_PROJECT.equals(prHeader.getPrTypeCode())
        ) {
            prHeader.batchMaintainDateAndCountAmount(this.prLineRepository);
        }else{
            //当申请类型为“计划申请” PLAN时 项目申请”PROJECT 成本中心 业务事项 产品类型 只能维护同一值
            // 保存时校验 如果不同报错：计划申请和项目申请只能维护同一成本中心、业务事项、产品类型
            this.checkLines(prHeader.getPrLineList());
        }

        PrHeaderVO oldPrHeaderVO;
        PrHeaderVO newPrHeaderVO;
        if (prHeader.sourcePlatformIsRule()) {
            oldPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
            prHeader.setPreviousPrStatusCode(oldPrHeaderVO.getPrStatusCode());
            if (prHeader.getRequestedBy() == null) {
                prHeader.setRequestedBy(oldPrHeaderVO.getRequestedBy());
            }

            this.prHeaderRepository.updateOptional(prHeader, HEADER_UPDATE_OPTIONAL);
            newPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
            this.prActionService.headerDataChangeDetection(oldPrHeaderVO, newPrHeaderVO);
        } else {
            oldPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
            prHeader.setPreviousPrStatusCode(oldPrHeaderVO.getPrStatusCode());
            this.prHeaderRepository.updateByPrimaryKeySelective(prHeader);
            this.prHeaderRepository.updateOptional(prHeader, PrHeader.SRM_UPDATE_FIELD_LIST);
            newPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
            this.prActionService.headerDataChangeDetection(oldPrHeaderVO, newPrHeaderVO);
        }

        return prHeader;
    }

    private void checkLines(List<PrLine> prLineList) {
        HashSet<Long> costIdSet = new HashSet<>();
        HashSet<String> wbsCodeSet = new HashSet<>();
//        HashSet<Long> costIdSet = new HashSet<>();
        prLineList.forEach(line->{
            costIdSet.add(line.getCostId());
            wbsCodeSet.add(line.getWbsCode());
        });
        if(costIdSet.size()>1||
                wbsCodeSet.size()>1
        ){
            throw new CommonException("error.cost.different");
        }
    }

    private void checkUnit(PrHeader prHeader) {
        Iterator var2 = prHeader.getPrLineList().iterator();

        while (var2.hasNext()) {
            PrLine prLine = (PrLine) var2.next();
            Integer flag = prLine.getUnitFlag();
            if (prLine.getItemId() != null && BaseConstants.Flag.YES.equals(flag) && prHeader.getPrSourcePlatform().equals("SRM")) {
                List<ItemListDTO> content = this.prLineMapper.relatePurchasePriceItemList(prHeader.getTenantId(), prLine.getItemCode(), prLine.getItemName(), (String) null, (Long) null, (Long) null, prHeader.getCompanyId());
                if (content != null && content.size() > 0) {
                    if (((ItemListDTO) content.get(0)).getOrderUomName() != null) {
                        if (!((ItemListDTO) content.get(0)).getOrderUomId().equals(prLine.getUomId())) {
                            throw new CommonException("error.pr.item_unit_error", new Object[0]);
                        }
                    } else if (!((ItemListDTO) content.get(0)).getPrimaryUomId().equals(prLine.getUomId())) {
                        throw new CommonException("error.pr.item_unit_error", new Object[0]);
                    }
                }
            }
        }

    }
}
