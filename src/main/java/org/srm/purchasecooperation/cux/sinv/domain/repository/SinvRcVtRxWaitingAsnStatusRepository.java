package org.srm.purchasecooperation.cux.sinv.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * description
 *
 * @author Penguin 2021/04/30 16:59
 */
public interface SinvRcVtRxWaitingAsnStatusRepository {

    Page<SinvRcvTrxWaitingAsnStatusDTO> pageRcvTrxWaitingAsnStatus(SinvRcvTrxWaitingAsnNumDTO asnNumDTO, PageRequest pageRequest);
}
