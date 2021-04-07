package org.srm.purchasecooperation.cux.accept.domain.repository;

import org.srm.purchasecooperation.accept.api.dto.AcceptListLineQueryDTO;
import org.srm.purchasecooperation.cux.accept.domain.vo.RCWLAcceptListLineVO;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface RCWLAcceptListHeaderRepository {

    /**
     * 查询验收单明细
     * 
     * @param queryDTO 查询DTO
     * @param pageRequest 分页参数
     * @return Page<RCWLAcceptListLineVO>
     */
    Page<RCWLAcceptListLineVO> rcwlGetPageDetailAcceptList(AcceptListLineQueryDTO queryDTO, PageRequest pageRequest);
}
