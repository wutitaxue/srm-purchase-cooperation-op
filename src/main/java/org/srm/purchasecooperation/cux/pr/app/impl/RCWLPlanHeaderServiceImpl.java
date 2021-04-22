package org.srm.purchasecooperation.cux.pr.app.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.imported.domain.entity.ImportData;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.profile.ProfileClient;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.srm.purchasecooperation.cux.pr.api.dto.HeaderQueryDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPlanHeaderService;
import org.srm.purchasecooperation.cux.pr.domain.entity.PlanHeader;
import org.srm.purchasecooperation.cux.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPlanHeaderRepository;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPrLineRepository;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderImportVO;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderVO;
import org.srm.purchasecooperation.cux.pr.infra.constant.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 采购计划表应用服务默认实现
 *
 * @author bin.zhang06@hand-china.com 2021-03-15 12:30:00
 */
@Service
public class RCWLPlanHeaderServiceImpl implements RCWLPlanHeaderService {
    @Autowired
    private RCWLPlanHeaderRepository RCWLPlanHeaderRepository;
    @Autowired
    private RCWLPrLineRepository RCWLPrLineRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private ProfileClient profileClient;

    /**
     * 查询
     *
     * @param pageRequest
     * @param organizationId
     * @param planHeaderParam
     * @return
     */
    @Override
    @ProcessLovValue
    public Page<PlanHeaderVO> listPlanHeader(PageRequest pageRequest, Long organizationId, HeaderQueryDTO planHeaderParam) {
        planHeaderParam.setTenantId(organizationId);
        Page<PlanHeaderVO> headerPage = RCWLPlanHeaderRepository.listPlanHeader(pageRequest, planHeaderParam);
        return headerPage;
    }

    private static final Logger logger = LoggerFactory.getLogger(RCWLPlanHeaderServiceImpl.class);

    /**
     * 批量取消
     *
     * @param organizationId
     * @param planHeaderList
     * @return
     */
    @Override
    public List<PlanHeader> batchCancelPlanHeader(Long organizationId, List<PlanHeader> planHeaderList) {

        List<Long> ids = planHeaderList.stream().map(PlanHeader::getPlanId).distinct().collect(Collectors.toList());

        logger.info("ids:"+ids);
        List<PlanHeader> planHeaderList1 = RCWLPlanHeaderRepository.selectByIds(ids.stream().map(Object::toString).collect(Collectors.joining(",")));
        logger.info("planHeaderList1:"+planHeaderList1.toString());
        if (!CollectionUtils.isEmpty(planHeaderList1)) {
            planHeaderList1.forEach(planHeaderVo -> {
                if (Constants.PlanHeaderApprovalStatus.CANCEL.equals(planHeaderVo.getApprovalStatus())) {
                    throw new CommonException("已取消的不能再取消");
                }
                if (Constants.PlanHeaderApprovalStatus.SUBMITTED.equals(planHeaderVo.getApprovalStatus())) {
                    throw new CommonException("审批中的不能取消");
                }
                //状态设置为取消
                planHeaderVo.setApprovalStatus(Constants.PlanHeaderApprovalStatus.CANCEL);
                //更改申请行的计划编号为空
                RCWLPrLineRepository.updatePrLine(planHeaderVo.getPlanId(), organizationId);

            });
        }
        RCWLPlanHeaderRepository.batchUpdateOptional(planHeaderList1, PlanHeader.FIELD_APPROVAL_STATUS);
        return null;
    }


