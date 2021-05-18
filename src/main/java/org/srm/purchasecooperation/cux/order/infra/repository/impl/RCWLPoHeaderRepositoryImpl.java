package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.domain.repository.RCWLPoHeaderRepository;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;
import org.srm.purchasecooperation.cux.order.infra.mapper.RCWLPoHeaderMapper;

import java.util.List;

@Component
public class RCWLPoHeaderRepositoryImpl  implements RCWLPoHeaderRepository {
    @Autowired
    private RCWLPoHeaderMapper rcwlPoHeaderMapper;


    /**
     * 查询出需要封装的item list
     *
     * @param ids
     * @param tenantId
     * @return
     */
    @Override
    public List<RCWLItemInfoVO> selectItemListByPoLineIdList(List<Long> ids, Long tenantId) {
        return this.rcwlPoHeaderMapper.selectItemListByPoLineIdList(ids,tenantId);
    }

    /**
     * 批量插入
     *
     * @param rcwlItemInfoVOList
     */
    @Override
    public void batchInsertItem(List<RCWLItemInfoVO> rcwlItemInfoVOList) {
        this.rcwlPoHeaderMapper.batchInsertItem(rcwlItemInfoVOList);
    }



    /**
     * 查询出需要封装的item category assign list
     *
     * @param lineIds
     * @param tenantId
     * @return
     */
    @Override
    public List<RCWLItemInfoVO> selectItemCategoryListByPoLineIdList(List<Long> lineIds, Long tenantId) {
        return rcwlPoHeaderMapper.selectItemCategoryListByPoLineIdList(lineIds,tenantId);
    }

    /**
     * 批量更新订单物料id和code
     *
     * @param poLineList
     */
    @Override
    public void batchUpdatePoLine(List<RCWLItemInfoVO> poLineList) {
        this.rcwlPoHeaderMapper.batchUpdatePoLine(poLineList);
    }

    /**
     * 批量插入物料名称多语言表smdm_item_tl
     *
     * @param poLineList
     */
    @Override
    public void batchInsertItemTl(List<RCWLItemInfoVO> poLineList) {
        this.rcwlPoHeaderMapper.batchInsertItemTl(poLineList);
    }

    /**
     * 查找来源电商平台
     *
     * @param poHeaderId
     * @param tenantId
     * @return
     */
    @Override
    public String selectShopMallSupplier(Long poHeaderId, Long tenantId) {
        return  this.rcwlPoHeaderMapper.selectShopMallSupplier(poHeaderId,tenantId);
    }


}
