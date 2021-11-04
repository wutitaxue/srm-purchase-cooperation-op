package org.srm.purchasecooperation.order.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.app.service.RcwlBudgetDistributionService;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;

import java.util.List;

/**
 * 预算分配 管理 API
 *
 * @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
 */
@RestController("rcwlPoBudgetDistributionController.v1")
@RequestMapping("/v1/{organizationId}/pr/rcwl-budget-distributions")
public class RcwlPrBudgetDistributionController extends BaseController {

    @Autowired
    private RcwlBudgetDistributionService rcwlBudgetDistributionService;

    @ApiOperation(value = "根据采购申请行生成预算分配")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/calculate")
    public ResponseEntity<List<RcwlBudgetDistributionDTO>> calculateBudget(@PathVariable(value = "organizationId") long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS = rcwlBudgetDistributionService.selectBudgetDistributionByPrLine(tenantId, rcwlBudgetDistributionDTO);
        return Results.success(rcwlBudgetDistributionDTOS);
    }

}
