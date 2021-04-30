package org.srm.purchasecooperation.cux.sinv.infra.repository.impl;

import java.util.Date;
import java.util.Objects;

import io.choerodon.core.domain.Page;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAddAsnStatusDTO;
import org.srm.purchasecooperation.cux.sinv.domain.repository.SinvRcvTrxHeaderAddAsnStatusRepository;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.SinvRcvTrxHeaderAddAsnStatusMapper;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxQueryDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxWaitingDTO;
import org.srm.purchasecooperation.sinv.infra.repository.impl.SinvRcvTrxHeaderRepositoryImpl;

/**
 * description
 *
 * @author Penguin 2021/04/30 15:02
 */
@Component
public class SinvRcvTrxHeaderAddAsnStatusRepositoryImpl implements SinvRcvTrxHeaderAddAsnStatusRepository {

    @Autowired
    private SinvRcvTrxHeaderAddAsnStatusMapper sinvRcvTrxHeaderAddAsnStatusMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SinvRcvTrxHeaderRepositoryImpl.class);

    @Override
    public Page<SinvRcvTrxWaitingAddAsnStatusDTO> selectSinvRcvTrxAddAsnStatusWaiting(SinvRcvTrxQueryDTO sinvRcvTrxQueryDTO, CustomUserDetails customUserDetails, PageRequest pageRequest) {
        LOGGER.info("srm-22587-selectSinvRcvTrxWaiting:begin");
        Date sysdate = new Date();
        if (Objects.nonNull(sinvRcvTrxQueryDTO.getTrxDateEnd())) {
            sinvRcvTrxQueryDTO.setTrxDateEnd(DateUtils.addDays(sinvRcvTrxQueryDTO.getTrxDateEnd(), 1));
        }

        Page<SinvRcvTrxWaitingAddAsnStatusDTO> sinvRcvTrxWaitingDTOPage = PageHelper.doPageAndSort(pageRequest, () -> {
            return this.sinvRcvTrxHeaderAddAsnStatusMapper.selectSinvRcvTrxWainting(sinvRcvTrxQueryDTO, customUserDetails);
        });
        sinvRcvTrxWaitingDTOPage.forEach((SinvRcvTrxWaitingAddAsnStatusDTO) -> {
            SinvRcvTrxWaitingAddAsnStatusDTO.setTrxDate(sysdate);
        });
        LOGGER.info("srm-22587-selectSinvRcvTrxWaiting:end");
        return sinvRcvTrxWaitingDTOPage;
    }
}
