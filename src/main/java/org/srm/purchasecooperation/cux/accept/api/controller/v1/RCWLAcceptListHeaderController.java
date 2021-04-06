package org.srm.purchasecooperation.cux.accept.api.controller.v1;

import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.accept.api.dto.AcceptListLineQueryDTO;
import org.srm.purchasecooperation.accept.domain.entity.AcceptListHeader;
import org.srm.purchasecooperation.cux.accept.app.service.RCWLAcceptListHeaderService;
import org.srm.purchasecooperation.cux.accept.domain.vo.RCWLAcceptListLineVO;
import org.srm.purchasecooperation.cux.accept.infra.constant.RCWLAcceptConstant;
import org.srm.purchasecooperation.cux.config.AcceptListSwaggerApiConfig;
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

@Tenant(RCWLAcceptConstant.TENANT_NUMBER)
@RestController("rcwlAcceptListHeaderController.v1")
@RequestMapping("/v1/{organizationId}/rcwl")
@Api(tags = AcceptListSwaggerApiConfig.ACCEPT_LIST_HEADER)
public class RCWLAcceptListHeaderController {
    @Autowired
    private RCWLAcceptListHeaderService acceptListHeaderService;

    @ApiOperation(value = "SRM-RCWL验收单明细查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/ac-header/line/page-list")
    public ResponseEntity<Page<RCWLAcceptListLineVO>> getPageDetailAcceptList(
                    @PathVariable("organizationId") Long tenantId, @Encrypt AcceptListLineQueryDTO queryDTO,
                    @ApiIgnore @SortDefault(value = AcceptListHeader.FIELD_CREATION_DATE,
                                    direction = Sort.Direction.DESC) PageRequest pageRequest) {
        return Results.success(acceptListHeaderService.rcwlGetPageDetailAcceptList(queryDTO, tenantId, pageRequest));
    }
}
