package org.srm.purchasecooperation.cux.api.controller.v1;

import io.swagger.annotations.Api;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.app.service.RCWLTaxInvoiceLineService;
import org.srm.purchasecooperation.cux.domain.entity.InvoiceData;

import javax.annotation.Resource;
import java.util.List;

@Api(
        tags = {"Sinv Asn Header (Org Level)"}
)
@RestController("rcwlTaxInvoiceLineController.v1")
@RequestMapping({"/v1/{organizationId}/taxInvoiceLine"})
public class RCWLTaxInvoiceLineController {
    @Resource
    private RCWLTaxInvoiceLineService rcwlTaxInvoiceLineService;

    @PostMapping({"/invoice-daxiang"})
    public Integer InvoiceSynchronization(@PathVariable("organizationId") Long tenantId,
                                          @Encrypt @RequestBody List<InvoiceData> invoiceDataList) {
        return rcwlTaxInvoiceLineService.InvoiceSynchronization(tenantId, invoiceDataList);
    }
}
