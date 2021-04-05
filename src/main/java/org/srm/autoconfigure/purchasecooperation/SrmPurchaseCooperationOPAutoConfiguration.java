package org.srm.autoconfigure.purchasecooperation;

import io.choerodon.resource.annoation.EnableChoerodonResourceServer;
import org.hzero.boot.platform.entity.autoconfigure.EntityRegistScan;
import org.hzero.core.jackson.annotation.EnableObjectMapper;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.srm.boot.autoconfigure.EnableSrmEvent;
import org.srm.purchasecooperation.autoconfigure.EnableSrmPurchaseCooperation;

/**
 * Purchase Cooperation OP Auto Configuration
 *
 * @author zhongsheng.xu@hand-china.com 2021-03-23 20:18:33
 */
@Configuration
//@EnableFeignClients("org.srm.purchasecooperation.cux")
@ComponentScan({"org.srm.purchasecooperation.cux","org.srm.purchasecooperation.cux.infra.mapper"})
//@EnableSrmPurchaseCooperation

@ComponentScan({"org.srm.purchasecooperation.asn", "org.srm.purchasecooperation.finance", "org.srm.purchasecooperation.forecast", "org.srm.purchasecooperation.order", "org.srm.purchasecooperation.pr", "org.srm.purchasecooperation.transaction", "org.srm.purchasecooperation.ssch", "org.srm.purchasecooperation.plan", "org.srm.purchasecooperation.budget", "org.srm.purchasecooperation.sinv", "org.srm.boot.platform.print", "org.srm.purchasecooperation.accept", "org.srm.purchasecooperation.common", "org.srm.source", "org.srm.purchasecooperation.utils"})
@EnableFeignClients({"org.srm.purchasecooperation.asn", "org.srm.purchasecooperation.finance", "org.srm.purchasecooperation.order", "org.srm.purchasecooperation.pr", "org.srm.purchasecooperation.transaction", "org.srm.purchasecooperation.plan", "org.srm.purchasecooperation.budget", "org.srm.purchasecooperation.sinv", "org.srm.source.priceLib", "io.choerodon", "org.srm.source","org.srm.purchasecooperation.cux"})
//@EnableChoerodonResourceServer
@EnableDiscoveryClient
@EnableAsync
@EnableAspectJAutoProxy(
        exposeProxy = true
)
@EnableObjectMapper
@EnableSrmEvent
@EntityRegistScan(
        basePackages = {"org.srm.purchasecooperation/asn/domain/entity/**"}
)
public class SrmPurchaseCooperationOPAutoConfiguration {
}
