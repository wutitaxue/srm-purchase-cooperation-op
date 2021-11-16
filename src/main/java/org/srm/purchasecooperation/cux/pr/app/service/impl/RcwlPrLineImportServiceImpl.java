package org.srm.purchasecooperation.cux.pr.app.service.impl;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/6/7 14:39
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.convert.CommonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.purchasecooperation.cux.pr.domain.vo.BudgetAccountVO;
import org.srm.purchasecooperation.cux.pr.domain.vo.RcwlPrLineImportVO;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RCWLPrLineMapper;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrImportMapper;
import org.srm.purchasecooperation.order.api.dto.UserCacheDTO;
import org.srm.purchasecooperation.pr.api.dto.PrHeaderCurrencyDto;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.app.service.impl.PrLineImportServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.pr.domain.vo.UnitVO;
import org.srm.web.annotation.Tenant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ImportService(
        templateCode = "SPRM.PR_LINE"
)
@Tenant("SRM-RCWL")
public class RcwlPrLineImportServiceImpl extends PrLineImportServiceImpl {
    public static final String ENABLE_UNIT_CONTROL = "000112";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RcwlPrImportMapper prImportMapper;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrHeaderService prHeaderService;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private RCWLPrLineMapper rcwlPrLineMapper;
    public RcwlPrLineImportServiceImpl() {
    }

    @Override
    public int getSize() {
        return 1000;
    }

    @Override
    public Boolean doImport(List<String> list) {
        String voStr = "[" + StringUtils.join(list.toArray(), ",") + "]";

        List prLineImportVOList;
        try {
            prLineImportVOList = this.objectMapper.readValue(voStr, this.objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{RcwlPrLineImportVO.class}));
        } catch (IOException var8) {
            return false;
        }

