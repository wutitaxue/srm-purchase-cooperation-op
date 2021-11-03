package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.pr.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrBudgetDistributionService;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.srm.web.annotation.Tenant;

import java.util.List;

/**
 * 预算分配 管理 API
 *
 * @author jie.wang05@hand-china.com 2021-10-27 14:49:26
 */
@RestController("rcwlBudgetDistributionController.v1")
@RequestMapping("/v1/{organizationId}/rcwl-budget-distributions")
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public class RcwlPrBudgetDistributionController extends BaseController {
    @Autowired
    private RcwlPrBudgetDistributionService rcwlPrBudgetDistributionService;

    @ApiOperation(value = "根据采购申请行生成预算分配")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/calculate")
    public ResponseEntity<List<RcwlBudgetDistributionDTO>> calculateBudgetDistributions(@PathVariable(value = "organizationId") long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS = rcwlPrBudgetDistributionService.selectBudgetDistributionByPrLine(tenantId, rcwlBudgetDistributionDTO);
        return Results.success(rcwlBudgetDistributionDTOS);
    }

    @ApiOperation(value = "创建预算分配")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<RcwlBudgetDistribution>> createBudgetDistributions(@PathVariable(value =
            "organizationId") long tenantId, @RequestBody List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS) {
        List<RcwlBudgetDistribution> budgetDistributions = rcwlPrBudgetDistributionService.createBudgetDistributions(tenantId, rcwlBudgetDistributionDTOS);
        return Results.success(budgetDistributions);
    }

}
