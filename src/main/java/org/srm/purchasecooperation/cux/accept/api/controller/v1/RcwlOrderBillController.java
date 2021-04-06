package org.srm.purchasecooperation.cux.accept.api.controller.v1;


import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;


@Api(
        tags = {"Order Bill Interface"}
)
@RestController("RcwlOrderBillController.v1")
@RequestMapping
public class RcwlOrderBillController {


    @ApiOperation("资产采购订单接口重推")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/v1/rcwl-order-bill"})
    public ResponseEntity<RcwlOrderBillDTO> sendOrderBillInserface(@Encrypt @RequestBody RcwlOrderBillDTO rcwlOrderBillDTO) {

        //CustomUserDetails userDetails = DetailsHelper.getUserDetails();

        return Results.success(null);
    }

}
