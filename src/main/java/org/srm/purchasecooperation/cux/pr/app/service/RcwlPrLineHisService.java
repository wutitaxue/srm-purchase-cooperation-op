package org.srm.purchasecooperation.cux.pr.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;

import java.util.List;

/**
 * 采购申请行(RcwlPrLineHis)应用服务
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-02 14:04:41
 */
public interface RcwlPrLineHisService {

    /**
     * 查询数据
     *
     * @param pageRequest    分页参数
     * @param rcwlPrLineHiss 查询条件
     * @return 返回值
     */
    Page<RcwlPrLineHis> selectList(PageRequest pageRequest, RcwlPrLineHis rcwlPrLineHiss);

    /**
     * 保存数据
     *
     * @param rcwlPrLineHiss 数据
     */
    void saveData(List<RcwlPrLineHis> rcwlPrLineHiss);

}
