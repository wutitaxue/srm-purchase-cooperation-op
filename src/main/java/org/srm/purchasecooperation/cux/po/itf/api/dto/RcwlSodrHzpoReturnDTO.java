package org.srm.purchasecooperation.cux.po.itf.api.dto;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/11/10 16:37
 * @Description:
 */
public class RcwlSodrHzpoReturnDTO {
    // 业务逻辑成功或失败
    private Boolean failed;
    // 响应消息
    private String message;
    // 拓展字段
    private String code;

    private static final RcwlSodrHzpoReturnDTO SUCCESS = new RcwlSodrHzpoReturnDTO(false);
    private static final RcwlSodrHzpoReturnDTO FAILD = new RcwlSodrHzpoReturnDTO(true, "程序报错，请联系管理员");


    public RcwlSodrHzpoReturnDTO(Boolean failed) {
        this(failed, null);
    }

    public RcwlSodrHzpoReturnDTO(Boolean failed, String message) {
        this(failed, message, null);
    }

    public RcwlSodrHzpoReturnDTO(Boolean failed, String message, String code) {
        this.failed = failed;
        this.message = message;
        this.code = code;
    }

    public static RcwlSodrHzpoReturnDTO success(){
        return SUCCESS;
    }

    public static RcwlSodrHzpoReturnDTO faild(Exception exception){
        String message = ExceptionUtils.getMessage(exception);
        return faild(message);
    }

    public static RcwlSodrHzpoReturnDTO faild(String message){
        if (message == null || message.length() == 0){
            return FAILD;
        }
        return new RcwlSodrHzpoReturnDTO(true, message, null);
    }

    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
