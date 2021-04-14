package org.srm.purchasecooperation.cux.transaction.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.transaction.domain.vo.RcwlReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.transaction.domain.repository.RcvTrxHeaderRepository;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;


public interface RcwlRcvTrxHeaderRepository {

    Page<RcwlReceiveRcvTrxDataVO> rcwlQueryCanRcvTrxDataEntrance(Long tenantId, ReceiveRcvTrxDataVO vo, PageRequest pageRequest);
}
