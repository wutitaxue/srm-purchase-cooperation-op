package org.srm.purchasecooperation.cux.po.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.po.api.dto.RcwlSodrHzpoLineDTO;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoLine;
import org.srm.purchasecooperation.cux.po.domain.repository.RcwlSodrHzpoLineRepository;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.po.infra.mapper.RcwlSodrHzpoLineMapper;
import org.srm.web.annotation.Tenant;

import java.util.List;

/**
 * 华住订单行 资源库实现
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
@Component
@Tenant("SRM-RCWL")
public class RcwlSodrHzpoLineRepositoryImpl extends BaseRepositoryImpl<RcwlSodrHzpoLine> implements RcwlSodrHzpoLineRepository {
    @Autowired
    private RcwlSodrHzpoLineMapper rcwlSodrHzpoLineMapper;

    @Override
    public Page<RcwlSodrHzpoLineDTO> pagePoLineList(Long tenantId, RcwlSodrHzpoLineDTO rcwlSodrHzpoLineDTO, PageRequest pageRequest) {
        rcwlSodrHzpoLineDTO.setTenantId(tenantId);
        return PageHelper.doPageAndSort(pageRequest, () -> {
            return rcwlSodrHzpoLineMapper.pagePoLineList(rcwlSodrHzpoLineDTO);
        });
    }

    @Override
    public List<RcwlSodrHzpoLineDTO> exportPoLineList(Long tenantId, RcwlSodrHzpoLineDTO rcwlSodrHzpoLineDTO) {
        rcwlSodrHzpoLineDTO.setTenantId(tenantId);
        return rcwlSodrHzpoLineMapper.pagePoLineList(rcwlSodrHzpoLineDTO);
    }
}
