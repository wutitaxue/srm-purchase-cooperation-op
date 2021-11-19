package org.srm.purchasecooperation.cux.pr.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.util.List;

/**
 * 采购申请行(RcwlPrLineHis)资源库
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-02 14:04:41
 */
public interface RcwlPrLineHisRepository extends BaseRepository<RcwlPrLineHis> {
    /**
     * 查询(当前最新版本历史数据)
     *
     * @param rcwlPrLineHis 查询条件
     * @return 返回值
     */
    List<PrLine> selectList(RcwlPrLineHis rcwlPrLineHis);
}
