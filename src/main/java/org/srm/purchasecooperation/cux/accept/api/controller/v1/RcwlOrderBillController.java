package org.srm.purchasecooperation.cux.accept.api.controller.v1;


import io.choerodon.core.exception.CommonException;
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
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxHeader;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxLine;
import org.srm.purchasecooperation.transaction.domain.repository.RcvTrxHeaderRepository;
import org.srm.purchasecooperation.transaction.domain.repository.RcvTrxLineRepository;

import java.util.List;


@Api(
        tags = {RcwlAcceptConstants.API_TAGS}
)
@RestController("RcwlOrderBillController.v1")
@RequestMapping({"/v1/{organizationId}"})
public class RcwlOrderBillController {

    @Autowired
    RcwlOrderBillService rcwlOrderBillService;
    @Autowired
    private RcvTrxHeaderRepository rcvTrxHeaderRepository;
    @Autowired
    private RcvTrxLineRepository rcvTrxLineRepository;

    @ApiOperation("资产采购订单接口重推")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/rcwl-order-bill"})
    public ResponseEntity<Long> sendOrderBillInserface(@PathVariable("organizationId") Long tenantId,@RequestParam(value = "rcvTrxnum",required = false) String rcvTrxnum,@RequestParam(value = "lineidList",required = false) List<Long> lineidList,@RequestParam(value = "type") String type) {
        if (rcvTrxnum !=null){
            RcvTrxHeader rcvTrxHeader = new RcvTrxHeader();
            rcvTrxHeader.setTrxNum(rcvTrxnum);
            rcvTrxHeader.setTenantId(tenantId);
            RcvTrxHeader Header = rcvTrxHeaderRepository.selectOne(rcvTrxHeader);
            if (Header == null){
                throw new CommonException("单据编码不存在!");
            }
            if("ORDER".equals(type) && Header.getAttributeVarchar6()!="1"){
                return Results.success();
            }
            RcvTrxLine rcvTrxLine = new RcvTrxLine();
            rcvTrxLine.setTenantId(tenantId);
            rcvTrxLine.setRcvTrxHeaderId(Header.getRcvTrxHeaderId());
            rcvTrxLineRepository.select(rcvTrxLine).forEach(item -> {
                        rcwlOrderBillService.sendOrderBillOne(tenantId,item.getRcvTrxLineId(),type);
                    }
            );
        }else if (lineidList.size() > 0){
            lineidList.forEach(item -> {
                rcwlOrderBillService.sendOrderBillOne(tenantId,item,type);
            });
        }
        return Results.success();
    }
}
