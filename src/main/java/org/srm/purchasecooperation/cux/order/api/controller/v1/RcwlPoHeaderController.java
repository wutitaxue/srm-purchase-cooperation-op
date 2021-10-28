package org.srm.purchasecooperation.cux.order.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
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
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoHeaderCreateService;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoHeaderItemService;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlPoLineMapper;
import org.srm.purchasecooperation.order.api.dto.ContractResultDTO;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.api.dto.PoHeaderAccordingToLineOfReferenceDTO;
import org.srm.purchasecooperation.order.api.dto.PoOrderSaveDTO;
import org.srm.purchasecooperation.order.app.service.PoChangeByContractService;
import org.srm.purchasecooperation.order.app.service.PoHeaderService;
import org.srm.purchasecooperation.order.app.service.PoLineService;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.repository.PoCreatingRepository;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationRepository;
import org.srm.purchasecooperation.order.domain.service.PoHeaderDomainService;
import org.srm.purchasecooperation.order.domain.vo.PoHeaderAccordingToLineOfReferenceVO;
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
    @Autowired
    private RcwlPoLineMapper rcwlPoLineMapper;
    @Autowired
    private RcwlPoHeaderCreateService rcwlPoHeaderCreateService;

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


    @ApiOperation(value = "采购申请按行引用汇总查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/po-header/from-pr/line")
    public ResponseEntity<Page<PoHeaderAccordingToLineOfReferenceVO>> selectAccordingToLineOfReference(
            @PathVariable Long organizationId,
            PageRequest pageRequest,
            @Encrypt PoHeaderAccordingToLineOfReferenceDTO poHeaderAccordingToLineOfReferenceDTO){
        poHeaderAccordingToLineOfReferenceDTO.setTenantId(organizationId);
        return Results.success(poLineService.selectAccordingToLineOfReference(pageRequest, poHeaderAccordingToLineOfReferenceDTO));
    }

    @ApiOperation(value = "采购申请按行引用零星申请查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/po-header/from-pr-sporadic/line")
    public ResponseEntity<Page<PoHeaderAccordingToLineOfReferenceVO>> selectSporadicAccording(
            @PathVariable Long organizationId,
            PageRequest pageRequest,
            @Encrypt PoHeaderAccordingToLineOfReferenceDTO poHeaderAccordingToLineOfReferenceDTO){
        poHeaderAccordingToLineOfReferenceDTO.setTenantId(organizationId);
        //申请类型为零星申请
        poHeaderAccordingToLineOfReferenceDTO.setPrTypeId(4L);
        return Results.success(poLineService.selectAccordingToLineOfReference(pageRequest, poHeaderAccordingToLineOfReferenceDTO));
    }

    @ApiOperation("无价格合同按头引用汇总")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping({"/po-header/from-contract/no-price"})
    public ResponseEntity<Page<ContractResultDTO>> selectNoPriceContract(@PathVariable("organizationId") Long tenantId, PageRequest pageRequest, @Encrypt ContractResultDTO contractResultDTO) {
        return Results.success(PageHelper.doPageAndSort(pageRequest, () -> {
            return this.rcwlPoLineMapper.selectNoPriceContract(tenantId, contractResultDTO);
        }));
    }

    @ApiOperation("无价格合同按头引用")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping({"/po-header/from-contract-result/no-price"})
    public ResponseEntity<PoDTO> createAnOrderBasedOnContract(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<ContractResultDTO> contractResultDTOList) {
        contractResultDTOList.forEach((contractResult) -> {
            contractResult.setTenantId(tenantId);
        });
        this.poHeaderDomainService.setPcAttribute(contractResultDTOList);

        return Results.success(this.rcwlPoHeaderCreateService.createAnOrderBasedOnContract(tenantId, contractResultDTOList));
    }
}
