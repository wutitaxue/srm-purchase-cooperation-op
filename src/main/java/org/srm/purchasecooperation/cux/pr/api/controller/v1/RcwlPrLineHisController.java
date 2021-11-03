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
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrLineHisService;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlPrLineHisRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 采购申请行(RcwlPrLineHis)表控制层
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-02 14:04:42
 */

@RestController("rcwlPrLineHisController.v1")
@RequestMapping("/v1/{organizationId}/scux-rcwl-pr-line-hiss")
public class RcwlPrLineHisController extends BaseController {

    @Autowired
    private RcwlPrLineHisRepository rcwlPrLineHisRepository;

    @Autowired
    private RcwlPrLineHisService rcwlPrLineHisService;

    @ApiOperation(value = "采购申请行列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<RcwlPrLineHis>> list(RcwlPrLineHis rcwlPrLineHis, @PathVariable Long organizationId, @ApiIgnore @SortDefault(value = RcwlPrLineHis.FIELD_PR_LINE_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<RcwlPrLineHis> list = rcwlPrLineHisService.selectList(pageRequest, rcwlPrLineHis);
        return Results.success(list);
    }

    @ApiOperation(value = "采购申请行明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{prLineId}")
    public ResponseEntity<RcwlPrLineHis> detail(@PathVariable Long prLineId) {
        RcwlPrLineHis rcwlPrLineHis = rcwlPrLineHisRepository.selectByPrimary(prLineId);
        return Results.success(rcwlPrLineHis);
    }

    @ApiOperation(value = "创建采购申请行")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<RcwlPrLineHis>> save(@PathVariable Long organizationId, @RequestBody List<RcwlPrLineHis> rcwlPrLineHiss) {
        validObject(rcwlPrLineHiss);
        SecurityTokenHelper.validTokenIgnoreInsert(rcwlPrLineHiss);
        rcwlPrLineHisService.saveData(rcwlPrLineHiss);
        return Results.success(rcwlPrLineHiss);
    }

    @ApiOperation(value = "删除采购申请行")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody RcwlPrLineHis rcwlPrLineHis) {
        SecurityTokenHelper.validToken(rcwlPrLineHis);
        rcwlPrLineHisRepository.deleteByPrimaryKey(rcwlPrLineHis);
        return Results.success();
    }

}