    /**
     * 创建保存采购计划
     *
     * @param planHeaderParam
     */
    @Override
    public void createAndUpdate(PlanHeader planHeaderParam) {
        //判断是新增还是更新
        Assert.notNull(planHeaderParam, BaseConstants.ErrorCode.NOT_NULL);
        logger.info("test", planHeaderParam.getPrHeaderId());
        logger.info("test1", planHeaderParam.toString());
        if (planHeaderParam.getPlanId() == null) {
            if (planHeaderParam.getPrHeaderId() == null) {
                planHeaderParam.setState(Constants.PlanHeaderState.NOT);
                String str = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SSRC.RCWL.PLAN_HEADER", "GLOBAL", "GLOBAL", (Map) null);
                planHeaderParam.setPlanNum(str);
                RCWLPlanHeaderRepository.insertSelective(planHeaderParam);
                logger.info("计划id:{}" + planHeaderParam.getPlanId());
            } else {
                planHeaderParam.setState(Constants.PlanHeaderState.ALREADY);
                String str = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SSRC.RCWL.PLAN_HEADER", "GLOBAL", "GLOBAL", (Map) null);
                planHeaderParam.setPlanNum(str);
                logger.info("plan_num:{}" + str);
                RCWLPlanHeaderRepository.insertSelective(planHeaderParam);
                logger.info("计划id:{}" + planHeaderParam.getPlanId());

                Long prLineId = RCWLPrLineRepository.selectPrLineId(planHeaderParam.getPrHeaderId(), planHeaderParam.getLineNum(), planHeaderParam.getTenantId());
                logger.info("prLineId:{}" + prLineId);
                System.out.println("prLineId:{}" + prLineId);
                //申请行表插入编号
                PrLine prLine = RCWLPrLineRepository.selectByPrimaryKey(prLineId);
                prLine.setAttributeBigint1(planHeaderParam.getPlanId());
                RCWLPrLineRepository.updateOptional(prLine, PrLine.FIELD_ATTRIBUTE_BIGINT1);
            }

        } else {
            PlanHeader planHeader = RCWLPlanHeaderRepository.selectByPrimaryKey(planHeaderParam.getPlanId());
            //根据申请表有无更新状态
            if (planHeaderParam.getPrHeaderId() == null) {
                planHeader.setState(Constants.PlanHeaderState.NOT);

            } else {
                planHeader.setState(Constants.PlanHeaderState.ALREADY);
                planHeader.setPrHeaderId(planHeaderParam.getPrHeaderId());
                planHeader.setPrLineId(planHeaderParam.getPrLineId());
                planHeader.setPrNum(planHeaderParam.getPrNum());
                planHeader.setLineNum(planHeaderParam.getLineNum());
            }
            planHeader.setAddFlag(planHeaderParam.getAddFlag());
            planHeader.setAgent(planHeaderParam.getAgent());
            planHeader.setAttachment(planHeaderParam.getAttachment());
            planHeader.setBidAmount(planHeaderParam.getBidAmount());
            planHeader.setBiddingMode(planHeaderParam.getBiddingMode());
            planHeader.setBidMethod(planHeaderParam.getBidMethod());
            planHeader.setBudgetAccount(planHeaderParam.getBudgetAccount());
            planHeader.setCompanyId(planHeaderParam.getCompanyId());
            planHeader.setCompanyName(planHeaderParam.getCompanyName());
            planHeader.setContractAmount(planHeaderParam.getContractAmount());
            planHeader.setCreationDateFrom(planHeaderParam.getCreationDateFrom());
            planHeader.setCreationDateTo(planHeaderParam.getCreationDateTo());
            planHeader.setDeApprFinTime(planHeaderParam.getDeApprFinTime());
            planHeader.setDemanders(planHeaderParam.getDemanders());
            planHeader.setDePlanFinTime(planHeaderParam.getDePlanFinTime());
            planHeader.setFormat(planHeaderParam.getFormat());

            planHeader.setPlanFinApprTime(planHeaderParam.getPlanFinApprTime());
            planHeader.setPlanFinBidTime(planHeaderParam.getPlanFinBidTime());
            planHeader.setPlanFinConTime(planHeaderParam.getPlanFinConTime());
            planHeader.setPlanFinIssueTime(planHeaderParam.getPlanFinIssueTime());
            planHeader.setPlanFinVenTime(planHeaderParam.getPlanFinVenTime());
            planHeader.setPrCategory(planHeaderParam.getPrCategory());
            planHeader.setProjectAmount(planHeaderParam.getProjectAmount());
            planHeader.setPrWay(planHeaderParam.getPrWay());
            planHeader.setRemarks(planHeaderParam.getRemarks());
            RCWLPlanHeaderRepository.updateByPrimaryKeySelective(planHeader);
        }
    }

    /**
     * 附件保存
     *
     * @param tenantId
     * @param planHeader
     */
    @Override
    public void saveAttachment(Long tenantId, PlanHeader planHeader) {
        if (null == planHeader || null == planHeader.getPlanId()) {
            throw new CommonException(BaseConstants.ErrorCode.ERROR);
        }
        PlanHeader selectPlanHeader = RCWLPlanHeaderRepository.selectByPrimaryKey(planHeader);
        selectPlanHeader.setAttachment(planHeader.getAttachment());
        RCWLPlanHeaderRepository.updateOptional(selectPlanHeader, PlanHeader.FIELD_ATTACHMENT);
    }

