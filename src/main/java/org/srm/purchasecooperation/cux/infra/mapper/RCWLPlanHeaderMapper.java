package org.srm.purchasecooperation.cux.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.api.dto.HeaderQueryDTO;
import org.srm.purchasecooperation.cux.api.dto.PlanHeaderExportDTO;
import org.srm.purchasecooperation.cux.domain.entity.PlanHeader;
import org.srm.purchasecooperation.cux.domain.vo.PlanHeaderExportVO;
import org.srm.purchasecooperation.cux.domain.vo.PlanHeaderVO;

import java.util.List;

/**
 * 采购计划表Mapper
 *
 * @author bin.zhang06@hand-china.com 2021-03-15 12:30:00
 */
public interface RCWLPlanHeaderMapper extends BaseMapper<PlanHeader> {

    List<PlanHeaderVO> listPlanHeader(HeaderQueryDTO planHeaderParam);

    List<PlanHeaderExportDTO> exportAllPlanHeader(@Param("planHeader") PlanHeader planHeader);

    List<PlanHeaderExportVO> selectPrHeaderExport(PlanHeaderExportDTO planHeaderExportDTO);

    Long selectPlanIdByPrIdAndLineNum(@Param("prHeaderId") Long prHeaderId, @Param("lineNum") String lineNum);


    Integer checkPrNumRep(@Param("prNum") String prNum, @Param("lineNum") String lineNum, @Param("tenantId") Long tenantId);

    Integer checkCompanyExist(@Param("companyNum") String companyNum);

    Integer checkudgetAccountExist(@Param("budgetAccount") String budgetAccount, @Param("tenantId") Long tenantId);

    Integer checkDemandersExist(@Param("demanders") String demanders, @Param("tenantId") Long tenantId);

    Integer checkAgentExist(@Param("agent") String agent, @Param("tenantId") Long tenantId);

    String selectCompanyName(@Param("companyName") String companyName, @Param("tenantId") Long tenantId);

    Long selectCompanyId(@Param("companyName") String companyName, @Param("tenantId") Long tenantId);

    String selectBudgetAccount(@Param("budgetAccount") String budgetAccount, @Param("tenantId") Long tenantId);

    String selectDemanders(@Param("demanders") String demanders, @Param("tenantId") Long tenantId);

    String selectAgent(@Param("agent") String agent, @Param("tenantId") Long tenantId);
}
