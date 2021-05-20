package org.srm.purchasecooperation.cux.transaction.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.transaction.domain.vo.RcwlReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.transaction.api.dto.RcvTrxQueryDataDTO;
import org.srm.purchasecooperation.transaction.app.service.RcvTrxHeaderService;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;

public interface RcwlRcvTrxHeaderService extends RcvTrxHeaderService {

    Page<RcwlReceiveRcvTrxDataVO> rcwlQueryCanRcvTrxDataEntrance(Long tenantId, RcvTrxQueryDataDTO vo, PageRequest pageRequest);
}
