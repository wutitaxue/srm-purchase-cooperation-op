package org.srm.purchasecooperation.cux.transaction.api.controller.v1;


import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.common.annotation.PurchaserPowerCron;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlRcvTrxHeaderService;
import org.srm.purchasecooperation.cux.transaction.domain.repository.RcwlRcvTrxHeaderRepository;
import org.srm.purchasecooperation.cux.transaction.domain.vo.RcwlReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.transaction.api.dto.RcvTrxQueryDataDTO;
import org.srm.purchasecooperation.transaction.app.service.RcvTrxHeaderService;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;
import org.srm.web.annotation.Tenant;

@Api(
        tags = {"Transaction Rcv Trx Header (Org Level)"}
)
@RestController("RcwlRcvTrxHeaderController.v1")
@RequestMapping({"/v1/{organizationId}/rcv-trx-header"})
@Tenant("SRM-RCWL")
public class RcwlRcvTrxHeaderController {

    @Autowired
    private RcwlRcvTrxHeaderService rcwlRcvTrxHeaderService;

    @ApiOperation("查询允许做接收的送货单行-分页")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/permit-receive-asn-line"})
    @PurchaserPowerCron
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<RcwlReceiveRcvTrxDataVO>> rcwlPermitReceiveAsnLine(@PathVariable Long organizationId, @Encrypt RcvTrxQueryDataDTO dto, PageRequest pageRequest) {
//        return this.rcwlRcvTrxHeaderService.getSrmReceiveSystemConfig(organizationId) ? Results.success(this.rcwlRcvTrxHeaderService.rcwlQueryCanRcvTrxDataEntrance(organizationId, dto, pageRequest)) : Results.success();
        ResponseEntity<Page<RcwlReceiveRcvTrxDataVO>> success = Results.success(this.rcwlRcvTrxHeaderService.rcwlQueryCanRcvTrxDataEntrance(organizationId, dto, pageRequest));
        return success;
    }
}
