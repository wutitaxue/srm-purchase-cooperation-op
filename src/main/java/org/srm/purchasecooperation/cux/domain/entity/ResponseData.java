package org.srm.purchasecooperation.cux.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ResponseData {
    private String code;
    private String message;
    private String state;
}
