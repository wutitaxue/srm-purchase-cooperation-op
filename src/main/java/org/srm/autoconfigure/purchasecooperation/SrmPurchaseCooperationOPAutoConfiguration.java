package org.srm.autoconfigure.purchasecooperation;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.srm.purchasecooperation.autoconfigure.EnableSrmPurchaseCooperation;

/**
 * Purchase Cooperation OP Auto Configuration
 *
 * @author zhongsheng.xu@hand-china.com 2021-03-23 20:18:33
 */
@Configuration
@EnableFeignClients("org.srm.purchasecooperation.cux")
@ComponentScan({"org.srm.purchasecooperation.cux","org.srm.purchasecooperation.cux.asn",
        "org.srm.purchasecooperation.cux.accept",
        "org.srm.purchasecooperation.cux.act", "org.srm.purchasecooperation.cux.api",
        "org.srm.purchasecooperation.cux.app", "org.srm.purchasecooperation.cux.config",
        "org.srm.purchasecooperation.cux.domain", "org.srm.purchasecooperation.cux.infra",
        "org.srm.purchasecooperation.cux.order",
        "org.srm.purchasecooperation.cux.pr", "org.srm.purchasecooperation.cux.sinv",
        "org.srm.purchasecooperation.cux.transaction"})
@EnableSrmPurchaseCooperation
public class SrmPurchaseCooperationOPAutoConfiguration {
}
