package org.srm.purchasecooperation.cux.act.domain.repository;


import org.srm.purchasecooperation.cux.act.api.dto.ActListFilesDto;
import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description 附件查询
 * @date 2021/4/6 10:21
 * @version:1.0
 */
public interface ActFilesRespository {
    List<ActListFilesDto> actFilesQuery(Long acceptListHeaderId, Long organizationId);
}
