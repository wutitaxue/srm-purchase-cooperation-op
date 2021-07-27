package org.srm.purchasecooperation.cux.asn.domain.service.impl;

import io.choerodon.core.exception.CommonException;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.srm.purchasecooperation.asn.domain.entity.AsnHeader;
import org.srm.purchasecooperation.asn.domain.entity.AsnLine;
import org.srm.purchasecooperation.asn.domain.repository.AsnHeaderRepository;
import org.srm.purchasecooperation.asn.domain.repository.AsnLineRepository;
import org.srm.purchasecooperation.asn.domain.service.impl.AsnHeaderDomainServiceImpl;
import org.srm.purchasecooperation.order.app.service.PoLineLocationService;
import org.srm.web.annotation.Tenant;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/7/27 15:19
 */
@Component
@Tenant("SRM-RCWL")
public class RcwlAsnHeaderDomainServiceImpl extends AsnHeaderDomainServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlAsnHeaderDomainServiceImpl.class);
    @Autowired
    private AsnHeaderRepository asnHeaderRepository;
    @Autowired
    private AsnLineRepository asnLineRepository;
    @Autowired
    @Lazy
    private PoLineLocationService poLineLocationService;
    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void closeAsn(List<AsnHeader> asnHeaderList, Long tenantId, Date date, Long userId) {
        LOGGER.debug("==>9402-AsnHeaderDomainServiceImpl-closeAsn-asnHeaderList：{}.", asnHeaderList);
        LOGGER.info("24730判断是否进入");
        //asnHeaderList.forEach(AsnHeader::validAsnShippedStatus);
        this.validAsnShippedStatus(asnHeaderList);
        this.asnHeaderRepository.batchUpdateOptional(asnHeaderList, new String[0]);
        this.syncCloseErpInterface(asnHeaderList, tenantId, date);
        this.asnHeaderRepository.batchUpdateOptional(asnHeaderList, new String[]{"asnStatus", "closeSyncDate", "closeSyncResponseMsg", "closeSyncStatus"});
        asnHeaderList.stream().filter((asnHeader) -> {
            return "SUCCESS".equals(asnHeader.getCloseSyncStatus());
        }).forEach((asnHeader) -> {
            List<AsnLine> asnLineList = (List)this.asnLineRepository.select("asnHeaderId", asnHeader.getAsnHeaderId()).stream().filter((asnLine) -> {
                return !Objects.equals(asnLine.getClosedFlag(), BaseConstants.Flag.YES) && !Objects.equals(asnLine.getCancelledFlag(), BaseConstants.Flag.YES);
            }).peek((line) -> {
                line.lineSetCloseStatus(date, userId);
            }).collect(Collectors.toList());
            this.asnLineRepository.batchUpdateOptional(asnLineList, new String[]{"closedBy", "closedDate", "closedFlag"});
            this.poLineLocationService.changeQuantityByAsnLine(asnLineList, "CLOSE");
        });
    }

    private void validAsnShippedStatus(List<AsnHeader> asnHeaderList) {
        asnHeaderList.forEach(asnHeader -> {
            if (!("SHIPPED".equals(asnHeader.getAsnStatus())||"ARRIVED".equals(asnHeader.getAsnStatus()))) {
                throw new CommonException("error.sinv.asn_approve_status_invalid", new Object[0]);
            }
        });
    }
}
