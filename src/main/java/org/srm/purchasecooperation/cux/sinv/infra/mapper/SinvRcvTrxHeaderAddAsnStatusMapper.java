package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import java.util.List;


import com.goinglink.hollyhock.mybatis.common.BaseMapper;
import io.choerodon.core.oauth.CustomUserDetails;


import org.apache.ibatis.annotations.Param;

import org.srm.purchasecooperation.cux.sinv.domain.entity.SinvRcvTrxAddAsnStatusHeader;
import org.srm.purchasecooperation.cux.sinv.infra.util.TenantValue;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxWaitingDTO;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxHeader;
import org.srm.purchasecooperation.sinv.infra.mapper.SinvRcvTrxHeaderMapper;
import org.srm.web.annotation.Tenant;
import org.srm.web.dynamic.ExtendMapper;

/**
 * description
 *
 * @author Penguin 2021/04/30 14:06
 */
@Tenant(TenantValue.tenantV)
public interface SinvRcvTrxHeaderAddAsnStatusMapper extends SinvRcvTrxHeaderMapper{

    List<SinvRcvTrxWaitingDTO> selectSinvRcvTrxWainting(@Param("sinvRcvTrxQueryDTO") SinvRcvTrxQueryDTO sinvRcvTrxQueryDTO, @Param("customUserDetails") CustomUserDetails customUserDetails);

}
