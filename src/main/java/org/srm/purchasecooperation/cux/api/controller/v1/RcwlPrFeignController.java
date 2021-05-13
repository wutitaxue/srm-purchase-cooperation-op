package org.srm.purchasecooperation.cux.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;



@Api(
        tags = {"Pr Header (Org Level)"}
)
@RestController("rcwlPrFeignController.v1")
@RequestMapping({"/v1/{organizationId}"})
public class RcwlPrFeignController {


    @Autowired
    private PrHeaderRepository prHeaderRepository;

    @ApiOperation("采购申请流程发起成功变更字段")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/purchase-requests/bsuccess"})
    @ProcessLovValue
    public ResponseEntity<PrHeader> prBsuccess(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestParam("prHeaderId") Long prHeaderId,@RequestParam("attributeVarchar17") String attributeVarchar17,@RequestParam("attributeVarchar18") String attributeVarchar18) {
        PrHeader prHeader = new PrHeader();
        prHeader.setTenantId(tenantId);
        prHeader.setPrHeaderId(prHeaderId);
        prHeader.setAttributeVarchar17(attributeVarchar17);
        prHeader.setAttributeVarchar18(attributeVarchar18);
        prHeaderRepository.updateByPrimaryKeySelective(prHeader);
        return Results.success(prHeader);
    }

    @ApiOperation("采购申请审批结束变更字段")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/purchase-requests/processend"})
    @ProcessLovValue
    public ResponseEntity<PrHeader> prMaintainablePrSummaries(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestParam("prHeaderId") Long prHeaderId) {
        PrHeader prHeader = new PrHeader();
        prHeader.setTenantId(tenantId);
        prHeader.setPrHeaderId(prHeaderId);
        prHeader.setAttributeVarchar17("0");
        prHeaderRepository.updateByPrimaryKeySelective(prHeader);
        return Results.success(prHeader);
    }
}
