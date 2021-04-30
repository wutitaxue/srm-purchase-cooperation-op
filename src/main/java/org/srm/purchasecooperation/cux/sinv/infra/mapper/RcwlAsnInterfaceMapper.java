package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.asn.api.dto.RcwlAsnAcceptOrRcvDTO;

public interface RcwlAsnInterfaceMapper extends BaseMapper<RcwlAsnAcceptOrRcvDTO> {
    //sinv_rcv_trx_line写回传单号 净入库数量
    void updateSinvLineReturn(@Param("sinv") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

    Long checkSinvLineReturn(@Param("sinv") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

    //判断所有list是否产生了对账单
    Long selectSinvStatusCount (@Param("sinv") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

    //如果都未产生对账单 将这些单据的加上的资产单据号”字段attribute_varchar1和 “入库数量”字段attribute_bigint1
    void deleteSinvLineReturn (@Param("sinv") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

    Long selectTenantIdByName(String tenantNum);
}
