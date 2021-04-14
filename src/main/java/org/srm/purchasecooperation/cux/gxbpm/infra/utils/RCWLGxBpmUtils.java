package org.srm.purchasecooperation.cux.gxbpm.infra.utils;

import java.util.UUID;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/13 11:25
 * @version:1.0
 */
public class RCWLGxBpmUtils {
    /**
     * 获取32位uuid
     * @return
     */
    public String getGxBpmUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
