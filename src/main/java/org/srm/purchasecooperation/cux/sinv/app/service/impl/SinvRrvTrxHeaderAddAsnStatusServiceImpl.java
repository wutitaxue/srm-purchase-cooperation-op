package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAddAsnStatusDTO;
import org.srm.purchasecooperation.cux.sinv.app.service.SinvRrvTrxHeaderAddAsnStatusService;
import org.srm.purchasecooperation.cux.sinv.domain.repository.SinvRcvTrxHeaderAddAsnStatusRepository;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxWaitingDTO;
import org.srm.purchasecooperation.sinv.app.service.impl.SinvRcvTrxHeaderServiceImpl;

/**
 * description
 *
 * @author Penguin 2021/04/30 14:54
 */

@Service

public class SinvRrvTrxHeaderAddAsnStatusServiceImpl implements SinvRrvTrxHeaderAddAsnStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SinvRcvTrxHeaderServiceImpl.class);

    @Autowired
    private SinvRcvTrxHeaderServiceImpl sinvRcvTrxHeaderService;

    @Autowired
    private SinvRcvTrxHeaderAddAsnStatusRepository sinvRcvTrxHeaderAddAsnStatusRepository;

    @Override
    public Page<SinvRcvTrxWaitingAddAsnStatusDTO> pageRcvTrxWaiting(SinvRcvTrxQueryDTO sinvRcvTrxQueryDTO, PageRequest pageRequest) {
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-pageRcvTrxWaiting:begin");
        CustomUserDetails customUserDetails = DetailsHelper.getUserDetails();
        sinvRcvTrxHeaderService.dealWtithSinvRcvTrxQueryDTO(sinvRcvTrxQueryDTO.getTenantId(), customUserDetails, sinvRcvTrxQueryDTO);
        Page<SinvRcvTrxWaitingAddAsnStatusDTO> sinvRcvTrxWaitingDTOPage = this.sinvRcvTrxHeaderAddAsnStatusRepository.selectSinvRcvTrxAddAsnStatusWaiting(sinvRcvTrxQueryDTO, customUserDetails, pageRequest);
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-pageRcvTrxWaiting:end");
        return sinvRcvTrxWaitingDTOPage;

    }
}
