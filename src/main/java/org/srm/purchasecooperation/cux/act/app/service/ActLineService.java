package org.srm.purchasecooperation.cux.act.app.service;

import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description 验收单行service
 * @date 2021/4/6 10:08
 * @version:1.0
 */
public interface ActLineService {
    public List<ActListLinesDto> actQuery(Long acceptListHeaderId, Long organizationId);
}
