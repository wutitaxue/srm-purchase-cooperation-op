package org.srm.purchasecooperation.cux.act.infra.mapper;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto26422;
import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto26422;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:28
 * @version:1.0
 */
public interface ActLineMapper {
    List<ActListLinesDto26422> actListLineQuery(Long acceptListHeaderId, Long organizationId);
}
