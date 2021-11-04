package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.apache.commons.collections.CollectionUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlPrLineHisRepository;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrLineHisMapper;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

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
    public List<PrLine> selectList(RcwlPrLineHis rcwlPrLineHis) {
        return rcwlPrLineHisMapper.selectList(rcwlPrLineHis);
    }

}
