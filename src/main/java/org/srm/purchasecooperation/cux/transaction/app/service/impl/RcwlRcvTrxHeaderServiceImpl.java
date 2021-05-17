package org.srm.purchasecooperation.cux.transaction.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlRcvTrxHeaderService;
import org.srm.purchasecooperation.cux.transaction.domain.repository.RcwlRcvTrxHeaderRepository;
import org.srm.purchasecooperation.cux.transaction.domain.vo.RcwlReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.transaction.api.dto.RcvTrxQueryDataDTO;
import org.srm.purchasecooperation.transaction.app.service.impl.RcvTrxHeaderServiceImpl;
import org.srm.web.annotation.Tenant;

@Service
@Tenant("SRM-RCWL")
public class RcwlRcvTrxHeaderServiceImpl extends RcvTrxHeaderServiceImpl implements RcwlRcvTrxHeaderService {

    @Autowired
    private RcwlRcvTrxHeaderRepository rcwlRcvTrxHeaderRepository;


    public Page<RcwlReceiveRcvTrxDataVO> rcwlQueryCanRcvTrxDataEntrance(Long tenantId, RcvTrxQueryDataDTO vo, PageRequest pageRequest) {
        RcwlReceiveRcvTrxDataVO dataVO = new RcwlReceiveRcvTrxDataVO();
        BeanUtils.copyProperties(vo, dataVO);
        Page<RcwlReceiveRcvTrxDataVO> receiveRcvTrxDataVOS = this.rcwlRcvTrxHeaderRepository.rcwlQueryCanRcvTrxDataEntrance(tenantId, dataVO, pageRequest);
        return receiveRcvTrxDataVOS;
    }
}
