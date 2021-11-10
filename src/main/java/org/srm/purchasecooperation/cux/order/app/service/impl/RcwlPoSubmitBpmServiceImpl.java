package org.srm.purchasecooperation.cux.order.app.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoBudgetItfService;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoSubmitBpmService;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;

import java.util.Date;

/**
 * @author pengxu.zhi@hand-china.com
 * @desc ...
 * @date 2021-11-02 15:26:14
 */
@Slf4j
@Service
public class RcwlPoSubmitBpmServiceImpl implements RcwlPoSubmitBpmService {

    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private RcwlPoBudgetItfService rcwlPoBudgetItfService;
    @Autowired
    private PoHeaderRepository poHeaderRepository;

    @Override
    public void rcwlSubmitBpmSuccessed(Long tenantId, String poNum, String procInstID, String newProcURL) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);
        //SODR.PO_STATUS
        poHeader.setAttributeVarchar36(procInstID);
        poHeader.setAttributeVarchar37(newProcURL);
        poHeader.setStatusCode("SUBMITTED");
        poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode","attributeVarchar36","attributeVarchar37"});
    }

    @Override
    public void rcwlSubmitBpmApproved(Long tenantId, String poNum) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);
        //获取自动发布配置
        String manualPublicFlag = this.poHeaderRepository.getPoConfigCodeValue(tenantId, poHeader.getPoHeaderId(), "SITE.SPUC.PO.MANUAL_PUBLISH");
        log.info("订单自动发布配置信息：{}",manualPublicFlag);

        //SODR.PO_STATUS
        poHeader.setApprovedFlag(1);
        poHeader.setApprovedDate(new Date());
        if (StringUtils.isNotEmpty(manualPublicFlag) && "1".equals(manualPublicFlag)) {
            poHeader.setStatusCode("APPROVED");
        } else {
            poHeader.setStatusCode("PUBLISHED");
        }
        poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode","approvedFlag","approvedDate"});
    }

    @SneakyThrows
    @Override
    public void rcwlSubmitBpmReject(Long tenantId, String poNum) {
        PoHeader poHeaderCondition = new PoHeader();
        poHeaderCondition.setPoNum(poNum);
        poHeaderCondition.setTenantId(tenantId);
        PoHeader poHeader = poHeaderMapper.selectOne(poHeaderCondition);
        //SODR.PO_STATUS
        poHeader.setStatusCode("REJECTED");
        poHeaderMapper.updateOptional(poHeader, new String[]{"statusCode"});

        PoDTO poDTO = new PoDTO();
        BeanUtils.copyProperties(poHeader,poDTO);
        //调用占预算接口释放预算，占用标识（01占用，02释放），当前释放逻辑：占用金额固定为0，清空占用金额
        rcwlPoBudgetItfService.invokeBudgetOccupy(poDTO, poDTO.getTenantId(), "02");
    }

}
