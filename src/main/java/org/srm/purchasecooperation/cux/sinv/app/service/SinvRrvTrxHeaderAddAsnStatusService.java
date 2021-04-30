package org.srm.purchasecooperation.cux.sinv.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAddAsnStatusDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;

/**
 * description
 *
 * @author Penguin 2021/04/30 14:54
 */
public interface SinvRrvTrxHeaderAddAsnStatusService {
    Page<SinvRcvTrxWaitingAddAsnStatusDTO> pageRcvTrxWaiting(SinvRcvTrxQueryDTO sinvRcvTrxQueryDTO, PageRequest pageRequest);
}
