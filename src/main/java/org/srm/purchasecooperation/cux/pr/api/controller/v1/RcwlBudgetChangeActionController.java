package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlBudgetChangeActionService;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlBudgetChangeActionRepository;
import org.srm.web.annotation.Tenant;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 预算变更记录表(RcwlBudgetChangeAction)表控制层
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-01 14:58:31
 */

@RestController("rcwlBudgetChangeActionController.v1")
@RequestMapping("/v1/{organizationId}/scux-rcwl-budget-change-actions")
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public class RcwlBudgetChangeActionController extends BaseController {

    @Autowired
    private RcwlBudgetChangeActionRepository rcwlBudgetChangeActionRepository;

    @Autowired
    private RcwlBudgetChangeActionService rcwlBudgetChangeActionService;

    @ApiOperation(value = "预算变更记录表列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<RcwlBudgetChangeAction>> list(RcwlBudgetChangeAction rcwlBudgetChangeAction, @PathVariable Long organizationId, @ApiIgnore @SortDefault(value = RcwlBudgetChangeAction.FIELD_BUDGET_CHANGE_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<RcwlBudgetChangeAction> list = rcwlBudgetChangeActionService.selectList(pageRequest, rcwlBudgetChangeAction);
        return Results.success(list);
    }

    @ApiOperation(value = "预算变更记录表明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{budgetChangeId}")
    public ResponseEntity<RcwlBudgetChangeAction> detail(@PathVariable Long budgetChangeId) {
        RcwlBudgetChangeAction rcwlBudgetChangeAction = rcwlBudgetChangeActionRepository.selectByPrimary(budgetChangeId);
        return Results.success(rcwlBudgetChangeAction);
    }

    @ApiOperation(value = "创建预算变更记录表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<RcwlBudgetChangeAction>> createBudgetChangeAction(@PathVariable("organizationId") Long tenantId, @RequestBody List<RcwlBudgetChangeAction> rcwlBudgetChangeActions) {
        validObject(rcwlBudgetChangeActions);
        SecurityTokenHelper.validTokenIgnoreInsert(rcwlBudgetChangeActions);
        rcwlBudgetChangeActionService.createBudgetChangeAction(tenantId, rcwlBudgetChangeActions);
        return Results.success(rcwlBudgetChangeActions);
    }

    @ApiOperation(value = "删除预算变更记录表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody RcwlBudgetChangeAction rcwlBudgetChangeAction) {
        SecurityTokenHelper.validToken(rcwlBudgetChangeAction);
        rcwlBudgetChangeActionRepository.deleteByPrimaryKey(rcwlBudgetChangeAction);
        return Results.success();
    }

}
