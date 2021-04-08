package org.srm.purchasecooperation.cux.pr.api.v1.controller;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.common.annotation.PurchaserPowerCron;
import org.srm.purchasecooperation.pr.api.dto.PrHeaderCreateDTO;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.web.annotation.Tenant;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/3/26 16:03
 * @version:1.0
 */
@Api(
        tags = {"Pr Header (Org Level)"}
)
@RestController("RcwlPrheaderController.v1")
@RequestMapping({"/v1/{organizationId}"})
@Tenant
public class RcwlPrheaderController extends BaseController {

    @Autowired
    private PrHeaderService prHeaderService;


    @ApiOperation("采购申请头更新")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PutMapping({"/purchase-requests"})
    @PurchaserPowerCron
    public ResponseEntity<PrHeader> updatePrHeader(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody PrHeader prHeader) {
        SecurityTokenHelper.validToken(prHeader, false);
        prHeader.setTenantId(tenantId);
        prHeader = this.prHeaderService.updatePrHeader(prHeader);
        return Results.success(prHeader);
    }
}
