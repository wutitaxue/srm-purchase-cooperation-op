package org.srm.purchasecooperation.cux.po.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoHeaderDTO;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoHeader;

import java.util.List;

/**
 * 华住订单头Mapper
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
public interface RcwlSodrHzpoHeaderMapper extends BaseMapper<RcwlSodrHzpoHeader> {
    /**
     * 分页查询华住订单头信息
     *
     * @param tenantId
     * @param rcwlSodrHzpoHeaderDTO
     * @return
     */
    List<RcwlSodrHzpoHeaderDTO> pagePoHeaderList(Long tenantId, RcwlSodrHzpoHeaderDTO rcwlSodrHzpoHeaderDTO);
}
