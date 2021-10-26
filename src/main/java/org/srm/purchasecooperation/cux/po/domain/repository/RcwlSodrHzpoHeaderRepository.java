package org.srm.purchasecooperation.cux.po.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoHeaderDTO;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoHeader;

import java.util.List;

/**
 * 华住订单头资源库
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
public interface RcwlSodrHzpoHeaderRepository extends BaseRepository<RcwlSodrHzpoHeader> {
    /**
     * 分页查询华住订单头信息
     *
     * @param rcwlSodrHzpoHeaderDTO
     * @param pageRequest
     * @return
     */
    Page<RcwlSodrHzpoHeaderDTO> pagePoHeaderList(Long tenantId, RcwlSodrHzpoHeaderDTO rcwlSodrHzpoHeaderDTO, PageRequest pageRequest);

    /**
     * 导出华住订单头信息
     *
     * @param tenantId
     * @param rcwlSodrHzpoHeaderDTO
     * @return
     */
    List<RcwlSodrHzpoHeaderDTO> exportPoHeaderList(Long tenantId, RcwlSodrHzpoHeaderDTO rcwlSodrHzpoHeaderDTO);
}
