package org.srm.purchasecooperation.cux.order.app.service.impl;

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
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoBudgetItfService;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrDataDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrHeaderDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrLineDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrLineDetailDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLTokenGetRequestDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLTokenGetResponseDTO;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPoToBpmMapper;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.vo.PoLineVO;
import org.srm.purchasecooperation.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengxu.zhi@hand-china.com 2021-11-03
 * @description 订单预算实现类
 */
@Service
public class RcwlPoBudgetItfServiceImpl implements RcwlPoBudgetItfService {
    @Autowired
    private RcwlPoBudgetItfService rcwlPoBudgetItfService;
    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private RCWLItfPrDataRespository rcwlItfPrDataRespository;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private RcwlPoToBpmMapper rcwlPoToBpmMapper;


    private static final Logger logger = LoggerFactory.getLogger(RcwlPoBudgetItfServiceImpl.class);
    private static final String NAME_SPACE = "SRM-RCWL";
    private static final String RCWL_BUDGET = "RCWL_BUDGET";
    private static final String SRM_RCWL_BUDGET = "SRM-RCWL-BUDGET";

    private static final String RCWL_BUDGET_TOKEN_GET = "RCWL_BUDGET_TOKEN_GET";
    private static final String SERVE_CODE = "SRM-RCWL";

    /**
     * 预算占用接口调用
     *
     * @param poDTO
     * @param tenantId
     */
    @Override
    public void invokeBudgetOccupy(PoDTO poDTO, Long tenantId, String occupyFlag) throws JsonProcessingException {

        //目前占用和释放统一按照占用推，释放时金额固定为0
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = new RCWLItfPrHeaderDTO();

        //获取数据
        rcwlItfPrHeaderDTO = rcwlPoBudgetItfService.getBudgetAccountItfData1(poDTO, tenantId, occupyFlag);

        RequestPayloadDTO payload = new RequestPayloadDTO();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        payload.setHeaderParamMap(headerMap);
        ObjectMapper mapper = new ObjectMapper();
        payload.setPayload(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));
        logger.info("报文1+" + rcwlItfPrHeaderDTO);
        logger.info("报文2+" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));
        payload.setMediaType("application/json");

        ResponsePayloadDTO responsePayloadDTO = interfaceInvokeSdk.invoke(NAME_SPACE,
                SRM_RCWL_BUDGET,
                RCWL_BUDGET,
                payload);
        String response = responsePayloadDTO.getPayload();
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
            String detailsMsg = res.get("details").getAsJsonArray().get(0).getAsJsonObject().get("msg").getAsString();
            try{
            JsonArray str = details.getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonArray();
            logger.info("str" + str);
            String simpleMessage = "";
            if (str.size() > 0) {
                for (int i = 0; i < str.size(); i++) {
                    JsonObject jsonObject = str.get(i).getAsJsonObject();
                    logger.info("jsonObject" + jsonObject.get("simplemessage"));
                    simpleMessage = simpleMessage + jsonObject.get("simplemessage") + ",";

                }
            }
            logger.info("simpleMessage" + simpleMessage);
            //   String simpleMessage = details.getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject().get("simplemessage").getAsString();
            if (StringUtils.isEmpty(simpleMessage)) {
                throw new CommonException(detailsMsg);
            } else {
                throw new CommonException(simpleMessage + "采购订单不可提交");
            }
            }catch(Exception e){
                throw new CommonException(detailsMsg);
            }

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
        String token = rcwlPoBudgetItfService.getToken();
        rcwlItfPrHeaderDTO.setToken(token);
        rcwlItfPrHeaderDTO.setType(RCWLConstants.InterfaceInitValue.TYPE);
        rcwlItfPrHeaderDTO.setDefinecode("KNYSZY");
        return rcwlItfPrHeaderDTO;
    }

