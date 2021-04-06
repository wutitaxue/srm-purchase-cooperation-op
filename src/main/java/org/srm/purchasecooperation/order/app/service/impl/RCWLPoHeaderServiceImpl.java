package org.srm.purchasecooperation.order.app.service.impl;

import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.app.service.RCWLPoHeaderService;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.repository.RCWLPoHeaderRepository;
import org.srm.purchasecooperation.pr.domain.entity.Item;
import org.srm.purchasecooperation.pr.domain.entity.ItemCategoryAssign;
import org.srm.purchasecooperation.pr.domain.repository.ItemCategoryAssignRepository;
import org.srm.purchasecooperation.pr.domain.repository.ItemRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bin.zhang
 */
@Service
public class RCWLPoHeaderServiceImpl implements RCWLPoHeaderService {
    @Autowired
    RCWLPoHeaderService rcwlPoHeaderService;
    @Autowired
    PoLineRepository poLineRepository;
    @Autowired
    CodeRuleBuilder codeRuleBuilder;
    @Autowired
    RCWLPoHeaderRepository poHeaderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemCategoryAssignRepository itemCategoryAssignRepository;
    private static final Logger logger = LoggerFactory.getLogger(RCWLPoHeaderServiceImpl.class);

    /**
     * 订单审批通过自动生成物料编码
     * @param poDTO
     * @param tenantId
     */
    @Override
    public void insertItemCode(PoDTO poDTO, Long tenantId) {
        logger.info("订单DTO:" + poDTO.toString());
        List<PoLineLocation> poLineLocationList = poDTO.getPoLineLocationList();
        List<Long> ids = poLineLocationList.stream().map(PoLineLocation::getPoLineId).distinct().collect(Collectors.toList());
        List<PoLine> poLineList = poLineRepository.selectByPoLineIdList(ids);
        logger.info("订单行:" + poLineList.toString());
        poLineList.forEach((poLine -> {
            //判断是否已存在物料
            boolean exist = this.judgeItemCodeExist(poLine.getPoLineId(), tenantId);
            //根据编码规则新增物料
            if (!exist) {
                //获取品类编码
                String categoryCode = poHeaderRepository.selectCategoryCode(poLine.getCategoryId(), tenantId);

                String str = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SODR.RCWL.ITEM_CODE", "GLOBAL", "GLOBAL", (Map) null);

                //物料表新增一条数据
                Item item = new Item();
                item.setTenantId(tenantId);
                item.setSourceCode("SRM");
                String ruleCode = this.codeRuleBuilder.generateCode("SMDM.ITEM", (Map) null);
                item.setItemNumber(ruleCode);
                item.setItemCode(categoryCode + str);
                item.setItemName(poLine.getItemName());
                item.setPrimaryUomId(poLine.getUomId());
                item.setQueryItemCode(categoryCode + str);
                itemRepository.insertSelective(item);


                //插入物料分配品类表
                ItemCategoryAssign itemCategoryAssign = new ItemCategoryAssign();
                itemCategoryAssign.setTenantId(tenantId);
                itemCategoryAssign.setItemId(item.getItemId());
                itemCategoryAssign.setSourceCode("SRM");
                itemCategoryAssign.setCategoryId(poLine.getCategoryId());
                itemCategoryAssignRepository.insertSelective(itemCategoryAssign);

                PoLine poLine1 = poLineRepository.selectByPrimaryKey(poLine);

                //物料id code 回写到订单行
                poLine1.setItemId(item.getItemId());
                poLine1.setItemCode(item.getItemCode());
                poLineRepository.updateOptional(poLine1, PoLine.FIELD_ITEM_CODE, PoLine.FIELD_ITEM_ID);
            }
        }
        ));
    }

    /**
     * 判断是否存在itemcode
     */
    private boolean judgeItemCodeExist(Long poLineId, Long tenantId) {
        if (poLineId != null) {
            PoLine poLine = poLineRepository.selectByPrimaryKey(poLineId);
            if (poLine.getItemId()!=null) {
                return true;
            }
            return false;
        }
        return false;
    }
}
