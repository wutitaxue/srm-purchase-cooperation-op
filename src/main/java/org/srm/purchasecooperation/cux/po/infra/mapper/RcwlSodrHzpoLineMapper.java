package org.srm.purchasecooperation.cux.po.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoLineDTO;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoLine;

import java.util.List;

/**
 * 华住订单行Mapper
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
public interface RcwlSodrHzpoLineMapper extends BaseMapper<RcwlSodrHzpoLine> {
    /**
     * 分页查询华住订单行信息
     *
     * @param tenantId
     * @param rcwlSodrHzpoLineDTO
     * @return
     */
    List<RcwlSodrHzpoLineDTO> pagePoLineList(Long tenantId, RcwlSodrHzpoLineDTO rcwlSodrHzpoLineDTO);
}
