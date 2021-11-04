package org.srm.purchasecooperation.cux.pr.infra.mapper;


import io.choerodon.mybatis.helper.ExtendMapper;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.pr.api.dto.PrActionDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrAction;
import org.srm.purchasecooperation.pr.infra.mapper.PrActionMapper;
import org.srm.web.annotation.Tenant;

import java.util.List;

/**
 * @description 融创申请变更接口拓展
 * @author jie.wang05@hand-china.com
 * @since
 */
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public interface RcwlPrActionMapper extends PrActionMapper, ExtendMapper<PrAction> {

    /**
     * 融创变更记录查询拓展
     *
     * @param prAction
     * @return
     */
    @Override
    List<PrActionDTO> pagePrChangeAction(PrAction prAction);
}