package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import gxbpm.dto.RCWLGxBpmStartDataDTO;
import gxbpm.service.RCWLGxBpmInterfaceService;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.file.FileClient;
import org.hzero.boot.file.dto.FileDTO;
import org.hzero.boot.file.feign.FileRemoteService;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.plugin.hr.EmployeeHelper;
import org.hzero.boot.platform.plugin.hr.entity.Employee;
import org.hzero.boot.platform.profile.ProfileClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.api.dto.PrToBpmDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.PrToBpmFileDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.PrToBpmLineDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrToBpmService;
import org.srm.purchasecooperation.cux.pr.utils.DateTimeUtil;
import org.srm.purchasecooperation.cux.pr.utils.constant.PrConstant;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/4/19 11:15
 * @version:1.0
 */
@Slf4j
@Service
public class RcwlPrToBpmServiceImpl implements RcwlPrToBpmService {
    @Autowired
    private RCWLGxBpmInterfaceService rcwlGxBpmInterfaceService;

    @Autowired
    private ProfileClient profileClient;

    @Autowired
    private FileRemoteService fileRemoteService;
    @Autowired
    private LovAdapter lovAdapter;

    /**
     *
     * @param prHeader
     * @param type create:采购申请创建提交的；change：采购申请变更提交的
     * @return
     */
    @Override
    public String prDataToBpm(PrHeader prHeader, String type) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        String reSrcSys = this.profileClient.getProfileValueByOptions(userDetails.getTenantId(), userDetails.getUserId(), userDetails.getRoleId(), PrConstant.BpmCodes.REQ_SRC_SYS_CODE);
        String reqTarSys = this.profileClient.getProfileValueByOptions(userDetails.getTenantId(), userDetails.getUserId(), userDetails.getRoleId(), PrConstant.BpmCodes.REQ_TAR_CODE);
        String reqIp = this.profileClient.getProfileValueByOptions(userDetails.getTenantId(), userDetails.getUserId(), userDetails.getRoleId(), PrConstant.BpmCodes.REQ_URL_CODE);
        String zYunUrl = this.profileClient.getProfileValueByOptions(userDetails.getTenantId(), userDetails.getUserId(), userDetails.getRoleId(), PrConstant.BpmCodes.ZYUN_URL_CODE);
        //采购申请行数据
        List<PrLine> prLineList = prHeader.getPrLineList();
        List<PrToBpmLineDTO> prToBpmLineDTOS = new ArrayList<>();
        prLineList.forEach(line -> {
            PrToBpmLineDTO prToBpmLineDTO = this.setLineDataMap(line);
            prToBpmLineDTOS.add(prToBpmLineDTO);
        });

        //附件信息
        List<PrToBpmFileDTO> fileDTOS = new ArrayList<>();
        FileClient fileClient = new FileClient(fileRemoteService);
        if(StringUtils.isNotBlank(prHeader.getAttachmentUuid())){
            List<FileDTO> attachmentFiles = fileClient.getAttachmentFiles(prHeader.getTenantId(), "private-bucket", prHeader.getAttachmentUuid());
            for (int i = 0; i < attachmentFiles.size(); i++) {
                fileDTOS.add(this.setUrlDataMap(attachmentFiles.get(i), i + 1));
            }
        }

        //甄云链接

