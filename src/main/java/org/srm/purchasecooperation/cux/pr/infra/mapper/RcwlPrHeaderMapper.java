package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.pr.api.dto.PrHeaderDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.vo.PrHeaderVO;
import org.srm.purchasecooperation.pr.infra.mapper.PrHeaderMapper;
import org.srm.web.annotation.Tenant;
import io.choerodon.mybatis.helper.ExtendMapper;

import java.util.List;
import java.util.Set;

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

    @Override
    List<PrHeader> selectHeaderAndLine(@Param("tenantId") Long tenantId, @Param("prNums") Set<String> prNums);

    void updateMsgResponse(PrHeader prHeader);
}
