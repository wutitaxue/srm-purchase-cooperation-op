package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.vo.PoLineLocationVO;

import java.util.List;

/**
 * @author pengxu.zhi@hand-china.com
 */
@Component
public interface RcwlPoLineLocationMapper {

    List<PoLineLocationVO> selectPoLineLocation(PoLineLocation poLineLocation);

}
