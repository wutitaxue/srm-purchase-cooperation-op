package org.srm.purchasecooperation.cux.accept.domain.repository;

import org.srm.purchasecooperation.accept.api.dto.AcceptListLineQueryDTO;
import org.srm.purchasecooperation.cux.accept.domain.vo.RCWLAcceptListLineVO;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface RCWLAcceptListHeaderRepository {

    Page<RCWLAcceptListLineVO> rcwlGetPageDetailAcceptList(AcceptListLineQueryDTO queryDTO, PageRequest pageRequest);
}
