package org.srm.purchasecooperation.cux.po.itf.domain.repository;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 17:03
 * @Description:
 */
public interface RcwlSodrHzpoItfRepository {
    Long getTenantId();

    Long checkUnifiedSocialCode(Long tenantId,String unifiedSocialCode);

    Long checkSkuCategoryCode(Long tenantId, String skuCategoryCode);

    Long checkSkuNode(Long tenantId, String skuNo);

}
