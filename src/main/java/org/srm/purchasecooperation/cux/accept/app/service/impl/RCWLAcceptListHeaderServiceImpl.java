package org.srm.purchasecooperation.cux.accept.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.accept.api.dto.AcceptListLineQueryDTO;
import org.srm.purchasecooperation.accept.app.service.impl.AcceptListHeaderServiceImpl;
import org.srm.purchasecooperation.asn.infra.utils.IdsExportConvert;
import org.srm.purchasecooperation.cux.accept.app.service.RCWLAcceptListHeaderService;
import org.srm.purchasecooperation.cux.accept.domain.repository.RCWLAcceptListHeaderRepository;
import org.srm.purchasecooperation.cux.accept.domain.vo.RCWLAcceptListLineVO;
import org.srm.purchasecooperation.cux.accept.infra.constant.RCWLAcceptConstant;
import org.srm.web.annotation.Tenant;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

@Service
@Tenant(RCWLAcceptConstant.TENANT_NUMBER)
public class RCWLAcceptListHeaderServiceImpl extends AcceptListHeaderServiceImpl
                implements RCWLAcceptListHeaderService {
    @Autowired
    private RCWLAcceptListHeaderRepository acceptListHeaderRepository;

    @Override
    public Page<RCWLAcceptListLineVO> rcwlGetPageDetailAcceptList(AcceptListLineQueryDTO queryDTO, Long tenantId,
                    PageRequest pageRequest) {
        queryDTO.setAcceptListLineIdList(IdsExportConvert.stringToList(queryDTO.getAcceptListLineIds()));
        queryDTO.setTenantId(tenantId);
        return acceptListHeaderRepository.rcwlGetPageDetailAcceptList(queryDTO, pageRequest);
    }
}
