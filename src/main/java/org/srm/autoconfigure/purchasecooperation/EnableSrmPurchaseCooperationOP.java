package org.srm.autoconfigure.purchasecooperation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * SRM协同服务配置导入
 *
 * @author zhongsheng.xu@hand-china.com 2021-03-23 20:17:03
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SrmPurchaseCooperationOPAutoConfiguration.class})
public @interface EnableSrmPurchaseCooperationOP {
}
