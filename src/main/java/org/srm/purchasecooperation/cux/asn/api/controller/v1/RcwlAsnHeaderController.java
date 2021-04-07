package org.srm.purchasecooperation.cux.asn.api.controller.v1;


import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.asn.domain.entity.RcwlAsnHeader;
import org.srm.purchasecooperation.asn.domain.repository.AsnHeaderRepository;
import org.srm.purchasecooperation.cux.asn.domain.repository.RcwlAsnHeaderRepository;
import org.srm.purchasecooperation.cux.asn.domain.vo.RcwlAsnHeaderVO;
import org.srm.purchasecooperation.cux.asn.infra.constant.Constants;
import org.srm.web.annotation.Tenant;

@Api(
        tags = {Constants.API_TAGS}
)
@RestController("RcwlAsnHeaderController.v1")
@RequestMapping({"/v1/{organizationId}/asn-header"})
@Tenant("SRM-RCWL")
public class RcwlAsnHeaderController {

    @Autowired
    private AsnHeaderRepository AsnHeaderRepository;

    @Autowired
    private RcwlAsnHeaderRepository rcwlAsnHeaderRepository;

    public RcwlAsnHeaderController() {
    }

    @ApiOperation("送货单头附件ID刷新")
    @PutMapping({"/attachment-uuid"})
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    public ResponseEntity<RcwlAsnHeader> updateAsnHeaderUuid(@PathVariable("organizationId") Long organizationId, @Encrypt @RequestBody RcwlAsnHeader rcwlasnHeader) {
        SecurityTokenHelper.validToken(rcwlasnHeader);
        Assert.notNull(rcwlasnHeader.getAsnHeaderId(), "error.data_invalid");
        this.AsnHeaderRepository.updateOptional(rcwlasnHeader, new String[]{"approveAttachmentUuid", "reviewAttachmentUuid", "otherAttachmentUuid", "supplierAttachmentUuid", "supplierAttaUuid","deliveredAttaUuid"});
        return Results.success(rcwlasnHeader);
    }

    @ApiOperation("送货单头查询")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/{asnHeaderId}"})
    public ResponseEntity<RcwlAsnHeaderVO> detailAsnHeader(@PathVariable("organizationId") Long organizationId, @Encrypt @PathVariable("asnHeaderId") Long asnHeaderId) {
        return Results.success(this.rcwlAsnHeaderRepository.selectRcwlAsnHeaderByHeaderId(asnHeaderId));
    }
}
