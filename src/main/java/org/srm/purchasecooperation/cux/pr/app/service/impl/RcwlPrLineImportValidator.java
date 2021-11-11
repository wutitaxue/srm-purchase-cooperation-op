package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidator;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidators;
import org.hzero.core.message.MessageAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.pr.domain.vo.RcwlPrLineImportVO;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrImportMapper;
import org.srm.purchasecooperation.pr.app.service.impl.PrLineImportValidator;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.vo.PrLineImportVO;
import org.srm.purchasecooperation.pr.domain.vo.UnitVO;
import org.srm.web.annotation.Tenant;

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@ImportValidators({@ImportValidator(
        templateCode = "SPRM.PR_LINE"
)})
@Tenant("SRM-RCWL")
public class RcwlPrLineImportValidator extends PrLineImportValidator {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RcwlPrImportMapper prImportMapper;
    @Autowired
    private PrHeaderRepository prHeaderRepository;

    public RcwlPrLineImportValidator() {
    }

    @Override
    public boolean validate(String data) {
        RcwlPrLineImportVO prLineImportVO;
        try {
            prLineImportVO = this.objectMapper.readValue(data, RcwlPrLineImportVO.class);
        } catch (IOException var6) {
            super.getContext().addErrorMsg(MessageAccessor.getMessage("error.data_invalid").desc());
            return false;
        }

        Long tenantId = DetailsHelper.getUserDetails().getTenantId();
        Long prHeaderId = Long.valueOf(this.getArgs().get("prHeaderId").toString());
        PrHeader prHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(prHeaderId);
        if (prHeader == null && !prHeader.getTenantId().equals(tenantId)) {
            this.addErrorMsg("error.data_invalid", prLineImportVO, (String)null);
            return false;
        } else if (StringUtils.isNotEmpty(super.getContext().getErrorMsg())) {
            return false;
        } else {
            prLineImportVO.setTenantId(tenantId);
            prLineImportVO.setOuId(prHeader.getOuId());
            prLineImportVO.setPurchaseOrgId(prHeader.getPurchaseOrgId());
            prLineImportVO.setCompanyId(prHeader.getCompanyId());
            return this.importValidator(prLineImportVO);
        }
    }

