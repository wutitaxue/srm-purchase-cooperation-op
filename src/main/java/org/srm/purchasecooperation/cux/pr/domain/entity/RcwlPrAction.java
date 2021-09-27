package org.srm.purchasecooperation.cux.pr.domain.entity;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hzero.core.base.BaseConstants;
import org.srm.purchasecooperation.order.infra.annotation.ChangeField;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.pr.domain.entity.PrAction;
import org.srm.purchasecooperation.pr.domain.entity.PrChangeConfig;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/9/27 19:31
 */
public class RcwlPrAction extends PrAction {
    public static PrAction createAction(String fieldName, org.srm.purchasecooperation.pr.domain.entity.PrLine before, PrLine after, PrChangeConfig config, PrHeader prHeader, PrAction prAction, Set<String> approveSet, PoHeaderMapper poHeaderMapper) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, before.getClass());
        Method readMethod = pd.getReadMethod();
        Object beforeValue = readMethod.invoke(before);
        Object afterValue = readMethod.invoke(after);
        byte var13 = -1;
        switch(fieldName.hashCode()) {
            case -431061492:
                if (fieldName.equals("supplierCompanyId")) {
                    var13 = 0;
                }
            default:
                switch(var13) {
                    case 0:
                        beforeValue = StringUtils.isBlank(before.getSupplierName()) ? before.getSupplierCompanyName() : before.getSupplierName();
                        afterValue = StringUtils.isBlank(after.getSupplierName()) ? after.getSupplierCompanyName() : after.getSupplierName();
                }

                CustomUserDetails customUserDetails = DetailsHelper.getUserDetails();
                Field field;
                String cfField;
                String name;
                String quanyField;
                Long oldValue;
                ChangeField annotation;
                String cfTable;
                if (beforeValue == null) {
                    if (afterValue != null && afterValue instanceof Date) {
                        checkCanModifyFlag(config);
                        if (BaseConstants.Flag.YES.equals(config.getPurchaseApprovalFlag())) {
                            approveSet.add(fieldName);
                        }

                        prAction.setTenantId(prHeader.getTenantId());
                        prAction.setPrHeaderId(prHeader.getPrHeaderId());
                        prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
                        prAction.setPrLineId(after.getPrLineId());
                        prAction.setDisplayLineNum(after.getDisplayLineNum());
                        prAction.setProcessTypeCode("CHANGE");
                        prAction.setProcessedDate(new Date());
                        prAction.setProcessUserId(customUserDetails.getUserId());
                        prAction.setProcessUserName(customUserDetails.getRealName());
                        prAction.setOldValueDate((Date)null);
                        prAction.setNewValueDate((Date)afterValue);
                        prAction.setChangeField(config.getFieldName());
                        return prAction;
                    }

                    if (afterValue != null && afterValue instanceof BigDecimal) {
                        checkCanModifyFlag(config);
                        if (BaseConstants.Flag.YES.equals(config.getPurchaseApprovalFlag())) {
                            approveSet.add(fieldName);
                        }

                        prAction.setTenantId(prHeader.getTenantId());
                        prAction.setPrHeaderId(prHeader.getPrHeaderId());
                        prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
                        prAction.setPrLineId(after.getPrLineId());
                        prAction.setDisplayLineNum(after.getDisplayLineNum());
                        prAction.setProcessTypeCode("CHANGE");
                        prAction.setProcessedDate(new Date());
                        prAction.setProcessUserId(customUserDetails.getUserId());
                        prAction.setProcessUserName(customUserDetails.getRealName());
                        prAction.setOldValueNum((BigDecimal)null);
                        prAction.setNewValueNum((BigDecimal)afterValue);
                        prAction.setChangeField(config.getFieldName());
                        return prAction;
                    }

                    if (afterValue != null && afterValue instanceof Long) {
                        checkCanModifyFlag(config);
                        if (BaseConstants.Flag.YES.equals(config.getPurchaseApprovalFlag())) {
                            approveSet.add(fieldName);
                        }

                        prAction.setTenantId(prHeader.getTenantId());
                        prAction.setPrHeaderId(prHeader.getPrHeaderId());
                        prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
                        prAction.setPrLineId(after.getPrLineId());
                        prAction.setDisplayLineNum(after.getDisplayLineNum());
                        prAction.setProcessTypeCode("CHANGE");
                        prAction.setProcessedDate(new Date());
                        prAction.setProcessUserId(customUserDetails.getUserId());
                        prAction.setProcessUserName(customUserDetails.getRealName());
                        prAction.setOldValue("");
                        prAction.setNewValue(Optional.ofNullable(afterValue).orElse("").toString());
                        prAction.setChangeField(config.getFieldName());
                        annotation = null;

                        try {
                            field = before.getClass().getDeclaredField(fieldName);
                            annotation = (ChangeField)field.getAnnotation(ChangeField.class);
                        } catch (NoSuchFieldException var22) {
                            var22.printStackTrace();
                        }

                        if (annotation != null) {
                            cfTable = annotation.table();
                            cfField = annotation.field();
                            name = annotation.name();
                            quanyField = annotation.quaryField();
                            if (StringUtils.isNotEmpty(name)) {
                                prAction.setChangeField(name);
                            }

                            if (StringUtils.isNotEmpty(cfTable) && StringUtils.isNotEmpty(cfField)) {
                                oldValue = Long.parseLong(prAction.getNewValue());
                                Object objectNew = poHeaderMapper.selectObjectByLong(oldValue, cfField, cfTable, quanyField);
                                prAction.setNewValue(Optional.ofNullable(objectNew).orElse("").toString());
                            }
                        }

                        return prAction;
                    }

                    if (afterValue != null) {
                        checkCanModifyFlag(config);
                        if (BaseConstants.Flag.YES.equals(config.getPurchaseApprovalFlag())) {
                            approveSet.add(fieldName);
                        }

                        prAction.setTenantId(prHeader.getTenantId());
                        prAction.setPrHeaderId(prHeader.getPrHeaderId());
                        prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
                        prAction.setPrLineId(after.getPrLineId());
                        prAction.setDisplayLineNum(after.getDisplayLineNum());
                        prAction.setProcessTypeCode("CHANGE");
                        prAction.setProcessedDate(new Date());
                        prAction.setProcessUserId(customUserDetails.getUserId());
                        prAction.setProcessUserName(customUserDetails.getRealName());
                        prAction.setOldValue("");
                        prAction.setNewValue(Optional.ofNullable(afterValue).orElse("").toString());
                        prAction.setChangeField(config.getFieldName());
                        return prAction;
                    }
                } else if (beforeValue instanceof Date && (afterValue instanceof Date || afterValue == null)) {
                    if (afterValue == null || !DateUtils.truncatedEquals((Date)beforeValue, (Date)afterValue, 5)) {
                        checkCanModifyFlag(config);
                        if (BaseConstants.Flag.YES.equals(config.getPurchaseApprovalFlag())) {
                            approveSet.add(fieldName);
                        }

                        prAction.setTenantId(prHeader.getTenantId());
                        prAction.setPrHeaderId(prHeader.getPrHeaderId());
                        prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
                        prAction.setPrLineId(after.getPrLineId());
                        prAction.setDisplayLineNum(after.getDisplayLineNum());
                        prAction.setProcessTypeCode("CHANGE");
                        prAction.setProcessedDate(new Date());
                        prAction.setProcessUserId(customUserDetails.getUserId());
                        prAction.setProcessUserName(customUserDetails.getRealName());
                        prAction.setOldValueDate((Date)beforeValue);
                        prAction.setNewValueDate(afterValue == null ? null : (Date)afterValue);
                        prAction.setChangeField(config.getFieldName());
                        return prAction;
                    }
                } else if (beforeValue instanceof BigDecimal && (afterValue instanceof BigDecimal || afterValue == null)) {
                    BigDecimal value1 = ((BigDecimal)beforeValue).setScale(2, 4);
                    BigDecimal value2 = afterValue == null ? null : ((BigDecimal)afterValue).setScale(2, 4);
                    if (!value1.equals(value2)) {
                        checkCanModifyFlag(config);
                        if (BaseConstants.Flag.YES.equals(config.getPurchaseApprovalFlag())) {
                            approveSet.add(fieldName);
                        }

                        prAction.setTenantId(prHeader.getTenantId());
                        prAction.setPrHeaderId(prHeader.getPrHeaderId());
                        prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
                        prAction.setPrLineId(after.getPrLineId());
                        prAction.setDisplayLineNum(after.getDisplayLineNum());
                        prAction.setProcessTypeCode("CHANGE");
                        prAction.setProcessedDate(new Date());
                        prAction.setProcessUserId(customUserDetails.getUserId());
                        prAction.setProcessUserName(customUserDetails.getRealName());
                        prAction.setOldValueNum((BigDecimal)beforeValue);
                        prAction.setNewValueNum(afterValue == null ? null : (BigDecimal)afterValue);
                        prAction.setChangeField(config.getFieldName());
                        return prAction;
                    }
                } else {
                    if (!beforeValue.equals(afterValue) && afterValue instanceof Long) {
                        checkCanModifyFlag(config);
                        if (BaseConstants.Flag.YES.equals(config.getPurchaseApprovalFlag())) {
                            approveSet.add(fieldName);
                        }

                        prAction.setTenantId(prHeader.getTenantId());
                        prAction.setPrHeaderId(prHeader.getPrHeaderId());
                        prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
                        prAction.setPrLineId(after.getPrLineId());
                        prAction.setDisplayLineNum(after.getDisplayLineNum());
                        prAction.setProcessTypeCode("CHANGE");
                        prAction.setProcessedDate(new Date());
                        prAction.setProcessUserId(customUserDetails.getUserId());
                        prAction.setProcessUserName(customUserDetails.getRealName());
                        prAction.setOldValue(Optional.ofNullable(beforeValue).orElse("").toString());
                        prAction.setNewValue(Optional.ofNullable(afterValue).orElse("").toString());
                        prAction.setChangeField(config.getFieldName());
                        annotation = null;

                        try {
                            field = before.getClass().getDeclaredField(fieldName);
                            annotation = (ChangeField)field.getAnnotation(ChangeField.class);
                        } catch (NoSuchFieldException var23) {
                            var23.printStackTrace();
                        }

                        if (annotation != null) {
                            cfTable = annotation.table();
                            cfField = annotation.field();
                            name = annotation.name();
                            quanyField = annotation.quaryField();
                            if (StringUtils.isNotEmpty(name)) {
                                prAction.setChangeField(name);
                            }

                            if (StringUtils.isNotEmpty(cfTable) && StringUtils.isNotEmpty(cfField)) {
                                oldValue = Long.parseLong(prAction.getOldValue());
                                Long newValue = Long.parseLong(prAction.getNewValue());
                                Object objectOld = poHeaderMapper.selectObjectByLong(oldValue, cfField, cfTable, quanyField);
                                Object objectNew = poHeaderMapper.selectObjectByLong(newValue, cfField, cfTable, quanyField);
                                prAction.setOldValue(Optional.ofNullable(objectOld).orElse("").toString());
                                prAction.setNewValue(Optional.ofNullable(objectNew).orElse("").toString());
                            }
                        }

                        return prAction;
                    }

                    if (!beforeValue.equals(afterValue)) {
                        checkCanModifyFlag(config);
                        if (BaseConstants.Flag.YES.equals(config.getPurchaseApprovalFlag())) {
                            approveSet.add(fieldName);
                        }

                        prAction.setTenantId(prHeader.getTenantId());
                        prAction.setPrHeaderId(prHeader.getPrHeaderId());
                        prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
                        prAction.setPrLineId(after.getPrLineId());
                        prAction.setDisplayLineNum(after.getDisplayLineNum());
                        prAction.setProcessTypeCode("CHANGE");
                        prAction.setProcessedDate(new Date());
                        prAction.setProcessUserId(customUserDetails.getUserId());
                        prAction.setProcessUserName(customUserDetails.getRealName());
                        prAction.setOldValue(Optional.ofNullable(beforeValue).orElse("").toString());
                        prAction.setNewValue(Optional.ofNullable(afterValue).orElse("").toString());
                        prAction.setChangeField(config.getFieldName());
                        return prAction;
                    }
                }

                return null;
        }
    }

    public static void checkCanModifyFlag(PrChangeConfig config) {
//        if (!BaseConstants.Flag.YES.equals(config.getCanModifyFlag()) && !BaseConstants.Flag.YES.equals(config.getExtensionFieldFlag())) {
//            throw new CommonException("error.change.field.change.refused", new Object[0]);
//        }
    }
}
