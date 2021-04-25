package org.srm.purchasecooperation.cux.sinv.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.sinv.domain.repository.RcwlSinvRcvTrxLineRepository;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.RcwlSinvRcvTrxLineMapper;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxLineDTO;
import org.srm.purchasecooperation.sinv.infra.repository.impl.SinvRcvTrxLineRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.math.BigDecimal;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 17:09
 */
@Component
@Tenant("SRM-RCWL")
public class RcwlSinvRcvTrxLineRepositoryImpl extends SinvRcvTrxLineRepositoryImpl implements RcwlSinvRcvTrxLineRepository  {
    @Autowired
    private RcwlSinvRcvTrxLineMapper rcwlSinvRcvTrxLineMapper;
    /**
     * 通过订单头行获取合同号，找合同上的质保金比例
     *  @param fromPoHeaderId
     * @param fromPoLineId
     * @param tenantId
     * @return
     */
    @Override
    public BigDecimal selectRententionMoneyPercent(Long fromPoHeaderId, Long fromPoLineId, Long tenantId) {

        return this.rcwlSinvRcvTrxLineMapper.selectRententionMoneyPercent(fromPoHeaderId,fromPoLineId,tenantId);
    }

    /**
     * 将质保金和收货人插入行表
     *
     * @param rcvTrxLineId
     * @param retentionMoney
     * @param attributeBigint2
     * @param tenantId
     */
    @Override
    public void insertRetentionMoneyAndReceiver(Long rcvTrxLineId, BigDecimal retentionMoney, Long attributeBigint2, Long tenantId) {
            this.rcwlSinvRcvTrxLineMapper.insertRetentionMoneyAndReceiver(rcvTrxLineId,retentionMoney,attributeBigint2,tenantId);
    }

}