    private boolean importValidator(RcwlPrLineImportVO prLineImportVO) {
        //需求结束日期不可小于需求开始日期
        if (prLineImportVO.getNeededDate().isBefore(prLineImportVO.getStartDate())) {
            getContext().addErrorMsg("需求结束日期不可小于需求开始日期");
            return false;
        }
        //需要校验导入模板中的物料名称、规格、型号、单位和物料基本信息中（smdm_item）数据是否一致
        if (StringUtils.isNotEmpty(prLineImportVO.getItemCode())) {
            List<PrLine> prLineVOs = this.prImportMapper.queryItemInfo(prLineImportVO);
            PrLine prLineVO = prLineVOs.get(0);
            String checkVarchar = prLineVO.getAttributeVarchar15();
            if (!StringUtils.equals(checkVarchar, "1")) {
                if(!StringUtils.equals(prLineImportVO.getItemName(), prLineVO.getItemName())){
                    getContext().addErrorMsg("导入模板中的物料名称与物料基本信息的物料名称不一致");
                    return false;
                }
                if(!StringUtils.equals(prLineImportVO.getItemSpecs(), prLineVO.getItemSpecs())){
                    getContext().addErrorMsg("导入模板中的规格与物料基本信息的规格不一致");
                    return false;
                }
                if(!StringUtils.equals(prLineImportVO.getItemModel(), prLineVO.getItemModel())){
                    getContext().addErrorMsg("导入模板中的型号与物料基本信息的型号不一致");
                    return false;
                }
                if(!StringUtils.equals(prLineImportVO.getUomCode(), prLineVO.getAttributeVarchar1())){
                    getContext().addErrorMsg("导入模板中的计量单位编码与物料基本信息的计量单位编码不一致");
                    return false;
                }

            }
        }
        //this.codeValid(prLineImportVO, this.prImportMapper::queryInvOrganizationInfo, prLineImportVO.getInvOrganizationCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("invOrganizationCode"), "sprm.pr_line_import.inv_organization_error", true);
        //this.codeValid(prLineImportVO, this.prImportMapper::queryInventoryInfo, prLineImportVO.getInventoryCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("inventoryCode"), "sprm.pr_line_import.inventory_code_error", false);
        if (!prLineImportVO.isValidFlag()) {
            return false;
        } else {
            if (StringUtils.isEmpty(prLineImportVO.getItemCode())) {
                if (StringUtils.isEmpty(prLineImportVO.getItemName())) {
                    this.addErrorMsg("sprm.pr_line_import.item_code_and_name_be_lost_error", prLineImportVO, (String)null);
                    return false;
                }

                this.codeValid(prLineImportVO, this.prImportMapper::queryUomInfo, prLineImportVO.getUomCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("uomCode"), "sprm.pr_line_import.uom_error", true);
            } else {
                this.codeValid(prLineImportVO, this.prImportMapper::queryItemInfo, prLineImportVO.getItemCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("itemCode"), "sprm.pr_line_import.item_error", false);
            }

            if (!prLineImportVO.isValidFlag()) {
                return false;
            } else {
                this.codeValid(prLineImportVO, this.prImportMapper::queryCategoryInfo, prLineImportVO.getCategoryCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("categoryCode"), "sprm.pr_line_import.category_error", false);
                if (!prLineImportVO.isValidFlag()) {
                    return false;
                } else {
                    if (prLineImportVO.getNeededDate() == null) {
                        this.addErrorMsg("sprm.pr_line_import.needed_date_error", prLineImportVO, (String)null);
                    }

                    //this.userValid(prLineImportVO, this.prImportMapper::queryUserInfo, prLineImportVO.getRequestedLoginName(), (String)PrLineImportVO.FIELD_NAME_MAP.get("requestedLoginName"), "sprm.pr_line_import.requested_user_error");
                    if (!prLineImportVO.isValidFlag()) {
                        return false;
                    } else {
                        //this.codeValid(prLineImportVO, this.prImportMapper::queryPurchaseAgent, prLineImportVO.getPurchaseAgentCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("purchaseAgentCode"), "sprm.pr_line_import.purchase_agent_error", false);
                        if (!prLineImportVO.isValidFlag()) {
                            return false;
                        } else {
                            this.codeValid(prLineImportVO, this.prImportMapper::queryTaxInfo, prLineImportVO.getTaxCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("taxCode"), "sprm.pr_line_import.tax_error", false);
                            if (!prLineImportVO.isValidFlag()) {
                                return false;
                            } else {
                                if (StringUtils.isNotEmpty(prLineImportVO.getExpBearDepUnitCode())) {
                                    UnitVO unitVO = this.prImportMapper.queryUnitInfo(prLineImportVO.getTenantId(), prLineImportVO.getExpBearDepUnitCode(), prLineImportVO.getCompanyId(), "G");
                                    if (unitVO == null) {
                                        unitVO = this.prImportMapper.queryUnitInfo(prLineImportVO.getTenantId(), prLineImportVO.getExpBearDepUnitCode(), prLineImportVO.getCompanyId(), "C");
                                        if (unitVO == null) {
                                            unitVO = this.prImportMapper.queryUnitInfoUnlimited(prLineImportVO.getTenantId(), prLineImportVO.getExpBearDepUnitCode());
                                            if (unitVO == null) {
                                                this.addErrorMsg("sprm.pr_line_import.unit_error", prLineImportVO, (String)PrLineImportVO.FIELD_NAME_MAP.get("expBearDepUnitCode"));
                                            }
                                        }
                                    }
                                }

                                if (!prLineImportVO.isValidFlag()) {
                                    return false;
                                } else {
                                    //this.codeValid(prLineImportVO, this.prImportMapper::querySupplierInfo, prLineImportVO.getAdviseSupplierCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("adviseSupplierCode"), "sprm.pr_line_import.supplier_error", false);
                                    if (!prLineImportVO.isValidFlag()) {
                                        return false;
                                    } else {
                                        this.codeValid(prLineImportVO, this.prImportMapper::queryCostInfo, prLineImportVO.getCostCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("costCode"), "sprm.pr_line_import.cost_error", false);
                                        if (!prLineImportVO.isValidFlag()) {
                                            return false;
                                        } else {
                                            this.codeValid(prLineImportVO, this.prImportMapper::queryAccountSubjectInfo, prLineImportVO.getAccountSubjectNum(), (String)PrLineImportVO.FIELD_NAME_MAP.get("accountSubjectNum"), "sprm.pr_line_import.account_subject_error", false);
                                            if (!prLineImportVO.isValidFlag()) {
                                                return false;
                                            } else {
                                                this.userValid(prLineImportVO, this.prImportMapper::queryUserInfo, prLineImportVO.getKeeperUserLoginName(), (String)PrLineImportVO.FIELD_NAME_MAP.get("keeperUserLoginName"), "sprm.pr_line_import.keeper_user_error");
                                                if (!prLineImportVO.isValidFlag()) {
                                                    return false;
                                                } else {
                                                    this.userValid(prLineImportVO, this.prImportMapper::queryUserInfo, prLineImportVO.getAccepterUserLoginName(), (String)PrLineImportVO.FIELD_NAME_MAP.get("accepterUserLoginName"), "sprm.pr_line_import.accepter_user_error");
                                                    if (!prLineImportVO.isValidFlag()) {
                                                        return false;
                                                    } else {
                                                        this.codeValid(prLineImportVO, this.prImportMapper::queryAccountAssignType, prLineImportVO.getAccountAssignTypeCode(), (String)PrLineImportVO.FIELD_NAME_MAP.get("accountAssignTypeCode"), "sprm.pr_line_import.account_assign_error", false);
                                                        this.codeValid(prLineImportVO, this.prImportMapper::queryWbs, prLineImportVO.getWbs(), (String)PrLineImportVO.FIELD_NAME_MAP.get("accountAssignTypeCode"), "sprm.pr_line_import.wbs", false);
                                                        return prLineImportVO.isValidFlag();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addErrorMsg(String messageCode, PrLineImportVO prLineImportVO, String fieldName) {
        super.getContext().addErrorMsg(MessageAccessor.getMessage(messageCode, new String[]{fieldName}).desc());
        prLineImportVO.setValidFlag(false);
    }

    private void codeValid(PrLineImportVO prLineImportVO, Function<PrLineImportVO, Object> mapper, String fieldValue, String fieldName, String messageCode, boolean requiredFlag) {
        if (StringUtils.isNotEmpty(fieldValue)) {
            Object obj = mapper.apply(prLineImportVO);
            if (obj == null) {
                this.addErrorMsg(messageCode, prLineImportVO, fieldName);
            } else if (obj instanceof List && ((List)obj).isEmpty()) {
                this.addErrorMsg(messageCode, prLineImportVO, fieldName);
            }
        } else if (requiredFlag) {
            this.addErrorMsg("sprm.pr_line_import.is_empty_error", prLineImportVO, fieldName);
        }

    }

    private void userValid(PrLineImportVO prLineImportVO, BiFunction<Long, String, Object> mapper, String fieldValue, String fieldName, String messageCode) {
        if (StringUtils.isNotEmpty(fieldValue)) {
            Object obj = mapper.apply(prLineImportVO.getTenantId(), fieldValue);
            if (obj == null) {
                this.addErrorMsg(messageCode, prLineImportVO, fieldName);
            }
        }

    }
}
