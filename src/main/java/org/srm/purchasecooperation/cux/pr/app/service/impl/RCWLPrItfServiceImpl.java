package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.interfaces.sdk.dto.RequestPayloadDTO;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.interfaces.sdk.invoke.InterfaceInvokeSdk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.api.dto.*;
import org.srm.purchasecooperation.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:接口
 * @author: bin.zhang
 * @createDate: 2021/4/10 13:57
 */
@Service
public class RCWLPrItfServiceImpl implements RCWLPrItfService {
    @Autowired
    private RCWLPrItfService rcwlPrItfService;
    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private RCWLItfPrDataRespository rcwlItfPrDataRespository;

    private static final Logger logger = LoggerFactory.getLogger(RCWLPrItfServiceImpl.class);

    /**
     * 预算占用接口调用
     *
     * @param prHeader
     * @param tenantId
     */
    @Override
    public void invokeBudgetOccupy(PrHeader prHeader, Long tenantId) throws JsonProcessingException {

        //接口请求数据获取
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPrItfService.getBudgetAccountItfData(prHeader, tenantId, "O");

        String namespace = "SRM-RCWL";
        String interfaceCode = "RCWL_BUDGET";
        String serverCode = "SRM-RCWL-BUDGET";
        RequestPayloadDTO payload = new RequestPayloadDTO();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        payload.setHeaderParamMap(headerMap);
        ObjectMapper mapper = new ObjectMapper();
        payload.setPayload(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));
        logger.info("报文1+" + rcwlItfPrHeaderDTO);
        logger.info("报文2+" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));
        payload.setMediaType("application/json");

        ResponsePayloadDTO responsePayloadDTO = interfaceInvokeSdk.invoke(namespace,
                serverCode,
                interfaceCode,
                payload);
        String response = responsePayloadDTO.getPayload().toString();
        logger.info("预算接口返回" + response);

        JsonObject res = new JsonParser().parse(response).getAsJsonObject();
        String msg = res.get("msg").getAsString();
        logger.info("msg" + msg);
        String code = res.get("code").getAsString();
        logger.info("code" + code);
        JsonArray details = res.get("details").getAsJsonArray();
        logger.info("details" + details);

        String status = res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("status").getAsString();
        logger.info("status" + status);

        if (!RCWLConstants.InterfaceInitValue.CODE.equals(code)) {
            throw new CommonException("接口调用失败");
        }
        if (!RCWLConstants.InterfaceInitValue.CODE.equals(status)) {
            String detailsMsg =res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("msg").getAsString();
            String simpleMessage = details.getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject().get("simplemessage").getAsString();
             if(StringUtils.isEmpty(simpleMessage)){
                 throw new CommonException(detailsMsg);
             }
            throw new CommonException(simpleMessage+"，采购申请不可提交");

        }


    }

    /**
     * 初始化头数据（包括调用接口获取token）
     *
     * @return
     */
    @Override
    public RCWLItfPrHeaderDTO initOccupyHeader() {
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = new RCWLItfPrHeaderDTO();
        String token = rcwlPrItfService.getToken();
        rcwlItfPrHeaderDTO.setToken(token);
        rcwlItfPrHeaderDTO.setType(RCWLConstants.InterfaceInitValue.TYPE);
        rcwlItfPrHeaderDTO.setDefinecode(RCWLConstants.InterfaceInitValue.DEFINE_CODE);
        return rcwlItfPrHeaderDTO;
    }

    /**
     * 获取接口请求全部数据
     *
     * @param prHeader
     * @param tenantId
     * @return
     */
    @Override
    public RCWLItfPrHeaderDTO getBudgetAccountItfData(PrHeader prHeader, Long tenantId, String flag) {
        //获取接口所需数据
        RCWLItfPrLineDTO rcwlItfPrLineDTO = this.initOccupy(prHeader, tenantId, flag);
        List<PrLine> lineDetailList = prHeader.getPrLineList();

        List<RCWLItfPrLineDetailDTO> rcwlItfPrLineDetailDTOS = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(lineDetailList)) {
            lineDetailList.forEach(prDetailLine -> {
                RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = this.initOccupyDetail(prDetailLine, tenantId);
                rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);
            });
        }

        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = new RCWLItfPrHeaderDTO();
        List<RCWLItfPrDataDTO> rcwlItfPrDataDTOS = new ArrayList<>();
        RCWLItfPrDataDTO rcwlItfPrDataDTO = new RCWLItfPrDataDTO();
        rcwlItfPrDataDTO.setYszy(rcwlItfPrLineDTO);
        rcwlItfPrDataDTO.setYszyzb(rcwlItfPrLineDetailDTOS);
        rcwlItfPrDataDTOS.add(rcwlItfPrDataDTO);


        rcwlItfPrHeaderDTO = rcwlPrItfService.initOccupyHeader();
        rcwlItfPrHeaderDTO.setData(rcwlItfPrDataDTOS);
        return rcwlItfPrHeaderDTO;
    }

    /**
     * 获取token
     *
     * @return
     */
    @Override
    public String getToken() {
        RCWLTokenGetRequestDTO requestDTO = new RCWLTokenGetRequestDTO();
        requestDTO.setOpenid(RCWLConstants.InterfaceInitValue.OPEN_ID);

        String namespace = "SRM-RCWL";
        String interfaceCode = "RCWL_BUDGET_TOKEN_GET";
        String serverCode = "SRM-RCWL";
        RequestPayloadDTO payload = new RequestPayloadDTO();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        payload.setHeaderParamMap(headerMap);
        payload.setPayload(JSON.toJSONString(requestDTO));
        payload.setMediaType("application/json");
        ResponsePayloadDTO responsePayloadDTO = interfaceInvokeSdk.invoke(namespace,
                serverCode,
                interfaceCode,
                payload);


        RCWLTokenGetResponseDTO responseDTO = new RCWLTokenGetResponseDTO();

        responseDTO = JSONObject.parseObject(responsePayloadDTO.getPayload(), RCWLTokenGetResponseDTO.class);
        String token = responseDTO.getToken();

        return token;
    }

    /**
     * 审批拒绝调用接口
     *
     * @param prHeaderList
     * @param tenantId
     */
    @Override
    public void invokeBudgetList(List<PrHeader> prHeaderList, Long tenantId) {
        if (CollectionUtils.isEmpty(prHeaderList)) {
            throw new CommonException("数据为空");
        } else {
            prHeaderList.forEach(prHeader -> {
                BeanUtils.copyProperties(this.prHeaderRepository.selectByPrimaryKey(prHeader.getPrHeaderId()), prHeader, new String[]{"approvedRemark"});
                List<PrLine> prLines = this.prLineRepository.selectByPrHeaderId(prHeader.getPrHeaderId());
                prHeader.setPrLineList(prLines);
                //触发释放接口
                try {
                    this.rcwlPrItfService.invokeBudgetRelease(prHeader, tenantId);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    /**
     * 释放接口
     *
     * @param prHeader
     * @param tenantId
     */
    @Override
    public void invokeBudgetRelease(PrHeader prHeader, Long tenantId) throws JsonProcessingException {
        //接口请求数据获取
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPrItfService.getBudgetAccountItfData(prHeader, tenantId, "R");

        String namespace = "SRM-RCWL";
        String interfaceCode = "RCWL_BUDGET";
        String serverCode = "SRM-RCWL-BUDGET";
        RequestPayloadDTO payload = new RequestPayloadDTO();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        payload.setHeaderParamMap(headerMap);

        ObjectMapper mapper = new ObjectMapper();
        payload.setPayload(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));
        logger.info("报文1+" + rcwlItfPrHeaderDTO);
        logger.info("报文2+" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));
        payload.setMediaType("application/json");

        ResponsePayloadDTO responsePayloadDTO = interfaceInvokeSdk.invoke(namespace,
                serverCode,
                interfaceCode,
                payload);
        String response = responsePayloadDTO.getPayload().toString();
        logger.info("预算接口返回" + response);

        JsonObject res = new JsonParser().parse(response).getAsJsonObject();
        String msg = res.get("msg").getAsString();
        logger.info("msg" + msg);
        String code = res.get("code").getAsString();
        logger.info("code" + code);
        JsonArray details = res.get("details").getAsJsonArray();
        logger.info("details" + details);

        String status = res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("status").getAsString();
        logger.info("status" + status);

        if (!RCWLConstants.InterfaceInitValue.CODE.equals(code)) {
            throw new CommonException("接口调用失败");
        }
        if (!RCWLConstants.InterfaceInitValue.CODE.equals(status)) {
            String detailsMsg =res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("msg").getAsString();
                throw new CommonException(detailsMsg);
        }


    }

    /**
     * 行关闭调用接口
     *
     * @param prLineVOS
     * @param tenantId
     */
    @Override
    public void linesClose(List<PrLineVO> prLineVOS, Long tenantId) throws JsonProcessingException {
        //将勾选行封装成报文数据
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPrItfService.getBudgetItfDataLine(prLineVOS, tenantId, "R");

        //调用接口
        String namespace = "SRM-RCWL";
        String interfaceCode = "RCWL_BUDGET";
        String serverCode = "SRM-RCWL-BUDGET";
        RequestPayloadDTO payload = new RequestPayloadDTO();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        payload.setHeaderParamMap(headerMap);
        ObjectMapper mapper = new ObjectMapper();
        payload.setPayload(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));
        payload.setMediaType("application/json");

        ResponsePayloadDTO responsePayloadDTO = interfaceInvokeSdk.invoke(namespace,
                serverCode,
                interfaceCode,
                payload);
        String response = responsePayloadDTO.getPayload().toString();
        logger.info("预算接口返回" + response);

        JsonObject res = new JsonParser().parse(response).getAsJsonObject();
        String msg = res.get("msg").getAsString();
        logger.info("msg" + msg);
        String code = res.get("code").getAsString();
        logger.info("code" + code);
        JsonArray details = res.get("details").getAsJsonArray();
        logger.info("details" + details);

        String status = res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("status").getAsString();
        logger.info("status" + status);

        if (!RCWLConstants.InterfaceInitValue.CODE.equals(code)) {
            throw new CommonException("接口调用失败");
        }
        if (!RCWLConstants.InterfaceInitValue.CODE.equals(status)) {
            String detailsMsg =res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("msg").getAsString();
            throw new CommonException(detailsMsg);
        }

    }

    /**
     * 将指定行数据封装成报文
     *
     * @param prLineVOS
     * @param tenantId
     * @param flag
     * @return
     */
    @Override
    public RCWLItfPrHeaderDTO getBudgetItfDataLine(List<PrLineVO> prLineVOS, Long tenantId, String flag) {
        Long prHeaderId = prLineVOS.get(0).getPrHeaderId();
        PrHeader prHeader = prHeaderRepository.selectByPrimaryKey(prHeaderId);

        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPrItfService.initOccupyHeader();

        RCWLItfPrDataDTO rcwlItfPrDataDTO = new RCWLItfPrDataDTO();

        RCWLItfPrLineDTO rcwlItfPrLineDTO = this.initOccupy(prHeader, tenantId, flag);

        List<RCWLItfPrLineDetailDTO> rcwlItfPrLineDetailDTOS = new ArrayList<>();

        prLineVOS.forEach(prLineVO -> {
            PrLine prLine = new PrLine();
            BeanUtils.copyProperties(prLineVO, prLine);
            RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = this.initOccupyDetail(prLine, tenantId);
            rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);
        });
        rcwlItfPrDataDTO.setYszy(rcwlItfPrLineDTO);
        rcwlItfPrDataDTO.setYszyzb(rcwlItfPrLineDetailDTOS);


        List<RCWLItfPrDataDTO> rcwlItfPrDataDTOS = new ArrayList<>();


        rcwlItfPrDataDTOS.add(rcwlItfPrDataDTO);


        rcwlItfPrHeaderDTO.setData(rcwlItfPrDataDTOS);

        return rcwlItfPrHeaderDTO;
    }

    /**
     * 勾选行取消触发接口（单行）
     *
     * @param prLineDTO
     * @param tenantId
     */
    @Override
    public void linesCancel(PrLineDTO prLineDTO, Long tenantId) throws JsonProcessingException {
        //将勾选行封装成报文数据
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPrItfService.getBudgetItfDataLineDTO(prLineDTO, tenantId, "R");

        //调用接口
        String namespace = "SRM-RCWL";
        String interfaceCode = "RCWL_BUDGET";
        String serverCode = "SRM-RCWL-BUDGET";
        RequestPayloadDTO payload = new RequestPayloadDTO();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        payload.setHeaderParamMap(headerMap);
        ObjectMapper mapper = new ObjectMapper();
        payload.setPayload(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));
        payload.setMediaType("aplication/json");

        ResponsePayloadDTO responsePayloadDTO = interfaceInvokeSdk.invoke(namespace,
                serverCode,
                interfaceCode,
                payload);
        String response = responsePayloadDTO.getPayload().toString();
        logger.info("预算接口返回" + response);

        JsonObject res = new JsonParser().parse(response).getAsJsonObject();
        String msg = res.get("msg").getAsString();
        logger.info("msg" + msg);
        String code = res.get("code").getAsString();
        logger.info("code" + code);
        JsonArray details = res.get("details").getAsJsonArray();
        logger.info("details" + details);

        String status = res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("status").getAsString();
        logger.info("status" + status);

        if (!RCWLConstants.InterfaceInitValue.CODE.equals(code)) {
            throw new CommonException("接口调用失败");
        }
        if (!RCWLConstants.InterfaceInitValue.CODE.equals(status)) {
            String detailsMsg =res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("msg").getAsString();
            throw new CommonException(detailsMsg);
        }


    }

    /**
     * 获取行数据
     *
     * @param prLineDTO
     * @param tenantId
     * @param r
     * @return
     */
    @Override
    public RCWLItfPrHeaderDTO getBudgetItfDataLineDTO(PrLineDTO prLineDTO, Long tenantId, String r) {
        Long prHeaderId = prLineDTO.getPrHeaderId();

        PrHeader prHeader = prHeaderRepository.selectByPrimaryKey(prHeaderId);

        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPrItfService.initOccupyHeader();

        RCWLItfPrDataDTO rcwlItfPrDataDTO = new RCWLItfPrDataDTO();

        RCWLItfPrLineDTO rcwlItfPrLineDTO = this.initOccupy(prHeader, tenantId, r);


        List<RCWLItfPrLineDetailDTO> rcwlItfPrLineDetailDTOS = new ArrayList<>();

        PrLine prLine = new PrLine();
        BeanUtils.copyProperties(prLineDTO, prLine);
        RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = this.initOccupyDetail(prLine, tenantId);
        rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);

        rcwlItfPrDataDTO.setYszy(rcwlItfPrLineDTO);
        rcwlItfPrDataDTO.setYszyzb(rcwlItfPrLineDetailDTOS);


        List<RCWLItfPrDataDTO> rcwlItfPrDataDTOS = new ArrayList<>();


        rcwlItfPrDataDTOS.add(rcwlItfPrDataDTO);

        rcwlItfPrHeaderDTO.setData(rcwlItfPrDataDTOS);
        return rcwlItfPrHeaderDTO;
    }

    /**
     * 预算变更提交接口
     *
     * @param prHeader
     * @param tenantId
     */
    @Override
    public void submitChange(PrHeader prHeader, Long tenantId) throws JsonProcessingException {
        //获取变更前的采购申请数据
        PrHeader oldPrHeader = prHeaderRepository.selectByPrimaryKey(prHeader.getPrHeaderId());

        //先调用释放接口
        rcwlPrItfService.invokeBudgetRelease(oldPrHeader, tenantId);

        //再调用占用接口
        rcwlPrItfService.invokeBudgetOccupy(prHeader, tenantId);
    }

    private RCWLItfPrLineDetailDTO initOccupyDetail(PrLine prDetailLine, Long tenantId) {
        RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = new RCWLItfPrLineDetailDTO();
        rcwlItfPrLineDetailDTO.setYszyje(prDetailLine.getTaxIncludedLineAmount().toString());
        rcwlItfPrLineDetailDTO.setYmytcode(prDetailLine.getBudgetAccountNum());
        String budgetAccountName = this.rcwlItfPrDataRespository.selectBudgetAccountName(prDetailLine.getBudgetAccountNum(), tenantId);
        if (StringUtils.isEmpty(budgetAccountName)) {
            throw new CommonException("业务用途为空");
        }
        System.out.println("预算科目" + budgetAccountName);
        rcwlItfPrLineDetailDTO.setYmytname(budgetAccountName);
        if((!StringUtils.isEmpty(prDetailLine.getWbsCode())) &&(!StringUtils.isEmpty(prDetailLine.getWbs()))  ){
            rcwlItfPrLineDetailDTO.setCplxcode(prDetailLine.getWbsCode());
            rcwlItfPrLineDetailDTO.setCplxname(prDetailLine.getWbs());
        }

        if((!StringUtils.isEmpty(prDetailLine.getWbsCode())) &&(StringUtils.isEmpty(prDetailLine.getWbs()))  ){
            rcwlItfPrLineDetailDTO.setCplxcode(prDetailLine.getWbsCode());
            String wbsName = this.rcwlItfPrDataRespository.selectWbsName(prDetailLine.getWbsCode(),prDetailLine.getPrLineId());
            rcwlItfPrLineDetailDTO.setCplxname(wbsName);
        }


        if((StringUtils.isEmpty(prDetailLine.getWbsCode())) &&!(StringUtils.isEmpty(prDetailLine.getWbs()))  ){
            rcwlItfPrLineDetailDTO.setCplxname(prDetailLine.getWbs());
            String wbsCode = this.rcwlItfPrDataRespository.selectWbsCode(prDetailLine.getWbs(),prDetailLine.getPrLineId());
            rcwlItfPrLineDetailDTO.setCplxcode(wbsCode);
        }

        if((StringUtils.isEmpty(prDetailLine.getWbsCode()))&&(StringUtils.isEmpty(prDetailLine.getWbs()))  ){
            throw new CommonException("产品类型为空");
        }
        rcwlItfPrLineDetailDTO.setLine(prDetailLine.getLineNum().toString());
        return rcwlItfPrLineDetailDTO;
    }

    public  RCWLItfPrLineDTO initOccupy(PrHeader prHeader, Long tenantId, String flag) {
        RCWLItfPrLineDTO itfPrLineDTO = new RCWLItfPrLineDTO();
        itfPrLineDTO.setMexternalsysid("CG");
        //01占用 02释放
        if("O".equals(flag)){
            itfPrLineDTO.setYslx("01");
        }else if("R".equals(flag)){
            itfPrLineDTO.setYslx("02");
        }

        itfPrLineDTO.setCreateuser("jg");
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        String dateString = formatter.format(prHeader.getCreationDate());
        itfPrLineDTO.setBilldate(dateString);
        itfPrLineDTO.setPaymentbillcode(prHeader.getPrNum());
        //测试使用
        //  itfPrLineDTO.setUnitcode("01");
        String unitCode = rcwlItfPrDataRespository.selectSapCode(prHeader.getCompanyId(),tenantId);
        if(StringUtils.isEmpty(unitCode)){
            throw new CommonException("组织机构不能为空");
        }
        itfPrLineDTO.setUnitcode(unitCode);
        return itfPrLineDTO;
    }
}
