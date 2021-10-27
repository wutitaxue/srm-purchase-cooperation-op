package org.srm.purchasecooperation.cux.po.itf.infra.mapper;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 17:18
 * @Description:
 */
public interface RcwlSodrHzpoItfMapper {
    /**
     * 获取租户信息
     *
     * @return
     */
    Long getTenantId();

    /**
     * 检查社会统一信用代码
     *
     * @param tenantId
     * @param unifiedSocialCode
     * @return
     */
    Long checkUnifiedSocialCode(Long tenantId, String unifiedSocialCode);

    /**
     * 检查商品品类代码
     *
     * @param tenantId
     * @param skuCategoryCode
     * @return
     */
    Long checkSkuCategoryCode(Long tenantId, String skuCategoryCode);

    /**
     * 检查商品编码
     *
     * @param tenantId
     * @param skuNo
     * @return
     */
    Long checkSkuNo(Long tenantId, String skuNo);

}
