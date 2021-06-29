package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.web.annotation.Tenant;
import io.choerodon.mybatis.helper.ExtendMapper;

import java.util.List;

/**
 * 二开
 *
 * @author furong.tang@hand-china.com
 */
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public interface CuxRCWLPrLineMapper extends PrLineMapper, ExtendMapper<PrLine> {

    /**
     * 新增查询条件
     * 查询采购订单行
     *
     * @param prLineDTO 查询条件
     * @return List<PrLineVO>
     */
    @Override
    List<PrLineVO> pageAssignList(PrLineDTO prLineDTO);

    /**
     *
     * @param prLineDTO
     * @return
     */
    @Override
    List<PrLineVO> cancelList(PrLineDTO prLineDTO);
}
