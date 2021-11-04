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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlBudgetChangeActionService;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlBudgetChangeActionRepository;
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
public class RcwlBudgetChangeActionController extends BaseController {
    @Autowired
    private RcwlBudgetChangeActionService rcwlBudgetChangeActionService;


    @ApiOperation(value = "创建预算变更记录表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<RcwlBudgetChangeAction>> createBudgetChangeAction(@PathVariable("organizationId") Long tenantId, @RequestBody List<RcwlBudgetChangeAction> rcwlBudgetChangeActions) {
        validObject(rcwlBudgetChangeActions);
        SecurityTokenHelper.validTokenIgnoreInsert(rcwlBudgetChangeActions);
        rcwlBudgetChangeActionService.createBudgetChangeAction(tenantId, rcwlBudgetChangeActions);
        return Results.success(rcwlBudgetChangeActions);
    }

}
