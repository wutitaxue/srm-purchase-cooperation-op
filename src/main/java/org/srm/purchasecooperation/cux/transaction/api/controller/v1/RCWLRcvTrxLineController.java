package org.srm.purchasecooperation.cux.transaction.api.controller.v1;

import java.util.HashMap;
import java.util.Map;

import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.transaction.api.dto.RCWLReceiveTransactionLineDTO;
import org.srm.purchasecooperation.cux.transaction.app.service.RCWLRcvTrxLineService;
import org.srm.purchasecooperation.cux.transaction.infra.constant.RCWLTransactionConstant;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;
import org.srm.purchasecooperation.transaction.config.TransactionSwaggerApiConfig;
import org.srm.web.annotation.Tenant;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Tenant(RCWLTransactionConstant.TENANT_NUMBER)
@RestController("rcwlRcvTrxLineController.v1")
@RequestMapping("/v1/{organizationId}/rcwl/rcv-trx-line")
@Api(TransactionSwaggerApiConfig.TRANSACTION_RCV_TRX_LINE)
public class RCWLRcvTrxLineController {
    @Autowired
    private RCWLRcvTrxLineService rcvTrxLineService;

    @ApiOperation("SRM-RCWL我(采购方)的收货记录查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping({"/for-purchase"})
    public ResponseEntity<Page<RCWLReceiveTransactionLineDTO>> rcwlQueryReceiveTransactionLineForPurchase(
                    @PathVariable("organizationId") Long tenantId, @Encrypt ReceiveTransactionLineDTO queryParam,
                    @ApiIgnore @SortDefault.SortDefaults({
                            @SortDefault(value = {"trxDate"}, direction = Sort.Direction.DESC),
                            @SortDefault(value = {"displayTrxNum"}, direction = Sort.Direction.DESC),
                            @SortDefault(value = {"displayTrxLineNum"},
                                            direction = Sort.Direction.ASC)}) PageRequest pageRequest) {
        Map<String, String> map = new HashMap();
        map.put("trxDate", "srtl.trx_date");
        map.put("displayTrxLineNum", "srtl.display_trx_line_num");
        pageRequest.resetOrder("srth", map);
        queryParam.setTenantId(tenantId);
        Page<RCWLReceiveTransactionLineDTO> res =
                        this.rcvTrxLineService.rcwlQueryReceiveTransactionLineForPurchase(queryParam, pageRequest);
        return Results.success(res);
    }
}
