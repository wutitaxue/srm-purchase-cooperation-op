package org.srm.purchasecooperation.cux.pr.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.pr.api.dto.HeaderQueryDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.PlanHeaderAttachementToBpmDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.PlanHeaderExportDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.PlanHeader;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderExportVO;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderVO;

import java.util.List;

/**
 * 采购计划表资源库
 *
 * @author bin.zhang06@hand-china.com 2021-03-15 12:30:00
 */
public interface RCWLPlanHeaderRepository extends BaseRepository<PlanHeader> {
    /**
     * 查询全部
     * @param pageRequest
     * @param planHeaderParam
     * @return
     */
    Page<PlanHeaderVO> listPlanHeader(PageRequest pageRequest, HeaderQueryDTO planHeaderParam);

//    /**
//     * 导出数据查询
//     * @param planHeader
//     * @param pageRequest
//     * @return
//     */
//    Page<PlanHeaderExportDTO> pageAllPlanHeader(PlanHeader planHeader, PageRequest pageRequest);

    /**
     * 导出数据查询
     * @param pageRequest
     * @param planHeaderExportDTO
     * @return
     */
    Page<PlanHeaderExportVO> exportPlanHeaders(PageRequest pageRequest, PlanHeaderExportDTO planHeaderExportDTO);

    /**
     * 查询计划id
     * @param prHeaderId
     * @param lineNum
     * @return
     */
    Long selectPlanIdByPrIdAndLineNum(Long prHeaderId, String lineNum);


  /**
  * 校验采购申请头行是否重复
  *
  */
    boolean checkPrNumRep(String prNum, String lineNum, Long tenantId);
    /**
     * 校验该公司code是否存在
     *导入时字段是name，实际输入的是公司编码
     */
    boolean checkCompanyExist(String companyNum);
    /**
     * 校验预算科目是否存在
     *
     */
    boolean checkBudgetAccountExist(String budgetAccount, Long tenantId);
    /**
     * 校验需求人是否存在
     *
     */
    boolean checkDemandersExist(String demanders, Long tenantId);
    /**
     * 校验经办人是否存在
     *
     */
    boolean checkAgentExist(String agent, Long tenantId);

    /**
     * 查询公司
     * @param companyName
     * @param tenantId
     * @return
     */
    String selectCompanyName(String companyName, Long tenantId);

    /**
     * 查询公司id
     * @param companyName
     * @param tenantId
     * @return
     */
    Long selectCompanyId(String companyName, Long tenantId);

    /**
     * 查询预算科目
     * @param budgetAccount
     * @param tenantId
     * @return
     */
    String selectBudgetAccount(String budgetAccount, Long tenantId);

    /**
     * 查询需求人
     * @param demanders
     * @param tenantId
     * @return
     */
    String selectDemanders(String demanders, Long tenantId);

    /**
     * 查询代理人
     * @param agent
     * @param tenantId
     * @return
     */
    String selectAgent(String agent, Long tenantId);

    /**
     * 提交采购计划，生成新的流程编号
     * @param list
     * @param organizationId
     */
    void submitPlanHeader(List<PlanHeader> list, Long organizationId);

    /**
     * 计算当前创建人本月创建单据数量
     * @param userId
     * @param organizationId
     * @return
     */
    Integer calThisMonthNumber(Long userId, Long organizationId);

  /**
   * 查询当前创建人合同实际完成时间在本月的数量
   * @param userId
   * @param organizationId
   * @return
   */
    Integer calLastMonthNumber(Long userId, Long organizationId);

  /**
   * 查询当前创建人合同实际完成时间在上月的数量
   * @param userId
   * @param organizationId
   * @return
   */
  Integer calLastMonthComplete(Long userId, Long organizationId);

  /**
   * 批量查询采购计划附件信息
   * @param list
   * @param organizationId
   * @return
   */
  List<PlanHeaderAttachementToBpmDTO> batchSelectAttachmentsInfo(List list, Long organizationId);
}
