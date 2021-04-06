package org.srm.purchasecooperation.order.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author bin.zhang
 */
public interface RCWLPoHeaderMapper {
    /**
     * 获取品类编码
     * @param categoryId
     * @param tenantId
     * @return
     */
    String selectCategoryCode(@Param("categoryId")Long categoryId, @Param("tenantId")Long tenantId);
}
