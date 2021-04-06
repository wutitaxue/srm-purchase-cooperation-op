package org.srm.purchasecooperation.cux.asn.api.controller.v1;


import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.asn.api.dto.RcwlAsnAcceptOrRcvDTO;
import org.srm.purchasecooperation.cux.asn.domain.service.RcwlAsnInterfaceService;

import java.util.List;

@Api(
        tags = {"eas-return"}
)
@RestController("RcwlAsnInterfaceController.v1")
//@Tenant("SRM-RCWL")
@RequestMapping({"/v1/eas-return"})
public class RcwlAsnInterfaceController {

    @Autowired
    private RcwlAsnInterfaceService rcwlAsnInterfaceService;

    /*
    * 二开接口 资产返回接口
    * 业务类型 1回传数据 2单据反审核
    * 采购平台单据类型 01接收 02验收
    * */
    @ApiOperation("资产返回接口")
    @Permission(
            permissionPublic = true
    )
    @PostMapping({"/return-accept-rcv"})
    public ResponseEntity<List<RcwlAsnAcceptOrRcvDTO>> returnAcceptOrRcvBack(@RequestBody List<RcwlAsnAcceptOrRcvDTO> list) {
        return Results.success(rcwlAsnInterfaceService.returnAcceptOrRcvBack(list));
    }
}
