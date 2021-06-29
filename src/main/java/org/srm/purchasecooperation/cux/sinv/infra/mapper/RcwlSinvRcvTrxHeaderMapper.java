package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import io.choerodon.core.oauth.CustomUserDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxFinishLineDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxWaitingDTO;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxHeader;
import org.srm.purchasecooperation.sinv.infra.mapper.SinvRcvTrxHeaderMapper;
import org.srm.web.annotation.Tenant;
import io.choerodon.mybatis.helper.ExtendMapper;

import java.util.List;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 21:07
 */
@Tenant("SRM-RCWL")
@Component
public interface RcwlSinvRcvTrxHeaderMapper extends SinvRcvTrxHeaderMapper, ExtendMapper<SinvRcvTrxHeader> {
    @Override
    List<SinvRcvTrxFinishLineDTO> selectSinvRcvTrxFinishLine(@Param("sinvRcvTrxQueryDTO") SinvRcvTrxQueryDTO sinvRcvTrxQueryDTO, @Param("customUserDetails") CustomUserDetails customUserDetails);
    SinvRcvTrxToKpiAutoPOLineVO countTrxHeaderByClosedFlag (@Param("sinvRcvTrxHeaderDTO") SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO);

    /**
     * 收货执行-待执行添加送货单状态
     *  dengqiping 2021.04.30
     * @param sinvRcvTrxQueryDTO
     * @param customUserDetails
     * @return
     */
    @Override
    List<SinvRcvTrxWaitingDTO> selectSinvRcvTrxWainting(@Param("sinvRcvTrxQueryDTO") SinvRcvTrxQueryDTO sinvRcvTrxQueryDTO, @Param("customUserDetails") CustomUserDetails customUserDetails);

    /**
     * 增加事物类型字段
     * @param tenantId
     * @param rcvTrxHeaderId
     * @return
     */
    SinvRcvTrxHeaderDTO selectHeaderdetail(@Param("tenantId") Long tenantId, @Param("rcvTrxHeaderId") Long rcvTrxHeaderId);

}
