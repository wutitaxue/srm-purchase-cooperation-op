package org.srm.purchasecooperation.cux.accept.app.service;

import org.srm.purchasecooperation.accept.api.dto.AcceptListLineQueryDTO;
import org.srm.purchasecooperation.cux.accept.domain.vo.RCWLAcceptListLineVO;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface RCWLAcceptListHeaderService {
    Page<RCWLAcceptListLineVO> rcwlGetPageDetailAcceptList(AcceptListLineQueryDTO queryDTO, Long tenantId,
                    PageRequest pageRequest);

}
