package org.srm.purchasecooperation.cux.po.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoHeaderDTO;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoLineDTO;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoHeader;
import org.srm.purchasecooperation.cux.po.domain.repository.RcwlSodrHzpoHeaderRepository;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.po.infra.mapper.RcwlSodrHzpoHeaderMapper;
import org.srm.purchasecooperation.cux.po.infra.mapper.RcwlSodrHzpoLineMapper;
import org.srm.web.annotation.Tenant;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 华住订单头 资源库实现
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
@Component
@Tenant("SRM-RCWL")
public class RcwlSodrHzpoHeaderRepositoryImpl extends BaseRepositoryImpl<RcwlSodrHzpoHeader> implements RcwlSodrHzpoHeaderRepository {
    @Autowired
    private RcwlSodrHzpoHeaderMapper rcwlSodrHzpoHeaderMapper;
    @Autowired
    private RcwlSodrHzpoLineMapper rcwlSodrHzpoLineMapper;

    @Override
    public Page<RcwlSodrHzpoHeaderDTO> pagePoHeaderList(Long tenantId, RcwlSodrHzpoHeaderDTO rcwlSodrHzpoHeaderDTO, PageRequest pageRequest) {
        rcwlSodrHzpoHeaderDTO.setTenantId(tenantId);
        return PageHelper.doPageAndSort(pageRequest, () -> {
            return rcwlSodrHzpoHeaderMapper.pagePoHeaderList(rcwlSodrHzpoHeaderDTO);
        });
    }

    @Override
    @ProcessLovValue
    public List<RcwlSodrHzpoHeaderDTO> exportPoHeaderList(Long tenantId, RcwlSodrHzpoHeaderDTO rcwlSodrHzpoHeaderDTO) {
        rcwlSodrHzpoHeaderDTO.setTenantId(tenantId);
        List<RcwlSodrHzpoHeaderDTO> rcwlSodrHzpoHeaderDTOS = rcwlSodrHzpoHeaderMapper.pagePoHeaderList(rcwlSodrHzpoHeaderDTO);
        List<Long> poHeaderIds = rcwlSodrHzpoHeaderDTOS.stream().map(RcwlSodrHzpoHeaderDTO::getPoHeaderId).collect(Collectors.toList());
        List<RcwlSodrHzpoLineDTO> rcwlSodrHzpoLineDTOS = rcwlSodrHzpoLineMapper.pagePoLineList(RcwlSodrHzpoLineDTO.builder().poHeaderIds(poHeaderIds).tenantId(tenantId).build());
        rcwlSodrHzpoHeaderDTOS.forEach(rcwlSodrHzpoHeader -> rcwlSodrHzpoHeader.setRcwlSodrHzpoLineDTOS(rcwlSodrHzpoLineDTOS.stream().filter(rcwlSodrHzpoLineDTO -> rcwlSodrHzpoHeader.getPoHeaderId().equals(rcwlSodrHzpoLineDTO.getPoHeaderId())).collect(Collectors.toList())));
        return rcwlSodrHzpoHeaderDTOS;
    }
}
