package org.srm.purchasecooperation.cux.config;

import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

public class AcceptListSwaggerApiConfig {
    /**
     * 验收单头
     */
    public static final String ACCEPT_LIST_HEADER = "Accept List Header";

    public AcceptListSwaggerApiConfig(Docket docket) {
        docket.tags(new Tag(ACCEPT_LIST_HEADER, "验收单头"));
    }
}
