package org.srm.purchasecooperation.cux.po.itf.infra.handle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.choerodon.core.exception.CommonException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.interfaces.sdk.dto.RequestPayloadDTO;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.interfaces.sdk.invoke.InterfaceInvokeSdk;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.boot.platform.lov.feign.LovFeignClient;
import org.hzero.boot.scheduler.infra.annotation.JobHandler;
import org.hzero.boot.scheduler.infra.enums.ReturnT;
import org.hzero.boot.scheduler.infra.handler.IJobHandler;
import org.hzero.boot.scheduler.infra.tool.SchedulerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoHeader;
import org.srm.purchasecooperation.cux.po.domain.repository.RcwlSodrHzpoHeaderRepository;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlOrderStatusDTO;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlOrderStatusQueryDTO;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlOrderStatusRcvDTO;
import org.srm.purchasecooperation.pr.infra.jobhandler.AutoPrToPobHandler;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/11/9 15:36
 * @Description:
 */
@JobHandler("RcwlHzorderStatusJob")
public class RcwlOrderQueryHandler implements IJobHandler {

    private static final String ENT = "SUNAC";
    private static final String SERVICECODE = "RCWL.SODRHZPO.STATUS_QUERY";
    private static final String INTERFACECODE = "STATUS_QUERY";
    private static final String TENANTNUM = "SRM-RCWL";
    private static final String APPID = "SUNAC";
    private static final String APPKEY = "79a2b87a5dd035617f9c838d44503e2f";
    private static final String ITFFAIL = "fail";
    /**
     * 日志打印对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlOrderQueryHandler.class);
    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;
    @Autowired
    private RcwlSodrHzpoHeaderRepository rcwlSodrHzpoHeaderRepository;
    @Autowired
    LovFeignClient lovFeignClient;

    @Override
    public ReturnT execute(Map<String, String> map, SchedulerTool tool) {
        //设置查询DTO
        RcwlOrderStatusQueryDTO queryDTO = new RcwlOrderStatusQueryDTO();
        queryDTO.setEnt(ENT);
        //设置当前时间戳
        queryDTO.setUpdateTimeEnd(System.currentTimeMillis());
        //设置一个小时前的时间戳
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -1);
        queryDTO.setUpdateTimeBegin(calendar.getTime().getTime());
        //获取订单列表
        String orderListString = map.get("orderIdList");
        LOGGER.info("获取到的String:{}", orderListString);
        if(StringUtils.isNotBlank(orderListString)) {
            //去除转义
            String orderString = StringEscapeUtils.unescapeJava(orderListString);
            //去除左右的中括号和双引号
            orderString = orderString.substring(1, orderString.length() - 1).replace("\"", "");
            LOGGER.info("拆分后的String:{}", orderString);
            if (StringUtils.isNotBlank(orderString)) {
                String[] strArrays = orderString.split(",");
                List<String> orderList = Arrays.asList(strArrays);
                queryDTO.setOrderIdList(orderList);
            }
        }
        //构建header参数
        long cutTime = System.currentTimeMillis();
        int nonce =  (int)cutTime%10000;
        String checkString =APPKEY + nonce + cutTime;
        String shString = DigestUtils.sha1Hex(checkString);


        //调用接口
        String body = JSONObject.toJSONString(queryDTO);

        //拼接头信息
        RequestPayloadDTO payload = new RequestPayloadDTO();
        Map<String, String> headerMap = new HashMap<>();
        // 请求头参数类型
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        headerMap.put("AppId",APPID);
        headerMap.put("Nonce",String.valueOf(nonce));
        headerMap.put("CurTime",String.valueOf(cutTime));
        headerMap.put("CheckSum",shString);
        payload.setHeaderParamMap(headerMap);
        // 请求体
        payload.setPayload(body);
        LOGGER.info("  ===========================================  " + payload);
        try {
            ResponsePayloadDTO responsePayloadDTO =
                    interfaceInvokeSdk.invoke(TENANTNUM, SERVICECODE, INTERFACECODE, payload);
            LOGGER.debug("responsePayloadDTO: {}", JSONObject.toJSONString(responsePayloadDTO));
            RcwlOrderStatusRcvDTO responseDTO = JSONArray.parseObject(String.valueOf(responsePayloadDTO.getPayload()), RcwlOrderStatusRcvDTO.class);
            if(ITFFAIL.equals(responsePayloadDTO.getStatus())){
                throw new CommonException("对方接口返回错误："+responsePayloadDTO.getMessage());
            }
            if (responseDTO.getData() == null) {
                LOGGER.error("回传数据为空!");
                return ReturnT.SUCCESS;
            } else {
                //查询表中订单编号
                List<RcwlSodrHzpoHeader> headers = rcwlSodrHzpoHeaderRepository.selectAll();
                List<String> orderNums = new ArrayList<>();
                long tenantId = headers.get(0).getTenantId();
                headers.forEach(header -> {
                    orderNums.add(header.getPoNum());
                });
                //获取报文中的所有订单编号
                List<String> queryOrderNums = new ArrayList<>();
                List<RcwlOrderStatusDTO> rcwlOrderStatusDTOS = responseDTO.getData();
                LOGGER.info("处理数据：{}",rcwlOrderStatusDTOS);
                Map<String, RcwlOrderStatusDTO> orderMap = new HashMap<>();
                rcwlOrderStatusDTOS.forEach(order -> {
                    orderMap.put(order.getOrderId(), order);
                    queryOrderNums.add(order.getOrderId());
                });
                //获取两个list都有的部分，这一部分需要做更新
                List<String> tableExistsNum = queryOrderNums.stream().filter(orderNums::contains).collect(Collectors.toList());
                List<RcwlOrderStatusDTO> updateLists = new ArrayList<>();
                tableExistsNum.forEach(item -> {
                    updateLists.add(orderMap.get(item));
                });
                //校验状态代码
                List<LovValueDTO> statusLovValues = lovFeignClient.queryLovValue("SCUX_RCWL_HZPO_STATUS",tenantId);
                List<String> statusValues = new ArrayList<>();
                statusLovValues.forEach(statusLovValue->{
                            statusValues.add(statusLovValue.getValue());
                        }
                );
                //处理更新list中的日期与ID
                List<RcwlSodrHzpoHeader> updateHeaders = new ArrayList<>();
                updateLists.forEach(updateList -> {
                    if(statusValues.contains(updateList.getStatus())){
                        headers.forEach(header -> {
                            if (updateList.getOrderId().equals(header.getPoNum())) {
                                header.setStatusCode(updateList.getStatus());
                                if (StringUtils.isNotBlank(updateList.getConfirmTime())) {
                                    header.setConfirmedDate(Instant.ofEpochMilli(Long.parseLong(updateList.getConfirmTime())).atZone(ZoneOffset.ofHours(8)).toLocalDate());
                                }
                                if (StringUtils.isNotBlank(updateList.getShipTime())) {
                                    header.setFirstShippingDate(Instant.ofEpochMilli(Long.parseLong(updateList.getShipTime())).atZone(ZoneOffset.ofHours(8)).toLocalDate());
                                }
                                if (StringUtils.isNotBlank(updateList.getSigningTime())) {
                                    header.setConfirmReceiptDate(Instant.ofEpochMilli(Long.parseLong(updateList.getSigningTime())).atZone(ZoneOffset.ofHours(8)).toLocalDate());
                                }
                                updateHeaders.add(header);
                            }
                        });
                    }
                });
                //更新数据
                rcwlSodrHzpoHeaderRepository.batchUpdateOptional(updateHeaders, "statusCode", "confirmedDate", "firstShippingDate", "confirmReceiptDate");
            }
        } catch (Exception e) {
            throw new CommonException("调用接口失败!"+ e.toString());
        }
        return ReturnT.SUCCESS;
    }
}
