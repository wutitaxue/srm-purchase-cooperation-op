package org.srm.purchasecooperation.cux.sinv.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAddAsnStatusDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;

/**
 * description
 *
 * @author Penguin 2021/04/30 15:00
 */
public interface SinvRcvTrxHeaderAddAsnStatusRepository {
    Page<SinvRcvTrxWaitingAddAsnStatusDTO> selectSinvRcvTrxAddAsnStatusWaiting(SinvRcvTrxQueryDTO sinvRcvTrxQueryDTO, CustomUserDetails customUserDetails, PageRequest pageRequest);
}
