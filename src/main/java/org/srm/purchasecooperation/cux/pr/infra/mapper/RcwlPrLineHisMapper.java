package org.srm.purchasecooperation.cux.pr.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;

import java.util.List;

/**
 * 采购申请行(RcwlPrLineHis)应用服务
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-02 14:04:40
 */
public interface RcwlPrLineHisMapper extends BaseMapper<RcwlPrLineHis> {
    /**
     * 基础查询
     *
     * @param rcwlPrLineHis 查询条件
     * @return 返回值
     */
    List<RcwlPrLineHis> selectList(RcwlPrLineHis rcwlPrLineHis);
}
