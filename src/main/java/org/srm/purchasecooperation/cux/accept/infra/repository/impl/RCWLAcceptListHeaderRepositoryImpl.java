package org.srm.purchasecooperation.cux.accept.infra.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.accept.api.dto.AcceptListLineQueryDTO;
import org.srm.purchasecooperation.accept.domain.vo.AcceptListLineVO;
import org.srm.purchasecooperation.accept.infra.repository.impl.AcceptListHeaderRepositoryImpl;
import org.srm.purchasecooperation.cux.accept.domain.repository.RCWLAcceptListHeaderRepository;
import org.srm.purchasecooperation.cux.accept.domain.vo.RCWLAcceptListLineVO;
import org.srm.purchasecooperation.cux.accept.infra.constant.RCWLAcceptConstant;
import org.srm.purchasecooperation.cux.accept.infra.mapper.RCWLAcceptListHeaderMapper;
import org.srm.web.annotation.Tenant;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

@Component
@Tenant(RCWLAcceptConstant.TENANT_NUMBER)
public class RCWLAcceptListHeaderRepositoryImpl extends AcceptListHeaderRepositoryImpl
                implements RCWLAcceptListHeaderRepository {
    @Autowired
    private RCWLAcceptListHeaderMapper acceptListHeaderMapper;

    /**
     * 查询验收单明细
     *
     * @param queryDTO 查询DTO
     * @param pageRequest 分页参数
     * @return Page<RCWLAcceptListLineVO>
     */
    @Override
    public Page<RCWLAcceptListLineVO> rcwlGetPageDetailAcceptList(AcceptListLineQueryDTO queryDTO,
                    PageRequest pageRequest) {
        return PageHelper.doPageAndSort(pageRequest, () -> {
            List<RCWLAcceptListLineVO> list = acceptListHeaderMapper.rcwlGetPageDetailAcceptList(queryDTO);
            if (list != null && list.size() > 0) {
                for (AcceptListLineVO acceptListLineVO : list) {
                    if (acceptListLineVO.getSourceCode() != null) {
                        acceptListLineVO.setPoHeaderNum(acceptListLineVO.getSourceCode());
                    }
                    if (acceptListLineVO.getSourceLineNum() != null) {
                        acceptListLineVO.setPoLineNum(acceptListLineVO.getSourceLineNum());
                    }
                }
            }
            return list;
        });
    }
}
