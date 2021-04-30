package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAsnNumDTO;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAsnStatusDTO;

import org.srm.purchasecooperation.cux.sinv.app.service.SinvRrvTrxHeaderAsnStatusService;
import org.srm.purchasecooperation.cux.sinv.domain.repository.SinvRcVtRxWaitingAsnStatusRepository;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;

/**
 * description
 *
 * @author Penguin 2021/04/30 16:43
 */
@Service
public class SinvRrvTrxHeaderAsnStatusServiceImpl implements SinvRrvTrxHeaderAsnStatusService {

    @Autowired
    private SinvRcVtRxWaitingAsnStatusRepository sinvRcVtRxWaitingAsnStatusRepository;

    @Override
    public Page<SinvRcvTrxWaitingAsnStatusDTO> pageRcvTrxWaitingAsnStatus(SinvRcvTrxWaitingAsnNumDTO asnNumDTO, PageRequest pageRequest) {

        this.sinvRcVtRxWaitingAsnStatusRepository.pageRcvTrxWaitingAsnStatus(asnNumDTO,pageRequest);

        return null;
    }
}
