package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.srm.purchasecooperation.cux.pr.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlBudgetDistributionService;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlBudgetDistributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.hzero.mybatis.helper.SecurityTokenHelper;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 预算分配 管理 API
 *
 * @author jie.wang05@hand-china.com 2021-10-27 14:49:26
 */
@RestController("rcwlBudgetDistributionController.v1")
@RequestMapping("/v1/{organizationId}/rcwl-budget-distributions")
public class RcwlBudgetDistributionController extends BaseController {

    @Autowired
    private RcwlBudgetDistributionRepository rcwlBudgetDistributionRepository;
    @Autowired
    private RcwlBudgetDistributionService rcwlBudgetDistributionService;

    @ApiOperation(value = "根据采购申请行生成预算分配")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/calculate")
    public ResponseEntity<List<RcwlBudgetDistributionDTO>> calculateBudgetDistributions(@PathVariable(value = "organizationId") long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS = rcwlBudgetDistributionService.selectBudgetDistributionByPrLine(tenantId, rcwlBudgetDistributionDTO);
        return Results.success(rcwlBudgetDistributionDTOS);
    }

    @ApiOperation(value = "创建预算分配")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<RcwlBudgetDistribution>> createBudgetDistributions(@PathVariable(value =
            "organizationId") long tenantId, @RequestBody List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS) {
        List<RcwlBudgetDistribution> budgetDistributions = rcwlBudgetDistributionService.createBudgetDistributions(tenantId, rcwlBudgetDistributionDTOS);
        return Results.success(budgetDistributions);
    }

}
