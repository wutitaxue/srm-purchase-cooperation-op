package org.srm.purchasecooperation.cux.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.api.dto.PrLineDTO;
import org.srm.purchasecooperation.cux.domain.entity.PrLine;
import org.srm.purchasecooperation.cux.domain.vo.PrHeaderVO;

/**
 * 采购申请行资源库
 *
 * @author bin.zhang06@hand-china.com 2021-03-16 15:49:15
 */
public interface RCWLPrLineRepository extends BaseRepository<PrLine> {
    /**
     * 根据计划编号查询采购申请行
     * @param planId
     * @return
     */
    PrLineDTO selectPrLine(Long planId);
    /**
     * 把计划编号设置为空
     * @param planId
     * * @param tenantId
     * @return
     */
    void updatePrLine(Long planId, Long tenantId);
    /**
     * 通过申请头id和行号查找行id
     * @param prHeaderId
     * @param lineNum
     * @return
     */
    Long selectPrLineId(Long prHeaderId, String lineNum, Long tenantId);
    /**
     * 通过申请头和行号查找头行id
     * @param prNum
     * @param lineNum
     * @return
     */
    PrHeaderVO selectByNum(String prNum, String lineNum, Long tenantId);
}
