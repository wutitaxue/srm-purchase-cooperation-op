package org.srm.purchasecooperation.cux.pr.infra.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.boot.imported.app.service.ValidatorHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidator;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.pr.domain.vo.RcwlPrLineImportVO;

/**
 * 采购申请行导入校验
 *
 * @author jiaxing.huang@hand-china.com 2021-09-07 16:31:02
 */
@ImportValidators({
        @ImportValidator(templateCode = "SPRM.PR_LINE")
})


public class RcwlPrLineImportValidator extends ValidatorHandler {
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPrLineImportValidator.class);

    @Override
    public boolean validate(String data) {
        boolean isBlank;
        try {
            RcwlPrLineImportVO rcwlPrLineImportVO = objectMapper.readValue(data, RcwlPrLineImportVO.class);
            rcwlPrLineImportVO.setTenantId(DetailsHelper.getUserDetails().getTenantId());
            isBlank = checkData(rcwlPrLineImportVO);
        } catch (Exception e) {
            LOGGER.error("import data examine error: [{}] ,data: [{}]", e, data);
            return false;
        }
        return isBlank;
    }

    /**
     * 数据校验
     *
     * @return 校验结果
     */
    private boolean checkData(RcwlPrLineImportVO rcwlPrLineImportVO) {
        //需求结束日期不可小于需求开始日期
        if (rcwlPrLineImportVO.getNeededDate().isBefore(rcwlPrLineImportVO.getStartDate())) {
            getContext().addErrorMsg("需求结束日期不可小于需求开始日期");
            return false;
        }
        return true;
    }
}



