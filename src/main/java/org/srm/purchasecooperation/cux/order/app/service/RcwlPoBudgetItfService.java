package org.srm.purchasecooperation.cux.order.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrHeaderDTO;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.vo.PoLineVO;
import org.srm.purchasecooperation.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;

import java.util.List;

public interface RcwlPoBudgetItfService {

    /**
     * 预算占用接口
     * @param poDTO
     * @param tenantId
     */
    void invokeBudgetOccupy(PoDTO poDTO, Long tenantId, String occupyFlag) throws JsonProcessingException;

    /**
     * 初始化请求头
     * @return
     */
    RCWLItfPrHeaderDTO initOccupyHeader();

//    /**
//     *获取数据
//     * @param poDTO
//     * @param tenantId
//     * @param flag(占用释放标识)
//     * @return
//     */
//    RCWLItfPrHeaderDTO getBudgetAccountItfData(PoDTO poDTO, Long tenantId, String flag);
    /**
     *获取数据
     * @param poDTO
     * @param tenantId
     * @param flag(占用释放标识)
     * @return
     */
    RCWLItfPrHeaderDTO getBudgetAccountItfData1(PoDTO poDTO, Long tenantId, String flag);

    /**
     * 获取token
     * @return
     */
    String getToken();


//    /**
//     * 将指定行数据封装成报文
//     * @param poLineVOS
//     * @param tenantId
//     * @param flag
//     * @return
//     */
//    RCWLItfPrHeaderDTO getBudgetItfDataLine(List<PoLineVO> poLineVOS, Long tenantId, String flag);
//
//
//    /**
//     * 获取行数据
//     * @param prLineDTO
//     * @param tenantId
//     * @param r
//     * @return
//     */
//    RCWLItfPrHeaderDTO getBudgetItfDataLineDTO(PrLineDTO prLineDTO, Long tenantId, String r);

}
