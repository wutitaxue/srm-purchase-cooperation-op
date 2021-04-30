package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import java.util.List;


import io.choerodon.core.oauth.CustomUserDetails;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAddAsnStatusDTO;
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
public interface SinvRcvTrxHeaderAddAsnStatusMapper extends BaseMapper<SinvRcvTrxAddAsnStatusHeader> {

    List<SinvRcvTrxWaitingAddAsnStatusDTO> selectSinvRcvTrxWainting(@Param("sinvRcvTrxQueryDTO") SinvRcvTrxQueryDTO sinvRcvTrxQueryDTO, @Param("customUserDetails") CustomUserDetails customUserDetails);

}
