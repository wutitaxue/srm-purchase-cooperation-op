package org.srm.purchasecooperation.cux.pr.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.cux.pr.domain.vo.BudgetAccountVO;
import org.srm.purchasecooperation.cux.pr.domain.vo.PrHeaderVO;

/**
 * 采购申请行Mapper
 *
 * @author bin.zhang06@hand-china.com 2021-03-16 15:49:15
 */
@Component
public interface RCWLPrLineMapper extends BaseMapper<PrLine> {

    PrLineDTO selectPrLine(@Param("planId") Long planId);

    void updatePrLine(@Param("planId") Long planId, @Param("tenantId") Long tenantId);
    /**
     * 通过申请头id和行号查找行id
     *
     * @param prHeaderId
     * @param lineNum
     * @return
     */
    Long selectPrLineId(@Param("prHeaderId") Long prHeaderId, @Param("lineNum") String lineNum, @Param("tenantId") Long tenantId);

    PrHeaderVO selectByNum(@Param("prNum") String prNum, @Param("lineNum") String lineNum, @Param("tenantId") Long tenantId);

    PrLine selectPrLineRecord(@Param("prLineId")Long prLineId);

    BudgetAccountVO selectBudgetAccount(@Param("budgetAccountNum")String budgetAccountNum, @Param("tenantId")Long tenantId);
}
