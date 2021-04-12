package org.srm.purchasecooperation.cux.acp.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import javassist.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpUserDataDTO;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpUserDataService;
import org.srm.purchasecooperation.cux.acp.domain.entity.RCWLAcpInvoiceData;
import org.srm.purchasecooperation.cux.acp.domain.repository.RCWLAcpUserDataRepository;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.web.annotation.Tenant;
import scala.reflect.internal.Trees;

/**
 * @author lu.cheng01@hand-china.com
 * @description 获取发票以及人员信息
 * @date 2021/4/8 15:42
 * @version:1.0
 */
@Service
@Tenant(rcwlActConstant.TENANT_NUMBER)
public class RCWLAcpUserDataServiceImpl implements RCWLAcpUserDataService {
    private static final Logger logger = LoggerFactory.getLogger(Loader.class);
//    @Autowired
    private RCWLAcpUserDataRepository rcwlAcpUserDataRepository;

    /**
     * 获取发票以及人员信息
     */
    @Override
    public RCWLAcpUserDataDTO acpGetData(RCWLAcpInvoiceData rcwlAcpInvoiceData) {
        String documentType = rcwlAcpInvoiceData.getDocumentType();
        String systemSource = rcwlAcpInvoiceData.getSystemSource();
        Long tenantId = DetailsHelper.getUserDetails().getTenantId();
        RCWLAcpUserDataDTO rcwlAcpUserDataDTO = new RCWLAcpUserDataDTO();
        /**
         * 判断单据类型是否存在：01.供方形式发票，02.购方形式发票，03.付款申请单
         */
        if (!(RCWLAcpConstant.INVOICE_TYPE_VENDOR.equals(documentType) || RCWLAcpConstant.INVOICE_TYPE_COMPANY.equals(documentType) || RCWLAcpConstant.INVOICE_TYPE_PAY.equals(documentType))) {
            throw new CommonException(RCWLAcpConstant.ERROR.INVOICE_TYPE_ERROR);
        }
        //如果为融创内部员工，员工号取员工账号，如果为供应商则去供应商编码
        if (RCWLAcpConstant.CGXT_SUNAC.equals(systemSource)) {
            rcwlAcpUserDataDTO.setUserId(DetailsHelper.getUserDetails().getUsername());
        } else if (RCWLAcpConstant.CGXT_SUPPLIER.equals(systemSource)) {
            rcwlAcpUserDataDTO = rcwlAcpUserDataRepository.acpGetData(tenantId, rcwlAcpInvoiceData.getDocumentNumber());
        } else {
            throw new CommonException(RCWLAcpConstant.ERROR.SYSTEM_SOURCE_ERROR);
        }
        return rcwlAcpUserDataDTO;
    }
}
