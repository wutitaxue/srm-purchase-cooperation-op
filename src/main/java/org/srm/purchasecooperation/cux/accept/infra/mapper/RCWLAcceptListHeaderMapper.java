package org.srm.purchasecooperation.cux.accept.infra.mapper;

import java.util.List;

import org.srm.purchasecooperation.accept.api.dto.AcceptListLineQueryDTO;
import org.srm.purchasecooperation.cux.accept.domain.vo.RCWLAcceptListLineVO;

public interface RCWLAcceptListHeaderMapper {
    List<RCWLAcceptListLineVO> rcwlGetPageDetailAcceptList(AcceptListLineQueryDTO queryDTO);
}
