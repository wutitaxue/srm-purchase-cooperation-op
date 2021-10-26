package org.srm.purchasecooperation.cux.po.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoHeaderDTO;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoLineDTO;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoLine;

import java.util.List;

/**
 * 华住订单行资源库
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
public interface RcwlSodrHzpoLineRepository extends BaseRepository<RcwlSodrHzpoLine> {
    /**
     * 分页查询华住订单行信息
     *
     * @param rcwlSodrHzpoLineDTO
     * @param tenantId
     * @param pageRequest
     * @return
     */
    Page<RcwlSodrHzpoLineDTO> pagePoLineList(Long tenantId, RcwlSodrHzpoLineDTO rcwlSodrHzpoLineDTO, PageRequest pageRequest);

    /**
     * 导出华住订单行信息
     *
     * @param tenantId
     * @param rcwlSodrHzpoLineDTO
     * @return
     */
    List<RcwlSodrHzpoLineDTO> exportPoLineList(Long tenantId, RcwlSodrHzpoLineDTO rcwlSodrHzpoLineDTO);
}