        //采购申请头数据
        Map<String, Object> typeMessage = this.getTypeMessage(type, prHeader);
        PrToBpmDTO prToBpmDTO = this.setHeaderDataMap(prHeader, (String) typeMessage.get("typeStr"), (String) typeMessage.get("subject"));
        prToBpmDTO.setzYunUrl(zYunUrl+prHeader.getPrHeaderId());
        prToBpmDTO.setPrToBpmLineDTOList(prToBpmLineDTOS);
        prToBpmDTO.setPrToBpmFileDTOList(fileDTOS);
        String data = JSONObject.toJSON(prToBpmDTO).toString();
        log.info("=========================json data===========================>" + data);
        RCWLGxBpmStartDataDTO rcwlGxBpmStartDataDTO = new RCWLGxBpmStartDataDTO();
        //设置传输值
        rcwlGxBpmStartDataDTO.setReSrcSys(reSrcSys);
        rcwlGxBpmStartDataDTO.setReqTarSys(reqTarSys);
        rcwlGxBpmStartDataDTO.setUserId(userDetails.getRealName());
        rcwlGxBpmStartDataDTO.setBtid("RCWLSRMCGSQ");
        rcwlGxBpmStartDataDTO.setBoid((String) typeMessage.get("prNum"));
        String procinstId = prHeader.getAttributeVarchar17();
        rcwlGxBpmStartDataDTO.setProcinstId(StringUtils.isNotBlank(procinstId)?procinstId:"0");
        rcwlGxBpmStartDataDTO.setData(data);
        String bpmUrl = "http://" + reqIp + "/Workflow/MTStart2.aspx?BSID=WLCGPT&BTID=RCWLSRMCGSQ&BOID=" + typeMessage.get("prNum");
        log.info("=========================bpmUrl ===========================>" + bpmUrl);
        // 调用bpm接口
//        int i = 1 / 0;
        try {
            ResponsePayloadDTO responsePayloadDTO = rcwlGxBpmInterfaceService.RcwlGxBpmInterfaceRequestData(rcwlGxBpmStartDataDTO);
            log.info("=========================return ===========================>" + responsePayloadDTO.toString());
        } catch (Exception e) {
            throw new CommonException("bpm.interface.error");
        }
        return bpmUrl;
    }

    private PrToBpmLineDTO setLineDataMap(PrLine line) {
        PrToBpmLineDTO prToBpmLineDTO = new PrToBpmLineDTO();
        prToBpmLineDTO.setDisplayLineNum(line.getDisplayLineNum());
        prToBpmLineDTO.setItemName(line.getItemName());
        prToBpmLineDTO.setCategoryName(line.getCategoryName());
        prToBpmLineDTO.setUomName(line.getUomName());
        prToBpmLineDTO.setNeededDate(new SimpleDateFormat(DateTimeUtil.PATTERN_DAY).format(line.getNeededDate()));
        prToBpmLineDTO.setQuantity(String.valueOf(line.getQuantity()));
        prToBpmLineDTO.setTaxIncludedUnitPrice(String.valueOf(line.getTaxIncludedUnitPrice()));
        prToBpmLineDTO.setTaxIncludedLineAmount(String.valueOf(line.getTaxIncludedLineAmount()));
        prToBpmLineDTO.setBudgetAccountId(lovAdapter.queryLovMeaning(PrConstant.BpmCodes.BUDGET_ACCOUNT, line.getTenantId(), String.valueOf(line.getBudgetAccountId())));
        prToBpmLineDTO.setCostId(lovAdapter.queryLovMeaning(PrConstant.BpmCodes.COST_CENTER, line.getTenantId(), String.valueOf(line.getCostId())));
        prToBpmLineDTO.setWbsCode(lovAdapter.queryLovMeaning(PrConstant.BpmCodes.WBS, line.getTenantId(), String.valueOf(line.getWbsCode())));
        prToBpmLineDTO.setRemark(line.getRemark());
        return prToBpmLineDTO;
    }

    private PrToBpmFileDTO setUrlDataMap(FileDTO file, int index) {
        PrToBpmFileDTO prToBpmFileDTO = new PrToBpmFileDTO();
        prToBpmFileDTO.setIndex(String.valueOf(index));
        prToBpmFileDTO.setFileName(file.getFileName());
        prToBpmFileDTO.setFileSize(String.valueOf(file.getFileSize()));
        return prToBpmFileDTO;
    }

    private Map<String, Object> setUrlMxDataMap(FileDTO file) {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    private PrToBpmDTO setHeaderDataMap(PrHeader header, String typeStr, String subject) {
        PrToBpmDTO prToBpmDTO = new PrToBpmDTO();
        Employee employee = EmployeeHelper.getEmployee(header.getCreatedBy(), header.getTenantId());
        prToBpmDTO.setSubject(subject);
        prToBpmDTO.setTypeStr(typeStr);
        prToBpmDTO.setPrNum(header.getPrNum());
        prToBpmDTO.setTitle(header.getTitle());
        prToBpmDTO.setEmployeeName(employee.getName());
        prToBpmDTO.setPrRequestedName(header.getPrRequestedName());
        prToBpmDTO.setCompanyName(header.getCompanyName());
        prToBpmDTO.setPurchaseOrgName(header.getPurchaseOrgName());
        prToBpmDTO.setProjectStaging(lovAdapter.queryLovMeaning(PrConstant.BpmCodes.PR_STAGING, header.getTenantId(), header.getAttributeVarchar40()));
        prToBpmDTO.setAmount(String.valueOf(header.getAmount()));
        prToBpmDTO.setPrTypeName(header.getPrTypeName());
        prToBpmDTO.setFormat(lovAdapter.queryLovMeaning(PrConstant.BpmCodes.PR_FORMAT, header.getTenantId(), header.getAttributeVarchar38()));
        prToBpmDTO.setBiddingMode(lovAdapter.queryLovMeaning(PrConstant.BpmCodes.JH_BIDDING, header.getTenantId(), header.getAttributeVarchar39()));
        prToBpmDTO.setCreationDate(new SimpleDateFormat(DateTimeUtil.PATTERN_SECOND).format(header.getCreationDate()));
        prToBpmDTO.setRemark(header.getRemark());
        return prToBpmDTO;
    }

    private Map<String, Object> getTypeMessage(String type, PrHeader header) {
        Map<String, Object> map = new HashMap<>();
        String title = StringUtils.isNotBlank(header.getTitle()) ? header.getTitle() : "";
        switch (type) {
            case "create":
                map.put("typeStr", "采购申请");
                map.put("subject", "采购申请" + header.getPrNum() + title);
                map.put("prNum", header.getPrNum());
                break;
            case "change":
                String prNum = header.getPrNum() + "-" + Math.round((Math.random()+1) * 1000);
                map.put("typeStr", "预算变更");
                map.put("subject", "预算变更" + prNum);
                map.put("prNum", prNum);
                break;
            default:
                map.put("typeStr", "采购申请");
                map.put("subject", "采购申请" + header.getPrNum() + title);
                map.put("prNum", header.getPrNum());
                break;
        }
        return map;
    }
}
