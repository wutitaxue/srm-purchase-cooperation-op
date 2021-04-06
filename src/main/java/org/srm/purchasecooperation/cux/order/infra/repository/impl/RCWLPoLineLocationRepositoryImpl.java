package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.common.api.dto.SmdmCurrencyDTO;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.cux.order.infra.constant.RCWLOrderConstant;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.vo.PoLineLocationVO;
import org.srm.purchasecooperation.order.infra.mapper.PoLineLocationMapper;
import org.srm.purchasecooperation.order.infra.repository.impl.PoLineLocationRepositoryImpl;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;
import org.srm.purchasecooperation.transaction.app.service.RcvTrxLineService;
import org.srm.web.annotation.Tenant;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

@Component
@Tenant(RCWLOrderConstant.TENANT_NUMBER)
public class RCWLPoLineLocationRepositoryImpl extends PoLineLocationRepositoryImpl {
    @Autowired
    private PoLineLocationMapper poLineLocationMapper;
    @Autowired
    private MdmService mdmService;
    @Autowired
    private RcvTrxLineService rcvTrxLineService;

    public RCWLPoLineLocationRepositoryImpl(PoLineLocationMapper poLineLocationMapper, LovValueHandle lovValueHandle) {
        super(poLineLocationMapper, lovValueHandle);
    }

    @Override
    public Page<PoLineLocationVO> pagePoLineLocation(PageRequest pageRequest, PoLineLocation poLineLocation) {
        poLineLocation.setNowDate(LocalDate.now().toDate());
        Page<PoLineLocationVO> poLineLocationVOSPage = PageHelper.doPageAndSort(pageRequest,
                        () -> this.poLineLocationMapper.selectPoLineLocation(poLineLocation));
        List<PoLineLocationVO> poLineLocationVOS = poLineLocationVOSPage.getContent();
        Iterator var6 = poLineLocationVOS.iterator();

        while (var6.hasNext()) {
            PoLineLocationVO poLineLocationVO = (PoLineLocationVO) var6.next();
            if (poLineLocationVO.getReturnedFlag() != null && poLineLocationVO.getReturnedFlag() == 1) {
                BigDecimal lineAmount = poLineLocationVO.getLineAmount();
                BigDecimal taxIncludedLineAmount = poLineLocationVO.getTaxIncludedLineAmount();
                BigDecimal newLineAmount = lineAmount.multiply(new BigDecimal(-1));
                BigDecimal newTaxIncludedLineAmount = taxIncludedLineAmount.multiply(new BigDecimal(-1));
                poLineLocationVO.setLineAmount(newLineAmount);
                poLineLocationVO.setTaxIncludedLineAmount(newTaxIncludedLineAmount);
            }
            // TODO 净入库逻辑
            ReceiveTransactionLineDTO queryParam = new ReceiveTransactionLineDTO();
            queryParam.setDisplayPoNum(poLineLocationVO.getDisplayPoNum());
            queryParam.setTenantId(poLineLocationVO.getTenantId());
            Page<ReceiveTransactionLineDTO> receiveTransactionLineDTOS =
                            rcvTrxLineService.queryReceiveTransactionLineForPurchase(queryParam, pageRequest);
            if (!CollectionUtils.isEmpty(receiveTransactionLineDTOS)) {
                Long inventoryQuantity = receiveTransactionLineDTOS.stream()
                                .mapToLong(ReceiveTransactionLineDTO::getAttributeBigint1).sum();
                poLineLocationVO.setNetDeliverQuantity(
                                new BigDecimal(inventoryQuantity).add(poLineLocationVO.getNetDeliverQuantity()));
            }
            SmdmCurrencyDTO domesticCurrencyDTO;
            if (Objects.nonNull(poLineLocationVO.getCurrencyCode())) {
                domesticCurrencyDTO = this.mdmService.selectSmdmCurrencyDto(poLineLocationVO.getTenantId(),
                                poLineLocationVO.getCurrencyCode());
                poLineLocationVO.setFinancialPrecision(domesticCurrencyDTO.getFinancialPrecision());
            }

            if (Objects.nonNull(poLineLocationVO.getHeaderDomesticCurrencyCode())) {
                domesticCurrencyDTO = this.mdmService.selectSmdmCurrencyDto(poLineLocationVO.getTenantId(),
                                poLineLocationVO.getHeaderDomesticCurrencyCode());
                poLineLocationVO.setDomesticFinancialPrecision(domesticCurrencyDTO.getFinancialPrecision());
            }
        }

        return poLineLocationVOSPage;
    }
}
