package org.srm.purchasecooperation.cux.sinv.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAsnNumDTO;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAsnStatusDTO;
import org.srm.purchasecooperation.cux.sinv.app.service.SinvRrvTrxHeaderAsnStatusService;
import org.srm.purchasecooperation.cux.sinv.config.TransactionSwaggerApiConfig;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxWaitingDTO;
import springfox.documentation.annotations.ApiIgnore;

/**
 * description
 *
 * @author Penguin 2021/04/30 16:36
 */

@Api(tags = TransactionSwaggerApiConfig.RCWL_SUPPLIER_OPERATION_ASN_HEADER)
@RestController("SinvRcvTrxHeaderAsnStatusController.v1")
@RequestMapping("/v1/{organizationId}/sinv/rcv/trx/header-asn-status")
public class SinvRcvTrxHeaderAsnStatusController {

    @Autowired
    private SinvRrvTrxHeaderAsnStatusService sinvRrvTrxHeaderAsnStatusService;


    @ApiOperation("收货执行-获取待执行数据-送货单状态")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/waiting"})
    public ResponseEntity<Page<SinvRcvTrxWaitingAsnStatusDTO>> pageRcvTrxWaiting(@PathVariable("organizationId") @ApiParam(value = "租户id",required = true) Long tenantId, @Encrypt SinvRcvTrxWaitingAsnNumDTO asnNumDTO, @ApiIgnore PageRequest pageRequest) {
        asnNumDTO.setTenantId(tenantId);
        Page<SinvRcvTrxWaitingAsnStatusDTO> sinvRcvTrxWaitingDTOPage = this.sinvRrvTrxHeaderAsnStatusService.pageRcvTrxWaitingAsnStatus(asnNumDTO, pageRequest);
        return Results.success(sinvRcvTrxWaitingDTOPage);
    }



}
