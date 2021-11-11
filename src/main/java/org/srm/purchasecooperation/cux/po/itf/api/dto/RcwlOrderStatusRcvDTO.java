package org.srm.purchasecooperation.cux.po.itf.api.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/11/9 15:55
 * @Description:
 */
@Data
public class RcwlOrderStatusRcvDTO {
    String code;
    String message;
    String success;
    List<RcwlOrderStatusDTO> data;
}
