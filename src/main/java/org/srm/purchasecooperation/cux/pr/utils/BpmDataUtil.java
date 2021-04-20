package org.srm.purchasecooperation.cux.pr.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @description: bpm传输数据封装
 * @author:yuanping.zhang
 * @createTime:2021/4/19 13:52
 * @version:1.0
 */
public class BpmDataUtil {
    public static String setBpmData(Map<String, Object> keyAndValues) {
        StringBuilder builder = new StringBuilder();
        for (String key : keyAndValues.keySet()) {
            setDetails(key, keyAndValues.get(key), builder);
        }
        String data = builder.toString();
        return data;
    }

    /**
     * 设置头标签
     *
     * @param headLabel 头标签
     * @param data      封装好的数据
     * @return
     */
    public static StringBuilder setHeadLabel(String headLabel, String data) {
        StringBuilder builder = new StringBuilder();
        setLeftLabel(builder, headLabel);
        builder.append(data);
        setRightLabel(builder, headLabel);
        return builder;
    }

    /**
     * 封装数据
     *
     * @param key     标签值
     * @param value   数据
     * @param builder StringBuilder
     * @return
     */
    private static StringBuilder setDetails(String key, Object value, StringBuilder builder) {
        if (StringUtils.isNotBlank(key)) {
            setLeftLabel(builder, key);
            builder.append(ObjectUtils.isEmpty(value)?"":value);
            setRightLabel(builder, key);
        }

        return builder;
    }


    /**
     * 封装左标签
     *
     * @param builder StringBuilder
     * @param key     标签值 如<DATA></DATA> 中的左边部分 <DATA>
     * @return
     */
    private static StringBuilder setLeftLabel(StringBuilder builder, String key) {
        return builder.append("<").append(key).append(">");
    }


    /**
     * 封装右标签
     *
     * @param builder StringBuilder
     * @param key     标签值 如<DATA></DATA> 中的右边部分 </DATA>
     * @return
     */
    private static StringBuilder setRightLabel(StringBuilder builder, String key) {
        return builder.append("</").append(key).append(">");
    }


    /**
     * 封装没有值的标签
     *
     * @param builder StringBuilder
     * @param key     标签值 如<DATA/>
     * @return
     */
    private static StringBuilder setNoneDataLabel(StringBuilder builder, String key) {
        return builder.append("<").append(key).append("/>");
    }


}
