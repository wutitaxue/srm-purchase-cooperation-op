package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.google.common.base.CaseFormat;
import io.choerodon.core.exception.CommonException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrAction;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.pr.app.service.impl.PrActionServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.PrAction;
import org.srm.purchasecooperation.pr.domain.entity.PrChangeConfig;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.web.annotation.Tenant;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/9/27 19:18
 */
@Tenant("SRM-RCWL")
public class RcwlPrActionServiceImpl extends PrActionServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPrActionServiceImpl.class);
    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Override
    public List<PrAction> createChangeAction(PrLine before, PrLine after, List<PrChangeConfig> configs, PrHeader prHeader, Set<String> approveSet) {
        List<PrAction> result = new ArrayList();
        List<String> fieldNamess = new ArrayList();
        Arrays.asList(FieldUtils.getAllFields(before.getClass())).forEach((item) -> {
            if (2 == item.getModifiers() || 4 == item.getModifiers()) {
                fieldNamess.add(item.getName());
            }

        });
        Map<String, PrChangeConfig> configMap = (Map)configs.stream().collect(Collectors.toMap((configx) -> {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, configx.getFieldName());
        }, Function.identity()));
        Iterator var9 = fieldNamess.iterator();

        while(var9.hasNext()) {
            String fieldName = (String)var9.next();
            if (configMap.containsKey(fieldName)) {
                PrAction prAction = new PrAction();
                PrChangeConfig config = (PrChangeConfig)configMap.get(fieldName);

                try {
                    prAction = RcwlPrAction.createAction(fieldName, before, after, config, prHeader, prAction, approveSet, this.poHeaderMapper);
                    if (prAction != null) {
                        result.add(prAction);
                    }
                } catch (Exception var14) {
                    LOGGER.error("Change Error : {}", var14);
                    LOGGER.info("Change Error : Purchase requisition ：" + prHeader.getDisplayPrNum() + "，line ：" + after.getDisplayLineNum() + "，fieldName：" + fieldName);
                    throw new CommonException(var14.getMessage(), new Object[]{config.getFieldNameMeaning()});
                }
            }
        }

        LOGGER.info("Purchase requisition ：" + prHeader.getDisplayPrNum() + "，line ：" + after.getDisplayLineNum() + "，changeAction：" + result.toString());
        return result;
    }
}
