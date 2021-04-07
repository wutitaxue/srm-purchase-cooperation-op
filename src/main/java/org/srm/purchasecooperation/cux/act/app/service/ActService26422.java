package org.srm.purchasecooperation.cux.act.app.service;

import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto26422;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:08
 * @version:1.0
 */
public interface ActService26422 {
    public ActListHeaderDto26422 actQuery(Long acceptListHeaderId,Long organizationId);
}
