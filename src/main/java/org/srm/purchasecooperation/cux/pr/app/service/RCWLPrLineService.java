package org.srm.purchasecooperation.cux.pr.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.pr.domain.vo.RCWLPrLineVO;
/**
 * 采购申请行应用服务
 *
 * @author bin.zhang06@hand-china.com 2021-03-16 15:49:15
 */
public interface RCWLPrLineService {
    /**
     * 分页查询采购申请行拓展
     *
     * @param pageRequest
     * @param tenantId
     * @param prHeaderId
     * @return
     */
    Page<RCWLPrLineVO> rCWLselectPrLinesPage(PageRequest pageRequest, Long tenantId, Long prHeaderId);
}
