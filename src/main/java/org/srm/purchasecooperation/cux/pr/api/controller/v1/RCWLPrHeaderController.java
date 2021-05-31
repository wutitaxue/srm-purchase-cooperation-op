package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.print.PrintHelper;
import org.srm.common.annotation.PurchaserPowerCron;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrToBpmService;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.web.annotation.Tenant;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:采购申请
 * @author: bin.zhang
 * @createDate: 2021/4/10 13:33
 */
@Api(
        tags = {"Pr Header (Org Level)"}
)
@RestController("RCWLPrHeaderController.v1")
@RequestMapping({"/v1/{organizationId}"})
@Tenant("SRM-RCWL")
public class RCWLPrHeaderController {
    @Autowired
    private PrHeaderService prHeaderService;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrintHelper printHelper;
    @Autowired
    private RCWLPrItfService rcwlPrItfService;
   // @Autowired
   //private RCWLPrHeaderSubmitService rcwlPrHeaderSubmitService;
    @Autowired
    private RcwlPrToBpmService rcwlPrToBpmService;
    private static final Logger logger = LoggerFactory.getLogger(RCWLPrHeaderController.class);


//    @ApiOperation("采购申请批量提交")
//    @Permission(
//            level = ResourceLevel.ORGANIZATION
//    )
//    @PostMapping({"/purchase-requests/submit"})
//    public ResponseEntity<Object> submit(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrHeader> prHeaderList) {
//        SecurityTokenHelper.validToken(prHeaderList, false);
//        List<Object> list = this.prHeaderService.batchSubmit(tenantId, prHeaderList);
//        List<ErrorDataVO> errorDataVOList = new ArrayList();
//        List<PrHeader> successList = new ArrayList();
//        Iterator var6 = list.iterator();
//
//        while(var6.hasNext()) {
//            Object obj = var6.next();
//            if (obj instanceof ErrorDataVO) {
//                errorDataVOList.add((ErrorDataVO)obj);
//            } else if (obj instanceof PrHeader) {
//                ((PrHeader)obj).setOperationFlag("I");
//                successList.add((PrHeader)obj);
//            }
//        }
//
//        List<PrHeader> successList = (List)successList.stream().filter((prHeader) -> {
//            return prHeader.checkPrSyncToSap(this.prHeaderService, this.customizeSettingHelper);
//        }).collect(Collectors.toList());
//        if (CollectionUtils.isNotEmpty(successList)) {
//            this.prHeaderService.exportPrToErp(tenantId, successList);
//        }
//
//        if (CollectionUtils.isNotEmpty(errorDataVOList)) {
//            return Results.success(new ErrorListVO(errorDataVOList));
//        } else {
//            if (CollectionUtils.isNotEmpty(successList)) {
//                ((PrHeader)successList.get(0)).setCustomUserDetails(DetailsHelper.getUserDetails());
//                this.prHeaderService.afterPrApprove(tenantId, successList);
//            }
//
//            this.prHeaderService.batchBudgetOccupyThrowException(tenantId, prHeaderList);
//            return Results.success(successList);
//        }
//    }


    @ApiOperation("采购申请提交")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/singleton-submit"})
    public ResponseEntity<PrHeader> singletonSubmit(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody PrHeader prHeader) throws JsonProcessingException {
        SecurityTokenHelper.validToken(prHeader, false);

      // String token = this.rcwlPrItfService.getToken();

     // this.rcwlPrItfService.invokeBudgetOccupy(prHeader,tenantId);

        prHeader = this.prHeaderService.singletonSubmit(tenantId, prHeader);
        boolean syncFlag = prHeader.checkPrSyncToSap(this.prHeaderService, this.customizeSettingHelper);
        if (syncFlag) {
            prHeader.setOperationFlag("I");
            this.prHeaderService.exportPrToErp(tenantId, Collections.singletonList(prHeader));
            prHeader.setCustomUserDetails(DetailsHelper.getUserDetails());
            this.prHeaderService.afterPrApprove(tenantId, Collections.singletonList(prHeader));
        }
        String dataToBpmUrl = this.rcwlPrToBpmService.prDataToBpm(prHeader, "create");
        prHeader.setAttributeVarchar37(dataToBpmUrl);
        return Results.success(prHeader);
    }



//    @ApiOperation("采购申请审批拒绝")
//    @Permission(
//            level = ResourceLevel.ORGANIZATION
//    )
//    @PostMapping({"/purchase-requests/approve/reject"})
//    @PurchaserPowerCron
//    public ResponseEntity<List<PrHeader>> prReject(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrHeader> prHeaderList) {
//        SecurityTokenHelper.validToken(prHeaderList, false);
//        //调用接口
//        this.rcwlPrItfService.invokeBudgetList(prHeaderList,tenantId);
//        return Results.success(this.prHeaderService.prReject(prHeaderList));
//    }


