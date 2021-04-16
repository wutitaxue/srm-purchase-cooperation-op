package org.srm.purchasecooperation.cux.act.app.service;

import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;

import java.io.IOException;

/**
 * @author lu.cheng01@hand-china.com
 * @description 验收单头service
 * @date 2021/4/6 10:08
 * @version:1.0
 */
public interface ActService {
    public ActListHeaderDto actQuery(Long acceptListHeaderId,Long organizationId) throws IOException;
}
