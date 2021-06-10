package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;

import java.util.List;

@Api(
        tags = {"Bpm Pr Header"}
)
@RestController("RcwlPrApproveBpmController.v1")
@RequestMapping({"/v1/{organizationId}"})
public class RcwlPrApproveBpmController {

    @Autowired
    private PrHeaderService prHeaderService;

    @ApiOperation("采购申请审批通过1")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/approve/approval1"})
    public ResponseEntity<List<PrHeader>> prApproval1(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrHeader> prHeaderList) {
        SecurityTokenHelper.validToken(prHeaderList, false);
        List<PrHeader> prHeaderApprovalList = this.prHeaderService.prApproval(tenantId, prHeaderList, Boolean.TRUE);
        this.prHeaderService.exportPrToErp(tenantId, prHeaderApprovalList);
//        ((PrHeader)prHeaderApprovalList.get(0)).setCustomUserDetails(DetailsHelper.getUserDetails());
//        this.prHeaderService.afterPrApprove(tenantId, prHeaderApprovalList);
        return Results.success(prHeaderApprovalList);
    }

    @ApiOperation("采购申请审批通过2")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/approve/approval2"})
    public ResponseEntity<List<PrHeader>> prApproval2(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrHeader> prHeaderList) {
//        SecurityTokenHelper.validToken(prHeaderList, false);
//        List<PrHeader> prHeaderApprovalList = this.prHeaderService.prApproval(tenantId, prHeaderList, Boolean.TRUE);
//        this.prHeaderService.exportPrToErp(tenantId, prHeaderApprovalList);
        ((PrHeader)prHeaderList.get(0)).setCustomUserDetails(DetailsHelper.getUserDetails());
        this.prHeaderService.afterPrApprove(tenantId, prHeaderList);
        return Results.success(prHeaderList);
    }
}
