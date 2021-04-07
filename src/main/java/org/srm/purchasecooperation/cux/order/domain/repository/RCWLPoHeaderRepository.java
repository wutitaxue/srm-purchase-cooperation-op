package org.srm.purchasecooperation.cux.order.domain.repository;

import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;

import java.util.List;

/**
 * @author bin.zhang
 */
public interface RCWLPoHeaderRepository {

    /**
     * 查询出需要封装的item list
     * @param ids
     * @param tenantId
     * @return
     */
    List<RCWLItemInfoVO> selectItemListByPoLineIdList(List<Long> ids, Long tenantId);

    /**
     * 批量插入
     * @param rcwlItemInfoVOList
     */
    void batchInsertItem(List<RCWLItemInfoVO> rcwlItemInfoVOList);

    /**
     * 通过订单行号查找item_id
     * @param poLineId
     * @param tenantId
     * @return
     */
    Long selectItemIdByPoLineId(Long poLineId, Long tenantId);

    /**
     * 查询出需要封装的item category assign list
     * @param lineIds
     * @param tenantId
     * @return
     */
    List<RCWLItemInfoVO> selectItemCategoryListByPoLineIdList(List<Long> lineIds, Long tenantId);

    /**
     * 批量更新订单物料id和code
     * @param poLineList
     */
    void batchUpdatePoLine(List<RCWLItemInfoVO> poLineList);
}
