package org.srm.purchasecooperation.order.domain.repository;

import org.hzero.mybatis.base.BaseRepository;

/**
 * @author bin.zhang
 */
public interface RCWLPoHeaderRepository  {

    String selectCategoryCode(Long categoryId, Long tenantId);
}
