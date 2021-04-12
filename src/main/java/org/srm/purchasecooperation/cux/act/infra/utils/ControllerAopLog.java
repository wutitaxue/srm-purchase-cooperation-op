package org.srm.purchasecooperation.cux.act.infra.utils;

import com.alibaba.fastjson.JSON;
import javassist.Loader;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.hzero.boot.interfaces.sdk.dto.RequestPayloadDTO;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.interfaces.sdk.invoke.InterfaceInvokeSdk;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpInvoiceElephantRequestDataDTO;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpInvoiceElephantResponseDto;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpOcrService;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenglu
 * @date 2021/3/18 16:25
 */
//@Component
//@Aspect
public class ControllerAopLog {
    @Autowired
    private RCWLAcpOcrService rcwlAcpOcrService;
    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;

    private JSONObject jsonObject = new JSONObject();
    private static final Logger logger = LoggerFactory.getLogger(Loader.class);

    @Before("execution(* org.srm.purchasecooperation.cux.act.api.controller.v1.*.*(..))")
    public void before(JoinPoint joinPoint) {
        JoinPoint join = joinPoint;
        logger.info("执行方法前通知");
    }

    /**
     * 在发票审批拒绝后调用接口
     *
     * @param joinPoint
     */
    @After("execution(* org.srm.purchasecooperation.finance.app.service.impl.InvoiceHeaderServiceImpl.invoiceApproveReturn(..))")
    public void InvoiceHeaderControllerInvoiceRejectafter(JoinPoint joinPoint) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {


        logger.info("执行方法通知后...");
        //用的最多通知的签名
        Signature signature = joinPoint.getSignature();
        logger.info("Signature:" + signature);
//        获取方法名
        logger.info(joinPoint.getSignature().getName());
        MethodSignature msg = (MethodSignature) signature;

        Object target = joinPoint.getTarget();
        //获取注解标注的方法
//        Method method = target.getClass().getMethod(msg.getName(), msg.getParameterTypes());
        //通过方法获取注解
        Object proceed;
        //获取参数
        Object[] args = joinPoint.getArgs();
        List<Object> parameter = Arrays.asList(joinPoint.getArgs());
        for (Object list : parameter
        ) {
            logger.info("方法参数：" + list.toString());
        }
        for (Object arg : args) {
            logger.info("#####" + arg);
        }
        logger.info("执行方法结束。。");

        /*//以上为测试，下面为执行方法
        //______________________________________________________*/

        List<InvoiceHeader> invoiceHeaderList = (List<InvoiceHeader>) parameter.get(1);
        //循环调用接口
        for (InvoiceHeader list : invoiceHeaderList
        ) {
            RCWLAcpInvoiceElephantRequestDataDTO rcwlAcpInvoiceElephantRequestDataDTO = rcwlAcpOcrService.acpInvoiceELephantGRequestData(list.getInvoiceHeaderId());
            ResponsePayloadDTO responsePayloadDTO = new ResponsePayloadDTO();
            RequestPayloadDTO requestPayloadDTO = new RequestPayloadDTO();
            requestPayloadDTO.setPayload(JSON.toJSONString(rcwlAcpInvoiceElephantRequestDataDTO));
            logger.info("rcwlAcpInvoiceElephantRequestDataDTO:"+JSON.toJSONString(rcwlAcpInvoiceElephantRequestDataDTO));

            logger.info(JSON.toJSONString("requestPayloadDTO:" + requestPayloadDTO));

            //掉用接口
//            try {
//                //接口调用
//                responsePayloadDTO = interfaceInvokeSdk.invoke("SRM",
//                        "SSLM.PUSH_SUPPLIER_TO_SAP",
//                        "SUPPLIER_PUSH",
//                        requestPayloadDTO);
//            } catch (Exception e) {
//                throw new CommonException("调用接口失败!" + e.getMessage());
//            }
        }


    }

    @After("execution(* org.srm.purchasecooperation.cux.act.app.service.impl.ActServiceImpl.actQuery(..))")
    public void rcwlActBpmControllerQueryListafter(JoinPoint joinPoint) {
        logger.info("执行方法通知后...");

        //用的最多通知的签名
        Signature signature = joinPoint.getSignature();
        logger.info("Signature:" + signature);
        //获取方法名
        logger.info("方法名：" + joinPoint.getSignature().getName());

        //获取参数值
        List<Object> parameter = Arrays.asList(joinPoint.getArgs());
        for (Object list : parameter
        ) {
            logger.info("方法参数：" + list.toString());
        }

        MethodSignature msg = (MethodSignature) signature;


        logger.info("msg:" + JSON.toJSONString(msg));

//        Object target = joinPoint.getTarget();
//
//        //获取注解标注的方法
//        try {
//            Method method = target.getClass().getMethod(msg.getName(), msg.getParameterTypes());
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        //通过方法获取注解
        //获取参数
        Object[] args = joinPoint.getArgs();

        logger.info("args:" + JSON.toJSONString(args));

        for (Object arg : args) {
            logger.info("#####" + arg);
        }

        logger.info("执行方法结束。。");
    }

//    @AfterReturning("execution(* org.srm.purchasecooperation.cux.act.api.controller.v1.*.*(..))")
//    public void AfterReturning() {
//        System.out.println("运行通知");
//    }

//    @AfterThrowing("execution(* org.srm.purchasecooperation.cux.act.api.controller.v1.*.*(..))")
//    public void afterThrowing() {
//        System.out.println("异常通知");
//    }

//    @Around("execution(* org.srm.purchasecooperation.cux.act.api.controller.v1.*.*(..))")
//    public void around(JoinPoint joinPoint) {
//        System.out.println("环绕通知");
//    }
}
