package org.srm.purchasecooperation.cux.transaction.infra.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.transaction.infra.infra.mapper.RcwlRcvTrxHeaderMapper;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.transaction.infra.mapper.RcvTrxHeaderMapper;
import org.srm.purchasecooperation.transaction.infra.repository.impl.RcvTrxHeaderRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.util.Objects;

@Component
@Tenant("SRM-RCWL")
public class RcwlRcvTrxHeaderRepositoryImpl extends RcvTrxHeaderRepositoryImpl {

    @Autowired
    private RcvTrxHeaderMapper headerMapper;

    @Autowired
    private RcwlRcvTrxHeaderMapper rcwlRcvTrxHeaderMapper;

    @Override
    public Page<ReceiveRcvTrxDataVO> queryCanRcvTrxDataEntrance(Long tenantId, ReceiveRcvTrxDataVO vo, PageRequest pageRequest) {
        vo.setTenantId(tenantId);
        if ("ORDER".equals(vo.getReceiveOrderType())) {
            Page<ReceiveRcvTrxDataVO> receiveRcvTrxDataVOPage = PageHelper.doPage(pageRequest.getPage(), pageRequest.getSize(), () -> {
                return this.headerMapper.queryCanRcvTrxDataByPo(vo);
            });
            receiveRcvTrxDataVOPage.forEach((receiveRcvTrxDataVO) -> {
                if (Objects.isNull(receiveRcvTrxDataVO.getLocationId())) {
                    receiveRcvTrxDataVO.setLocationId(receiveRcvTrxDataVO.getLocationIdCopy());
                }

            });
            return receiveRcvTrxDataVOPage;
        } else {
            return PageHelper.doPage(pageRequest.getPage(), pageRequest.getSize(), () -> {
                //前台查询增加过滤送货单头状态为 DELIVERED/送货完成 的单据 2021/3/16
                return this.rcwlRcvTrxHeaderMapper.rcwlQueryCanRcvTrxData(vo);
            });
        }
    }
}
