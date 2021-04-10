package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.print.PrintHelper;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.pr.api.controller.v1.PrHeaderController;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.vo.ErrorDataVO;
import org.srm.purchasecooperation.pr.domain.vo.ErrorListVO;
import org.srm.web.annotation.Tenant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:采购申请
 * @author: bin.zhang
 * @createDate: 2021/4/10 13:33
 */
@Api(
        tags = {"Pr Header (Org Level)"}
)
@RestController("prHeaderController.v1")
@RequestMapping({"/v1/{organizationId}"})
@Tenant("SRM-RCWL")
public class RCWLPrHeaderController extends PrHeaderController {
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
//    @Override
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

    @Override
    @ApiOperation("采购申请提交")
    @Permission(
            level = ResourceLevel.ORGANIZATION
    )
    @PostMapping({"/purchase-requests/singleton-submit"})
    public ResponseEntity<PrHeader> singletonSubmit(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody PrHeader prHeader) {
        SecurityTokenHelper.validToken(prHeader, false);
        //融创 预算占用释放接口

        prHeader = this.prHeaderService.singletonSubmit(tenantId, prHeader);
        boolean syncFlag = prHeader.checkPrSyncToSap(this.prHeaderService, this.customizeSettingHelper);
        if (syncFlag) {
            prHeader.setOperationFlag("I");
            this.prHeaderService.exportPrToErp(tenantId, Collections.singletonList(prHeader));
            prHeader.setCustomUserDetails(DetailsHelper.getUserDetails());
            this.prHeaderService.afterPrApprove(tenantId, Collections.singletonList(prHeader));
        }

        return Results.success(prHeader);
    }
}
