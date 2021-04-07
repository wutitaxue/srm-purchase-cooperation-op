package org.srm.purchasecooperation.cux.transaction.app.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.srm.purchasecooperation.asn.app.service.SendMessageToPrService;
import org.srm.purchasecooperation.common.api.dto.SmdmCurrencyDTO;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.cux.transaction.domain.entity.RCWLRcvTrxLine;
import org.srm.purchasecooperation.cux.transaction.infra.constant.RCWLTransactionConstant;
import org.srm.purchasecooperation.cux.transaction.infra.mapper.RcwlPrHeaderMapper;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.app.service.PoLineService;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.infra.constant.PoConstants;
import org.srm.purchasecooperation.order.infra.convertor.CommonConvertor;
import org.srm.purchasecooperation.sinv.app.service.dto.PcHeaderDetailDTO;
import org.srm.purchasecooperation.transaction.api.dto.CreateRcvTrcDTO;
import org.srm.purchasecooperation.transaction.app.service.RcvTrxLineService;
import org.srm.purchasecooperation.transaction.app.service.impl.RcvTrxHeaderServiceImpl;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxHeader;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxLine;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxType;
import org.srm.purchasecooperation.transaction.domain.repository.RcvTrxHeaderRepository;
import org.srm.purchasecooperation.transaction.domain.repository.RcvTrxLineRepository;
import org.srm.purchasecooperation.transaction.domain.repository.RcvTrxTypeRepository;
import org.srm.purchasecooperation.transaction.domain.service.TrxByItfDomainService;
import org.srm.purchasecooperation.transaction.domain.vo.RcvTrxHeaderVO;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;
import org.srm.purchasecooperation.transaction.infra.constant.Constants;
import org.srm.web.annotation.Tenant;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

