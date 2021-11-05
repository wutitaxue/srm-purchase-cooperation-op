package org.srm.purchasecooperation.order.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.app.service.RcwlBudgetDistributionService;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.repository.RcwlBudgetDistributionRepository;
import org.srm.purchasecooperation.order.infra.mapper.RcwlBudgetDistributionMapper;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 预算分配应用服务默认实现
 *
 * @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
 */
@Slf4j
@Service
public class RcwlBudgetDistributionServiceImpl implements RcwlBudgetDistributionService {
    @Autowired
    private RcwlBudgetDistributionRepository rcwlBudgetDistributionRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private RcwlBudgetDistributionMapper rcwlBudgetDistributionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RcwlBudgetDistribution> selectBudgetDistributionByPoLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        this.processPoLine(rcwlBudgetDistributionDTO);

        log.info("rcwlBudgetDistributionDTO:{}",rcwlBudgetDistributionDTO);

        // 根据订单头行ID、获取预算分配数据
        List<RcwlBudgetDistribution> budgetDistributionsInDB = rcwlBudgetDistributionRepository.selectByCondition((Condition.builder(RcwlBudgetDistribution.class).
                andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_PO_HEADER_ID, rcwlBudgetDistributionDTO.getPoHeaderId())
                        .andEqualTo(RcwlBudgetDistribution.FIELD_PO_LINE_ID, rcwlBudgetDistributionDTO.getPoLineId())
                        .andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, tenantId)
                ).orderByAsc(RcwlBudgetDistribution.FIELD_BUDGET_DIS_YEAR).build()));

        log.info("原预算分配数据：{}",budgetDistributionsInDB.size());

        List<Integer> budgetDisYears = budgetDistributionsInDB.stream().map(RcwlBudgetDistribution::getBudgetDisYear).collect(Collectors.toList());

        //未创建或年份不匹配则直接创建，匹配到则赋值原预算占用（手工）重新创建
        if (CollectionUtils.isEmpty(budgetDistributionsInDB) || !Collections.max(budgetDisYears).equals(rcwlBudgetDistributionDTO.getNeedEndDateYear())
        || !Collections.min(budgetDisYears).equals(rcwlBudgetDistributionDTO.getNeedStartDateYear())){
            log.info("未创建或年份不匹配");
            return createBudgetDistributionByPoLine(tenantId, rcwlBudgetDistributionDTO,null);
        }

        //校验各年原预算值（手工）是否等于行金额
        BigDecimal totalBudgetDisAmount = budgetDistributionsInDB.stream().map(bd -> Optional.ofNullable(bd.getBudgetDisAmount()).orElse(BigDecimal.ZERO)).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("订单行总金额：{},各年原预算值（手工）：{}",rcwlBudgetDistributionDTO.getLineAmount(),totalBudgetDisAmount);
        if (totalBudgetDisAmount.compareTo(Optional.ofNullable(rcwlBudgetDistributionDTO.getLineAmount()).orElse(BigDecimal.ZERO))!= 0){
            budgetDistributionsInDB = null;
        }

        //重新计算系统预算，获取原手工预算，重新创建
        log.info("已有数据且年份匹配");
        List<RcwlBudgetDistribution> budgetDistributionCreateList = createBudgetDistributionByPoLine(tenantId, rcwlBudgetDistributionDTO, budgetDistributionsInDB);
        return budgetDistributionCreateList;
    }

    private List<RcwlBudgetDistribution> createBudgetDistributionByPoLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO, List<RcwlBudgetDistribution> budgetDistributionsInDB) {
        List<RcwlBudgetDistribution> budgetDistributionCreateList = new ArrayList<>();

        String poAndPoLineNum = rcwlBudgetDistributionMapper.selectPoAndPoLineNum(tenantId, rcwlBudgetDistributionDTO.getPoHeaderId(), rcwlBudgetDistributionDTO.getPoLineId());

        //预算总时长(月) = A2年B2月- A1年B1月=12-B1+B2+（A2-A1-1）*12
        Integer budgetDisGap = 12 - rcwlBudgetDistributionDTO.getNeedStartDateMonth() + rcwlBudgetDistributionDTO.getNeedEndDateMonth()
                + (rcwlBudgetDistributionDTO.getNeedEndDateYear() - rcwlBudgetDistributionDTO.getNeedStartDateYear() - 1) * 12;

        for (Integer i = rcwlBudgetDistributionDTO.getNeedStartDateYear(); i <= rcwlBudgetDistributionDTO.getNeedEndDateYear(); i++) {
            RcwlBudgetDistribution budgetDistribution = new RcwlBudgetDistribution();
            budgetDistribution.setPoHeaderId(rcwlBudgetDistributionDTO.getPoHeaderId());
            budgetDistribution.setPoLineId(rcwlBudgetDistributionDTO.getPoLineId());
            budgetDistribution.setBudgetDisYear(i);
            budgetDistribution.setBudgetDisGap(Long.valueOf(budgetDisGap));
            budgetDistribution.setTenantId(tenantId);
            budgetDistribution.setLineAmount(rcwlBudgetDistributionDTO.getLineAmount());
            budgetDistribution.setPoAndPoLineNum(poAndPoLineNum);

            //预算占用(系统计算值)
            if (i.equals(rcwlBudgetDistributionDTO.getNeedStartDateYear())) {
                //订单开始结束日期年月相同，不使用计算公式
                if (Long.valueOf(0).equals(budgetDisGap)){
                    BigDecimal budgetDisAmountCal = rcwlBudgetDistributionDTO.getLineAmount();
                    budgetDistribution.setBudgetDisAmountCal(budgetDisAmountCal);
                    budgetDistribution.setBudgetDisAmount(budgetDisAmountCal);
                }else {
                    //A年预算占用金额=（12-B1）/预算总时长(月)*行金额
                    BigDecimal budgetDisAmountCal = rcwlBudgetDistributionDTO.getLineAmount().multiply(new BigDecimal((12 - rcwlBudgetDistributionDTO.getNeedStartDateMonth())).divide(new BigDecimal(budgetDisGap), 6, RoundingMode.HALF_UP));
                    budgetDistribution.setBudgetDisAmountCal(budgetDisAmountCal);
                    budgetDistribution.setBudgetDisAmount(budgetDisAmountCal);
                }
                log.info("起始年");
            } else if (i.equals(rcwlBudgetDistributionDTO.getNeedEndDateYear())) {
                //C年预算占用金额=B2/预算总时长(月)*行金额
                BigDecimal budgetDisAmountCal = rcwlBudgetDistributionDTO.getLineAmount().multiply(new BigDecimal(rcwlBudgetDistributionDTO.getNeedEndDateMonth()).divide(new BigDecimal(budgetDisGap),6, RoundingMode.HALF_UP));
                budgetDistribution.setBudgetDisAmountCal(budgetDisAmountCal);
                budgetDistribution.setBudgetDisAmount(budgetDisAmountCal);
                log.info("结束年:{}",budgetDisAmountCal);
            } else {
                //AC之间年份金额=12/预算总时长(月)*行金额
                BigDecimal budgetDisAmountCal = rcwlBudgetDistributionDTO.getLineAmount().multiply(new BigDecimal(12).divide(new BigDecimal(budgetDisGap),6, RoundingMode.HALF_UP));
                budgetDistribution.setBudgetDisAmountCal(budgetDisAmountCal);
                budgetDistribution.setBudgetDisAmount(budgetDisAmountCal);
                log.info("中间年:{}",budgetDisAmountCal);
            }

            if (CollectionUtils.isNotEmpty(budgetDistributionsInDB)){
                Integer finalI = i;
                List<RcwlBudgetDistribution> rcwlBudgetDistributions = budgetDistributionsInDB.stream()
                        .filter(bd -> finalI.equals(bd.getBudgetDisYear())).collect(Collectors.toList());
                //预算占用(手工填写)
                log.info("预算年份：{},原年份预算值（手工）：{}",finalI,rcwlBudgetDistributions.get(0));
                budgetDistribution.setBudgetDisAmount(CollectionUtils.isEmpty(rcwlBudgetDistributions)?null:rcwlBudgetDistributions.get(0).getBudgetDisAmount());
            }

            budgetDistributionCreateList.add(budgetDistribution);
        }

        //清除原数据
        rcwlBudgetDistributionRepository.delete(RcwlBudgetDistribution.builder()
                .poHeaderId(rcwlBudgetDistributionDTO.getPoHeaderId()).poLineId(rcwlBudgetDistributionDTO.getPoLineId())
                .tenantId(rcwlBudgetDistributionDTO.getTenantId()).build());

        rcwlBudgetDistributionRepository.batchInsert(budgetDistributionCreateList);

        return budgetDistributionCreateList;
    }

    private void processPoLine(RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        //订单行金额
        Assert.notNull(rcwlBudgetDistributionDTO.getLineAmount(), "error.line_amount_not_exists");

        //订单开始日期
        LocalDate startDate = rcwlBudgetDistributionDTO.getAttributeDate1();
        Assert.notNull(startDate, "error.start_date_not_exists");
        rcwlBudgetDistributionDTO.setNeedStartDateYear(startDate.getYear());
        rcwlBudgetDistributionDTO.setNeedStartDateMonth(startDate.getMonthValue());

        //订单结束日期
        LocalDate endDate = rcwlBudgetDistributionDTO.getNeedByDate();
        Assert.notNull(endDate, "error.end_date_not_exists");
        rcwlBudgetDistributionDTO.setNeedEndDateYear(endDate.getYear());
        rcwlBudgetDistributionDTO.setNeedEndDateMonth(endDate.getMonthValue());
    }

    @Override
    public List<RcwlBudgetDistribution> batchUpdateBudgetDistributions(Long tenantId, List<RcwlBudgetDistribution> rcwlBudgetDistributionList) {

        List<PoLine> poLines = this.poLineRepository.selectByCondition(Condition.builder(PoLine.class)
                .andWhere(Sqls.custom().andEqualTo(PoLine.FIELD_PO_HEADER_ID, rcwlBudgetDistributionList.get(0).getPoHeaderId())
                        .andEqualTo(PoLine.FIELD_PO_LINE_ID, rcwlBudgetDistributionList.get(0).getPoLineId())
                        .andEqualTo(PoLine.FIELD_TENANT_ID, tenantId)).build());
        if (CollectionUtils.isEmpty(poLines)){
            throw new CommonException("订单行数据不存在！");
        }

        //校验各年原预算值（手工）是否等于行金额
        BigDecimal totalBudgetDisAmount = rcwlBudgetDistributionList.stream().map(bd -> Optional.ofNullable(bd.getBudgetDisAmount()).orElse(BigDecimal.ZERO)).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("订单行总金额：{},各年原预算值（手工）：{}",poLines.get(0).getLineAmount(),totalBudgetDisAmount);
        if (totalBudgetDisAmount.compareTo(Optional.ofNullable(poLines.get(0).getLineAmount()).orElse(BigDecimal.ZERO))!= 0){
            throw new CommonException("预算占用合计必须等于订单行金额，请重新维护预算拆分！");
        }

        rcwlBudgetDistributionRepository.batchUpdateByPrimaryKeySelective(rcwlBudgetDistributionList);
        for (RcwlBudgetDistribution rcwlBudgetDistribution :rcwlBudgetDistributionList){
            rcwlBudgetDistribution.setLineAmount(poLines.get(0).getLineAmount());
        }
        return rcwlBudgetDistributionList;
    }

    @Override
    public List<RcwlBudgetDistributionDTO> selectBudgetDistributionByPrLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        // prLine不为空,先计算行金额,表示是采购申请变更
        PrLine prLine = rcwlBudgetDistributionDTO.getPrLine();
        if(!ObjectUtils.isEmpty(prLine)){
            prLine.countLineAmount();
            rcwlBudgetDistributionDTO.setLineAmount(prLine.getLineAmount());
            rcwlBudgetDistributionDTO.setChangeSubmit(1);
        }
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionResults = new ArrayList<>();
        // 根据采购申请头、行id计算跨年预算的值
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS = rcwlBudgetDistributionRepository.selectBudgetDistributionByPrLine(tenantId, rcwlBudgetDistributionDTO);
        rcwlBudgetDistributionDTOS.forEach(itemLine -> {
            List<Integer> yearPrLineYears = new ArrayList<>(Integer.parseInt(String.valueOf(itemLine.getNeededDateYear() - itemLine.getAttributeDate1Year() + 1)));
            for (Integer i = itemLine.getAttributeDate1Year(); i <= itemLine.getNeededDateYear(); i++) {
                yearPrLineYears.add(i);
            }
            rcwlBudgetDistributionDTO.setBudgetDisYears(yearPrLineYears);
        });
        // 根据采购申请头、行id和申请行的年份集合去查询跨年预算的值
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionRealValues =
                rcwlBudgetDistributionRepository.selectBudgetDistribution(tenantId, rcwlBudgetDistributionDTO);
        // -------------- 计算系统分摊金额 begin -------------------------------------------
        if (CollectionUtils.isNotEmpty(rcwlBudgetDistributionDTOS)) {
            // 一行只有一条数据
            RcwlBudgetDistributionDTO itemLine = rcwlBudgetDistributionDTOS.get(0);
            for (Integer i = itemLine.getAttributeDate1Year(); i <= itemLine.getNeededDateYear(); i++) {
                RcwlBudgetDistributionDTO rcwlBudgetDistributionResult = new RcwlBudgetDistributionDTO();
                BeanCopier beanCopier = BeanCopier.create(RcwlBudgetDistributionDTO.class, RcwlBudgetDistributionDTO.class, false);
                beanCopier.copy(itemLine, rcwlBudgetDistributionResult, null);
                rcwlBudgetDistributionResult.setBudgetDisYear(i);
                if (i.equals(itemLine.getAttributeDate1Year())) {
                    // 当前行【占用年份】=A1，则为【申请行总金额】/【占用总时长（月）】*【12-B1】
                    rcwlBudgetDistributionResult.setAutoCalculateBudgetDisAmount(rcwlBudgetDistributionResult.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionResult.getBudgetDisGap()), 6, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal(12 - rcwlBudgetDistributionResult.getAttributeDate1Month()).setScale(6, BigDecimal.ROUND_HALF_UP)));
                } else if (i.equals(itemLine.getNeededDateYear())) {
                    // 若当前行【占用年份】=A2,则为【申请行总金额】/【占用总时长（月）】*B2
                    rcwlBudgetDistributionResult.setAutoCalculateBudgetDisAmount(rcwlBudgetDistributionResult.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionResult.getBudgetDisGap()), 6, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal(rcwlBudgetDistributionResult.getNeededDateMonth()).setScale(6, BigDecimal.ROUND_HALF_UP)));
                } else {
                    // 前两者不满足，则为【申请行总金额】/【占用总时长（月）】*12
                    rcwlBudgetDistributionResult.setAutoCalculateBudgetDisAmount(rcwlBudgetDistributionResult.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionResult.getBudgetDisGap()), 6, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal(12).setScale(6, BigDecimal.ROUND_HALF_UP)));
                }
                // 根据采购申请头、行id和申请行的年份去查询跨年预算的值
                if (!org.springframework.util.CollectionUtils.isEmpty(rcwlBudgetDistributionRealValues)) {
                    Integer finalI = i;
                    BigDecimal budgetDisAmount = rcwlBudgetDistributionRealValues.stream().filter(rcwlBudgetDistributionRealValue -> rcwlBudgetDistributionDTO.getPrHeaderId().equals(rcwlBudgetDistributionRealValue.getPrHeaderId()) && rcwlBudgetDistributionDTO.getPrLineId().equals(rcwlBudgetDistributionRealValue.getPrLineId()) && finalI.equals(rcwlBudgetDistributionRealValue.getBudgetDisYear())).findFirst().orElse(new RcwlBudgetDistributionDTO()).getBudgetDisAmount();
                    rcwlBudgetDistributionResult.setBudgetDisAmount(budgetDisAmount);
                }
                // 强制行金额显示六位
                rcwlBudgetDistributionResult.setLineAmount(rcwlBudgetDistributionResult.getLineAmount().setScale(6, BigDecimal.ROUND_HALF_UP));
                rcwlBudgetDistributionResults.add(rcwlBudgetDistributionResult);
            }
            // -------------- 计算系统分摊金额 end -------------------------------------------
        }
        return rcwlBudgetDistributionResults;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RcwlBudgetDistribution> createBudgetDistributions(Long tenantId, List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS) {
        if (!org.springframework.util.CollectionUtils.isEmpty(rcwlBudgetDistributionDTOS)) {
            // 查询已经存在的跨年预算数据
            List<RcwlBudgetDistribution> budgetDistributions =
                    rcwlBudgetDistributionRepository.selectByCondition(Condition.builder(RcwlBudgetDistribution.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, tenantId).andEqualTo(RcwlBudgetDistribution.FIELD_PR_HEADER_ID, rcwlBudgetDistributionDTOS.get(0).getPrHeaderId()).andEqualTo(RcwlBudgetDistribution.FIELD_PR_LINE_ID, rcwlBudgetDistributionDTOS.get(0).getPrLineId())).orderByAsc(RcwlBudgetDistribution.FIELD_BUDGET_DIS_YEAR).build());
            // 记录最后要插入/更新的跨年预算数据
            List<RcwlBudgetDistribution> changeBudgetDistributions = new ArrayList<>(rcwlBudgetDistributionDTOS.size());
            // 转换数据
            rcwlBudgetDistributionDTOS.forEach(rcwlBudgetDistributionDTO -> {
                // 跨年预算未保存过,占用金额去系统分摊金额;保存过,则直接取实际分摊金额
                changeBudgetDistributions.add(RcwlBudgetDistribution.builder().prHeaderId(rcwlBudgetDistributionDTO.getPrHeaderId()).prLineId(rcwlBudgetDistributionDTO.getPrLineId()).budgetDisGap(rcwlBudgetDistributionDTO.getBudgetDisGap()).budgetDisYear(rcwlBudgetDistributionDTO.getBudgetDisYear()).budgetDisAmount(org.springframework.util.CollectionUtils.isEmpty(budgetDistributions) && ObjectUtils.isEmpty(rcwlBudgetDistributionDTO.getBudgetDisAmount()) ? rcwlBudgetDistributionDTO.getAutoCalculateBudgetDisAmount() : rcwlBudgetDistributionDTO.getBudgetDisAmount()).tenantId(tenantId).build());
            });
            // 判断行金额和实际分摊金额总值是否相等,不相等---可能是人为调整有问题,或者金额变化了
            if (rcwlBudgetDistributionDTOS.get(0).getLineAmount().compareTo(rcwlBudgetDistributionDTOS.stream().map(RcwlBudgetDistributionDTO::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add)) != 0) {
                throw new CommonException("error.pr.line.amount.budget.error");
            }
            // 总体逻辑:已有跨年预算,删除->重新插入.  对于校验的部分,提交那里会限制住
            if (!org.springframework.util.CollectionUtils.isEmpty(budgetDistributions)) {
                rcwlBudgetDistributionRepository.batchDeleteByPrimaryKey(budgetDistributions);
            }
            rcwlBudgetDistributionRepository.batchInsert(changeBudgetDistributions);
            return changeBudgetDistributions;
        }
        return null;
    }

}
