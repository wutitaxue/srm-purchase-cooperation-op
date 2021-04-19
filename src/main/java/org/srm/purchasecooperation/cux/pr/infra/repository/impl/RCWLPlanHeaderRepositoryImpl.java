package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.api.dto.HeaderQueryDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.PlanHeaderExportDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.PlanHeader;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPlanHeaderRepository;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderExportVO;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderVO;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RCWLPlanHeaderMapper;

import java.util.List;

/**
 * 采购计划表 资源库实现
 *
 * @author bin.zhang06@hand-china.com 2021-03-15 12:30:00
 */
@Component
public class RCWLPlanHeaderRepositoryImpl extends BaseRepositoryImpl<PlanHeader> implements RCWLPlanHeaderRepository {
    @Autowired
    private RCWLPlanHeaderMapper rcwlPlanHeaderMapper;

    @Override
    @ProcessLovValue
    public Page<PlanHeaderVO> listPlanHeader(PageRequest pageRequest, HeaderQueryDTO planHeaderParam) {

        return PageHelper.doPageAndSort(pageRequest, () -> rcwlPlanHeaderMapper.listPlanHeader(planHeaderParam));
    }

//    @Override
//    public Page<PlanHeaderExportDTO> pageAllPlanHeader(PlanHeader planHeader, PageRequest pageRequest) {
//        return PageHelper.doPageAndSort(pageRequest, () -> rcwlPlanHeaderMapper.exportAllPlanHeader(planHeader));
//    }

    @Override
    @ProcessLovValue
    public Page<PlanHeaderExportVO> exportPlanHeaders(PageRequest pageRequest, PlanHeaderExportDTO planHeaderExportDTO) {
        return PageHelper.doPageAndSort(pageRequest, () -> rcwlPlanHeaderMapper.selectPrHeaderExport(planHeaderExportDTO));
    }

    @Override
    public Long selectPlanIdByPrIdAndLineNum(Long prHeaderId, String lineNum) {
        return this.rcwlPlanHeaderMapper.selectPlanIdByPrIdAndLineNum(prHeaderId, lineNum);
    }
    /**
     * 校验采购申请头行是否重复
     *
     * @param prNum
     * @param lineNum
     */
    @Override
    public boolean checkPrNumRep(String prNum, String lineNum,Long tenantId) {
        Integer count = this.rcwlPlanHeaderMapper.checkPrNumRep(prNum, lineNum,tenantId);
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验该公司code是否存在
     * 导入时字段是name，实际输入的是公司编码
     *
     * @param companyNum
     */
    @Override
    public boolean checkCompanyExist(String companyNum) {
        Integer count = this.rcwlPlanHeaderMapper.checkCompanyExist(companyNum);
        if (count == 0) {
            return false;
        } else {
            return true ;
        }
    }

    /**
     * 校验预算科目是否存在
     *
     * @param budgetAccount
     */
    @Override
    public boolean checkBudgetAccountExist(String budgetAccount,Long tenantId) {
        Integer count = this.rcwlPlanHeaderMapper.checkudgetAccountExist(budgetAccount,tenantId);
        if (count == 0) {
            return false;
        } else {
            return true ;
        }
    }

    /**
     * 校验需求人是否存在
     *
     * @param demanders
     * @param tenantId
     */
    @Override
    public boolean checkDemandersExist(String demanders, Long tenantId) {
        Integer count = this.rcwlPlanHeaderMapper.checkDemandersExist(demanders,tenantId);
        if (count == 0) {
            return false;
        } else {
            return true ;
        }
    }

    /**
     * 校验经办人是否存在
     *
     * @param agent
     * @param tenantId
     */
    @Override
    public boolean checkAgentExist(String agent, Long tenantId) {
        Integer count = this.rcwlPlanHeaderMapper.checkAgentExist(agent,tenantId);
        if (count == 0) {
            return false;
        } else {
            return true ;
        }
    }

    @Override
    public String selectCompanyName(String companyName, Long tenantId) {
        return this.rcwlPlanHeaderMapper.selectCompanyName(companyName,tenantId);
    }

    @Override
    public Long selectCompanyId(String companyName, Long tenantId) {
        return this.rcwlPlanHeaderMapper.selectCompanyId(companyName,tenantId);
    }

    @Override
    public String selectBudgetAccount(String budgetAccount, Long tenantId) {
        return this.rcwlPlanHeaderMapper.selectBudgetAccount(budgetAccount,tenantId);
    }

    @Override
    public String selectDemanders(String demanders, Long tenantId) {
        return this.rcwlPlanHeaderMapper.selectDemanders(demanders,tenantId);
    }

    @Override
    public String selectAgent(String agent, Long tenantId) {
        return this.rcwlPlanHeaderMapper.selectAgent(agent,tenantId);
    }

    /**
     * 提交采购计划，生成新的流程编号
     *
     * @param list
     * @param organizationId
     */
    @Override
    public void submitPlanHeader(List<PlanHeader> list, Long organizationId) {
        this.rcwlPlanHeaderMapper.submitPlanHeader(list,organizationId);

    }


}
