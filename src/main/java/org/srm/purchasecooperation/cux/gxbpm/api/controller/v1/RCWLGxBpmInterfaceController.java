package org.srm.purchasecooperation.cux.gxbpm.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.hzero.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.gxbpm.api.dto.RCWLGxBpmRequestDataDTO;
import org.srm.purchasecooperation.cux.gxbpm.api.dto.RCWLGxBpmStartDataDTO;
import org.srm.purchasecooperation.cux.gxbpm.app.service.RCWLGxBpmInterfaceService;
import org.srm.purchasecooperation.cux.gxbpm.infra.constant.RCWLGxBpmConstant;
import org.srm.web.annotation.Tenant;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 21:53
 * @version:1.0
 */
@Tenant(RCWLGxBpmConstant.TENANT_NUMBER)
@RestController("RCWLGxBpmInterfaceController.v1")
@RequestMapping("/v1/{organizationId}/interface")
public class RCWLGxBpmInterfaceController {

    @Autowired
    private RCWLGxBpmInterfaceService rcwlGxBpmInterfaceService;

    @ApiOperation(value = "共享传输数据到BPM")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/gx-bpm")
    public ResponseEntity<RCWLGxBpmRequestDataDTO> RcwlGxBpmInterfacePost(@ApiParam(value = "传输数据", required = true) @RequestBody RCWLGxBpmStartDataDTO rcwlGxBpmStartDataDTO ) {
        return Results.success(rcwlGxBpmInterfaceService.RcwlGxBpmInterfaceRequestData(rcwlGxBpmStartDataDTO));
    }
}
