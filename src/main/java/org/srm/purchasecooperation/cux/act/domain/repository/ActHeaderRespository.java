package org.srm.purchasecooperation.cux.act.domain.repository;

import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;

/**
 * @author lu.cheng01@hand-china.com
 * @description 验收单头查询
 * @date 2021/4/6 10:21
 * @version:1.0
 */
public interface ActHeaderRespository {
    ActListHeaderDto actQuery(Long acceptListHeaderId,Long organizationId);
}
