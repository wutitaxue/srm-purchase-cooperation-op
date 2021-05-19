package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.util.List;

/**
 * @author bin.zhang
 */
@Component
public interface RCWLItfPrDataMapper {
    /**
     * 查找sap公司编码
     * @param companyId
     * @param tenantId
     * @return
     */
    String selectSapCode(@Param("companyId") Long companyId, @Param("tenantId") Long tenantId);

    /**
     * 查找预算科目名称
     * @param budgetAccountNum
     * @param tenantId
     * @return
     */
   // String selectBudgetAccountName(@Param("budgetAccountNum") String budgetAccountNum, @Param("tenantId") Long tenantId);

    /**
     * 查找wbs名称
     * @param wbsCode
     * @param prLineId
     * @return
     */
    String selectWbsName(@Param("wbsCode") String wbsCode, @Param("prLineId") Long prLineId);

    /**
     * 查找wbs
     * @param wbs
     * @param prLineId
     * @return
     */
    String selectWbsCode(@Param("wbs") String wbs, @Param("prLineId") Long prLineId);

    /**
     * 查找业务用途编码
     * @param budgetAccountId
     * @return
     */
    String selectBudgetAccountNum(@Param("budgetAccountId")Long budgetAccountId);
    /**
     * 查找业务用途名称
     * @param budgetAccountId
     * @return
     */
    String selectBudgetAccountName(@Param("budgetAccountId")Long budgetAccountId);

    /**
     * 获取申请头信息（接口）
     * @param prNum
     * @param tenantId
     * @return
     */
    PrHeader selectPrHeaderByPrNum(@Param("prNum")String prNum, @Param("tenantId")Long tenantId);

    /**
     * 获取采购申请行信息（接口）
     * @param prHeaderId
     * @param tenantId
     * @return
     */
    List<PrLine> selectPrLineListById(@Param("prHeaderId")Long prHeaderId, @Param("tenantId")Long tenantId);

    /**
     * 获取申请行信息（主要是变更前的占用金额）
     * @param prHeaderId
     * @param tenantId
     * @return
     */
    List<PrLine> selectPrLineListByIdOld(@Param("prHeaderId")Long prHeaderId, @Param("tenantId")Long tenantId);
    /**
     * 校验是否可以触发接口
     *
     * @param prHeaderId
     * @param tenantId
     * @return
     */
    Integer validateInvokeItf(@Param("prHeaderId")Long prHeaderId, @Param("tenantId")Long tenantId);
}
