package org.srm.purchasecooperation.cux.pr.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;

import java.util.List;

/**
 * 采购申请行(RcwlPrLineHis)资源库
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-02 14:04:41
 */
public interface RcwlPrLineHisRepository extends BaseRepository<RcwlPrLineHis> {
    /**
     * 查询
     *
     * @param rcwlPrLineHis 查询条件
     * @return 返回值
     */
    List<RcwlPrLineHis> selectList(RcwlPrLineHis rcwlPrLineHis);

    /**
     * 根据主键查询（可关联表）
     *
     * @param prLineId 主键
     * @return 返回值
     */
    RcwlPrLineHis selectByPrimary(Long prLineId);
}
