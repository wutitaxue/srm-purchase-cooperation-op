package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.common.api.dto.SmdmCurrencyDTO;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlMyPoHeaderMapper;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlPoLineLocationMapper;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.vo.PoLineLocationVO;
import org.srm.purchasecooperation.order.infra.mapper.PoLineLocationMapper;
import org.srm.purchasecooperation.order.infra.repository.impl.PoLineLocationRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Tenant("SRM-RCWL")
public class RcwlPoLineLocationRepositoryImpl extends PoLineLocationRepositoryImpl {

    @Autowired
    private PoLineLocationMapper poLineLocationMapper;
    @Autowired
    private RcwlPoLineLocationMapper rcwlPoLineLocationMapper;
    @Autowired
    private MdmService mdmService;
    @Autowired
    private RcwlMyPoHeaderMapper rcwlMyPoHeaderMapper;

    @Autowired
    public RcwlPoLineLocationRepositoryImpl(PoLineLocationMapper poLineLocationMapper, LovValueHandle lovValueHandle) {
        super(poLineLocationMapper, lovValueHandle);
    }

    @Override
    public Page<PoLineLocationVO> pagePoLineLocation(PageRequest pageRequest, PoLineLocation poLineLocation) {
        poLineLocation.setNowDate(LocalDate.now().toDate());
        Page<PoLineLocationVO> poLineLocationVOSPage = PageHelper.doPageAndSort(pageRequest, () -> {
//            Page<PoLineLocationVO> page = (Page<PoLineLocationVO>) poLineLocationMapper.selectPoLineLocation(poLineLocation);
            Page<PoLineLocationVO> page = (Page<PoLineLocationVO>) rcwlPoLineLocationMapper.selectPoLineLocation(poLineLocation);
            List<PoLineLocationVO> collect = page.stream().map(m -> {
                m.setAttributeVarchar40(rcwlMyPoHeaderMapper.rcwlSelect(m.getPoHeaderId()));
                return m;
            }).collect(Collectors.toList());
            page.setContent(collect);
            return page;
        });
        List<PoLineLocationVO> poLineLocationVOS = poLineLocationVOSPage.getContent();
        Iterator var5 = poLineLocationVOS.iterator();

        while(var5.hasNext()) {
            PoLineLocationVO poLineLocationVO = (PoLineLocationVO)var5.next();
            if (poLineLocationVO.getReturnedFlag() != null && poLineLocationVO.getReturnedFlag() == 1) {
                BigDecimal lineAmount = poLineLocationVO.getLineAmount();
                BigDecimal taxIncludedLineAmount = poLineLocationVO.getTaxIncludedLineAmount();
                BigDecimal newLineAmount = lineAmount.multiply(new BigDecimal(-1));
                BigDecimal newTaxIncludedLineAmount = taxIncludedLineAmount.multiply(new BigDecimal(-1));
                poLineLocationVO.setLineAmount(newLineAmount);
                poLineLocationVO.setTaxIncludedLineAmount(newTaxIncludedLineAmount);
            }

            SmdmCurrencyDTO domesticCurrencyDTO;
            if (Objects.nonNull(poLineLocationVO.getCurrencyCode())) {
                domesticCurrencyDTO = this.mdmService.selectSmdmCurrencyDto(poLineLocationVO.getTenantId(), poLineLocationVO.getCurrencyCode());
                poLineLocationVO.setFinancialPrecision(domesticCurrencyDTO.getFinancialPrecision());
            }

            if (Objects.nonNull(poLineLocationVO.getHeaderDomesticCurrencyCode())) {
                domesticCurrencyDTO = this.mdmService.selectSmdmCurrencyDto(poLineLocationVO.getTenantId(), poLineLocationVO.getHeaderDomesticCurrencyCode());
                poLineLocationVO.setDomesticFinancialPrecision(domesticCurrencyDTO.getFinancialPrecision());
            }
        }

        return poLineLocationVOSPage;
    }
}
