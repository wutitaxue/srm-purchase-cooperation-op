package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;

import java.util.List;

/**
 * @author bin.zhang
 */
public interface RCWLPoHeaderMapper {



    /**
     * 查询出需要封装的item list
     * @param ids
     * @param tenantId
     * @return
     */
    List<RCWLItemInfoVO> selectItemListByPoLineIdList(@Param("ids") List<Long> ids, @Param("tenantId") Long tenantId);

    /**
     * 批量插入
     * @param rcwlItemInfoVOList
     */
    void batchInsertItem(@Param("rcwlItemInfoVOList") List<RCWLItemInfoVO> rcwlItemInfoVOList);

    /**
     * 通过订单行号查找item_id
     * @param poLineId
     * @param tenantId
     * @return
     */
    Long selectItemIdByPoLineId(@Param("poLineId") Long poLineId, @Param("tenantId") Long tenantId);

    /**
     * 查询出需要封装的item category assign list
     * @param lineIds
     * @param tenantId
     * @return
     */
    List<RCWLItemInfoVO> selectItemCategoryListByPoLineIdList(@Param("lineIds") List<Long> lineIds, @Param("tenantId") Long tenantId);

    /**
     * 批量更新订单物料id和code
     * @param poLineList
     */
    void batchUpdatePoLine(@Param("poLineList") List<RCWLItemInfoVO> poLineList);
}
