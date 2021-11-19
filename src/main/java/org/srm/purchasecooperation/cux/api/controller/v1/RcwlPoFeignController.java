package org.srm.purchasecooperation.cux.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoSubmitBpmService;



@Api(tags = {"Rcwl Po Header (Org Level)"})
@RestController("rcwlPoFeignController.v1")
@RequestMapping({"/v1/{organizationId}/rcwl-po-header"})
public class RcwlPoFeignController {

    @Autowired
    private RcwlPoSubmitBpmService rcwlPoSubmitBpmService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPoFeignController.class);


    @ApiOperation(value = "采购订单BPM提交成功")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit-to-bpm-successed")
    public ResponseEntity<Void> poSubmitBpmSuccessed( @PathVariable("organizationId") Long tenantId, @RequestParam("poNum") String poNum,
                                                      @RequestParam("procInstID") String procInstID,@RequestParam("newProcURL") String newProcURL) {
        LOGGER.info("调用采购订单BPM提交成功接口参数：tenantId：{}，poNum：{}，procInstID：{}，newProcURL：{}",tenantId,poNum,procInstID,newProcURL);
        rcwlPoSubmitBpmService.rcwlSubmitBpmSuccessed(tenantId, poNum, procInstID, newProcURL);
        return Results.success();
    }

    @ApiOperation(value = "采购订单BPM审批通过")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit-to-bpm-approved")
    public ResponseEntity<Void> poBpmApproved(@PathVariable("organizationId") Long tenantId, @RequestParam("poNum") String poNum) {
        rcwlPoSubmitBpmService.rcwlSubmitBpmApproved(tenantId, poNum);
        return Results.success();
    }

    @ApiOperation(value = "采购订单BPM审批拒绝")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit-to-bpm-rejected")
    public ResponseEntity<Void> poBpmReject( @PathVariable("organizationId") Long tenantId, @RequestParam("poNum") String poNum ) {
        rcwlPoSubmitBpmService.rcwlSubmitBpmReject(tenantId, poNum);
        return Results.success();
    }
}
