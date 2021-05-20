package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.srm.common.annotation.PurchaserPowerCron;
import org.srm.purchasecooperation.cux.pr.api.dto.RcwlPurchaseCompanyVo;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlCompanyService;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPrLineRepository;
import org.srm.purchasecooperation.pr.api.dto.PrLineCloseResultDTO;
import org.srm.purchasecooperation.pr.app.service.PrLineService;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.pr.domain.PurchaseCompanyVo;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.web.annotation.Tenant;

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
    public ResponseEntity<List<PrLine>> feignUpdatePrLine(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrLine> prLines){
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
    public ResponseEntity<RcwlPurchaseCompanyVo> getPurchaseCompany(@PathVariable("organizationId") @ApiParam(value = "租户id",required = true) Long tenantId, @Encrypt PurchaseCompanyVo purchaseCompanyVo) {
        PurchaseCompanyVo purchaseCompanyVo1 = new PurchaseCompanyVo();
        purchaseCompanyVo1 = this.prLineService.getPurchaseCompany(tenantId, purchaseCompanyVo);
        RcwlPurchaseCompanyVo rcwlPurchaseCompanyVo = new RcwlPurchaseCompanyVo();
        BeanUtils.copyProperties(purchaseCompanyVo1, rcwlPurchaseCompanyVo);
        rcwlPurchaseCompanyVo.setRcwlUnitName(rcwlCompanyService.selectCompanyRcwlUnitName(purchaseCompanyVo1.getCompanyId(),tenantId));
        return Results.success(rcwlPurchaseCompanyVo);
    }

}
