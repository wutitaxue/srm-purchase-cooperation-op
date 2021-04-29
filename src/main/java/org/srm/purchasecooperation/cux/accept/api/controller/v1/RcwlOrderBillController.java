package org.srm.purchasecooperation.cux.accept.api.controller.v1;


import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.accept.infra.constant.RcwlAcceptConstants;
import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;


@Api(
        tags = {RcwlAcceptConstants.API_TAGS}
)
@RestController("RcwlOrderBillController.v1")
@RequestMapping({"/v1/{organizationId}"})
public class RcwlOrderBillController {

    @Autowired
    RcwlOrderBillService rcwlOrderBillService;

    @ApiOperation("资产采购订单接口重推")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/rcwl-order-bill"})
    public ResponseEntity<Long> sendOrderBillInserface(@PathVariable("organizationId") Long tenantId,Long rcvTrxLineId,String rcvTrxnum,String type) {

        rcwlOrderBillService.sendOrderBillOne(tenantId,rcvTrxLineId,type,rcvTrxnum);

        return Results.success();
    }

}
