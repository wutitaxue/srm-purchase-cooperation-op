package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.pr.api.dto.HeaderQueryDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.PlanHeaderExportDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPlanHeaderService;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrLineService;
import org.srm.purchasecooperation.cux.pr.config.*;
import org.srm.purchasecooperation.cux.pr.domain.entity.PlanHeader;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPlanHeaderRepository;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderExportVO;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderVO;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 采购计划表 管理 API
 *
 * @author bin.zhang06@hand-china.com 2021-03-15 12:30:00
 */
@Api(tags = SprmSwaggerApiConfig.SPRM_PLAN)
@RestController("planHeaderController.v1")
@RequestMapping("/v1/{organizationId}/plan-headers")
public class RCWLPlanHeaderController extends BaseController {

    @Autowired
    private RCWLPlanHeaderRepository RCWLPlanHeaderRepository;
    @Autowired
    private RCWLPlanHeaderService RCWLPlanHeaderService;
    @Autowired
    private RCWLPrLineService prLineService;
    @ApiOperation(value = "采购计划查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    //@Permission(permissionPublic = true)
    @GetMapping("/list")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<PlanHeaderVO>> listRfxHeader(@PathVariable Long organizationId,
                                                            HeaderQueryDTO planHeaderParam,
                                                            @ApiIgnore @SortDefault(value = PlanHeader.FIELD_PLAN_ID,
                                                                     direction = Sort.Direction.DESC) PageRequest pageRequest) {

        planHeaderParam.setTenantId(organizationId);
        return Results.success(RCWLPlanHeaderService.listPlanHeader(pageRequest, organizationId, planHeaderParam));
    }


    @ApiOperation("采购计划头导出")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    //@Permission(permissionPublic = true)
    @GetMapping({"/all/export"})
    @ExcelExport(
            value = PlanHeaderExportVO.class

    )
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<List<PlanHeaderExportVO>> exportPrHeaders(@PathVariable("organizationId") Long organizationId, PlanHeaderExportDTO planHeaderExportDTO, ExportParam exportParam, HttpServletResponse response,@ApiIgnore @SortDefault(value = PlanHeader.FIELD_CREATION_DATE, direction = Sort.Direction.DESC) PageRequest pageRequest) {
        planHeaderExportDTO.setTenantId(organizationId);
        Page<PlanHeaderExportVO> planHeaderExportVOS = RCWLPlanHeaderRepository.exportPlanHeaders(pageRequest, planHeaderExportDTO);
        return Results.success(planHeaderExportVOS);
    }

    @ApiOperation(value = "采购计划附件保存")
    @Permission(level = ResourceLevel.ORGANIZATION)

   // @Permission(permissionPublic = true)
    @PostMapping("/header/attachment")
    public ResponseEntity saveAttachment(@PathVariable("organizationId") Long tenantId, @RequestBody PlanHeader planHeader) {
        RCWLPlanHeaderService.saveAttachment(tenantId, planHeader);
        return Results.success();
    }
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @ApiOperation(value = "批量取消采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
//   @Permission(permissionPublic = true)
    @PostMapping("/cancel")
    public ResponseEntity<List<PlanHeader>> batchCancelPlanHeader(@ApiParam(value = "租户id", required = true) @PathVariable(value = "organizationId") Long organizationId,
                                                                  @ApiParam(value = "采购计划表list") @RequestBody List<PlanHeader> planHeaderList) {

        return Results.success(RCWLPlanHeaderService.batchCancelPlanHeader(organizationId, planHeaderList));
    }
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @ApiOperation(value = "创建保存采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    //@Permission(permissionPublic = true)
    @PostMapping("/save")
    public ResponseEntity batchCreateAndUpdate(@PathVariable Long organizationId,@RequestBody PlanHeader planHeaderParam) {
        planHeaderParam.setTenantId(organizationId);
        this.RCWLPlanHeaderService.createAndUpdate(planHeaderParam);
        return Results.success();
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    @ApiOperation(value = "提交采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    //@Permission(permissionPublic = true)
    @PostMapping("/submit")
    public ResponseEntity<PlanHeaderVO> submitPlanHeader(@PathVariable Long organizationId,@RequestBody List<PlanHeaderVO> planHeaderVOS) throws IOException {

        PlanHeaderVO planHeaderVO =  this.RCWLPlanHeaderService.submitPlanHeader(planHeaderVOS,organizationId);
        return Results.success(planHeaderVO);
    }
    @ApiOperation(value = "BPM回传更新采购计划审批状态")
    @Permission(level = ResourceLevel.ORGANIZATION)
    //@Permission(permissionPublic = true)
    @PostMapping("/bpm-approve")
    public ResponseEntity bpmApproveToPlanHeader(@PathVariable Long organizationId,@RequestParam(value = "processNum") String processNum,@RequestParam(value = "approveFlag") String approveFlag){
        if(StringUtils.isNotEmpty(processNum)&&StringUtils.isNotEmpty(approveFlag)) {
            this.RCWLPlanHeaderService.updateStateFromBPM(processNum, approveFlag);
        }
        return Results.success();
    }
    @ApiOperation(value = "订单按金额行数据修复")
   // @Permission(level = ResourceLevel.ORGANIZATION)
    @Permission(permissionPublic = true)
    @PostMapping("/data-fix-one")
    public ResponseEntity dataFix(@PathVariable Long organizationId){
        this.RCWLPlanHeaderService.fixDataOne(organizationId);
        return Results.success();
    }
    @ApiOperation(value = "订单按数量行数据修复")
    // @Permission(level = ResourceLevel.ORGANIZATION)
    @Permission(permissionPublic = true)
    @PostMapping("/data-fix-two")
    public ResponseEntity dataFix1(@PathVariable Long organizationId){
        this.RCWLPlanHeaderService.fixDataTwo(organizationId);
        return Results.success();
    }
    @ApiOperation(value = "送货单接收数量行数据修复")
    // @Permission(level = ResourceLevel.ORGANIZATION)
    @Permission(permissionPublic = true)
    @PostMapping("/data-fix-three")
    public ResponseEntity dataFix2(@PathVariable Long organizationId){
        this.RCWLPlanHeaderService.fixDataThree(organizationId);
        return Results.success();
    }
}
