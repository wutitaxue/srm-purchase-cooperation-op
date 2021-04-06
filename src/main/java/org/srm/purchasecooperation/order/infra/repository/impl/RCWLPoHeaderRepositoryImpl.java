package org.srm.purchasecooperation.order.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.order.domain.repository.RCWLPoHeaderRepository;
import org.srm.purchasecooperation.order.infra.mapper.RCWLPoHeaderMapper;

/**
 * @author bin.zhang
 */
@Component
public class RCWLPoHeaderRepositoryImpl  implements RCWLPoHeaderRepository {
    @Autowired
    RCWLPoHeaderMapper rcwlPoHeaderMapper;
    @Override
    public String selectCategoryCode(Long categoryId, Long tenantId) {
        return this.rcwlPoHeaderMapper.selectCategoryCode(categoryId,tenantId);
    }
}
