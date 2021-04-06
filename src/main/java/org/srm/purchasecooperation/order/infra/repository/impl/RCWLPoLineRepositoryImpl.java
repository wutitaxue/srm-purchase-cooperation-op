package org.srm.purchasecooperation.order.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.domain.repository.RCWLPoHeaderRepository;
import org.srm.purchasecooperation.order.infra.mapper.PoLineMapper;
import org.srm.purchasecooperation.order.infra.mapper.RCWLPoHeaderMapper;
import org.srm.purchasecooperation.order.infra.mapper.RCWLPoLineMapper;
import org.srm.web.annotation.Tenant;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * @author bin.zhang
 */
@Component
@Tenant("SRM-RCWL")
public class RCWLPoLineRepositoryImpl extends PoLineRepositoryImpl {
    @Autowired
    RCWLPoLineMapper rcwlPoLineMapper;
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
}
