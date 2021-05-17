package org.srm.purchasecooperation.cux.pr.utils;

import io.choerodon.core.exception.CommonException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/4/19 20:04
 * @version:1.0
 */
public class DateTimeUtil {
    public static final String PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DAY = "yyyy-MM-dd";

    /**
     * 获取自定义格式的当前时间
     * @param pattern
     * @return
     */
    public static Date getCurrentTime(String pattern){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(df.format(date));
        } catch (ParseException e) {
            throw new CommonException("time_error", new Object[0]);
        }
    }

    /**
     * 获取当前时间
     * @return
     */
    public static Date getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_SECOND);
        try {
            return df.parse(df.format(date));
        } catch (ParseException e) {
            throw new CommonException("time_error", new Object[0]);
        }
    }

    /**
     * 获取当前日期
     * @return
     */
    public static Date getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_DAY);
        try {
            return df.parse(df.format(date));
        } catch (ParseException e) {
            throw new CommonException("time_error", new Object[0]);
        }
    }
    /**
     * 时间转换格式
     * @return
     */
    public static Date dateTimeFormat(Date time){
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_SECOND);
        try {
            return df.parse(df.format(time));
        } catch (ParseException e) {
            throw new CommonException("time_error", new Object[0]);
        }
    }

    /**
     * 时间转换自定义格式
     * @return
     */
    public static Date dateTimeFormat(Date time,String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(df.format(time));
        } catch (ParseException e) {
            throw new CommonException("time_error", new Object[0]);
        }
    }


    /**
     * 日期转换格式
     * @return
     */
    public static Date dateFormat(Date time){
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_DAY);
        try {
            return df.parse(df.format(time));
        } catch (ParseException e) {
            throw new CommonException("time_error", new Object[0]);
        }
    }

    /**
     * 日期转换自定义格式
     * @return
     */
    public static Date dateFormat(Date time,String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(df.format(time));
        } catch (ParseException e) {
            throw new CommonException("time_error", new Object[0]);
        }
    }

}
