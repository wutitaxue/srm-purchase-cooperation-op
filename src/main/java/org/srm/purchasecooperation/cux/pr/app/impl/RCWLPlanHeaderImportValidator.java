package org.srm.purchasecooperation.cux.pr.app.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.boot.imported.app.service.ValidatorHandler;
import org.hzero.boot.imported.domain.entity.ImportData;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidator;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidators;
import org.hzero.core.message.MessageAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPlanHeaderService;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderImportVO;

import java.io.IOException;

/**
 * @author zhangbin
 */
@ImportValidators({@ImportValidator(
        templateCode = "SPRM.PLAN_HEADER"
)})
public class RCWLPlanHeaderImportValidator extends ValidatorHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RCWLPlanHeaderService RCWLPlanHeaderService;
    private static final Logger logger = LoggerFactory.getLogger(RCWLPlanHeaderImportValidator.class);

    public RCWLPlanHeaderImportValidator() {
    }

    @Override
    public boolean validate(String data) {
        PlanHeaderImportVO planHeaderImportVO;
        ImportData importData = this.getContext();
        Long tenantId = DetailsHelper.getUserDetails().getTenantId();
        logger.info("importData" + importData.toString());
        try {
            planHeaderImportVO = this.objectMapper.readValue(data, PlanHeaderImportVO.class);
            logger.info("planHeaderImportVO" + planHeaderImportVO);
            boolean checkPass = RCWLPlanHeaderService.checkData(planHeaderImportVO, importData, tenantId);
            return checkPass;
        } catch (IOException var6) {
            super.getContext().addErrorMsg(MessageAccessor.getMessage("error.data_invalid").desc());
            return false;
        }
    }

}

