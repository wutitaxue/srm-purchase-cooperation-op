package org.srm.purchasecooperation.cux.po.itf.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.po.itf.domain.repository.RcwlSodrHzpoItfRepository;
import org.srm.purchasecooperation.cux.po.itf.infra.mapper.RcwlSodrHzpoItfMapper;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 17:04
 * @Description:
 */
@Component
public class RcwlSodrHzpoItfRepositoryImpl implements RcwlSodrHzpoItfRepository {
    @Autowired
    private RcwlSodrHzpoItfMapper rcwlSodrHzpoItfMapper;
    @Override
    public Long getTenantId() {
        return rcwlSodrHzpoItfMapper.getTenantId();
    }

    @Override
    public Long checkUnifiedSocialCode(Long tenantId, String unifiedSocialCode) {
        return rcwlSodrHzpoItfMapper.checkUnifiedSocialCode(tenantId,unifiedSocialCode);
    }

    @Override
    public Long checkSkuCategoryCode(Long tenantId, String skuCategoryCode) {
        return rcwlSodrHzpoItfMapper.checkSkuCategoryCode(tenantId,skuCategoryCode);
    }

    @Override
    public Long checkSkuNode(Long tenantId, String skuNo) {
        return rcwlSodrHzpoItfMapper.checkSkuNo(tenantId,skuNo);
    }

}
