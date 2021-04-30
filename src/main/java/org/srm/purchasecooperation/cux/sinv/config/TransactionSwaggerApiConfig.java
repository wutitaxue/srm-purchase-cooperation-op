package org.srm.purchasecooperation.cux.sinv.config;

import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

public class TransactionSwaggerApiConfig {
    public static final String SUPPLIER_OPERATION = "supplier operation";
    public static final String SUPPLIER_OPERATION_REQ = "supplier operation req";
    public static final String RCWL_SUPPLIER_POOL = "Rcwl Supplier Pool";
    public static final String RCWL_SUPPLIER_OPERATION_ASN_HEADER = "rcwl supplier operation asn header";



    @Autowired
    public TransactionSwaggerApiConfig(Docket docket) {
        docket.tags(new Tag(SUPPLIER_OPERATION, "供应商创建数据接口"));
        docket.tags(new Tag(SUPPLIER_OPERATION_REQ, "供应商请求数据接口"));
        docket.tags(new Tag(RCWL_SUPPLIER_POOL, "Rcwl供应商生命周期汇总"));
        docket.tags(new Tag(RCWL_SUPPLIER_OPERATION_ASN_HEADER, "收货执行添加收货状态"));


    }
}