        if (CollectionUtils.isEmpty(prLineImportVOList)) {
            return true;
        } else {
            Long tenantId = DetailsHelper.getUserDetails().getTenantId();
            Long prHeaderId = Long.valueOf(this.getArgs().get("prHeaderId").toString());
            PrHeader prHeader = this.prHeaderRepository.selectByPrimaryKey(prHeaderId);
            if (prHeader != null && tenantId.equals(prHeader.getTenantId())) {
                List<PrLine> prLines = this.convertPrLine(prHeader, prLineImportVOList);
                this.importPrLine(prHeader, prLines);
                return true;
            } else {
                return false;
            }
        }
    }

    private List<PrLine> convertPrLine(PrHeader prHeader, List<RcwlPrLineImportVO> prLineImportVOList) {
        List<PrLine> prLineList = new ArrayList();
        Long tenantId = prHeader.getTenantId();

        PrLine prLine;
        for(Iterator var5 = prLineImportVOList.iterator(); var5.hasNext(); prLineList.add(prLine)) {
            RcwlPrLineImportVO prLineImportVO = (RcwlPrLineImportVO)var5.next();
            prLineImportVO.setTenantId(tenantId);
            prLineImportVO.setOuId(prHeader.getOuId());
            prLineImportVO.setPurchaseOrgId(prHeader.getPurchaseOrgId());
            prLineImportVO.setCompanyId(prHeader.getCompanyId());
            prLine = CommonConverter.beanConvert(PrLine.class, prLineImportVO);
            prLine.addHeaderField(prHeader);
            PrLine prLineTmp;
            //系统自动根据采购申请头上的公司给相应公司下的库存组织作为默认值
            PrLine prLineOrg = this.prImportMapper.queryInvOrganizationInfoByCompanyId(prHeader.getCompanyId());
            prLine.setInvOrganizationId(prLineOrg.getInvOrganizationId());
            prLine.setInvOrganizationName(prLineOrg.getInvOrganizationName());
            //新增业务用途字段
            if (StringUtils.isNotEmpty(prLineImportVO.getBudgetAccountNum())) {
                BudgetAccountVO budgetAccountVO = this.rcwlPrLineMapper.selectBudgetAccount(prLineImportVO.getBudgetAccountNum(),tenantId);
                if(!ObjectUtils.isEmpty(budgetAccountVO)){
                    prLine.setBudgetAccountId(budgetAccountVO.getBudgetAccountId());
                }
            }
            if (StringUtils.isNotEmpty(prLineImportVO.getInventoryCode())) {
                prLineTmp = this.prImportMapper.queryInventoryInfo(prLineImportVO);
                prLine.setInventoryId(prLineTmp.getInventoryId());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getItemCode())) {
                prLine.setItemCode(prLineImportVO.getItemCode());
                PrLine prLineVO = this.prImportMapper.queryCategoryInfo(prLineImportVO);
                String checkVarchar = prLineVO.getAttributeVarchar15();
                List<PrLine> prLines = this.prImportMapper.queryItemInfo(prLineImportVO);
                prLineTmp = prLines.get(0);
                prLine.setItemId(prLineTmp.getItemId());
                prLine.setItemAbcClass(prLineTmp.getItemAbcClass());
                Long uomId = this.prImportMapper.queryUomInfo(prLineImportVO).getUomId();
                prLine.setUomId(uomId);
                //String itemOrgUomFlag = this.customizeSettingHelper.queryBySettingCode(tenantId, "000112");
                //if (StringUtils.isNotBlank(itemOrgUomFlag) && String.valueOf(Flag.YES).equals(itemOrgUomFlag)) {
                //    Long uomId = this.prImportMapper.selectItemOrgUom(prLine);
                //    if (uomId != null) {
                //        prLine.setUomId(uomId);
                //    }
                //}
                //导入行的物料名称、规格、型号、单位有值的话，物料名称、规格、型号以导入模板的为准，
                //单位需校验在系统中存在；如果模板中没有值，那么导入之后，系统自动根据物料编码带出。
                if (StringUtils.equals(checkVarchar, BaseConstants.Flag.YES.toString())) {
                    if (StringUtils.isEmpty(prLine.getItemName())) {
                        prLine.setItemName(prLineTmp.getItemName());
                    }
                    if (StringUtils.isEmpty(prLine.getItemSpecs())) {
                        prLine.setItemSpecs(prLineTmp.getItemSpecs());
                    }
                    if (StringUtils.isEmpty(prLine.getItemModel())) {
                        prLine.setItemModel(prLineTmp.getItemModel());
                    }
                    if (ObjectUtils.isEmpty(prLine.getUomId())) {
                        prLine.setUomId(prLineTmp.getUomId());
                    }
                    if (prLines.size() == 1) {
                        prLine.setCategoryId(prLineTmp.getCategoryId());
                    }
                } else {
                    prLine.setItemName(prLine.getItemName());
                    prLine.setItemSpecs(prLine.getItemSpecs());
                    prLine.setItemModel(prLine.getItemModel());
                    prLine.setUomId(prLineTmp.getUomId());
                    if (prLines.size() == 1) {
                        prLine.setCategoryId(prLineTmp.getCategoryId());
                    }
                }
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getCategoryCode()) && Objects.isNull(prLine.getCategoryId())) {
                prLine.setCategoryId(this.prImportMapper.queryCategoryInfo(prLineImportVO).getCategoryId());
            }

            prLine.setNeededDate(prLineImportVO.localDate2Date(prLineImportVO.getNeededDate()));
            if (StringUtils.isNotEmpty(prLineImportVO.getRequestedLoginName())) {
                UserCacheDTO userCacheDTO = this.prImportMapper.queryUserInfo(tenantId, prLineImportVO.getRequestedLoginName());
                prLine.setRequestedBy(userCacheDTO.getUserId());
                prLine.setPrRequestedName(userCacheDTO.getRealName());
            } else if (StringUtils.isNotEmpty(prHeader.getPrRequestedName())) {
                prLine.setRequestedBy(prHeader.getRequestedBy());
                prLine.setPrRequestedName(prHeader.getPrRequestedName());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getPurchaseAgentCode())) {
                prLineTmp = this.prImportMapper.queryPurchaseAgent(prLineImportVO);
                prLine.setPurchaseAgentId(prLineTmp.getPurchaseAgentId());
                prLine.getFlex().put("purchaseAgentId", prLine.getPurchaseAgentId());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getTaxCode())) {
                prLineTmp = this.prImportMapper.queryTaxInfo(prLineImportVO);
                prLine.setTaxRate(prLineTmp.getTaxRate());
                prLine.setTaxId(prLineTmp.getTaxId());
            }

            if (Objects.nonNull(prHeader.getOriginalCurrency())) {
                prLine.setCurrencyCode(prHeader.getOriginalCurrency());
            }

            PrHeaderCurrencyDto prHeaderCurrencyDto = this.prHeaderRepository.selectMdmAccurate(prHeader.getTenantId(), prHeader.getLocalCurrency());
            if (Objects.nonNull(prLine.getCurrencyCode())) {
                PrHeaderCurrencyDto prLineCurrencyDto = this.prHeaderRepository.selectMdmAccurate(prHeader.getTenantId(), prLine.getCurrencyCode());
                if (Objects.nonNull(prLineCurrencyDto)) {
                    prLine.countLineAmount(prLineCurrencyDto.getDefaultPrecision(), prLineCurrencyDto.getFinancialPrecision());
                } else {
                    prLine.countLineAmount();
                }
            } else {
                prLine.countLineAmount();
            }

            if (Objects.nonNull(prHeaderCurrencyDto)) {
                prLine.calculateLocalMoney(prHeaderCurrencyDto.getDefaultPrecision(), prHeaderCurrencyDto.getFinancialPrecision());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getAdviseSupplierCode())) {
                prLineTmp = this.prImportMapper.querySupplierInfo(prLineImportVO);
                prLine.setSupplierId(prLineTmp.getSupplierId());
                prLine.setSupplierCode(prLineTmp.getSupplierCode());
                prLine.setSupplierName(prLineTmp.getSupplierName());
                prLine.setSupplierCompanyId(prLineTmp.getSupplierCompanyId());
                prLine.setSupplierCompanyName(prLineTmp.getSupplierCompanyName());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getExpBearDepUnitCode())) {
                UnitVO unitVO = this.prImportMapper.queryUnitInfo(prLineImportVO.getTenantId(), prLineImportVO.getExpBearDepUnitCode(), prLineImportVO.getCompanyId(), "G");
                if (unitVO == null) {
                    unitVO = this.prImportMapper.queryUnitInfo(prLineImportVO.getTenantId(), prLineImportVO.getExpBearDepUnitCode(), prLineImportVO.getCompanyId(), "C");
                    if (unitVO == null) {
                        unitVO = this.prImportMapper.queryUnitInfoUnlimited(prLineImportVO.getTenantId(), prLineImportVO.getExpBearDepUnitCode());
                    }
                }

                prLine.setExpBearDepId(String.valueOf(unitVO.getUnitId()));
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getCostCode())) {
                prLineTmp = this.prImportMapper.queryCostInfo(prLineImportVO);
                prLine.setCostId(prLineTmp.getCostId());
                prLine.setCostCode(prLineTmp.getCostCode());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getAccountSubjectNum())) {
                prLineTmp = this.prImportMapper.queryAccountSubjectInfo(prLineImportVO);
                prLine.setAccountSubjectId(prLineTmp.getAccountSubjectId());
                prLine.setAccountSubjectNum(prLineTmp.getAccountSubjectNum());
            }

            UserCacheDTO userCacheDTO;
            if (StringUtils.isNotEmpty(prLineImportVO.getKeeperUserLoginName())) {
                userCacheDTO = this.prImportMapper.queryUserInfo(tenantId, prLineImportVO.getKeeperUserLoginName());
                prLine.setKeeperUserId(userCacheDTO.getUserId());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getAccepterUserLoginName())) {
                userCacheDTO = this.prImportMapper.queryUserInfo(tenantId, prLineImportVO.getAccepterUserLoginName());
                prLine.setAccepterUserId(userCacheDTO.getUserId());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getAccountAssignTypeCode())) {
                prLineTmp = this.prImportMapper.queryAccountAssignType(prLineImportVO);
                prLine.setAccountAssignTypeId(prLineTmp.getAccountAssignTypeId());
            }

            if (StringUtils.isNotEmpty(prLineImportVO.getWbs())) {
                prLineTmp = this.prImportMapper.queryWbs(prLineImportVO);
                prLine.setWbs(prLineTmp.getWbs());
                prLine.setWbsCode(prLineTmp.getWbsCode());
            }
        }

        return prLineList;
    }

    private void importPrLine(PrHeader prHeader, List<PrLine> prLineList) {
        prHeader.setPrLineList(prLineList);
        this.prHeaderService.updatePrHeader(prHeader);
        prLineList = prLineList.stream().peek((prLine) -> {
            prLine.setPurchaseAgentId((Long)prLine.getFlex().get("purchaseAgentId"));
        }).filter((prLine) -> Objects.nonNull(prLine.getPurchaseAgentId())).collect(Collectors.toList());
        this.prLineRepository.batchUpdateOptional(prLineList, new String[]{"purchaseAgentId"});
    }
}
