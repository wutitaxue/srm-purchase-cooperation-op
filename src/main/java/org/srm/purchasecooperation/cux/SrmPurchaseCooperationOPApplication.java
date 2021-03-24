package org.srm.purchasecooperation.cux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.srm.autoconfigure.purchasecooperation.EnableSrmPurchaseCooperationOP;

/**
 * SRM协同服务应用
 *
 * @author zhongsheng.xu@hand-china.com 2021-03-23 20:16:03
 */
@SpringBootApplication
@EnableSrmPurchaseCooperationOP
public class SrmPurchaseCooperationOPApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrmPurchaseCooperationOPApplication.class, args);
    }

}
