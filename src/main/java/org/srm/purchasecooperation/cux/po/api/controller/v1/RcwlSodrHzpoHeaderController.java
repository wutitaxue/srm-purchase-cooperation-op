package org.srm.purchasecooperation.cux.po.api.controller.v1;

import io.swagger.annotations.Api;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoHeaderDTO;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoHeader;
import org.srm.purchasecooperation.cux.po.domain.repository.RcwlSodrHzpoHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.hzero.mybatis.helper.SecurityTokenHelper;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.srm.web.annotation.Tenant;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 华住订单头 管理 API
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
@RestController("rcwlSodrHzpoHeaderController.v1")
@RequestMapping("/v1/{organizationId}/rcwl-sodr-hzpo-headers")
@Api(
        tags = {"Sodr Hzpo Header"}
)
@Tenant("SRM-RCWL")
public class RcwlSodrHzpoHeaderController extends BaseController {

    @Autowired
    private RcwlSodrHzpoHeaderRepository rcwlSodrHzpoHeaderRepository;

    @ApiOperation(value = "华住订单头列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    @ProcessLovValue(
            targetField = {"body"}
    )
    public ResponseEntity<Page<RcwlSodrHzpoHeaderDTO>> list(@PathVariable("organizationId") Long tenantId, RcwlSodrHzpoHeaderDTO rcwlSodrHzpoHeaderDTO, @ApiIgnore @SortDefault(value = RcwlSodrHzpoHeader.FIELD_PO_HEADER_ID,
            direction = Sort.Direction.ASC) PageRequest pageRequest) {
        Page<RcwlSodrHzpoHeaderDTO> list = rcwlSodrHzpoHeaderRepository.pagePoHeaderList(tenantId, rcwlSodrHzpoHeaderDTO, pageRequest);
        return Results.success(list);
    }

    @ApiOperation(value = "华住订单头列表导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ProcessLovValue(
            targetField = {"body"}
    )
    @ExcelExport(RcwlSodrHzpoHeaderDTO.class)
    public ResponseEntity<List<RcwlSodrHzpoHeaderDTO>> exportList(@PathVariable("organizationId") Long tenantId, RcwlSodrHzpoHeaderDTO rcwlSodrHzpoHeaderDTO, ExportParam exportParam,
                                                                  HttpServletResponse response) {
        List<RcwlSodrHzpoHeaderDTO> list = rcwlSodrHzpoHeaderRepository.exportPoHeaderList(tenantId, rcwlSodrHzpoHeaderDTO);
        return Results.success(list);
    }

}
