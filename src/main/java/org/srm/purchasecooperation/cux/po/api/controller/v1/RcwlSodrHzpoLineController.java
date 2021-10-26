package org.srm.purchasecooperation.cux.po.api.controller.v1;

import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.vo.ExportParam;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoLineDTO;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoLine;
import org.srm.purchasecooperation.cux.po.domain.repository.RcwlSodrHzpoLineRepository;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 华住订单行 管理 API
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
@RestController("rcwlSodrHzpoLineController.v1")
@RequestMapping("/v1/{organizationId}/rcwl-sodr-hzpo-lines")
@Api(
        tags = {"Sodr HzPo Line"}
)
@Tenant("SRM-RCWL")
public class RcwlSodrHzpoLineController extends BaseController {

    @Autowired
    private RcwlSodrHzpoLineRepository rcwlSodrHzpoLineRepository;

    @ApiOperation(value = "华住订单行列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<RcwlSodrHzpoLineDTO>> list(@PathVariable("organizationId") Long tenantId, RcwlSodrHzpoLineDTO rcwlSodrHzpoLineDTO, @ApiIgnore @SortDefault(value = RcwlSodrHzpoLine.FIELD_PO_LINE_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<RcwlSodrHzpoLineDTO> list = rcwlSodrHzpoLineRepository.pagePoLineList(tenantId, rcwlSodrHzpoLineDTO, pageRequest);
        return Results.success(list);
    }

    @ApiOperation(value = "华住订单行列表导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    public ResponseEntity<List<RcwlSodrHzpoLineDTO>> exportList(@PathVariable("organizationId") Long tenantId, RcwlSodrHzpoLineDTO rcwlSodrHzpoLineDTO, ExportParam exportParam,
                                                                HttpServletResponse response) {
        List<RcwlSodrHzpoLineDTO> list = rcwlSodrHzpoLineRepository.exportPoLineList(tenantId, rcwlSodrHzpoLineDTO);
        return Results.success(list);
    }
}
