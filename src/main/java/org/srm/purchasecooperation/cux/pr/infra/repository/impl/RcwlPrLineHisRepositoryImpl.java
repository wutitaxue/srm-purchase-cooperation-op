package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.apache.commons.collections.CollectionUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlPrLineHisRepository;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrLineHisMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 采购申请行(RcwlPrLineHis)资源库
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-02 14:04:41
 */
@Component
public class RcwlPrLineHisRepositoryImpl extends BaseRepositoryImpl<RcwlPrLineHis> implements RcwlPrLineHisRepository {
    @Resource
    private RcwlPrLineHisMapper rcwlPrLineHisMapper;

    @Override
    public List<RcwlPrLineHis> selectList(RcwlPrLineHis rcwlPrLineHis) {
        return rcwlPrLineHisMapper.selectList(rcwlPrLineHis);
    }

    @Override
    public RcwlPrLineHis selectByPrimary(Long prLineId) {
        RcwlPrLineHis rcwlPrLineHis = new RcwlPrLineHis();
        rcwlPrLineHis.setPrLineId(prLineId);
        List<RcwlPrLineHis> rcwlPrLineHiss = rcwlPrLineHisMapper.selectList(rcwlPrLineHis);
        if (CollectionUtils.isEmpty(rcwlPrLineHiss)) {
            return null;
        }
        return rcwlPrLineHiss.get(0);
    }

}
