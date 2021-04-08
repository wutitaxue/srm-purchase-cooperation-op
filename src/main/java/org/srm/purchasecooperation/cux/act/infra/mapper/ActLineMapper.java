package org.srm.purchasecooperation.cux.act.infra.mapper;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;
import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:28
 * @version:1.0
 */
public interface ActLineMapper {
    List<ActListLinesDto> actListLineQuery(Long acceptListHeaderId, Long organizationId);
}
