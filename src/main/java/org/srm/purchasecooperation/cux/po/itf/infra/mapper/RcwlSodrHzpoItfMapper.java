package org.srm.purchasecooperation.cux.po.itf.infra.mapper;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 17:18
 * @Description:
 */
public interface RcwlSodrHzpoItfMapper {
    Long getTenantId();

    Long checkUnifiedSocialCode(Long tenantId, String unifiedSocialCode);

    Long checkSkuCategoryCode(Long tenantId, String skuCategoryCode);

    Long checkSkuNo(Long tenantId, String skuNo);

}
