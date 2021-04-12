package org.srm.purchasecooperation.cux.acp.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.hzero.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpInvoiceElephantRequestDataDTO;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpOcrDTO;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpOcrService;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpUserDataService;
import org.srm.purchasecooperation.cux.acp.domain.entity.RCWLAcpInvoiceData;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.web.annotation.Tenant;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lu.cheng01@hand-china.com
 * @description 发票调用大象云接口
 * @date 2021/4/8 20:09
 * @version:1.0
 */
@Tenant(rcwlActConstant.TENANT_NUMBER)
@RestController("RCWLAcpInvoiceController.v1")
@RequestMapping("/v1/{organizationId}/acp")
public class RCWLAcpInvoiceController {
    @Autowired
    private RCWLAcpOcrService rcwlAcpOcrService;

    @ApiOperation(value = "获取加密后的大象云url")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/invoice/getUrl")
    public ResponseEntity<RCWLAcpOcrDTO> getOcrUrl(@RequestBody RCWLAcpInvoiceData rcwlAcpInvoiceData) {
        return Results.success(rcwlAcpOcrService.acpGetData(rcwlAcpInvoiceData));
    }

    @ApiOperation(value = "获取加密后的大象请求数据")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/invoice/get-request-data")
    public ResponseEntity<RCWLAcpInvoiceElephantRequestDataDTO> getInvoiceElephantRequestData(@ApiParam(value = "发票id", required = true) @Param("InvoiceHeaderId") Long InvoiceHeaderId) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        return Results.success(rcwlAcpOcrService.acpInvoiceELephantGRequestData(InvoiceHeaderId));
    }
}
