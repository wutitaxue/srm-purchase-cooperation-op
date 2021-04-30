package org.srm.purchasecooperation.cux.sinv.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAsnNumDTO;
import org.srm.purchasecooperation.cux.sinv.api.controller.dto.SinvRcvTrxWaitingAsnStatusDTO;
import org.srm.purchasecooperation.cux.sinv.domain.repository.SinvRcVtRxWaitingAsnStatusRepository;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.SinvRcvTrxWaitingAsnStatusMapper;
import org.srm.purchasecooperation.sinv.infra.repository.impl.SinvRcvTrxHeaderRepositoryImpl;

/**
 * description
 *
 * @author Penguin 2021/04/30 17:00
 */
@Component
public class SinvRcVtRxWaitingAsnStatusRepositoryImpl implements SinvRcVtRxWaitingAsnStatusRepository {


    private  SinvRcvTrxWaitingAsnStatusMapper sinvRcvTrxWaitingAsnStatusMapper;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(SinvRcvTrxHeaderRepositoryImpl.class);

    @Override
    public Page<SinvRcvTrxWaitingAsnStatusDTO> pageRcvTrxWaitingAsnStatus(SinvRcvTrxWaitingAsnNumDTO asnNumDTO, PageRequest pageRequest) {
        LOGGER.info("srm-63592-selectSinvRcvTrxWaitingAsnStatus:begin");
        String asnNum = asnNumDTO.getAsnNum();
        Long tenantId = asnNumDTO.getTenantId();
        if (StringUtils.isBlank(asnNum)) {
            throw new CommonException("送货单单号不能为空!!");
        }

        if (tenantId == null) {
            throw new CommonException("租户id不能为空!!");
        }

        Page<SinvRcvTrxWaitingAsnStatusDTO> statusDTOPage = PageHelper.doPageAndSort(pageRequest, () -> {
            return this.sinvRcvTrxWaitingAsnStatusMapper.selectRcvTrxWaitingAsnStatus(asnNum, tenantId);
        });
        LOGGER.info("srm-63592-selectSinvRcvTrxWaitingAsnStatus:end");
        return statusDTOPage;
    }
}
