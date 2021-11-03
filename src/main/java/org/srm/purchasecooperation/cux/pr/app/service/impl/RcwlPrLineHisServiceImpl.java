package org.srm.purchasecooperation.cux.pr.app.service.impl;


import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrLineHisService;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlPrLineHisRepository;

import java.util.List;

/**
 * 采购申请行(RcwlPrLineHis)应用服务
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-02 14:04:42
 */
@Service
public class RcwlPrLineHisServiceImpl implements RcwlPrLineHisService {
    @Autowired
    private RcwlPrLineHisRepository rcwlPrLineHisRepository;

    @Override
    public Page<RcwlPrLineHis> selectList(PageRequest pageRequest, RcwlPrLineHis rcwlPrLineHis) {
        return PageHelper.doPageAndSort(pageRequest, () -> rcwlPrLineHisRepository.selectList(rcwlPrLineHis));
    }

    @Override
    public void saveData(List<RcwlPrLineHis> rcwlPrLineHiss) {
        rcwlPrLineHiss.forEach(item -> {
            if (item.getPrLineId() == null) {
                rcwlPrLineHisRepository.insertSelective(item);
            } else {
                rcwlPrLineHisRepository.updateByPrimaryKeySelective(item);
            }
        });
    }
}
