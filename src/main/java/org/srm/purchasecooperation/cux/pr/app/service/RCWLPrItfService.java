package org.srm.purchasecooperation.cux.pr.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.srm.purchasecooperation.pr.api.dto.PrLineDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrHeaderDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;

import java.util.List;

public interface RCWLPrItfService {

    /**
     * 预算占用接口
     * @param prHeader
     * @param tenantId
     */
    void invokeBudgetOccupy(PrHeader prHeader, Long tenantId) throws JsonProcessingException;

    /**
     * 初始化请求头
     * @return
     */
    RCWLItfPrHeaderDTO initOccupyHeader();

    /**
     *获取数据
     * @param prHeader
     * @param tenantId
     * @param flag(占用释放标识)
     * @return
     */
    RCWLItfPrHeaderDTO getBudgetAccountItfData(PrHeader prHeader, Long tenantId, String flag);

    /**
     * 获取token
     * @return
     */
    String getToken();

    /**
     * 审批拒绝调用接口
     * @param prHeaderList
     * @param tenantId
     */
    void invokeBudgetList(List<PrHeader> prHeaderList, Long tenantId);

    /**
     * 释放接口
     * @param prHeader
     * @param tenantId
     */
    void invokeBudgetRelease(PrHeader prHeader, Long tenantId) throws JsonProcessingException;

    /**
     * 行关闭调用接口
     * @param prLineVOS
     * @param tenantId
     */
    void linesClose(List<PrLineVO> prLineVOS, Long tenantId) throws JsonProcessingException;

    /**
     * 将指定行数据封装成报文
     * @param prLineVOS
     * @param tenantId
     * @param flag
     * @return
     */
    RCWLItfPrHeaderDTO getBudgetItfDataLine(List<PrLineVO> prLineVOS, Long tenantId, String flag);

    /**
     * 勾选行取消触发接口（单行）
     * @param prLineDTO
     * @param tenantId
     */

    void linesCancel(PrLineDTO prLineDTO, Long tenantId) throws JsonProcessingException;

    /**
     * 获取行数据
     * @param prLineDTO
     * @param tenantId
     * @param r
     * @return
     */
    RCWLItfPrHeaderDTO getBudgetItfDataLineDTO(PrLineDTO prLineDTO, Long tenantId, String r);

    /**
     * 预算变更提交接口
     * @param prHeader
     * @param tenantId
     */
    void submitChange(PrHeader prHeader, Long tenantId) throws JsonProcessingException;
}
