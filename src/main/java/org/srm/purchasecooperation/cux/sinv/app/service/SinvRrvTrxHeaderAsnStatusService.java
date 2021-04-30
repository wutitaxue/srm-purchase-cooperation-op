package org.srm.purchasecooperation.cux.sinv.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAsnNumDTO;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAsnStatusDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;

/**
 * description
 *
 * @author Penguin 2021/04/30 16:42
 */
public interface SinvRrvTrxHeaderAsnStatusService {
    Page<SinvRcvTrxWaitingAsnStatusDTO> pageRcvTrxWaitingAsnStatus(SinvRcvTrxWaitingAsnNumDTO asnNumDTO, PageRequest pageRequest);
}
