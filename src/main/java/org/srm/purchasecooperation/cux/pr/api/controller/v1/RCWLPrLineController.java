package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.Loader;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.srm.common.annotation.PurchaserPowerCron;
import org.srm.purchasecooperation.cux.pr.api.dto.RcwlPurchaseCompanyVo;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrLineService;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlCompanyService;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPrLineRepository;
import org.srm.purchasecooperation.cux.pr.domain.vo.RCWLPrLineVO;
import org.srm.purchasecooperation.pr.api.dto.PrLineCloseResultDTO;
import org.srm.purchasecooperation.pr.app.service.PrLineService;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.pr.domain.PurchaseCompanyVo;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.web.annotation.Tenant;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @description:常量
 * @author: bin.zhang
 * @createDate: 2021/4/14 16:17
 */
@Api(
        tags = {"Pr Line (Org Level)"}
)
@RestController("RCWLPrLineController.v1")
@RequestMapping({"/v1/{organizationId}"})
@Tenant("SRM-RCWL")
public class RCWLPrLineController {
    @Autowired
    private PrLineService prLineService;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private RCWLPrItfService rcwlPrItfService;
    @Autowired
    private RCWLItfPrDataRespository rcwlItfPrDataRespository;
    @Autowired
    private RCWLPrLineRepository rcwlPrLineRepository;
    @Autowired
    private RcwlCompanyService rcwlCompanyService;
    @Autowired
    private RCWLPrLineService rcwlPrLineService;

    private static final Logger logger = LoggerFactory.getLogger(Loader.class);

    @ApiOperation("采购申请行取消")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/lines/cancel"})
    @PurchaserPowerCron
    public ResponseEntity<Boolean> prLineCancel(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrLineVO> prLineVOS) throws JsonProcessingException {
        Assert.notEmpty(prLineVOS, "error.not_null");
        SecurityTokenHelper.validToken(prLineVOS);

        Boolean flag = this.prLineService.prLineCancel(prLineVOS);
        //调用接口
        this.rcwlPrItfService.linesClose(prLineVOS, tenantId);
        return Results.success(flag);
    }

    @ApiOperation("采购申请行关闭")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/lines/close"})
    @PurchaserPowerCron
    public ResponseEntity<PrLineCloseResultDTO> prLineClose(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrLineVO> prLineVOS) throws JsonProcessingException {
        Assert.notEmpty(prLineVOS, "error.not_null");
        SecurityTokenHelper.validToken(prLineVOS);
        PrLineCloseResultDTO prLineCloseResultDTO = this.prLineService.prLineClose(prLineVOS);

        //调用接口
        this.rcwlPrItfService.linesClose(prLineVOS, tenantId);

        return Results.success(prLineCloseResultDTO);
    }


    @ApiOperation("更新采购申请行信息-source服务-入围单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping({"/purchase-requests/source/lines"})
    public ResponseEntity<List<PrLine>> feignUpdatePrLine(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrLine> prLines) {
        Assert.notEmpty(prLines, "error.not_null");
        SecurityTokenHelper.validToken(prLines);
        List<PrLine> prLineList = rcwlPrLineRepository.updateSourcePrLine(prLines);
        return Results.success(prLineList);
    }

    @ApiOperation("子账户权限下的公司，业务实体")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/purchase-requests/purchase-company"})
    public ResponseEntity<RcwlPurchaseCompanyVo> getPurchaseCompany(@PathVariable("organizationId") @ApiParam(value = "租户id", required = true) Long tenantId, @Encrypt PurchaseCompanyVo purchaseCompanyVo) throws JsonProcessingException {
        logger.info("-------------执行公司查询：-----------");
        ObjectMapper mapper = new ObjectMapper();
        PurchaseCompanyVo purchaseCompanyVo1 = this.prLineService.getPurchaseCompany(tenantId, purchaseCompanyVo);
        RcwlPurchaseCompanyVo rcwlPurchaseCompanyVo = new RcwlPurchaseCompanyVo();

        logger.info("-------------26422:purchaseCompanyVo1:" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(purchaseCompanyVo1));
        if (!ObjectUtils.isEmpty(purchaseCompanyVo1)) {
            rcwlPurchaseCompanyVo.setCompanyId(purchaseCompanyVo1.getCompanyId());
            rcwlPurchaseCompanyVo.setCompanyName(purchaseCompanyVo1.getCompanyName());
            rcwlPurchaseCompanyVo.setCompanyNum(purchaseCompanyVo1.getCompanyNum());
            rcwlPurchaseCompanyVo.setAddress(purchaseCompanyVo1.getAddress());
            rcwlPurchaseCompanyVo.setSpfmCompanyId(purchaseCompanyVo1.getSpfmCompanyId());
            rcwlPurchaseCompanyVo.setInventoryCode(purchaseCompanyVo1.getInventoryCode());
            rcwlPurchaseCompanyVo.setInventoryId(purchaseCompanyVo1.getInventoryId());
            rcwlPurchaseCompanyVo.setInventoryName(purchaseCompanyVo1.getInventoryName());
            rcwlPurchaseCompanyVo.setInvOrganizationId(purchaseCompanyVo1.getInvOrganizationId());
            rcwlPurchaseCompanyVo.setOrganizationId(purchaseCompanyVo1.getOrganizationId());
            rcwlPurchaseCompanyVo.setOrganizationCode(purchaseCompanyVo1.getOrganizationCode());
            rcwlPurchaseCompanyVo.setOrganizationName(purchaseCompanyVo1.getOrganizationName());
            rcwlPurchaseCompanyVo.setOuCode(purchaseCompanyVo1.getOuCode());
            rcwlPurchaseCompanyVo.setOuId(purchaseCompanyVo1.getOuId());
            rcwlPurchaseCompanyVo.setOuName(purchaseCompanyVo1.getOuName());
            rcwlPurchaseCompanyVo.setPurchaseOrgId(purchaseCompanyVo1.getPurchaseOrgId());
            rcwlPurchaseCompanyVo.setPurchaseOrgName(purchaseCompanyVo1.getPurchaseOrgName());
            rcwlPurchaseCompanyVo.setTenantId(purchaseCompanyVo1.getTenantId());
            rcwlPurchaseCompanyVo.setUserId(purchaseCompanyVo1.getUserId());
            if (!ObjectUtils.isEmpty(purchaseCompanyVo1.getCompanyId())) {
                rcwlPurchaseCompanyVo.setRcwlUnitName(rcwlCompanyService.selectCompanyRcwlUnitName(purchaseCompanyVo1.getCompanyId(), tenantId));
            }
            logger.info("-------------copy 后的rcwlPurchaseCompanyVo：" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlPurchaseCompanyVo));
        }
        return Results.success(rcwlPurchaseCompanyVo);
    }

    @ApiOperation("采购申请行查询-分页")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @GetMapping({"/purchase-requests/{prHeaderId}/page"})
    @ProcessLovValue(
            targetField = {"body"}
    )
    public ResponseEntity<Page<RCWLPrLineVO>> pagePrLines(@PathVariable("organizationId") Long tenantId, @Encrypt @PathVariable Long prHeaderId, @ApiIgnore @SortDefault(value = {"lineNum"},direction = Sort.Direction.ASC) PageRequest pageRequest) {
        Page<RCWLPrLineVO> prLineVOPage = this.rcwlPrLineService.rCWLselectPrLinesPage(pageRequest, tenantId, prHeaderId);
        return Results.success(prLineVOPage);
    }

}
