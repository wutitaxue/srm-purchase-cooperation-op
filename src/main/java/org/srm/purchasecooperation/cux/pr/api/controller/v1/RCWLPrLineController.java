package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.web.bind.annotation.*;
import org.srm.common.annotation.PurchaserPowerCron;
import org.srm.purchasecooperation.pr.api.dto.PrLineCloseResultDTO;
import org.srm.purchasecooperation.pr.app.service.PrLineService;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
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


}
