package org.srm.purchasecooperation.cux.transaction.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.transaction.domain.repository.RcwlRcvTrxHeaderRepository;
import org.srm.purchasecooperation.cux.transaction.domain.vo.RcwlReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.cux.transaction.infra.mapper.RcwlRcvTrxHeaderMapper;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.transaction.infra.mapper.RcvTrxHeaderMapper;
import org.srm.purchasecooperation.transaction.infra.repository.impl.RcvTrxHeaderRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.util.Objects;

@Component
@Tenant("SRM-RCWL")
public class RcwlRcvTrxHeaderRepositoryImpl implements RcwlRcvTrxHeaderRepository {
    @Autowired
    private RcwlRcvTrxHeaderMapper rcvTrxHeaderMapper;

    public Page<RcwlReceiveRcvTrxDataVO> rcwlQueryCanRcvTrxDataEntrance(Long tenantId, ReceiveRcvTrxDataVO vo, PageRequest pageRequest) {
        vo.setTenantId(tenantId);
        if ("ORDER".equals(vo.getReceiveOrderType())) {
            Page<RcwlReceiveRcvTrxDataVO> receiveRcvTrxDataVOPage = PageHelper.doPage(pageRequest.getPage(), pageRequest.getSize(), () -> {
                return this.rcvTrxHeaderMapper.rcwlQueryCanRcvTrxDataByPo(vo);
            });
            receiveRcvTrxDataVOPage.forEach((receiveRcvTrxDataVO) -> {
                if (Objects.isNull(receiveRcvTrxDataVO.getLocationId())) {
                    receiveRcvTrxDataVO.setLocationId(receiveRcvTrxDataVO.getLocationIdCopy());
                }
            });
            return receiveRcvTrxDataVOPage;
        } else {
            return PageHelper.doPage(pageRequest.getPage(), pageRequest.getSize(), () -> {
                return this.rcvTrxHeaderMapper.rcwlQueryCanRcvTrxData(vo);
            });
        }
    }
}
