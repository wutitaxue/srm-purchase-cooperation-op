package org.srm.purchasecooperation.cux.accept.app.service;

import org.srm.purchasecooperation.accept.api.dto.AcceptListLineQueryDTO;
import org.srm.purchasecooperation.cux.accept.domain.vo.RCWLAcceptListLineVO;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface RCWLAcceptListHeaderService {
<<<<<<< HEAD
    /**
     * 查询验收单明细
     *
     * @param queryDTO 查询DTO
     * @param tenantId 租户id
     * @param pageRequest 分页参数
     * @return Page<RCWLAcceptListLineVO>
     */
=======
>>>>>>> origin/feature-srm-56060
    Page<RCWLAcceptListLineVO> rcwlGetPageDetailAcceptList(AcceptListLineQueryDTO queryDTO, Long tenantId,
                    PageRequest pageRequest);

}
