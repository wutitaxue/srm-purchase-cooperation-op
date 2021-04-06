package org.srm.purchasecooperation.cux.transaction.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.asn.api.dto.AsnHeaderDTO;
import org.srm.purchasecooperation.asn.domain.entity.AsnHeader;
import org.srm.purchasecooperation.asn.domain.vo.AsnLineVO;
import org.srm.purchasecooperation.asn.infra.mapper.AsnHeaderMapper;
import org.srm.purchasecooperation.asn.infra.mapper.AsnLineMapper;
import org.srm.purchasecooperation.cux.transaction.api.dto.RCWLReceiveTransactionLineDTO;
import org.srm.purchasecooperation.cux.transaction.app.service.RCWLRcvTrxLineService;
import org.srm.purchasecooperation.cux.transaction.domain.repository.RCWLRcvtrxLineRepository;
import org.srm.purchasecooperation.cux.transaction.infra.constant.RCWLTransactionConstant;
import org.srm.purchasecooperation.transaction.api.dto.RctTrxAsnDetailDTO;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;
import org.srm.purchasecooperation.transaction.app.service.impl.RcvTrxLineServiceImpl;
import org.srm.purchasecooperation.transaction.infra.mapper.RcvTrxAsnDetailMapper;
import org.srm.web.annotation.Tenant;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

@Service
@Tenant(RCWLTransactionConstant.TENANT_NUMBER)
public class RCWLRcvtrxLineServiceImpl extends RcvTrxLineServiceImpl implements RCWLRcvTrxLineService {
    @Autowired
    private RCWLRcvtrxLineRepository rcvTrxLineRepository;
    @Autowired
    private AsnLineMapper asnLineMapper;
    @Autowired
    private AsnHeaderMapper asnHeaderMapper;
    @Autowired
    private RcvTrxAsnDetailMapper rcvTrxAsnDetailMapper;

    @Override
    public Page<RCWLReceiveTransactionLineDTO> rcwlQueryReceiveTransactionLineForPurchase(
                    ReceiveTransactionLineDTO queryParam, PageRequest pageRequest) {
        if (queryParam != null) {
            queryParam.initTrxDateFromForQuery().initTrxDateToForQuery();
        }

        return this.queryAsn(queryParam, pageRequest, "PURCHASER");
    }

    private Page<RCWLReceiveTransactionLineDTO> queryAsn(ReceiveTransactionLineDTO queryParam, PageRequest pageRequest,
                    String temp) {
        if (!Objects.isNull(queryParam.getAsnNum())) {
            Page<RCWLReceiveTransactionLineDTO> asnPageResults = new Page();
            AsnHeaderDTO asnHeaderDTO = new AsnHeaderDTO();
            asnHeaderDTO.setTenantId(queryParam.getTenantId());
            asnHeaderDTO.setAsnNum(queryParam.getAsnNum());
            List<AsnHeader> asnHeaderVO = this.asnHeaderMapper.queryByAsnNum(asnHeaderDTO);
            new ArrayList();
            if (asnHeaderVO != null && asnHeaderVO.size() > 0) {
                List<AsnLineVO> asnLineVOs = this.asnLineMapper
                                .selectAsnLinesByHeaderId(((AsnHeader) asnHeaderVO.get(0)).getAsnHeaderId());
                List<Long> searchAsnLineIds =
                                (List) asnLineVOs.stream().map(AsnLineVO::getAsnLineId).collect(Collectors.toList());
                List<RctTrxAsnDetailDTO> rcvTrxAsnDetailList = this.rcvTrxAsnDetailMapper
                                .selectRcvAsnDetailByAsnLines(queryParam.getTenantId(), searchAsnLineIds);
                if (BigDecimal.ZERO.intValue() < rcvTrxAsnDetailList.size()) {
                    Set<Long> rcvTrxLineIds = (Set) rcvTrxAsnDetailList.stream()
                                    .map(RctTrxAsnDetailDTO::getRcvTrxLineId).collect(Collectors.toSet());
                    queryParam.setRcvTrxLineIds(rcvTrxLineIds);
                    queryParam.setAsnNum((String) null);
                    asnPageResults = this.judgeUserCampCode(pageRequest, queryParam, temp);
                } else {
                    queryParam.setAsnHeaderId(((AsnHeader) asnHeaderVO.get(0)).getAsnHeaderId());
                    asnPageResults = this.judgeUserCampCode(pageRequest, queryParam, temp);
                }
            }

            return asnPageResults;
        } else {
            return this.judgeUserCampCode(pageRequest, queryParam, temp);
        }
    }

    private Page<RCWLReceiveTransactionLineDTO> judgeUserCampCode(PageRequest pageRequest,
                    ReceiveTransactionLineDTO queryParam, String temp) {
        Page<RCWLReceiveTransactionLineDTO> pageResults = new Page();
        if ("PURCHASER".equals(temp)) {
            pageResults = PageHelper.doPageAndSort(pageRequest, () -> {
                return this.rcvTrxLineRepository.rcwlQueryReceiveTransactionLineForPurchase(queryParam);
            });
        } else if ("SUPPLIER".equals(temp)) {
            pageResults = PageHelper.doPageAndSort(pageRequest, () -> {
                return this.rcvTrxLineRepository.rcwlQueryReceiveTransactionLineForPurchase(queryParam);
            });
        }

        pageResults.forEach((pageResult) -> {
            if (BaseConstants.Flag.YES.equals(pageResult.getReturnedFlag())
                            && BaseConstants.Flag.YES.equals(pageResult.getNewSinvFlag())) {
                pageResult.setQuantity(BigDecimal.ZERO.subtract(pageResult.getQuantity()));
                pageResult.setNetAmount(BigDecimal.ZERO.subtract(pageResult.getNetAmount()));
            }

        });
        return pageResults;
    }

}
