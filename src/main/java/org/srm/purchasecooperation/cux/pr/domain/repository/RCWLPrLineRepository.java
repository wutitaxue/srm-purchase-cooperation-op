package org.srm.purchasecooperation.cux.pr.domain.repository;

import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.cux.pr.domain.vo.PrHeaderVO;
import org.srm.purchasecooperation.cux.pr.domain.vo.RCWLPrLineVO;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;

import java.util.List;

/**
 * 采购申请行资源库
 *
 * @author bin.zhang06@hand-china.com 2021-03-16 15:49:15
 */
public interface RCWLPrLineRepository {
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

    /**
     * source服务-入围单更新入围单信息
     *
     * @param prLines 采购申请行信息
     * @return List<PrLine>
     */
    List<org.srm.purchasecooperation.pr.domain.entity.PrLine> updateSourcePrLine(List<org.srm.purchasecooperation.pr.domain.entity.PrLine> prLines);

    /**
     * 通过id查找行记录
     * @param prLineId
     * @return
     */
    PrLine selectPrLineRecord(Long prLineId);

    /**
     * 分页查询采购申请行拓展
     *
     * @param pageRequest
     * @param tenantId
     * @param prHeaderId
     * @return
     */
    List<RCWLPrLineVO> selectPrLines(PageRequest pageRequest, Long tenantId, Long prHeaderId);
}
