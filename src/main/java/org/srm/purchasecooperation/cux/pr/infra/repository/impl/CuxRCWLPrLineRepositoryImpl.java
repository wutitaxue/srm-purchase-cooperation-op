package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.core.base.AopProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.cux.pr.infra.mapper.CuxRCWLPrLineMapper;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RCWLPrLineMapper;
import org.srm.purchasecooperation.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.pr.app.service.PrLineService;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.purchasecooperation.pr.infra.repository.impl.PrLineRepositoryImpl;
import org.srm.web.annotation.Tenant;

import java.util.List;

@Component
@Tenant(RCWLConstants.TENANT_CODE)
public class CuxRCWLPrLineRepositoryImpl extends PrLineRepositoryImpl implements AopProxy<PrLineService> {

    @Autowired
    private CuxRCWLPrLineMapper cuxRCWLPrLineMapper;

    @Autowired
    private RCWLPrLineMapper rcwlPrLineMapper;


    @Override
    public List<PrLineVO> pageAssignList(PrLineDTO prLineDTO) {
        List<PrLineVO> prLineVOList = this.cuxRCWLPrLineMapper.pageAssignList(prLineDTO);
        for(PrLineVO prLineVO : prLineVOList){
            prLineVO.setAttributeVarchar1(String.valueOf(prLineVO.getTaxIncludedLineAmount().doubleValue() - prLineVO.getAttributeDecimal1().doubleValue()));
        }
        return prLineVOList;
        //return this.cuxRCWLPrLineMapper.pageAssignList(prLineDTO);
    }
    @Override
    public List<PrLineVO> selectPrLines(PageRequest pageRequest, Long tenantId, Long prHeaderId) {
        return PageHelper.doSort(pageRequest.getSort(), () -> {
            return this.rcwlPrLineMapper.listPrLines(tenantId, prHeaderId);
        });
    }


}
