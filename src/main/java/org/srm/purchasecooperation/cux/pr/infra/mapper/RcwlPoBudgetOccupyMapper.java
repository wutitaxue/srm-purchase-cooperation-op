package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.api.dto.RCWLPoLineDetailDTO;
import org.srm.purchasecooperation.cux.pr.domain.vo.PoBudgetOccupyLineVO;

import java.util.List;

/**
 * @author:pengxu.zhi@hand-china.com
 * @createTime:2021/11/05 17:24
 */
@Component
public interface RcwlPoBudgetOccupyMapper {

    /**
     * 根据订单行获取预算分配
     * @param tenantId
     * @param poHeaderId
     * @return
     */
    List<PoBudgetOccupyLineVO> selectBudgetDistributionByLine(Long tenantId, Long poHeaderId);

    /**
     * 根据订单行获取预算分配
     * @param tenantId
     * @param poHeaderId
     * @return
     */
    List<PoBudgetOccupyLineVO> selectNoBudgetDistributionPoLine(Long tenantId, Long poHeaderId);
}
