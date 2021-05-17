package org.srm.purchasecooperation.cux.transaction.infra.mapper;

import org.srm.purchasecooperation.cux.transaction.domain.vo.RcwlReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;

import java.util.List;

public interface RcwlRcvTrxHeaderMapper {

    List<RcwlReceiveRcvTrxDataVO> rcwlQueryCanRcvTrxData(ReceiveRcvTrxDataVO receiveRcvTrxDataVO);

    List<RcwlReceiveRcvTrxDataVO> rcwlQueryCanRcvTrxDataByPo(ReceiveRcvTrxDataVO vo);
}
