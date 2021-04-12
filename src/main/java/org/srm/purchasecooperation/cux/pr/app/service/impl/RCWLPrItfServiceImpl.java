package org.srm.purchasecooperation.cux.pr.app.service.impl;

import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrLineDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrLineDetailDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.util.List;

/**
 * @description:接口
 * @author: bin.zhang
 * @createDate: 2021/4/10 13:57
 */
@Service
public class RCWLPrItfServiceImpl implements RCWLPrItfService {
    /**
     * 预算占用释放接口
     * @param prHeader
     * @param tenantId
     */
    @Override
    public void invokeBudget(PrHeader prHeader, Long tenantId) {
       //获取接口所需数据
        RCWLItfPrLineDTO rcwlItfPrLineDTO = RCWLItfPrLineDTO.initOccupy(prHeader,tenantId);
        List<PrLine> lineDetailList = prHeader.getPrLineList();
        lineDetailList.forEach(prDetailLine -> {

        });


    }
}
