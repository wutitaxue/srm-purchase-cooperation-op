package org.srm.purchasecooperation.cux.asn.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.srm.purchasecooperation.cux.asn.domain.entity.RcwlAsnHeader;
import org.srm.purchasecooperation.cux.asn.domain.vo.RcwlAsnHeaderVO;

public interface RcwlAsnHeaderMapper extends BaseMapper<RcwlAsnHeader> {

    RcwlAsnHeaderVO selectRcwlAsnHeaderByHeaderId(Long asnHeaderId);

}