    @ApiOperation("采购申请整单关闭")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/close"})
    @PurchaserPowerCron
    public ResponseEntity<PrHeader> close(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody PrHeader prHeader) throws JsonProcessingException {
        Assert.notNull(prHeader, "error.not_null");
        SecurityTokenHelper.validToken(prHeader, false);
        PrHeader returnCloseResults = this.prHeaderService.closeWholePrNote(tenantId, prHeader);
        //调用接口
        this.rcwlPrItfService.invokeBudgetOccupyClose(prHeader,tenantId);
        return Results.success(returnCloseResults);
    }



    @ApiOperation("采购申请整单取消")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/cancel"})
    @PurchaserPowerCron
    public ResponseEntity<List<PrHeader>> cancel(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody List<PrHeader> prHeaders) throws JsonProcessingException {
        Assert.notEmpty(prHeaders, "error.not_null");
        SecurityTokenHelper.validToken(prHeaders, false);
        List<PrHeader> returnPrHeaders = this.prHeaderService.cancelWholePrNote(tenantId, prHeaders);
        //调用接口
        PrHeader prHeader = prHeaders.get(0);

        this.rcwlPrItfService.invokeBudgetOccupyClose(prHeader,tenantId);

        return Results.success(returnPrHeaders);
    }

    @ApiOperation("采购申请变更提交")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PutMapping({"/purchase-requests/submit-change"})
    @PurchaserPowerCron
    public ResponseEntity<PrHeader> changeSubmit(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody PrHeader prHeader) throws JsonProcessingException {



        Assert.notNull(prHeader, "error.not_null");
        SecurityTokenHelper.validToken(prHeader, false);
        Assert.notEmpty(prHeader.getPrLineList(), "error.not_null");

        //触发变更接口
        //this.rcwlPrItfService.submitChange(prHeader,tenantId);


        Set<String> approveSet = new HashSet();
        prHeader = this.prHeaderService.changeSubmit(tenantId, prHeader, approveSet);
//        //触发变更接口
        this.rcwlPrItfService.submitChange(prHeader,tenantId);

        boolean syncFlag = prHeader.checkPrSyncToSap(this.prHeaderService, this.customizeSettingHelper);
        if ((CollectionUtils.isNotEmpty(approveSet) || "REJECTED".equals(prHeader.getPrStatusCode())) && syncFlag) {
            this.prHeaderService.afterChangeSubmit(tenantId, prHeader);
        }
        String prDataToBpm = this.rcwlPrToBpmService.prDataToBpm(prHeader, "change");
        prHeader.setAttributeVarchar37(prDataToBpm);
        return Results.success(prHeader);
    }

    @ApiOperation("BPM审批回传调用预算接口(采购申请提交)")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/after-bpm-approve"})
    public ResponseEntity afterBpmApprove(@PathVariable("organizationId") Long tenantId,@RequestParam(value = "prNum") String prNum,@RequestParam(value ="approveFlag") String approveFlag ) throws JsonProcessingException {
        logger.info("回传数据prNum：=================="+prNum);
        logger.info("回传数据approveFlag：=================="+approveFlag);

              if(!StringUtils.isEmpty(prNum)){
                  //bpm回传拒绝标识时触发预算释放接口
                  if(RCWLConstants.BPMApproveFlag.REJECTED.equals(approveFlag)) {
                      this.rcwlPrItfService.afterBpmApprove(prNum, approveFlag);
                  }
              }
        return Results.success();
    }

    @ApiOperation("BPM审批回传调用预算接口(采购申请变更提交)")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/after-bpm-approve-change"})
    public ResponseEntity afterBpmApproveByChange(@PathVariable("organizationId") Long tenantId,@RequestParam(value = "prNum") String prNum,@RequestParam(value ="approveFlag") String approveFlag ) throws JsonProcessingException {
        logger.info("回传数据prNum：=================="+prNum);
        logger.info("回传数据approveFlag：=================="+approveFlag);

        if(!StringUtils.isEmpty(prNum)){
            //bpm回传拒绝标识时触发预算接口
            if(RCWLConstants.BPMApproveFlag.REJECTED.equals(approveFlag)) {
                this.rcwlPrItfService.afterBpmApproveByChange(prNum, approveFlag);
            }
        }
        return Results.success();
    }
}
