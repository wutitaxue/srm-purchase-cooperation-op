package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.asn.api.dto.RcwlAsnAcceptOrRcvDTO;

public interface RcwlAsnInterfaceMapper extends BaseMapper<RcwlAsnAcceptOrRcvDTO> {
    //sinv_rcv_trx_line写回传单号receipt_no 净入库数量inventory_quantity 、资产系统采购订单号purchase_order_no
    void updateSinvLineReturn(@Param("sinv") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

    //查询表sinv_rcv_trx_line字段invoice_matched_status值是否为UNINVOICED是给Y 否给N
    Long selectSinvStatusCount (@Param("sinv") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

    void deleteSinvLineReturn (@Param("sinv") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

    //spuc_accept_list_line写回传单号assetNumber、资产系统采购订单号purchase_order_no
    void updateSpucLineReturn( @Param("accept") RcwlAsnAcceptOrRcvDTO acceptListLine);

    //查询表sfin_bill_detail_accept根据验收单头ID和行ID查询这表是否有值 有给N 没有Y
    Long selectSpucCount (@Param("accept") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

    void deleteSpucLineReturn (@Param("accept") RcwlAsnAcceptOrRcvDTO sinvRcvTrxLine);

}