    /**
     * 导入校验
     *
     * @param planHeaderImportVO
     * @param importData
     * @param tenantId
     * @return
     */
    @Override
    public boolean checkData(PlanHeaderImportVO planHeaderImportVO, ImportData importData, Long tenantId) {
        //校验采购申请头行是否存在重复
        if (planHeaderImportVO.getPrNum() != null && planHeaderImportVO.getLineNum() != null) {
            boolean check = RCWLPlanHeaderRepository.checkPrNumRep(planHeaderImportVO.getPrNum(), planHeaderImportVO.getLineNum(), tenantId);
            if (!check) {
                importData.addErrorMsg("已存在重复的采购申请头和行");
                return false;
            }
            return true;
        } else if (planHeaderImportVO.getCompanyName() != null) {
            boolean check = RCWLPlanHeaderRepository.checkCompanyExist(planHeaderImportVO.getCompanyName());
            if (!check) {
                importData.addErrorMsg("该公司编码不存在");
                return false;
            }
            return true;
        } else if (planHeaderImportVO.getBudgetAccount() != null) {
            boolean check = RCWLPlanHeaderRepository.checkBudgetAccountExist(planHeaderImportVO.getBudgetAccount(), tenantId);
            if (!check) {
                importData.addErrorMsg("该预算科目不存在");
                return false;
            }
            return true;
        } else if (planHeaderImportVO.getDemanders() != null) {
            boolean check = RCWLPlanHeaderRepository.checkDemandersExist(planHeaderImportVO.getDemanders(), tenantId);
            if (!check) {
                importData.addErrorMsg("该需求人不存在");
                return false;
            }
            return true;
        } else if (planHeaderImportVO.getAgent() != null) {
            boolean check = RCWLPlanHeaderRepository.checkAgentExist(planHeaderImportVO.getAgent(), tenantId);
            if (!check) {
                importData.addErrorMsg("该经办人不存在");
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * 采购计划提交
     *
     * @param planHeaderVOS
     * @param organizationId
     * @return
     */
    @Override
    public PlanHeaderVO submitPlanHeader(List<PlanHeaderVO> planHeaderVOS, Long organizationId) {
        List<Long> ids = planHeaderVOS.stream().map(PlanHeaderVO::getPlanId).distinct().collect(Collectors.toList());
        List<PlanHeader> planHeaderList = RCWLPlanHeaderRepository.selectByIds(ids.stream().map(Object::toString).collect(Collectors.joining(",")));
        logger.info("planHeaderList:" + planHeaderList);

        List<PlanHeader> list = new ArrayList<>();
        String processNum = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SSRC.RCWL.PLAN_BPM", "GLOBAL", "GLOBAL", (Map) null);
        planHeaderList.forEach(planHeader -> {
            if (!Constants.PlanHeaderApprovalStatus.NEW.equals(planHeader.getApprovalStatus())) {
                throw new CommonException("只有审批状态为新建的单据才可以提交");
            }
            planHeader.setProcessNum(processNum);
            list.add(planHeader);
        });
        RCWLPlanHeaderRepository.batchUpdateOptional(list, PlanHeader.FIELD_PROCESS_NUM);
        //提交计划单号，生成新的流程编号
//        try {
//            RCWLPlanHeaderRepository.submitPlanHeader(list, organizationId);
//        }catch (Exception e){
//            logger.info("异常");
//        }
       // RCWLPlanHeaderRepository.submitPlanHeader(list, organizationId);

        //将url拼接返回给前端
        String reSrcSys = profileClient.getProfileValueByOptions(DetailsHelper.getUserDetails().getTenantId(), DetailsHelper.getUserDetails().getUserId(), DetailsHelper.getUserDetails().getRoleId(), "RCWL_BPM_URLIP");
        logger.info("配置维护：" + reSrcSys);
        String url = "http://" + reSrcSys + "/Workflow/MTStart2.aspx?BSID=WLCGGXPT&BTID=RCWLSRMCGJH&BOID=" + processNum;
        PlanHeaderVO planHeaderVO = new PlanHeaderVO();
        planHeaderVO.setUrl(url);
        return planHeaderVO;
    }


}
