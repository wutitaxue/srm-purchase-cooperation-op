package org.srm.purchasecooperation.cux.pr.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.imported.domain.entity.ImportData;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.api.dto.HeaderQueryDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.PlanHeader;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderImportVO;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderVO;

import java.io.IOException;
import java.util.List;

/**
 * 采购计划表应用服务
 *
 * @author bin.zhang06@hand-china.com 2021-03-15 12:30:00
 */
@Service
public interface RCWLPlanHeaderService {
    /**
     * 查询全部
     * @param pageRequest
     * @param organizationId
     * @param planHeaderParam
     * @return
     */
    Page<PlanHeaderVO> listPlanHeader(PageRequest pageRequest, Long organizationId, HeaderQueryDTO planHeaderParam);

    /**
     * 批量取消
     * @param organizationId
     * @param planHeaderList
     * @return
     */
    List<PlanHeader> batchCancelPlanHeader(Long organizationId, List<PlanHeader> planHeaderList);

    /**
     * 创建保存
     * @param planHeader
     */
    void createAndUpdate(PlanHeader planHeader);

    /**
     * 附件保存
     * @param tenantId
     * @param planHeader
     */
    void saveAttachment(Long tenantId, PlanHeader planHeader);

    /**
     * 导入校验
     * @param planHeaderImportVO
     * @param importData
     * @param tenantId
     * @return
     */
    boolean checkData(PlanHeaderImportVO planHeaderImportVO, ImportData importData, Long tenantId);

    /**
     * 采购计划提交
     * @param planHeaderVOS
     * @param organizationId
     * @return
     */

    PlanHeaderVO submitPlanHeader(List<PlanHeaderVO> planHeaderVOS, Long organizationId) throws IOException;

    /**
     * bpm回传更新审批状态
     * @param processNum
     * @param approveFlag
     */
    void updateStateFromBPM(String processNum, String approveFlag);

    void fixDataOne(Long organizationId);
}
