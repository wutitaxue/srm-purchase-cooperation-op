package org.srm.purchasecooperation.cux.app.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.imported.app.service.BatchImportHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.core.convert.CommonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.domain.entity.PlanHeader;
import org.srm.purchasecooperation.cux.domain.repository.RCWLPlanHeaderRepository;
import org.srm.purchasecooperation.cux.domain.repository.RCWLPrLineRepository;
import org.srm.purchasecooperation.cux.domain.vo.PlanHeaderImportVO;
import org.srm.purchasecooperation.cux.domain.vo.PrHeaderVO;
import org.srm.purchasecooperation.cux.infra.constant.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ImportService(
        templateCode = "SPRM.PLAN_HEADER"
)
public class RCWLPlanHeaderImportServiceImpl extends BatchImportHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RCWLPlanHeaderRepository RCWLPlanHeaderRepository;
    @Autowired
    private RCWLPrLineRepository RCWLPrLineRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    private static final Logger logger = LoggerFactory.getLogger(org.srm.purchasecooperation.cux.app.impl.RCWLPlanHeaderImportServiceImpl.class);

    public RCWLPlanHeaderImportServiceImpl() {
    }

    @Override
    public int getSize() {
        return 1000;
    }

    /**
     * @param list
     * @return
     */
    @Override
    public Boolean doImport(List<String> list) {
        String voStr = "[" + StringUtils.join(list.toArray(), ",") + "]";
        List planHeaderImportVOList;
        try {
            //获取导入数据
            planHeaderImportVOList = (List) this.objectMapper.readValue(voStr, this.objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{PlanHeaderImportVO.class}));
        } catch (IOException var8) {
            return false;
        }

        if (CollectionUtils.isEmpty(planHeaderImportVOList)) {
            return true;
        } else {

            List<PlanHeader> planHeaders = this.convertPrLine(planHeaderImportVOList);
            logger.info("导出测试4" + planHeaders.toString());
            this.importPlanHeader(planHeaders);
            return true;

        }
    }

    private List<PlanHeader> convertPrLine(List<PlanHeaderImportVO> planHeaderImportVOList) {
        List<PlanHeader> planHeaderList = new ArrayList();
        PlanHeader planHeader;
        for (Iterator var5 = planHeaderImportVOList.iterator(); var5.hasNext(); planHeaderList.add(planHeader)) {
            PlanHeaderImportVO planHeaderImportVO = (PlanHeaderImportVO) var5.next();
            planHeader = (PlanHeader) CommonConverter.beanConvert(PlanHeader.class, planHeaderImportVO);
        }
        logger.info("导出测试3" + planHeaderList.toString());

        return planHeaderList;
    }

    private void importPlanHeader(List<PlanHeader> planHeaderList) {
        //新增采购计划
        Long tenantId = DetailsHelper.getUserDetails().getTenantId();
        if (!org.apache.commons.collections4.CollectionUtils.isEmpty(planHeaderList)) {
            planHeaderList.forEach(planHeaderVo -> {
                planHeaderVo.setTenantId(tenantId);
                //采购申请头行都存在 id才插表
                if (planHeaderVo.getPrNum() != null && planHeaderVo.getLineNum() != null) {
                    PrHeaderVO prHeaderVO = RCWLPrLineRepository.selectByNum(planHeaderVo.getPrNum(), planHeaderVo.getLineNum(), tenantId);
                    planHeaderVo.setState(Constants.PlanHeaderState.ALREADY);
                    planHeaderVo.setPrHeaderId(prHeaderVO.getPrHeaderId());
                    planHeaderVo.setPrLineId(prHeaderVO.getPrLineId());
                } else {
                    planHeaderVo.setState(Constants.PlanHeaderState.NOT);
                }
                //根据名字查找对应的编码，插入表里

                //公司名字查找 导入实际传的是code
                String companyName = RCWLPlanHeaderRepository.selectCompanyName(planHeaderVo.getCompanyName(), tenantId);
                Long companyId = RCWLPlanHeaderRepository.selectCompanyId(planHeaderVo.getCompanyName(), tenantId);
                String budgetAccount = RCWLPlanHeaderRepository.selectBudgetAccount(planHeaderVo.getBudgetAccount(), tenantId);
                String demanders = RCWLPlanHeaderRepository.selectDemanders(planHeaderVo.getDemanders(), tenantId);
                String agent = RCWLPlanHeaderRepository.selectAgent(planHeaderVo.getAgent(), tenantId);

                String str = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SSRC.RCWL.PLAN_HEADER", "GLOBAL", "GLOBAL", (Map) null);
                planHeaderVo.setPlanNum(str);

                planHeaderVo.setCompanyName(companyName);
                planHeaderVo.setCompanyId(companyId);
                planHeaderVo.setBudgetAccount(budgetAccount);
                planHeaderVo.setDemanders(demanders);
                planHeaderVo.setAgent(agent);
                RCWLPlanHeaderRepository.insertSelective(planHeaderVo);
            });
        }
    }

}