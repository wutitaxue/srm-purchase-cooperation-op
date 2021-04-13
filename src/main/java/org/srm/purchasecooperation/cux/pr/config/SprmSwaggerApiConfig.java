package org.srm.purchasecooperation.cux.pr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Allen  2018/12/21
 */
@Configuration
public class SprmSwaggerApiConfig {
    public static final String SPRM_PLAN = "SprmPlan";

    @Autowired
    public SprmSwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(SPRM_PLAN, "采购计划")

        );
    }
    public static String getSprmPlan() {
        return SPRM_PLAN;
    }
}
