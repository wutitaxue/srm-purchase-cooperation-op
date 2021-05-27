package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPrLineRepository;
import org.srm.purchasecooperation.cux.pr.domain.vo.PrHeaderVO;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RCWLPrLineMapper;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.purchasecooperation.pr.infra.repository.impl.PrLineRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.util.List;


/**
 * 采购申请行 资源库实现
 *
 * @author bin.zhang06@hand-china.com 2021-03-16 15:49:15
 */
@Component
@Tenant("SRM-RCWL")
public class RCWLPrLineRepositoryImpl extends PrLineRepositoryImpl implements RCWLPrLineRepository {

    @Autowired
    private RCWLPrLineMapper rcwlPrLineMapper;
    @Autowired
    private PrLineMapper prLineMapper;
    /**
     * 根据计划编号查询采购申请行
     *
     * @param planId
     * @return
     */
    @Override
    public PrLineDTO selectPrLine(Long planId) {
        return rcwlPrLineMapper.selectPrLine(planId);
    }

    /**
     * 把计划编号设置为空
     *
     * @param planId
     * @return
     */
    @Override
    public void updatePrLine(Long planId ,Long tenantId) {
        rcwlPrLineMapper.updatePrLine(planId,tenantId);
    }

    /**
     * 通过申请头id和行号查找行id
     *
     * @param prHeaderId
     * @param lineNum
     * @return
     */
    @Override
    public Long selectPrLineId(Long prHeaderId, String lineNum,Long tenantId) {
        return rcwlPrLineMapper.selectPrLineId(prHeaderId,lineNum,tenantId);
    }

    /**
     * 通过申请头和行号查找头行id
     *
     * @param prNum
     * @param lineNum
     * @param tenantId
     * @return
     */
    @Override
    public PrHeaderVO selectByNum(String prNum, String lineNum, Long tenantId) {
        return rcwlPrLineMapper.selectByNum(prNum,lineNum,tenantId);
    }

    @Override
    public List<org.srm.purchasecooperation.pr.domain.entity.PrLine> updateSourcePrLine(List<org.srm.purchasecooperation.pr.domain.entity.PrLine> prLines) {
        prLines.forEach(prLine -> {
            org.srm.purchasecooperation.pr.domain.entity.PrLine prLineOld = prLineMapper.selectByPrimaryKey(prLine);
            prLine.setObjectVersionNumber(prLineOld.getObjectVersionNumber());
            prLineMapper.updateOptional(prLine, "attributeBigint1");
        });
        return prLines;
    }

    /**
     * 通过id查找行记录
     *
     * @param prLineId
     * @return
     */
    @Override
    public PrLine selectPrLineRecord(Long prLineId) {
        return rcwlPrLineMapper.selectPrLineRecord(prLineId) ;
    }

    @Override
    public List<PrLineVO> selectPrLines(PageRequest pageRequest, Long tenantId, Long prHeaderId) {
        return PageHelper.doSort(pageRequest.getSort(), () -> {
            return this.rcwlPrLineMapper.listPrLines(tenantId, prHeaderId);
        });
    }
}
