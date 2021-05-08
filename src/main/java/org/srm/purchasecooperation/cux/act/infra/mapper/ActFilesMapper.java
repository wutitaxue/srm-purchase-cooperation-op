package org.srm.purchasecooperation.cux.act.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.act.api.dto.ActListFilesDto;
import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description 附件查询
 * @date 2021/4/6 10:28
 * @version:1.0
 */
public interface ActFilesMapper {
    List<ActListFilesDto> actListFilseQuery( @Param("acceptListHeaderId") Long acceptListHeaderId, @Param("organizationId") Long organizationId );
}
