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
@ComponentScan("org.srm.purchasecooperation.cux")
@EnableSrmPurchaseCooperation
public class SrmPurchaseCooperationOPAutoConfiguration {
}
