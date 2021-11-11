package org.srm.purchasecooperation.cux.pr.infra.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidator;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.pr.domain.vo.RcwlPrLineImportVO;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrImportMapper;
import org.srm.purchasecooperation.pr.app.service.impl.PrLineImportValidator;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.util.List;

/**
 * 采购申请行导入校验
 *
 * @author jiaxing.huang@hand-china.com 2021-09-07 16:31:02
 */
@ImportValidators({
        @ImportValidator(templateCode = "SPRM.PR_LINE")
})


public class RcwlPrLineImportValidator extends PrLineImportValidator {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RcwlPrImportMapper prImportMapper;

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
        //需要校验导入模板中的物料名称、规格、型号、单位和物料基本信息中（smdm_item）数据是否一致
        if (StringUtils.isNotEmpty(rcwlPrLineImportVO.getItemCode())) {
            List<PrLine> prLineVOs = this.prImportMapper.queryItemInfo(rcwlPrLineImportVO);
            PrLine prLineVO = prLineVOs.get(0);
            String checkVarchar = prLineVO.getAttributeVarchar15();
            if (!StringUtils.equals(checkVarchar, "1")) {
                if(!StringUtils.equals(rcwlPrLineImportVO.getItemName(), prLineVO.getItemName())){
                    getContext().addErrorMsg("导入模板中的物料名称与物料基本信息的物料名称不一致");
                }
                if(!StringUtils.equals(rcwlPrLineImportVO.getItemSpecs(), prLineVO.getItemSpecs())){
                    getContext().addErrorMsg("导入模板中的规格与物料基本信息的规格不一致");
                }
                if(!StringUtils.equals(rcwlPrLineImportVO.getItemModel(), prLineVO.getItemModel())){
                    getContext().addErrorMsg("导入模板中的型号与物料基本信息的型号不一致");
                }
                if(!StringUtils.equals(rcwlPrLineImportVO.getUomCode(), prLineVO.getAttributeVarchar1())){
                    getContext().addErrorMsg("导入模板中的计量单位编码与物料基本信息的计量单位编码不一致");
                }

            }
        }
        return true;
    }
}



