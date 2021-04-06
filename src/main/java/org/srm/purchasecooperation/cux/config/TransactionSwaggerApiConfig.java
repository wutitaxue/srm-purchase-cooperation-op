package org.srm.purchasecooperation.cux.config;

import org.springframework.beans.factory.annotation.Autowired;

import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

public class TransactionSwaggerApiConfig {
    public static final String TRANSACTION_RCV_TRX_LINE = "Transaction Rcv Trx Line";
    public static final String TRANSACTION_RCV_TRX_HEADER = "Transaction Rcv Trx Header";

    @Autowired
    public TransactionSwaggerApiConfig(Docket docket) {
        docket.tags(new Tag(TRANSACTION_RCV_TRX_LINE, "采购事务行"));
        docket.tags(new Tag(TRANSACTION_RCV_TRX_HEADER, "采购事务头"));
    }
}
