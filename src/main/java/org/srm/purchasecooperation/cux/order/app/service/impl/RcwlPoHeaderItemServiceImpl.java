package org.srm.purchasecooperation.cux.order.app.service.impl;

import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoHeaderItemService;
import org.srm.purchasecooperation.cux.order.domain.repository.RcwlPoHeaderRepository;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
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
public class RcwlPoHeaderItemServiceImpl implements RcwlPoHeaderItemService {
    @Autowired
    private RcwlPoHeaderItemService rcwlPoHeaderItemService;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private RcwlPoHeaderRepository poHeaderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemCategoryAssignRepository itemCategoryAssignRepository;
    @Autowired
    private PoHeaderRepository HeaderRepository;

    private static final Logger logger = LoggerFactory.getLogger(org.srm.purchasecooperation.cux.order.app.service.impl.RcwlPoHeaderItemServiceImpl.class);

    @Override
    public void insertItemCode(PoDTO poDTO, Long tenantId) {
        logger.info("订单DTO:" + poDTO.toString());

        List prHeaderIdList = new ArrayList();
        prHeaderIdList.add(poDTO.getPoHeaderId());
        List<PoLine> lineList = poLineRepository.selectByPoHeaderIdList(prHeaderIdList);
        List<Long> ids = lineList.stream().map(PoLine::getPoLineId).distinct().collect(Collectors.toList());

        //查询需要封装的item数据集合(排除存在物料id的数据)
        List<RCWLItemInfoVO> rcwlItemInfoVOList = poHeaderRepository.selectItemListByPoLineIdList(ids, tenantId);
        List<ItemCategoryAssign> itemCategoryAssignList = new ArrayList<>();
        // 生成对应条编码
        if (CollectionUtils.isNotEmpty(rcwlItemInfoVOList)) {
            //判断来自哪个电商平台
            String dsFlag = poHeaderRepository.selectShopMallSupplier(poDTO.getPoHeaderId(),tenantId);

            rcwlItemInfoVOList.forEach(item -> {
                String itemCode = null;
                String categoryCode = item.getItemCode();
                //电商来源的物料编码为 品类编码+来源（00，01，02等）+电商编码
                if("CG".equals(dsFlag)){
                    itemCode = categoryCode+"04"+item.getProductNum();
                }else if("ZKH".equals(dsFlag)){
                    itemCode = categoryCode+"03"+item.getProductNum();
                }else if("JD".equals(dsFlag)){
                    itemCode = categoryCode+"02"+item.getProductNum();
                }else{
                    String str = this.codeRuleBuilder.generateCode("SODR.RCWL.ITEM_CODE",  (Map) null);
                    itemCode = categoryCode+str;
                }

                //物料设值
                item.setTenantId(tenantId);
                //String categoryCode = item.getItemCode();
               // String str = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SODR.RCWL.ITEM_CODE", "GLOBAL", "GLOBAL", (Map) null);
                String ruleCode = this.codeRuleBuilder.generateCode("SMDM.ITEM", (Map) null);
                item.setItemNumber(ruleCode);
                item.setItemCode(itemCode);
                item.setQueryItemCode(itemCode);
                item.setSpecifications(item.getSpecifications());
                item.setModel(item.getModel());
            });
            logger.info("物料封装数据:" + rcwlItemInfoVOList.toString());
            //批量插入物料表
            poHeaderRepository.batchInsertItem(rcwlItemInfoVOList);

            List<Long> lineIds = rcwlItemInfoVOList.stream().map(RCWLItemInfoVO::getPoLineId).distinct().collect(Collectors.toList());
            //查询出需要封装好的itemCategoryAssign
            List<RCWLItemInfoVO> itemCategoryList = poHeaderRepository.selectItemCategoryListByPoLineIdList(lineIds, tenantId);

            if (CollectionUtils.isNotEmpty(itemCategoryList)) {
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
            }


            //把item_id item_code回写到订单行
            List<RCWLItemInfoVO> poLineList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(itemCategoryList)) {
                itemCategoryList.forEach(poLine -> {
                    RCWLItemInfoVO lineUpdateInfo = new RCWLItemInfoVO();
                    lineUpdateInfo.setTenantId(tenantId);
                    lineUpdateInfo.setItemId(poLine.getItemId());
                    lineUpdateInfo.setItemCode(poLine.getItemCode());
                    lineUpdateInfo.setPoLineId(poLine.getPoLineId());
                    lineUpdateInfo.setItemName(poLine.getItemName());
                    poLineList.add(lineUpdateInfo);
                });
                logger.info("订单行封装数据:" + poLineList.toString());
                //批量更新订单物料id和code
                poHeaderRepository.batchUpdatePoLine(poLineList);
                //批量插入物料名称多语言表smdm_item_tl
                poHeaderRepository.batchInsertItemTl(poLineList);
            }


        }



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
