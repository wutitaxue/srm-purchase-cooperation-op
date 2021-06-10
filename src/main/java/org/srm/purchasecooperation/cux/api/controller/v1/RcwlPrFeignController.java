package org.srm.purchasecooperation.cux.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.util.Results;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.mybatis.util.Sqls;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.pr.domain.vo.ErrorDataVO;
import org.srm.purchasecooperation.pr.domain.vo.ErrorListVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Api(
        tags = {"Rcwl Pr Header (Org Level)"}
)
@RestController("rcwlPrFeignController.v1")
@RequestMapping({"/v1/{organizationId}"})
public class RcwlPrFeignController {


    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrHeaderService prHeaderService;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private PrLineRepository prLineRepository;

    @ApiOperation("采购申请流程发起成功变更字段")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/purchase-requests/bsuccess"})
    @ProcessLovValue
    public ResponseEntity<PrHeader> prBsuccess(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestParam("prHeaderId") Long prHeaderId,@RequestParam("attributeVarchar17") String attributeVarchar17,@RequestParam("attributeVarchar18") String attributeVarchar18) {
        PrHeader prHeader = new PrHeader();
        prHeader.setTenantId(tenantId);
        prHeader.setPrHeaderId(prHeaderId);
        prHeader.setObjectVersionNumber(prHeaderRepository.selectOne(prHeader).getObjectVersionNumber());
        prHeader.setAttributeVarchar17(attributeVarchar17);
        prHeader.setAttributeVarchar18(attributeVarchar18);
        prHeaderRepository.updateByPrimaryKeySelective(prHeader);
        return Results.success(prHeader);
    }

    @ApiOperation("采购申请审批结束变更字段")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/purchase-requests/processend"})
    @ProcessLovValue
    public ResponseEntity<PrHeader> prProcessend(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestParam("prHeaderId") Long prHeaderId) {
        PrHeader prHeader = new PrHeader();
        prHeader.setTenantId(tenantId);
        prHeader.setPrHeaderId(prHeaderId);
        prHeader.setObjectVersionNumber(prHeaderRepository.selectOne(prHeader).getObjectVersionNumber());
        prHeader.setAttributeVarchar17("0");
        prHeaderRepository.updateByPrimaryKeySelective(prHeader);
        return Results.success(prHeader);
    }

    @PostMapping({"/purchase-requests/prsubmit"})
    public ResponseEntity<Object> submit(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrHeader> prHeaderList) {
        prHeaderList.forEach(item -> {
            PrHeader prHeader = prHeaderRepository.selectOne(item);
            prHeaderService.submit(tenantId,prHeader);
        });
        return Results.success(prHeaderList);
    }

    @ApiOperation("采购申请审批通过")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/approve/prapproval"})
    public ResponseEntity<List<PrHeader>> prApproval(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrHeader> prHeaderList) {
//        prHeaderList.forEach(item -> {
//            PrHeader prHeader = new PrHeader();
//            prHeader.setTenantId(item.getTenantId());
//            prHeader.setPrHeaderId(item.getPrHeaderId());
//            item.setObjectVersionNumber(prHeaderRepository.selectOne(item).getObjectVersionNumber());
//        });
        List<PrHeader> prHeaderApprovalList = this.prHeaderService.prApproval(tenantId, prHeaderList, Boolean.TRUE);
        this.prHeaderService.exportPrToErp(tenantId, prHeaderApprovalList);
        ((PrHeader)prHeaderApprovalList.get(0)).setCustomUserDetails(DetailsHelper.getUserDetails());
        this.prHeaderService.afterPrApprove(tenantId, prHeaderApprovalList);
        return Results.success(prHeaderApprovalList);
    }
    @ApiOperation("采购申请审批拒绝")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/approve/prreject"})
    public ResponseEntity<List<PrHeader>> prReject(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrHeader> prHeaderList) {
        prHeaderList.forEach(item -> {
            PrHeader prHeader = new PrHeader();
            prHeader.setTenantId(item.getTenantId());
            prHeader.setPrHeaderId(item.getPrHeaderId());
            item.setObjectVersionNumber(prHeaderRepository.selectOne(item).getObjectVersionNumber());
        });
        return Results.success(this.prHeaderService.prReject(prHeaderList));
    }
}
