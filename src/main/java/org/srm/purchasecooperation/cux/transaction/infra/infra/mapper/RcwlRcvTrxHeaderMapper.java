package org.srm.purchasecooperation.cux.transaction.infra.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxHeader;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;

import java.util.List;

public interface RcwlRcvTrxHeaderMapper {

    List<ReceiveRcvTrxDataVO> rcwlQueryCanRcvTrxData(ReceiveRcvTrxDataVO receiveRcvTrxDataVO);

}
