package org.srm.purchasecooperation.cux.acp.app.service.impl;

import com.alibaba.fastjson.JSON;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import javassist.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.acp.api.dto.*;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpOcrService;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpUserDataService;
import org.srm.purchasecooperation.cux.acp.domain.entity.RCWLAcpInvoiceData;
import org.srm.purchasecooperation.cux.acp.domain.entity.RCWLAcpInvoiceHeaderData;
import org.srm.purchasecooperation.cux.acp.domain.repository.RCWLAcpInvoiceHeaderRepository;
import org.srm.purchasecooperation.cux.acp.domain.repository.RCWLAcpUserDataRepository;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.acp.infra.utils.RCWLElephantUtil;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.web.annotation.Tenant;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author lu.cheng01@hand-china.com
 * @description 获取发票以及人员信息
 * @date 2021/4/8 15:42
 * @version:1.0
 */
@Service
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public class RCWLAcpOcrServiceImpl implements RCWLAcpOcrService {
    private static final Logger logger = LoggerFactory.getLogger(Loader.class);
    @Autowired
    private RCWLAcpUserDataRepository rcwlAcpUserDataRepository;

    @Autowired
    private RCWLAcpInvoiceHeaderRepository rcwlAcpInvoiceHeaderRepository;

    /**
     * 获取发票以及人员信息
     */
    @Override
    public RCWLAcpOcrDTO acpGetData(RCWLAcpInvoiceData rcwlAcpInvoiceData) {
        String documentType = rcwlAcpInvoiceData.getDocumentType();
        String systemSource = rcwlAcpInvoiceData.getSystemSource();
        Long tenantId = DetailsHelper.getUserDetails().getTenantId();
        RCWLAcpUserDataDTO rcwlAcpUserDataDTO = new RCWLAcpUserDataDTO();
        RCWLAcpOcrDTO rcwlAcpOcrDTO = new RCWLAcpOcrDTO();
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
        rcwlAcpUserDataDTO.setAppSecId(RCWLAcpConstant.APPSECID);
        rcwlAcpUserDataDTO.setDocumentNumber(rcwlAcpInvoiceData.getDocumentNumber());
        rcwlAcpUserDataDTO.setDocumentType(documentType);
        rcwlAcpUserDataDTO.setSystemSource(systemSource);
        logger.info("rcwlAcpUserDataDTO:" + JSON.toJSONString(rcwlAcpUserDataDTO));
        //base64加密
        rcwlAcpOcrDTO.setUrl(RCWLAcpConstant.URL + Base64.getEncoder().encodeToString(JSON.toJSONString(rcwlAcpUserDataDTO).getBytes(Charset.defaultCharset())));
        return rcwlAcpOcrDTO;
    }

    /**
     * 获取发票更新状态的请求加密数据
     *
     * @param InvoiceHeaderId
     * @return
     */
    @Override
    public RCWLAcpInvoiceElephantRequestDataDTO acpInvoiceELephantGRequestData(Long InvoiceHeaderId) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Long tenantId = DetailsHelper.getUserDetails().getTenantId();

        RCWLElephantUtil rcwlElephantUtil = new RCWLElephantUtil();

        RCWLAcpInvoiceElephantRequestDataDTO rcwlAcpInvoiceElephantRequestDataDTO = new RCWLAcpInvoiceElephantRequestDataDTO();

        //获取发票头数据
        RCWLAcpInvoiceHeaderData rcwlAcpInvoiceHeaderData = rcwlAcpInvoiceHeaderRepository.getAcpInvoiceHeaderData(tenantId, InvoiceHeaderId);

        if (rcwlAcpInvoiceHeaderData.getTaxCount() == 0) {
//            不能抛异常
//            throw new CommonException(RCWLAcpConstant.ERROR.INVOCIE_TAX_LINE_ERROR);
        } else {
            RCWLAcpInvoiceElephantAuthorizeDTO rcwlAcpInvoiceElephantAuthorizeDTO = new RCWLAcpInvoiceElephantAuthorizeDTO();
            RCWLAcpInvoiceElephantGlobalInfoDTO rcwlAcpInvoiceElephantGlobalInfoDTO = new RCWLAcpInvoiceElephantGlobalInfoDTO();
            RCWLAcpInvoiceElephantDataDTO rcwlAcpInvoiceElephantDataDTO = new RCWLAcpInvoiceElephantDataDTO();

            rcwlAcpInvoiceElephantAuthorizeDTO.setAppSecId(RCWLAcpConstant.APPSECID);

            rcwlAcpInvoiceElephantGlobalInfoDTO.setAppId(RCWLAcpConstant.AcpInvoiceGlobalInfo.APPID);
            rcwlAcpInvoiceElephantGlobalInfoDTO.setVersion(RCWLAcpConstant.AcpInvoiceGlobalInfo.VERSION);
            rcwlAcpInvoiceElephantGlobalInfoDTO.setInterfaceCode(RCWLAcpConstant.AcpInvoiceGlobalInfo.INTERFACECODE);

            rcwlAcpInvoiceElephantDataDTO.setDocumentNumber(rcwlAcpInvoiceHeaderData.getDocumentNumber());

            //获取发票状态并转化为大象的状态编码
            rcwlAcpInvoiceElephantDataDTO.setStatus(RCWLAcpConstant.AcpInvoiceStatus.status.get(rcwlAcpInvoiceHeaderData.getStatus()));

            logger.info("查询出来的单据状态：" + rcwlAcpInvoiceHeaderData.getStatus());
            logger.info("状态：" + RCWLAcpConstant.AcpInvoiceStatus.status.get(rcwlAcpInvoiceHeaderData.getStatus()));
            //拼接需要加密签名的字符串,data后面为 data信息的base64加密

            String srcStr = RCWLAcpConstant.SRCSTR + "authorize=" + JSON.toJSONString(rcwlAcpInvoiceElephantAuthorizeDTO) + "&globalInfo=" + JSON.toJSONString(rcwlAcpInvoiceElephantGlobalInfoDTO) + "&data=" + Base64.getEncoder().encodeToString(JSON.toJSONString(rcwlAcpInvoiceElephantDataDTO).getBytes(Charset.defaultCharset()));

            logger.info("data:"+JSON.toJSONString(rcwlAcpInvoiceElephantDataDTO));
            logger.info(Base64.getEncoder().encodeToString(JSON.toJSONString(rcwlAcpInvoiceElephantDataDTO).getBytes(Charset.defaultCharset())));
            logger.info("srcStr:" + srcStr);
            //获取加密字符串
            String appSec = rcwlElephantUtil.getAppSec(srcStr);

            rcwlAcpInvoiceElephantAuthorizeDTO.setAppSec(appSec);

            rcwlAcpInvoiceElephantRequestDataDTO.setAuthorize(rcwlAcpInvoiceElephantAuthorizeDTO);
            rcwlAcpInvoiceElephantRequestDataDTO.setGlobalInfo(rcwlAcpInvoiceElephantGlobalInfoDTO);
            rcwlAcpInvoiceElephantRequestDataDTO.setData(rcwlAcpInvoiceElephantDataDTO);

            logger.info("RCWLAcpInvoiceHeaderData:" + JSON.toJSONString(rcwlAcpInvoiceHeaderData));
        }
        return rcwlAcpInvoiceElephantRequestDataDTO;
    }
}
