package org.srm.purchasecooperation.cux.transaction.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.common.annotation.PurchaserPowerCron;


import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;
import org.srm.purchasecooperation.cux.transaction.infra.mapper.RcwlOrderBillMapper;
import org.srm.purchasecooperation.transaction.api.dto.CreateRcvTrcDTO;
import org.srm.purchasecooperation.transaction.app.service.RcvTrxHeaderService;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxHeader;
import org.srm.web.annotation.Tenant;

import java.util.List;


@Api(
        tags = {"Transaction Rcv Trx Header (Org Level)"}
)
@RestController("RcwlRcvTrxHeaderController.v1")
@RequestMapping({"/v1/{organizationId}/rcv-trx-header"})
@Tenant("SRM-RCWL")
public class RcwlRcvTrxHeaderController extends BaseController {

    @Autowired
    @Lazy
    private RcvTrxHeaderService rcvTrxHeaderService;

    @Autowired
    private RcwlOrderBillMapper rcvTrxHeaderMapper;

    @Autowired
    private RcwlOrderBillService rcwlOrderBillService;

    @ApiOperation("新增采购接收事务头、行")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/create-rcv-trx-header-line"})
    @PurchaserPowerCron
    public ResponseEntity<RcvTrxHeader> createRcvTrx(@PathVariable Long organizationId, @RequestParam(required = false) String receivedBy, @RequestParam(required = false) String receiveOrderType, @Encrypt @RequestBody List<CreateRcvTrcDTO> dtos) {
        this.validList(dtos, new Class[0]);
        RcvTrxHeader rcvTrxHeader;
        if ("ORDER".equals(receiveOrderType)) {
            rcvTrxHeader = rcvTrxHeaderService.createRcvTrxByPo(organizationId, receivedBy, dtos);
        } else {
            rcvTrxHeader = rcvTrxHeaderService.createRcvTrx(organizationId, receivedBy, dtos);
            //调用资产采购订单接口传输接收单数据
            for (CreateRcvTrcDTO cvTrcvTrcDTO:dtos){
                RcwlOrderBillDTO rcwlOrderBillDTO = rcvTrxHeaderMapper.selectSendAsn(cvTrcvTrcDTO.getAsnLineId());
                rcwlOrderBillDTO.setfProviderContactName(receivedBy);
                rcwlOrderBillDTO.setfIsNew(Boolean.valueOf(rcwlOrderBillDTO.getfIsNew()));
                rcwlOrderBillService.sendOrderBillOne(rcwlOrderBillDTO);
            }
        }

        if (rcvTrxHeader != null) {
            this.rcvTrxHeaderService.sendMessage(rcvTrxHeader.getTenantId(), rcvTrxHeader.getRcvTrxHeaderId());
        }

        return Results.success(rcvTrxHeader);
    }

}
