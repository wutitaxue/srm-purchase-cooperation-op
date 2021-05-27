package org.srm.purchasecooperation.cux.api.controller.v1;

import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.app.service.RcwlTaxInvoiceLineService;
import org.srm.purchasecooperation.cux.domain.entity.PayLoad;
import org.srm.purchasecooperation.cux.domain.entity.ResponseData;

import javax.annotation.Resource;

@Api(
        tags = {"elephant"}
)
@RestController("rcwlElephantController.v1")
@RequestMapping({"/v1/rcwlElephantController"})
public class RcwlElephantController extends BaseController {
    @Resource
    private RcwlTaxInvoiceLineService rcwlTaxInvoiceLineService;

    @PostMapping({"/invoice-elephant2"})
    @ApiOperation("税务发票数据同步")
    @Permission(
            permissionPublic = true
    )
    public ResponseData InvoiceSynchronization(
            @RequestBody PayLoad payLoad) {
        ResponseData responseData = new ResponseData();
        responseData =  rcwlTaxInvoiceLineService.InvoiceSynchronization(3L, payLoad.getInvoiceDataList());
        return responseData;
//        return rcwlTaxInvoiceLineService.ResponseData(new ResponseData());
    }


    @PostMapping(value = "/invoice-elephant",consumes = {"application/json"})
    @ApiOperation("税务发票数据同步")
    @Permission(
            permissionPublic = true
    )
    public ResponseData InvoiceSynchronization2(
            @RequestBody PayLoad payLoad) {
        ResponseData responseData = new ResponseData();
        responseData =  rcwlTaxInvoiceLineService.InvoiceSynchronization(3L, payLoad.getInvoiceDataList());
        return responseData;
//        return rcwlTaxInvoiceLineService.ResponseData(new ResponseData());
    }
}
