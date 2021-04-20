package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.web.annotation.Tenant;
import org.srm.web.dynamic.ExtendMapper;

import java.util.List;

/**
 * 二开
 *
 * @author furong.tang@hand-china.com
 */
@Tenant
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

}
