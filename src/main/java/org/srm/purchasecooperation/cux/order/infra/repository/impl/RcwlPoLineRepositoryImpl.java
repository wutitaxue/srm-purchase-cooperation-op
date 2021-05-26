package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlPoLineMapper;
import org.srm.purchasecooperation.cux.order.util.TennantValue;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.infra.mapper.PoLineMapper;
import org.srm.purchasecooperation.order.infra.repository.impl.PoLineRepositoryImpl;
import org.srm.web.annotation.Tenant;

/**
 * description
 *
 * @author Zhouzy 2021/05/26 17:34
 */
@Component
@Tenant(TennantValue.tenantV)
public class RcwlPoLineRepositoryImpl extends PoLineRepositoryImpl {


    @Autowired
    RcwlPoLineMapper poLineMapper;

    @Override
    public Page<PoLineDetailDTO> pageLineDetail(PageRequest pageRequest, Long poHeaderId, Integer camp, Long tenantId) {
        return PageHelper.doPageAndSort(pageRequest, () -> {
            return this.poLineMapper.listLineDetail(tenantId, poHeaderId, LocalDate.now().toDate());
        });
    }

}
