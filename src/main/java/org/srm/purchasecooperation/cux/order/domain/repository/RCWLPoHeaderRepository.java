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

    /**
     * 批量插入物料名称多语言表smdm_item_tl
     * @param poLineList
     */

    void batchInsertItemTl(List<RCWLItemInfoVO> poLineList);

    /**
     * 查找来源电商平台
     * @param poHeaderId
     * @param tenantId
     * @return
     */
    String selectShopMallSupplier(Long poHeaderId, Long tenantId);
}
