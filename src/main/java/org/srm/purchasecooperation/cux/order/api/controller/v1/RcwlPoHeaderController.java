package org.srm.purchasecooperation.cux.order.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.print.PrintHelper;
import org.srm.common.annotation.PurchaserPowerCron;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoHeaderItemService;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.api.dto.PoOrderSaveDTO;
import org.srm.purchasecooperation.order.app.service.PoChangeByContractService;
import org.srm.purchasecooperation.order.app.service.PoHeaderService;
import org.srm.purchasecooperation.order.app.service.PoLineService;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.repository.PoCreatingRepository;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationRepository;
import org.srm.purchasecooperation.order.domain.service.PoHeaderDomainService;
import org.srm.web.annotation.Tenant;

import java.util.Collections;
import java.util.List;

/**
 * @author bin.zhang
 */
@RestController("RcwlPoHeaderController.v1")
@RequestMapping({"/v1/{organizationId}"})
@Api(
        tags = {"Po Header"}
)
@Tenant("SRM-RCWL")
public class RcwlPoHeaderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPoHeaderController.class);
    @Autowired
    private PoHeaderService poHeaderService;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private PoLineService poLineService;
    @Autowired
    private PrintHelper printHelper;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private PoCreatingRepository poCreatingRepository;
    @Autowired
    private PoHeaderDomainService poHeaderDomainService;
    @Autowired
    private PoChangeByContractService poChangeByContractService;
    @Autowired
    private PoLineLocationRepository poLineLocationRepository;
    @Value("${service.home-url}")
    private String homeUrl;
    @Value("${service.od-url}")
    private String odUrl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RcwlPoHeaderItemService rcwlPoHeaderItemService;

    @ApiOperation("手工审批通过采购订单")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/po-header/detail-approve"})
    @ProcessLovValue(
            targetField = {"body"}
    )
    @PurchaserPowerCron
    public ResponseEntity<List<PoDTO>> poApproveDetail(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PoOrderSaveDTO> poOrderSavaDTOList) {
        List<PoDTO> poDTOList = this.poHeaderService.poApprove(PoHeader.convertSaveDetailToPoDtoList(poOrderSavaDTOList, tenantId));
        String approvalStatusReturn = this.customizeSettingHelper.queryBySettingCode(((PoDTO) poDTOList.get(0)).getTenantId(), "010212");
        poDTOList.stream().forEach((poDTO) -> {
            String manualPublicFlag = this.poHeaderRepository.getPoConfigCodeValue(poDTO.getTenantId(), poDTO.getPoHeaderId(), "SITE.SPUC.PO.MANUAL_PUBLISH");
            List<PoDTO> singletonList = Collections.singletonList(poDTO);
            if (String.valueOf(BaseConstants.Flag.YES).equals(approvalStatusReturn)) {
                this.poHeaderService.erpPoApproveStatusReturn(singletonList, !String.valueOf(BaseConstants.Flag.YES).equals(manualPublicFlag), DetailsHelper.getUserDetails().getUserId());
            }
            //融创新增 自动生成物料编码RCWLPoHeaderController
            this.rcwlPoHeaderItemService.insertItemCode(poDTO, tenantId);
        });
        return Results.success(poDTOList);
    }

    @ApiOperation("批量审批通过采购订单")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/po-header/batch-approve"})
    @ProcessLovValue(
            targetField = {"body"}
    )
    @PurchaserPowerCron
    public ResponseEntity<List<PoDTO>> poApprove(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PoDTO> poDTOList) {
        poDTOList.forEach((item) -> {
            item.setTenantId(tenantId);
        });
        poDTOList = this.poHeaderService.poApprove(poDTOList);
        String approvalStatusReturn = this.customizeSettingHelper.queryBySettingCode(tenantId, "010212");
        poDTOList.stream().forEach((poDTO) -> {
            String manualPublicFlag = this.poHeaderRepository.getPoConfigCodeValue(poDTO.getTenantId(), poDTO.getPoHeaderId(), "SITE.SPUC.PO.MANUAL_PUBLISH");
            List<PoDTO> singletonList = Collections.singletonList(poDTO);
            if (String.valueOf(BaseConstants.Flag.YES).equals(approvalStatusReturn)) {
                this.poHeaderService.erpPoApproveStatusReturn(singletonList, !String.valueOf(BaseConstants.Flag.YES).equals(manualPublicFlag), DetailsHelper.getUserDetails().getUserId());
            }
            //融创新增 自动生成物料编码RCWLPoHeaderController
            this.rcwlPoHeaderItemService.insertItemCode(poDTO, tenantId);
        });
        return Results.success(poDTOList);
    }
}
