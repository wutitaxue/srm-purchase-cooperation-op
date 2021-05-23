package org.srm.purchasecooperation.cux.act.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.profile.ProfileClient;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;
import org.srm.purchasecooperation.cux.act.api.dto.RcwlBpmUrlDto;
import org.srm.purchasecooperation.cux.act.app.service.ActService;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.purchasecooperation.finance.api.dto.InvoiceTransactionSearchDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;
import org.srm.web.annotation.Tenant;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 9:59
 * @version:1.0
 */
//@Tenant(rcwlActConstant.TENANT_NUMBER)
@RestController("rcwlActBpmController.v1")
@RequestMapping("/v1/{organizationId}/act")
public class rcwlActBpmController {
    @Autowired
    private ActService actService;
    @Autowired
    private ProfileClient profileClient;

    /**
     * 验收单bpm接口查询
     *
     * @param acceptListHeaderId
     * @return
     */
    @ApiOperation(value = "验收单BPM接口传输")
    @Permission(level = ResourceLevel.ORGANIZATION)
//    @Permission(permissionPublic = true)
    @PostMapping("/getAct")
    public ResponseEntity<ActListHeaderDto> queryList(@ApiParam(value = "租户Id", required = true) @PathVariable("organizationId") Long organizationId, @ApiParam(value = "验收单头id", required = true) @Param("acceptListHeaderId") Long acceptListHeaderId) throws IOException {
        return Results.success(actService.actQuery(acceptListHeaderId, organizationId));
    }

    @ApiOperation(value = "验收单BPM提交")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit-to-bpm")
    public ResponseEntity<RcwlBpmUrlDto> submitToBpm(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO) throws IOException {
        return Results.success(actService.rcwlActSubmitBpm(tenantId, sinvRcvTrxHeaderDTO));
    }

    @ApiOperation(value = "验收单BPM提交成功")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit-to-bpm-successed")
    public ResponseEntity<Void> submitToBpmSuccessed(@PathVariable("organizationId") Long tenantId, @RequestParam("settleNum") String settleNum, @RequestParam("attributeVarchar18") String attributeVarchar18, @RequestParam("attributeVarchar19") String attributeVarchar19) {
        DetailsHelper.setCustomUserDetails(Long.parseLong(profileClient.getProfileValueByOptions(tenantId, null, null, "RCWL_USER_ID")), "zh_CN");
        actService.RcwlBpmSubmitSuccess(tenantId, settleNum, attributeVarchar18, attributeVarchar19);
        return Results.success();
    }

    @ApiOperation(value = "验收单BPM审批通过")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit-to-bpm-approved")
    public ResponseEntity<Void> bpmApproved(@PathVariable("organizationId") Long tenantId, @RequestParam("settleNum") String settleNum) {
        DetailsHelper.setCustomUserDetails(Long.parseLong(profileClient.getProfileValueByOptions(tenantId, null, null, "RCWL_USER_ID")), "zh_CN");
        actService.RcwlBpmApproved(tenantId, settleNum);
        return Results.success();
    }

    @ApiOperation(value = "验收单BPM审批拒绝")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit-to-bpm-rejected")
    public ResponseEntity<Void> bpmReject(@PathVariable("organizationId") Long tenantId, @RequestParam("settleNum") String settleNum) {
        DetailsHelper.setCustomUserDetails(Long.parseLong(profileClient.getProfileValueByOptions(tenantId, null, null, "RCWL_USER_ID")), "zh_CN");
        actService.RcwlBpmReject(tenantId, settleNum);
        return Results.success();
    }

    @ApiOperation(value = "验收单BPM审批拒绝，置0接口")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit-to-bpm-rejected3")
    public ResponseEntity<Void> bpmReject3(@PathVariable("organizationId") Long tenantId, @RequestParam("settleNum") String settleNum) {
        DetailsHelper.setCustomUserDetails(Long.parseLong(profileClient.getProfileValueByOptions(tenantId, null, null, "RCWL_USER_ID")), "zh_CN");
        actService.RcwlBpmReject3(tenantId, settleNum);
        return Results.success();
    }

}
