package org.srm.purchasecooperation.cux.act.app.service;

import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto26422;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:08
 * @version:1.0
 */
public interface ActLineService26422 {
    public List<ActListLinesDto26422> actQuery(Long acceptListHeaderId, Long organizationId);
}
