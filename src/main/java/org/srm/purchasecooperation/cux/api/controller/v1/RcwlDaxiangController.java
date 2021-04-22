package org.srm.purchasecooperation.cux.api.controller.v1;

import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.app.service.RCWLTaxInvoiceLineService;
import org.srm.purchasecooperation.cux.domain.entity.InvoiceData;
import org.srm.purchasecooperation.cux.domain.entity.ResponseData;
import org.srm.web.annotation.Tenant;

import javax.annotation.Resource;
import java.util.List;

@Api(
        tags = {"elephant"}
)
@RestController("rcwlElephantController.v1")
@RequestMapping({"/v1/RcwlElephantController"})
@Tenant("SRM-RCWL")
public class RcwlDaxiangController {
    @Resource
    private RCWLTaxInvoiceLineService rcwlTaxInvoiceLineService;

    @PostMapping({"/invoice-elephant"})
    @ApiOperation("税务发票数据同步")
    @Permission(
            permissionPublic = true
    )

    public ResponseData InvoiceSynchronization(
                                         @RequestBody List<InvoiceData> invoiceDataList) {
        ResponseData responseData = new ResponseData();
        responseData =  rcwlTaxInvoiceLineService.InvoiceSynchronization(0L, invoiceDataList);
        return responseData;
//        return rcwlTaxInvoiceLineService.ResponseData(new ResponseData());
    }
}