/*    *//**
     * 获取接口请求全部数据
     *
     * @param poDTO
     * @param tenantId
     * @return
     *//*
    @Override
    public RCWLItfPrHeaderDTO getBudgetAccountItfData(PoDTO poDTO, Long tenantId, String flag) {
        //获取接口所需数据
        RCWLItfPrLineDTO rcwlItfPrLineDTO = this.initOccupy(poDTO, tenantId, flag);
        //List<PrLine> lineDetailList = prHeader.getPrLineList();
        PoLine poLine = new PoLine();
        poLine.setPoHeaderId(poDTO.getPoHeaderId());
        List<PoLine> lineDetailList = this.poLineRepository.select(poLine);

        List<RCWLItfPrLineDetailDTO> rcwlItfPrLineDetailDTOS = new ArrayList<>();
        if ("R".equals(flag)) {
            if (CollectionUtils.isNotEmpty(lineDetailList)) {
                lineDetailList.forEach(poDetailLine -> {
                    RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = this.initOccupyDetail(poDetailLine, tenantId);
                    if ("R".equals(flag)){
                        rcwlItfPrLineDetailDTO.setYszyje("0");
                    }
                    rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);
                });
            }
        } else if ("O".equals(flag)) {
            if (CollectionUtils.isNotEmpty(lineDetailList)) {
                lineDetailList.forEach(prDetailLine -> {
                    //根据pr_line_id查找scux_rcwl_budget_change_action数据
                    List<Integer> budgetDisYears = rcwlItfPrDataRespository.selectBudgetChangeActionDisYear(prDetailLine.getPrLineId());
                    for(int year : budgetDisYears){
                        RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = this.initOccupyDetail(prDetailLine, tenantId);
                        rcwlItfPrLineDetailDTO.setYsdate(String.valueOf(year));
                        //查找budget_group为old的budget_dis_amount
                        BigDecimal bigDecimal = rcwlItfPrDataRespository.selectBudgetDisAmountByBudgetGroup(prDetailLine.getPrLineId(), year);
                        if(bigDecimal == null){
                            rcwlItfPrLineDetailDTO.setYszyje("0");
                        } else {
                            rcwlItfPrLineDetailDTO.setYszyje(bigDecimal.toString());
                        }
                        rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);
                    }
                });
            }
        }

        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = new RCWLItfPrHeaderDTO();
        List<RCWLItfPrDataDTO> rcwlItfPrDataDTOS = new ArrayList<>();
        RCWLItfPrDataDTO rcwlItfPrDataDTO = new RCWLItfPrDataDTO();
        rcwlItfPrDataDTO.setYszy(rcwlItfPrLineDTO);
        rcwlItfPrDataDTO.setYszyzb(rcwlItfPrLineDetailDTOS);
        rcwlItfPrDataDTOS.add(rcwlItfPrDataDTO);


        rcwlItfPrHeaderDTO = rcwlPoBudgetItfService.initOccupyHeader();
        rcwlItfPrHeaderDTO.setData(rcwlItfPrDataDTOS);
        return rcwlItfPrHeaderDTO;
    }*/
    /**
     * 获取接口请求全部数据
     *
     * @param poDTO
     * @param tenantId
     * @return
     */
    @Override
    public RCWLItfPrHeaderDTO getBudgetAccountItfData1(PoDTO poDTO, Long tenantId, String occupyFlag) {
        //获取接口所需数据
        RCWLItfPrLineDTO rcwlItfPrLineDTO = this.initOccupy(poDTO, tenantId, occupyFlag);

        PoLine poLine = new PoLine();
        poLine.setPoHeaderId(poDTO.getPoHeaderId());
        List<PoLine> lineDetailList = this.poLineRepository.select(poLine);

        List<RCWLItfPrLineDetailDTO> rcwlItfPrLineDetailDTOS = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(lineDetailList)) {
            lineDetailList.forEach(poDetailLine -> {
                    RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = this.initOccupyDetail(poDetailLine, tenantId, occupyFlag);
                    rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);
            });
        }

        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = new RCWLItfPrHeaderDTO();
        List<RCWLItfPrDataDTO> rcwlItfPrDataDTOS = new ArrayList<>();
        RCWLItfPrDataDTO rcwlItfPrDataDTO = new RCWLItfPrDataDTO();
        rcwlItfPrDataDTO.setYszy(rcwlItfPrLineDTO);
        rcwlItfPrDataDTO.setYszyzb(rcwlItfPrLineDetailDTOS);
        rcwlItfPrDataDTOS.add(rcwlItfPrDataDTO);

        rcwlItfPrHeaderDTO = rcwlPoBudgetItfService.initOccupyHeader();
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
        RequestPayloadDTO payload = new RequestPayloadDTO();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        payload.setHeaderParamMap(headerMap);
        payload.setPayload(JSON.toJSONString(requestDTO));
        payload.setMediaType("application/json");
        ResponsePayloadDTO responsePayloadDTO = interfaceInvokeSdk.invoke(NAME_SPACE,
                SERVE_CODE,
                RCWL_BUDGET_TOKEN_GET,
                payload);

        RCWLTokenGetResponseDTO responseDTO = new RCWLTokenGetResponseDTO();
        try {
            responseDTO = JSONObject.parseObject(responsePayloadDTO.getPayload(), RCWLTokenGetResponseDTO.class);
        } catch (Exception e) {
            throw new CommonException("token接口调用失败" + e.getMessage());
        }

        String token = responseDTO.getToken();

        return token;
    }


