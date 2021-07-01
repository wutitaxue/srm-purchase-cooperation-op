package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.order.api.dto.ContractResultDTO;
import org.srm.purchasecooperation.order.api.dto.PoHeaderAccordingToLineOfReferenceDTO;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlPoLineMapper;
import org.srm.purchasecooperation.order.domain.vo.PoHeaderAccordingToLineOfReferenceVO;
import org.srm.purchasecooperation.order.infra.repository.impl.PoLineRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;


@Component
@Tenant("SRM-RCWL")
public class RcwlPoLineRepositoryImpl extends PoLineRepositoryImpl {
    @Autowired
     private RcwlPoLineMapper rcwlPoLineMapper;
    @Override
    public Page<PoLineDetailDTO> pageLineDetail(PageRequest pageRequest, Long poHeaderId, Integer camp, Long tenantId) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());

        return PageHelper.doPageAndSort(pageRequest, () -> {
            return this.rcwlPoLineMapper.listLineDetail1(tenantId, poHeaderId, date);
        });
    }

    @Override
    public Page<ContractResultDTO> selectContractResultDTO(PageRequest pageRequest, Long tenantId, ContractResultDTO contractResultDTO) {
        return PageHelper.doPageAndSort(pageRequest, () -> {
            return this.rcwlPoLineMapper.selectContractResult(tenantId, contractResultDTO);
        });
    }

    @Override
    @ProcessLovValue
    public List<PoHeaderAccordingToLineOfReferenceVO> selectAccordingToLineOfReference(PoHeaderAccordingToLineOfReferenceDTO poHeaderAccordingToLineOfReferenceDTO) {
        return this.rcwlPoLineMapper.selectAccordingToLineOfReference(poHeaderAccordingToLineOfReferenceDTO);
    }
}
