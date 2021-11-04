package org.srm.purchasecooperation.cux.pr.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.util.List;

/**
 * @author jie.wang05@hand-china.com 2021/11/4 17:42
 * @description 采购申请变更DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RcwlBudgetChangeActionQueryDTO {
    /**
     * 采购申请变更预算集合
     */
    private List<RcwlBudgetChangeAction> rcwlBudgetChangeActions;

    /**
     * 采购申请行
     */
    private PrLine prLine;
}