@Service
@Tenant(RCWLTransactionConstant.TENANT_NUMBER)
public class RCWLRcvTrxHeaderServiceImpl extends RcvTrxHeaderServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RCWLRcvTrxHeaderServiceImpl.class);
    @Autowired
    private RcvTrxLineRepository rcvTrxLineRepository;
    @Autowired
    private RcvTrxTypeRepository trxTypeRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private RcvTrxHeaderRepository rcvTrxHeaderRepository;
    @Autowired
    private PoLineLocationRepository poLineLocationRepository;
    @Autowired
    private MdmService mdmService;
    @Autowired
    private PoLineService poLineService;
    @Autowired
    private RcvTrxLineService rcvTrxLineService;
    @Autowired
    private TrxByItfDomainService trxByItfDomainService;
    @Autowired
    private SendMessageToPrService sendMessageToPrService;
    @Autowired
    private RcwlPrHeaderMapper prHeaderMapper;

    /**
     * 新增采购事务头行（基于订单）
     *
     * @param tenantId
     * @param receivedBy
     * @param dtos
     * @return
     */
    @Override
    public RcvTrxHeader createRcvTrxByPo(Long tenantId, String receivedBy, List<CreateRcvTrcDTO> dtos) {
        Assert.notEmpty(dtos, PoConstants.ErrorCode.PO_NOT_EXSITS);
        // 查询出发运行事务行
        Set<Long> poLocationLineIds =
                        dtos.stream().map(CreateRcvTrcDTO::getPoLineLocationId).collect(Collectors.toSet());
        List<ReceiveRcvTrxDataVO> receiveRcvTrxDataVOS =
                        rcvTrxHeaderRepository.queryRcvTrxDataInByLocationId(tenantId, poLocationLineIds);
        receiveRcvTrxDataVOS.stream().forEach(
                        receiveRcvTrxDataVO -> receiveRcvTrxDataVO.setAgentId(receiveRcvTrxDataVO.getPoAgentId()));

        // 将接收方式set进查询集合中用于采购事务行上的判断
        receiveRcvTrxDataVOS.stream().forEach(receiveRcvTrxDataVO -> receiveRcvTrxDataVO
                        .setReceiveOrderType(PoConstants.ReceiveOlderType.RECRIVE_ORDER_TYPE_ORDER));


        // 新增采购事务头
        RcvTrxHeader rcvTrxHeader = CommonConvertor.beanConvert(RcvTrxHeader.class, receiveRcvTrxDataVOS.get(0));
        this.insertRcvTrcHeader(rcvTrxHeader, receivedBy, tenantId);

        // 新增采购事务行
        List<RcvTrxLine> rcvTrxLines = this.insertRcvTrcLine(receiveRcvTrxDataVOS, rcvTrxHeader, tenantId, dtos);

        // 转换事务数量和金额
        rcvTrxLineRepository.convertTrxQuantityAndAmount(rcvTrxLines);
        rcvTrxLineRepository.batchUpdateOptional(rcvTrxLines, RcvTrxLine.FIELD_QUANTITY, RcvTrxLine.FIELD_NET_AMOUNT,
                        RcvTrxLine.FIELD_TAX_INCLUDED_AMOUNT);

        // 执行事务之前将行上的是否来源订单标识置为1
        rcvTrxLines.forEach(rcvTrxLine -> rcvTrxLine.setFromOrderFlag(1));
        // 同个订单行下，允超数量会在通过接受事务行的超收数量不断减少
        Map<Long, BigDecimal> poLineIdTolerQuantityMap = new HashMap<>();
        for (int i = 0; i < receiveRcvTrxDataVOS.size(); i++) {
            PoLineLocation poLineLocation =
                            poLineLocationRepository.selectByPrimaryKey(rcvTrxLines.get(i).getFromPoLineLocationId());
            BigDecimal tolerQuantity;
            // 允超数量根据采购订单行维度控制，如果MAP中对应的订单行已存在，取之前的，不然重新查询
            if (poLineIdTolerQuantityMap.containsKey(poLineLocation.getPoLineId())) {
                tolerQuantity = poLineIdTolerQuantityMap.get(poLineLocation.getPoLineId());
            } else {
                tolerQuantity = poLineLocationRepository.selectTolerQuantity(poLineLocation.getPoLineId(),
                                new ArrayList<>());
            }
            // 此次接收数量不能大于(可接收数量+允超数量)
            Assert.isTrue(receiveRcvTrxDataVOS.get(i).getCanReceiveQuantity().add(tolerQuantity)
                            .compareTo(rcvTrxLines.get(i).getQuantity()) >= 0,
                            Constants.ErrorCode.THIS_TIME_REVEIVE_QUANTITY_LARGER);
            // 接收数量多于可接收数量的部分 在允超数量下减少并记录
            if (receiveRcvTrxDataVOS.get(i).getCanReceiveQuantity().compareTo(rcvTrxLines.get(i).getQuantity()) < 0) {
                poLineIdTolerQuantityMap.put(poLineLocation.getPoLineId(), receiveRcvTrxDataVOS.get(i)
                                .getCanReceiveQuantity().add(tolerQuantity).subtract(rcvTrxLines.get(i).getQuantity()));
            }
        }
        // 执行事务
        rcvTrxLines.forEach(d -> rcvTrxLineService.trxExecution(rcvTrxHeader, d));

        // 所有事务执行完毕后，更新事务头的执行状态与时间
        rcvTrxHeader.setProcessFlag(BaseConstants.Flag.YES);
        rcvTrxHeader.setProcessDate(new Date());
        // 8-10 触发SRM->SAP 接收与冲销接口
        RcvTrxHeaderVO rcvTrxHeaderVO = trxByItfDomainService.rcvTrxToErpItf(tenantId, rcvTrxHeader, rcvTrxLines);
        rcvTrxHeader.setDisplayTrxNum(rcvTrxHeaderVO.getDisplayTrxNum());

        rcvTrxHeaderRepository.updateOptional(rcvTrxHeader, RcvTrxHeader.FIELD_PROCESS_FLAG,
                        RcvTrxHeader.FIELD_PROCESS_DATE, RcvTrxHeader.FIELD_DISPLAY_TRX_NUM);
        rcvTrxHeader.setRcvTrxLineList(rcvTrxLines);
        // 发送消息给申请
        List<Long> rcvTrxLineIdList =
                        rcvTrxLines.stream().map(RcvTrxLine::getRcvTrxLineId).collect(Collectors.toList());
        sendMessageToPrService.sendTrxMessageToPr(tenantId, Collections.singletonList(rcvTrxHeader.getRcvTrxHeaderId()),
                        rcvTrxLineIdList, BaseConstants.Flag.YES);
        return rcvTrxHeader;
    }

    /**
     * 初始化采购事务头并新增
     *
     * @param rcvTrxHeader 采购事务头
     * @param receivedBy 接收人
     * @param tenantId 租户ID
     */
    private void insertRcvTrcHeader(RcvTrxHeader rcvTrxHeader, String receivedBy, Long tenantId) {
        // 接收事务编号
        rcvTrxHeader.setTrxNum(codeRuleBuilder.generateCode(Constants.CODE_RULE_TRX_NUM, null));
        rcvTrxHeader.setDisplayTrxNum(rcvTrxHeader.getTrxNum());
        // 取当前年
        rcvTrxHeader.setTrxYear((long) Calendar.getInstance().get(Calendar.YEAR));
        // 当前日期
        rcvTrxHeader.setTrxDate(new Date());
        // 取事务日期
        rcvTrxHeader.setApprovedDate(rcvTrxHeader.getTrxDate());
        // 接收人
        rcvTrxHeader.setReceivedBy(receivedBy);
        // 租户ID
        rcvTrxHeader.setTenantId(tenantId);
        // 来源代码默认SRM
        rcvTrxHeader.setSourceCode(Constants.SourceCode.SRM);
        rcvTrxHeader.setExternalSystemCode(Constants.SourceCode.SRM);
        // 数量校验标识 值为1，需要校验数量
        rcvTrxHeader.setQuantityCheckFlag(BaseConstants.Flag.YES);
        // 是否初始化数据标识, 值为1，是初始化数据
        rcvTrxHeader.setInitFlag(BaseConstants.Flag.NO);
        // 需要执行标识 值为1，需要执行
        rcvTrxHeader.setNeedProcessFlag(BaseConstants.Flag.YES);
        // 事务执行标识 值为0，未执行
        rcvTrxHeader.setProcessFlag(BaseConstants.Flag.NO);
        rcvTrxHeaderRepository.insertSelective(rcvTrxHeader);
    }

    /**
     * 初始化采购事务行并新增
     *
     * @param receiveRcvTrxDataVOS 采购事务行集合
     * @param rcvTrxHeader 采购事务头
     * @param tenantId 租户ID
     * @param dtos 页面接收信息，包括接收数量
     * @return List&lt;RcvTrxLine&gt; 新增后的采购事务行
     */
    private List<RcvTrxLine> insertRcvTrcLine(List<ReceiveRcvTrxDataVO> receiveRcvTrxDataVOS, RcvTrxHeader rcvTrxHeader,
                    Long tenantId, List<CreateRcvTrcDTO> dtos) {
        LOGGER.debug("begin insertRcvTrcLine rcvTrxHeader： {}；dtos：{}", rcvTrxHeader, dtos);
        List<RcvTrxLine> rcvTrxLines = new ArrayList<>();
        String rcvTrxTypeCode = Constants.RcvTrxTypeCode.RECEIVE_DELIVER;
        for (int i = 0; i < receiveRcvTrxDataVOS.size(); i++) {
            RCWLRcvTrxLine rcvTrxLine = CommonConvertor.beanConvert(RCWLRcvTrxLine.class, receiveRcvTrxDataVOS.get(i));
            rcvTrxLine.setTenantId(tenantId);
            // 采购事务头ID
            rcvTrxLine.setRcvTrxHeaderId(rcvTrxHeader.getRcvTrxHeaderId());
            // 事务日期 取事务头上的事务日期
            rcvTrxLine.setTrxDate(rcvTrxHeader.getTrxDate());
            // 事务年份 取事务头上的事务年份
            rcvTrxLine.setTrxYear(rcvTrxHeader.getTrxYear());
            // 根据租户ID、数据来源、外部来源系统代码，采购事务类型代码为“101”为条件找到事务类型ID
            // 如果订单行的returned_flag 为1的话接收类型为161
            PoLine poLine = poLineRepository.selectByPrimaryKey(rcvTrxLine.getFromPoLineId());
            if (!Objects.isNull(poLine) && poLine.getReturnedFlag() == 1) {
                rcvTrxTypeCode = Constants.RcvTrxTypeCode.RECEIVE_DELIVER_161;
            }
            // 如果订单行不为空，将订单行上的申请信息设置到事务行上
            if (Objects.nonNull(poLine)) {
                rcvTrxLine.setPrHeaderId(poLine.getPrHeaderId());
                rcvTrxLine.setPrLineId(poLine.getPrLineId());
            }
            Long rcvTrxTypeId = trxTypeRepository.selectRcvTrxTypeIdByUnique(new RcvTrxType(tenantId,
                            Constants.SourceCode.SRM, rcvTrxTypeCode, Constants.SourceCode.SRM));
            // 唯一
            Assert.notNull(rcvTrxTypeId, Constants.ErrorCode.RCV_TRX_TYPE_CODE_NOT_EXISTS);
            // 接收事务类型ID
            rcvTrxLine.setRcvTrxTypeId(rcvTrxTypeId);
            // 库存类型
            rcvTrxLine.setStockType(Constants.StockType.OWN_STOCK);
            // 事务数量:当前用户输入此次接收数量
            // 新增实际收货日期字段
            for (CreateRcvTrcDTO dto : dtos) {
                // 基于订单做接收时送货单行id会传空值，所以需要先判断
                if (PoConstants.ReceiveOlderType.RECRIVE_ORDER_TYPE_ORDER
                                .equals(receiveRcvTrxDataVOS.get(i).getReceiveOrderType())) {
                    if (dto.getPoLineLocationId().equals(receiveRcvTrxDataVOS.get(i).getPoLineLocationId())) {
                        rcvTrxLine.setQuantity(dto.getThisTimeReceiveQuantity());
                        rcvTrxLine.setRemark(dto.getRemark());
                        rcvTrxLine.setLocatorId(dto.getLocationId());
                        rcvTrxLine.setInventoryId(dto.getInventoryId());
                        // 所属事务头下的行号
                        rcvTrxLine.setTrxLineNum(dto.getTrxLineNum());
                        // 实际收货日期
                        rcvTrxLine.setRealityReceiveDate(dto.getRealityReceiveDate());
                        // 来源发运行号
                        rcvTrxLine.setFromPoLineLocationId(receiveRcvTrxDataVOS.get(i).getPoLineLocationId());
                        break;
                    }
                } else if (dto.getAsnLineId().equals(receiveRcvTrxDataVOS.get(i).getAsnLineId())) {
                    rcvTrxLine.setQuantity(dto.getThisTimeReceiveQuantity());
                    rcvTrxLine.setRemark(dto.getRemark());
                    rcvTrxLine.setLocatorId(dto.getLocationId());
                    rcvTrxLine.setInventoryId(dto.getInventoryId());
                    // 所属事务头下的行号
                    rcvTrxLine.setTrxLineNum(dto.getTrxLineNum());
                    // 实际收货日期
                    rcvTrxLine.setRealityReceiveDate(dto.getRealityReceiveDate());
                    break;
                }

            }
            // 事务展示行号
            rcvTrxLine.setDisplayTrxLineNum(String.valueOf(rcvTrxLine.getTrxLineNum()));
            // // 此次接收数量不能大于可接收数量
            // Assert.isTrue(receiveRcvTrxDataVOS.get(i).getCanReceiveQuantity().compareTo(rcvTrxLine.getQuantity())
            // > -1,
            // Constants.ErrorCode.THIS_TIME_REVEIVE_QUANTITY_LARGER);
            // 获取事务行所对应的订单类型id
            if (rcvTrxLine.getFromPoHeaderId() != null) {
                Long orderTypeId = rcvTrxHeaderRepository.queryOrderType(rcvTrxLine.getFromPoHeaderId());
                // 判断该事务行是否为暂不处理
                Integer result = rcvTrxHeaderRepository.judgeBill(rcvTrxLine.getTenantId(), rcvTrxLine.getCompanyId(),
                                rcvTrxLine.getSupplierCompanyId(), orderTypeId);
                if (result > 0) {
                    rcvTrxLine.setNeedInvoiceFlag(BaseConstants.Flag.NO);
                }
            }
            // 获取币种对应精度
            SmdmCurrencyDTO smdmCurrencyDTO = mdmService.selectSmdmCurrencyDto(tenantId, rcvTrxLine.getCurrencyCode());
            // 金额精度
            int financialPrecision = smdmCurrencyDTO.getFinancialPrecision();

            // 进行一个保留两位小数的四舍五入
            if (rcvTrxLine.getNetPrice() != null && rcvTrxLine.getQuantity() != null) {
                rcvTrxLine.setNetAmount(rcvTrxLine.getNetPrice().multiply(rcvTrxLine.getQuantity())
                                .setScale(financialPrecision, RoundingMode.HALF_UP));
            }
            if (rcvTrxLine.getTaxIncludedPrice() != null && rcvTrxLine.getQuantity() != null) {
                rcvTrxLine.setTaxIncludedAmount(rcvTrxLine.getTaxIncludedPrice().multiply(rcvTrxLine.getQuantity())
                                .setScale(financialPrecision, RoundingMode.HALF_UP));
            }
            if (rcvTrxLine.getEnteredTrxPrice() != null && rcvTrxLine.getQuantity() != null) {
                rcvTrxLine.setEnteredTrxAmount(rcvTrxLine.getEnteredTrxPrice().multiply(rcvTrxLine.getQuantity())
                                .setScale(financialPrecision, RoundingMode.HALF_UP));
                // 这里得单价是除了每的，但是后面的单价我们要的是没除的
                rcvTrxLine.setNetPrice(rcvTrxLine.getEnteredTrxPrice());
                rcvTrxLine.setPoUnitPrice(rcvTrxLine.getEnteredTrxPrice());
            }
            // TODO 事务金额，质保金字段逻辑
            rcvTrxLine.setNetAmount(
                            new BigDecimal(rcvTrxLine.getAttributeBigint1()).multiply(rcvTrxLine.getNetPrice()));
            // 分页查询订单行明细信息
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPage(0);
            pageRequest.setSize(10);
            Page<PoLineDetailDTO> poLineDetailDTOS = poLineService.pageLineDetail(pageRequest, poLine.getPoHeaderId(),
                            poLine.getTenantId(), 1, 0, PoConstants.PoEntryPoint.PURCHASE_PO_CONFIRM);
            List<PoLineDetailDTO> content = poLineDetailDTOS.getContent();
            content.forEach(dto -> {
                String contractNum = dto.getContractNum();
                String[] split = contractNum.split("|");
                PcHeaderDetailDTO pcHeaderDetailDTO = prHeaderMapper.selectPcHeaderDetailByPcNum(split[0], tenantId);
                if (StringUtils.isEmpty(pcHeaderDetailDTO.getAttribute1())) {
                    rcvTrxLine.setAccpMetentionMoney(new BigDecimal(0));
                } else {
                    BigDecimal bigDecimal = new BigDecimal(pcHeaderDetailDTO.getAttribute1());
                    rcvTrxLine.setAccpMetentionMoney(bigDecimal.multiply(rcvTrxLine.getNetAmount()));
                }
            });
            rcvTrxLines.add(rcvTrxLine);
        }
        // 新增行事务
        rcvTrxLineRepository.batchInsertSelective(rcvTrxLines);
        return rcvTrxLines;
    }
}
