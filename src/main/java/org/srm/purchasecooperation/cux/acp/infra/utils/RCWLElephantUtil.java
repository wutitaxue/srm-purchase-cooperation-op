package org.srm.purchasecooperation.cux.acp.infra.utils;

import com.aliyun.openservices.shade.org.apache.commons.codec.binary.Base64;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lu.cheng01@hand-china.com
 * @description 大象慧云工具类
 * @date 2021/4/9 17:32
 * @version:1.0
 */
public class RCWLElephantUtil {
    /**
     * 获取签名
     * @param srcStr 拼接出来的请求字符串
     * @return
     */
    public String getAppSec(String srcStr) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String appSecKey = RCWLAcpConstant.APPSECKEY;
        SecretKeySpec keySpec = new SecretKeySpec(appSecKey.getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(keySpec);
        byte[] signBytes = mac.doFinal(srcStr.getBytes("UTF-8"));
        String appSec = Base64.encodeBase64String(signBytes);
        System.out.println("得到的appSec："+appSec);
        return appSec;
    }
}