//    /**
//     * 将指定行数据封装成报文
//     *
//     * @param poLineVOS
//     * @param tenantId
//     * @param flag
//     * @return
//     */
//    @Override
//    public RCWLItfPrHeaderDTO getBudgetItfDataLine(List<PoLineVO> poLineVOS, Long tenantId, String flag) {
//        Long poHeaderId = poLineVOS.get(0).getPoHeaderId();
//        PoHeader poHeader = poHeaderRepository.selectByPrimaryKey(poHeaderId);
//        PoDTO poDTO = new PoDTO();
//        BeanUtils.copyProperties(poHeader, poDTO);
//
//        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPoBudgetItfService.initOccupyHeader();
//
//        RCWLItfPrDataDTO rcwlItfPrDataDTO = new RCWLItfPrDataDTO();
//
//        RCWLItfPrLineDTO rcwlItfPrLineDTO = this.initOccupy(poDTO, tenantId, flag);
//
//        List<RCWLItfPrLineDetailDTO> rcwlItfPrLineDetailDTOS = new ArrayList<>();
//
//        poLineVOS.forEach(poLineVO -> {
//            PrLine prLine = new PrLine();
//            BeanUtils.copyProperties(poLineVO, prLine);
//            RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = this.initCloseLine(prLine, tenantId, null);
//            rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);
//        });
//        rcwlItfPrDataDTO.setYszy(rcwlItfPrLineDTO);
//        rcwlItfPrDataDTO.setYszyzb(rcwlItfPrLineDetailDTOS);
//
//        List<RCWLItfPrDataDTO> rcwlItfPrDataDTOS = new ArrayList<>();
//
//        rcwlItfPrDataDTOS.add(rcwlItfPrDataDTO);
//
//        rcwlItfPrHeaderDTO.setData(rcwlItfPrDataDTOS);
//
//        return rcwlItfPrHeaderDTO;
//    }


    private RCWLItfPrLineDetailDTO initCloseLine(PrLine prDetailLine, Long tenantId, String from) {
        RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = new RCWLItfPrLineDetailDTO();
        if ("create".equals(from)) {
            rcwlItfPrLineDetailDTO.setYszyje("0");
        } else {
            //判断否存在转单情况(查询执行情况)
            BigDecimal quantity = this.rcwlItfPrDataRespository.selectSumQuantity(prDetailLine.getPrLineId(), tenantId);
            if (quantity == null) {
                rcwlItfPrLineDetailDTO.setYszyje("0");
            } else {
                //BigDecimal taxIncludeAmount = quantity.multiply(prDetailLine.getTaxIncludedUnitPrice());
                //修改为取不含税金额
                BigDecimal taxIncludeAmount = quantity.multiply(prDetailLine.getUnitPrice());
                rcwlItfPrLineDetailDTO.setYszyje(taxIncludeAmount.toString());
            }
        }

        if (prDetailLine.getBudgetAccountId() == null) {
            throw new CommonException("业务用途为空");
        }
        String budgetAccountNum = this.rcwlItfPrDataRespository.selectBudgetAccountNum(prDetailLine.getBudgetAccountId());
        String budgetAccountName = this.rcwlItfPrDataRespository.selectBudgetAccountName(prDetailLine.getBudgetAccountId());

        rcwlItfPrLineDetailDTO.setYmytcode(budgetAccountNum);
        rcwlItfPrLineDetailDTO.setYmytname(budgetAccountName);

        if ((!StringUtils.isEmpty(prDetailLine.getWbsCode())) && (!StringUtils.isEmpty(prDetailLine.getWbs()))) {
            rcwlItfPrLineDetailDTO.setCplxcode(prDetailLine.getWbsCode());
            rcwlItfPrLineDetailDTO.setCplxname(prDetailLine.getWbs());
        }

        if ((!StringUtils.isEmpty(prDetailLine.getWbsCode())) && (StringUtils.isEmpty(prDetailLine.getWbs()))) {
            rcwlItfPrLineDetailDTO.setCplxcode(prDetailLine.getWbsCode());
            String wbsName = this.rcwlItfPrDataRespository.selectWbsName(prDetailLine.getWbsCode(), prDetailLine.getPrLineId());
            rcwlItfPrLineDetailDTO.setCplxname(wbsName);
        }

        if ((StringUtils.isEmpty(prDetailLine.getWbsCode())) && !(StringUtils.isEmpty(prDetailLine.getWbs()))) {
            rcwlItfPrLineDetailDTO.setCplxname(prDetailLine.getWbs());
            String wbsCode = this.rcwlItfPrDataRespository.selectWbsCode(prDetailLine.getWbs(), prDetailLine.getPrLineId());
            rcwlItfPrLineDetailDTO.setCplxcode(wbsCode);
        }

        if ((StringUtils.isEmpty(prDetailLine.getWbsCode())) && (StringUtils.isEmpty(prDetailLine.getWbs()))) {
            throw new CommonException("产品类型为空");
        }
        rcwlItfPrLineDetailDTO.setLine(prDetailLine.getLineNum().toString());
        return rcwlItfPrLineDetailDTO;
    }

