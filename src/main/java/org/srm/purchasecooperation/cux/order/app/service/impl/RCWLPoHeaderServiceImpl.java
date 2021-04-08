package org.srm.purchasecooperation.cux.order.app.service.impl;

import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.cux.order.app.service.RCWLPoHeaderService;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.cux.order.domain.repository.RCWLPoHeaderRepository;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;
import org.srm.purchasecooperation.pr.domain.entity.ItemCategoryAssign;
import org.srm.purchasecooperation.pr.domain.repository.ItemCategoryAssignRepository;
import org.srm.purchasecooperation.pr.domain.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bin.zhang
 */
@Service
public class RCWLPoHeaderServiceImpl implements RCWLPoHeaderService {
    @Autowired
    private RCWLPoHeaderService rcwlPoHeaderService;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private RCWLPoHeaderRepository poHeaderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemCategoryAssignRepository itemCategoryAssignRepository;
    private static final Logger logger = LoggerFactory.getLogger(org.srm.purchasecooperation.cux.order.app.service.impl.RCWLPoHeaderServiceImpl.class);

    @Override
    public void insertItemCode(PoDTO poDTO, Long tenantId) {
        logger.info("订单DTO:" + poDTO.toString());
        List<PoLineLocation> poLineLocationList = poDTO.getPoLineLocationList();
        List<Long> ids = poLineLocationList.stream().map(PoLineLocation::getPoLineId).distinct().collect(Collectors.toList());

        //查询需要封装的item数据集合(排除存在物料id的数据)
        List<RCWLItemInfoVO> rcwlItemInfoVOList = poHeaderRepository.selectItemListByPoLineIdList(ids, tenantId);
        List<ItemCategoryAssign> itemCategoryAssignList = new ArrayList<>();
        // 生成对应条编码
        rcwlItemInfoVOList.forEach(item -> {
            //物料设值
            item.setTenantId(tenantId);
            String categoryCode = item.getItemCode();
            String str = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SODR.RCWL.ITEM_CODE", "GLOBAL", "GLOBAL", (Map) null);
            String ruleCode = this.codeRuleBuilder.generateCode("SMDM.ITEM", (Map) null);
            item.setItemNumber(ruleCode);
            item.setItemCode(categoryCode + str);
            item.setQueryItemCode(categoryCode + str);
        });
        logger.info("物料封装数据:" + rcwlItemInfoVOList.toString());
        //批量插入物料表
        poHeaderRepository.batchInsertItem(rcwlItemInfoVOList);


        List<Long> lineIds = rcwlItemInfoVOList.stream().map(RCWLItemInfoVO::getPoLineId).distinct().collect(Collectors.toList());
        //查询出需要封装好的itemCategoryAssign
        List<RCWLItemInfoVO> itemCategoryList = poHeaderRepository.selectItemCategoryListByPoLineIdList(lineIds, tenantId);

        itemCategoryList.forEach(item -> {
            ItemCategoryAssign itemCategoryAssign = new ItemCategoryAssign();
            itemCategoryAssign.setItemId(item.getItemId());
            itemCategoryAssign.setTenantId(tenantId);
            itemCategoryAssign.setSourceCode(item.getSourceCode());
            itemCategoryAssign.setCategoryId(item.getCategoryId());
            itemCategoryAssignList.add(itemCategoryAssign);
        });
        logger.info("品类封装数据:" + itemCategoryAssignList.toString());
        //批量插入物料分配品类表
        itemCategoryAssignRepository.batchInsertSelective(itemCategoryAssignList);

        //把item_id item_code回写到订单行
        List<RCWLItemInfoVO> poLineList = new ArrayList<>();
        itemCategoryList.forEach(poLine -> {
            RCWLItemInfoVO lineUpdateInfo = new RCWLItemInfoVO();
            lineUpdateInfo.setTenantId(tenantId);
            lineUpdateInfo.setItemId(poLine.getItemId());
            lineUpdateInfo.setItemCode(poLine.getItemCode());
            lineUpdateInfo.setPoLineId(poLine.getPoLineId());
            poLineList.add(lineUpdateInfo);
        });
        logger.info("订单行封装数据:" + poLineList.toString());
        //批量更新订单物料id和code
        poHeaderRepository.batchUpdatePoLine(poLineList);
    }

    /**
     * 判断是否存在itemcode
     */
//    private boolean judgeItemCodeExist(Long poLineId, Long tenantId) {
//        if (poLineId != null) {
//            PoLine poLine = poLineRepository.selectByPrimaryKey(poLineId);
//            if (poLine.getItemId() != null) {
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }
}
