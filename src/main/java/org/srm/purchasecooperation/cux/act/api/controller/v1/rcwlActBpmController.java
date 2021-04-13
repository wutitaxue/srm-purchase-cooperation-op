package org.srm.purchasecooperation.cux.act.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;
import org.srm.purchasecooperation.cux.act.app.service.ActService;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.purchasecooperation.finance.api.dto.InvoiceTransactionSearchDTO;
import org.srm.web.annotation.Tenant;
import springfox.documentation.annotations.ApiIgnore;

import java.util.logging.Logger;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 9:59
 * @version:1.0
 */
@Tenant(rcwlActConstant.TENANT_NUMBER)
@RestController("rcwlActBpmController.v1")
@RequestMapping("/v1/{organizationId}/act")
public class rcwlActBpmController {
    @Autowired
    private ActService actService;

    /**
     * 验收单bpm接口查询
     *
     * @param acceptListHeaderId
     * @return
     */
    @ApiOperation(value = "验收单BPM接口传输")
    @Permission(level = ResourceLevel.ORGANIZATION)
//    @Permission(permissionPublic = true)
    @PostMapping("/getAct")
    @ProcessLovValue
    public ResponseEntity<ActListHeaderDto> queryList(@ApiParam(value = "租户Id", required = true) @PathVariable("organizationId") Long organizationId, @ApiParam(value = "验收单头id", required = true) @Param("acceptListHeaderId") Long acceptListHeaderId) {
        return Results.success(actService.actQuery(acceptListHeaderId,organizationId));
    }
}