/*
    */
/**
     * 获取行数据
     *
     * @param
     * @return
     *//*

    @Override
    public RCWLItfPrHeaderDTO getBudgetItfDataLineDTO(PrLineDTO prLineDTO, Long tenantId, String r) {
        Long prHeaderId = prLineDTO.getPrHeaderId();
        PrHeader prHeader = prHeaderRepository.selectByPrimaryKey(prHeaderId);
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPoBudgetItfService.initOccupyHeader();
        RCWLItfPrDataDTO rcwlItfPrDataDTO = new RCWLItfPrDataDTO();
        PoDTO poDTO = new PoDTO();
        BeanUtils.copyProperties(prHeader, poDTO);
        RCWLItfPrLineDTO rcwlItfPrLineDTO = this.initOccupy(poDTO, tenantId, r);
        List<RCWLItfPrLineDetailDTO> rcwlItfPrLineDetailDTOS = new ArrayList<>();
        PoLine poLine = new PoLine();
        BeanUtils.copyProperties(prLineDTO, poLine);
        RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = this.initOccupyDetail(prLine, tenantId);
        rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);

        rcwlItfPrDataDTO.setYszy(rcwlItfPrLineDTO);
        rcwlItfPrDataDTO.setYszyzb(rcwlItfPrLineDetailDTOS);


        List<RCWLItfPrDataDTO> rcwlItfPrDataDTOS = new ArrayList<>();


        rcwlItfPrDataDTOS.add(rcwlItfPrDataDTO);

        rcwlItfPrHeaderDTO.setData(rcwlItfPrDataDTOS);
        return rcwlItfPrHeaderDTO;
    }
*/


    private RCWLItfPrLineDetailDTO initOccupyDetail(PoLine poDetailLine, Long tenantId, String occupyFlag) {
        RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO = new RCWLItfPrLineDetailDTO();
        //rcwlItfPrLineDetailDTO.setYszyje(prDetailLine.getTaxIncludedLineAmount().toString());
        //修改为取不含税金额
        rcwlItfPrLineDetailDTO.setYszyje(String.valueOf(poDetailLine.getLineAmount()));
        if (poDetailLine.getAttributeVarchar21() == null) {
            throw new CommonException("业务用途为空");
        }
        String budgetAccountName = this.rcwlPoToBpmMapper.selectBudgetAccountName(tenantId, poDetailLine.getAttributeVarchar21());

//        rcwlItfPrLineDetailDTO.setYmytcode(prDetailLine.getBudgetAccountNum());
//        String budgetAccountName = this.rcwlItfPrDataRespository.selectBudgetAccountName(prDetailLine.getBudgetAccountNum(), tenantId);
//        if (StringUtils.isEmpty(budgetAccountName)) {
//            throw new CommonException("业务用途为空");
//        }
//        System.out.println("预算科目" + budgetAccountName);
//        rcwlItfPrLineDetailDTO.setYmytname(budgetAccountName);
        rcwlItfPrLineDetailDTO.setYmytcode(poDetailLine.getAttributeVarchar21());
        rcwlItfPrLineDetailDTO.setYmytname(budgetAccountName);

        if ((!StringUtils.isEmpty(poDetailLine.getWbsCode())) && (!StringUtils.isEmpty(poDetailLine.getWbs()))) {
            rcwlItfPrLineDetailDTO.setCplxcode(poDetailLine.getWbsCode());
            rcwlItfPrLineDetailDTO.setCplxname(poDetailLine.getWbs());
        }

        if ((!StringUtils.isEmpty(poDetailLine.getWbsCode())) && (StringUtils.isEmpty(poDetailLine.getWbs()))) {
            rcwlItfPrLineDetailDTO.setCplxcode(poDetailLine.getWbsCode());
            String wbsName = this.rcwlItfPrDataRespository.selectWbsName(poDetailLine.getWbsCode(), poDetailLine.getPrLineId());
            rcwlItfPrLineDetailDTO.setCplxname(wbsName);
        }

        if ((StringUtils.isEmpty(poDetailLine.getWbsCode())) && !(StringUtils.isEmpty(poDetailLine.getWbs()))) {
            rcwlItfPrLineDetailDTO.setCplxname(poDetailLine.getWbs());
            String wbsCode = this.rcwlItfPrDataRespository.selectWbsCode(poDetailLine.getWbs(), poDetailLine.getPrLineId());
            rcwlItfPrLineDetailDTO.setCplxcode(wbsCode);
        }

        if ((StringUtils.isEmpty(poDetailLine.getWbsCode())) && (StringUtils.isEmpty(poDetailLine.getWbs()))) {
            throw new CommonException("产品类型为空");
        }
        //占用金额
        if ("01".equals(occupyFlag)){
            //TODO 需引用76号分支代码
            rcwlItfPrLineDetailDTO.setYszyje("100");
        }else {
            //释放固定为0
            rcwlItfPrLineDetailDTO.setYszyje("0");
        }
        //预算占用日期
        rcwlItfPrLineDetailDTO.setYsdate(String.valueOf("2021"));
        rcwlItfPrLineDetailDTO.setLine(poDetailLine.getLineNum().toString());
        return rcwlItfPrLineDetailDTO;
    }

    public RCWLItfPrLineDTO initOccupy(PoDTO poDTO, Long tenantId, String flag) {
        RCWLItfPrLineDTO itfPrLineDTO = new RCWLItfPrLineDTO();
        itfPrLineDTO.setMexternalsysid("CG");
        logger.info("预算占用标识flag:{}",flag);
        //01占用 02释放
//        if ("O".equals(flag)) {
//            itfPrLineDTO.setYslx("01");
//        } else if ("R".equals(flag)) {
//            itfPrLineDTO.setYslx("02");
//        }
        itfPrLineDTO.setYslx("01");
        itfPrLineDTO.setCreateuser("jq");
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        String dateString = formatter.format(poDTO.getCreationDate());
        itfPrLineDTO.setBilldate(dateString);
        itfPrLineDTO.setPaymentbillcode(poDTO.getPoNum());
        //测试使用
        String unitCode = rcwlItfPrDataRespository.selectSapCode(poDTO.getCompanyId(), tenantId);
        if (StringUtils.isEmpty(unitCode)) {
            throw new CommonException("组织机构不能为空");
        }
        itfPrLineDTO.setUnitcode(unitCode);
        return itfPrLineDTO;
    }

}
