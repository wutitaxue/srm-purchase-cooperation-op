package org.srm.purchasecooperation.order.app.service.impl;

import io.choerodon.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.app.service.RcwlBudgetDistributionService;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.repository.RcwlBudgetDistributionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RcwlBudgetDistribution> selectBudgetDistributionByPoLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        processPoLine(rcwlBudgetDistributionDTO);

        // 根据订单头行ID、获取预算分配数据
        List<RcwlBudgetDistribution> budgetDistributionsInDB = rcwlBudgetDistributionRepository.selectByCondition((Condition.builder(RcwlBudgetDistribution.class).
                andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_PO_HEADER_ID, rcwlBudgetDistributionDTO.getPoHeaderId())
                        .andEqualTo(RcwlBudgetDistribution.FIELD_PO_LINE_ID, rcwlBudgetDistributionDTO.getPoLineId())
                        .andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, tenantId)
                ).orderByAsc(RcwlBudgetDistribution.FIELD_BUDGET_DIS_YEAR).build()));

        log.info("原预算分配数据：{}",budgetDistributionsInDB.size());

        //未匹配则直接创建，匹配到则赋值原预算占用（手工）重新创建
        if (CollectionUtils.isEmpty(budgetDistributionsInDB)){
            return createBudgetDistributionByPoLine(tenantId, rcwlBudgetDistributionDTO,null);
        }

        List<RcwlBudgetDistribution> budgetDistributionDelList = new ArrayList<>(budgetDistributionsInDB);
        //校验各年原预算值（手工）是否等于行金额
        BigDecimal totalBudgetDisAmount = budgetDistributionsInDB.stream().map(bd -> Optional.ofNullable(bd.getBudgetDisAmount()).orElse(BigDecimal.ZERO)).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("订单行总金额：{},各年原预算值（手工）：{}",rcwlBudgetDistributionDTO.getLineAmount(),totalBudgetDisAmount);
        if (totalBudgetDisAmount.compareTo(Optional.ofNullable(rcwlBudgetDistributionDTO.getLineAmount()).orElse(BigDecimal.ZERO))!= 0){
            budgetDistributionsInDB = null;
        }

        //重新计算系统预算，获取原手工预算，重新创建并清空原数据
        List<RcwlBudgetDistribution> budgetDistributionCreateList = createBudgetDistributionByPoLine(tenantId, rcwlBudgetDistributionDTO, budgetDistributionsInDB);
        rcwlBudgetDistributionRepository.batchDelete(budgetDistributionDelList);
        return budgetDistributionCreateList;
    }

    private List<RcwlBudgetDistribution> createBudgetDistributionByPoLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO, List<RcwlBudgetDistribution> budgetDistributionsInDB) {
        List<RcwlBudgetDistribution> budgetDistributionCreateList = new ArrayList<>();

        //预算总时长(月) = A2年B2月- A1年B1月=12-B1+B2+（A2-A1-1）*12
       Long budgetDisGap = 12 - rcwlBudgetDistributionDTO.getNeedByDateMonth() + rcwlBudgetDistributionDTO.getAttributeDate1Month()
                + (rcwlBudgetDistributionDTO.getNeedByDateYear() - rcwlBudgetDistributionDTO.getAttributeDate1Year() - 1) * 12;

        for (Long i = rcwlBudgetDistributionDTO.getAttributeDate1Year(); i <= rcwlBudgetDistributionDTO.getNeedByDateYear(); i++) {
            RcwlBudgetDistribution budgetDistribution = new RcwlBudgetDistribution();
            budgetDistribution.setPoHeaderId(rcwlBudgetDistributionDTO.getPoHeaderId());
            budgetDistribution.setPoLineId(rcwlBudgetDistributionDTO.getPoLineId());
            budgetDistribution.setBudgetDisYear(i);
            budgetDistribution.setBudgetDisGap(budgetDisGap);
            budgetDistribution.setTenantId(tenantId);

            if (CollectionUtils.isNotEmpty(budgetDistributionsInDB)){
                Long finalI = i;
                RcwlBudgetDistribution rcwlBudgetDistribution = budgetDistributionsInDB.stream()
                        .filter(bd -> finalI.equals(bd.getBudgetDisYear())).findFirst().get();
                //预算占用(手工填写)
                log.info("预算年份：{},原年份预算值（手工）：{}",finalI,rcwlBudgetDistribution);
                budgetDistribution.setBudgetDisAmount(Objects.isNull(rcwlBudgetDistribution)?null:rcwlBudgetDistribution.getBudgetDisAmount());
            }

            //预算占用(系统计算值)
            if (i.equals(rcwlBudgetDistributionDTO.getAttributeDate1Year())) {
                //A年预算占用金额=（12-B1）/预算总时长(月)*行金额
                budgetDistribution.setBudgetDisAmountCal(rcwlBudgetDistributionDTO.getLineAmount().multiply(new BigDecimal((12 - rcwlBudgetDistributionDTO.getAttributeDate1Month())).divide(new BigDecimal(budgetDisGap), 6, RoundingMode.HALF_UP)));
            } else if (i.equals(rcwlBudgetDistributionDTO.getNeedByDateYear())) {
                //C年预算占用金额=B2/预算总时长(月)*行金额
                budgetDistribution.setBudgetDisAmountCal(rcwlBudgetDistributionDTO.getLineAmount().multiply(new BigDecimal(rcwlBudgetDistributionDTO.getNeedByDateMonth()).divide(new BigDecimal(budgetDisGap),6, RoundingMode.HALF_UP)));
            } else {
                //AC之间年份金额=12/预算总时长(月)*行金额
                budgetDistribution.setBudgetDisAmountCal(rcwlBudgetDistributionDTO.getLineAmount().multiply(new BigDecimal(12).divide(new BigDecimal(budgetDisGap),6, RoundingMode.HALF_UP)));
            }
            budgetDistributionCreateList.add(budgetDistribution);
        }

        rcwlBudgetDistributionRepository.batchInsert(budgetDistributionCreateList);

        return budgetDistributionCreateList;
    }

    private void processPoLine(RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        //订单行金额
        Assert.notNull(rcwlBudgetDistributionDTO.getLineAmount(), "error.line_amount_not_exists");

        //订单开始日期
        LocalDate startDate = rcwlBudgetDistributionDTO.getAttributeDate1();
        Assert.notNull(startDate, "error.start_date_not_exists");
        rcwlBudgetDistributionDTO.setAttributeDate1Year(Long.valueOf(startDate.getYear()));
        rcwlBudgetDistributionDTO.setAttributeDate1Month(Long.valueOf(startDate.getMonthValue()));

        //订单结束日期
        LocalDate endDate = rcwlBudgetDistributionDTO.getNeedByDate();
        Assert.notNull(endDate, "error.end_date_not_exists");
        rcwlBudgetDistributionDTO.setNeedByDateYear(Long.valueOf(endDate.getYear()));
        rcwlBudgetDistributionDTO.setNeedByDateMonth(Long.valueOf(endDate.getMonthValue()));
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
        return rcwlBudgetDistributionList;
    }

}
