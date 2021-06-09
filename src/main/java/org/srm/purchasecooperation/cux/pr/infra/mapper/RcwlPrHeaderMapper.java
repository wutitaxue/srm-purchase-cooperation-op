package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.pr.api.dto.PrHeaderDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.vo.PrHeaderVO;
import org.srm.purchasecooperation.pr.infra.mapper.PrHeaderMapper;
import org.srm.web.annotation.Tenant;
import org.srm.web.dynamic.ExtendMapper;

import java.util.List;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/4/13 20:26
 * @version:1.0
 */
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public interface RcwlPrHeaderMapper extends PrHeaderMapper, ExtendMapper<PrHeader> {
    @Override
    List<PrHeaderVO> selectPrHeaderDetail(@Param("tenantId") Long tenantId, @Param("prHeaderId") Long prHeaderId);

    @Override
    List<PrHeaderVO> selectWorkbenchPrSummaries(PrHeaderDTO prHeaderDTO);


    @Override
    List<PrHeaderVO> selectCancellablePr(PrHeaderDTO prHeaderDTO);
}
