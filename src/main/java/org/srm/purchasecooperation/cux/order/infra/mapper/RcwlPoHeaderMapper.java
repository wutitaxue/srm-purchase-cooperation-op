package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;

import java.util.List;

/**
 * @author bin.zhang
 */
@Component
public interface RcwlPoHeaderMapper {



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

    /**
     * 批量插入物料名称多语言表
     * @param poLineList
     */
    void batchInsertItemTl(@Param("poLineList")List<RCWLItemInfoVO> poLineList);

    /**
     * 查找来源电商平台
     * @param poHeaderId
     * @param tenantId
     * @return
     */
    String selectShopMallSupplier(@Param("poHeaderId")Long poHeaderId, @Param("tenantId")Long tenantId);
}
