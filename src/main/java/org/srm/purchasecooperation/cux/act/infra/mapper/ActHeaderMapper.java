package org.srm.purchasecooperation.cux.act.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;
import retrofit2.http.PartMap;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:28
 * @version:1.0
 */
public interface ActHeaderMapper {
    ActListHeaderDto actListHeaderQuery(@Param("acceptListHeaderId") Long acceptListHeaderId, @Param("organizationId") Long organizationId);

    Long settleIdQuery(String settleNum);

    /**
     * 更新bpminstanceid-》置0
     * @param settleNum
     */
    void updateBpmInstanceId(String settleNum);
}
