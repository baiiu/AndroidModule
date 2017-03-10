package com.baiiu.commontool.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * author: baiiu
 * date: on 16/2/3 17:47
 * description: long型值转换为XX月XX号,前天,昨天,几点
 * <br>
 * 使用Calendar来处理时间比较.
 */
public class DateFormatUtil {

    public static String format(long inputTime) {
        try {
            Date date = new Date(inputTime);

            //该新闻的calendar
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTimeInMillis(inputTime);

            //现在的calendar
            Calendar todayMorningCalendar = Calendar.getInstance();
            todayMorningCalendar.set(Calendar.HOUR_OF_DAY, 0);
            todayMorningCalendar.set(Calendar.MINUTE, 0);
            todayMorningCalendar.set(Calendar.SECOND, 0);//设置为00:00

            if (inputCalendar.before(todayMorningCalendar)) {
                todayMorningCalendar.roll(Calendar.DAY_OF_MONTH, -1);//设置为昨天的00:00

                if (inputCalendar.before(todayMorningCalendar)) {
                    todayMorningCalendar.roll(Calendar.DAY_OF_MONTH, -1);//设置为前天的00:00

                    if (inputCalendar.before(todayMorningCalendar)) {

                        if (inputCalendar.get(Calendar.YEAR) == todayMorningCalendar.get(Calendar.YEAR)) {
                            //同一年
                            return toMMdd(date);
                        } else {
                            return toyyyyMMdd(date);
                        }

                    } else {
                        return "前天";
                    }

                } else {
                    //前天
                    return "昨天";
                }

            } else {
                //今天的时间
                long timeGap = System.currentTimeMillis() - inputTime;
                if (timeGap < 10 * 1000) {//小于10s
                    return "刚刚";
                } else if (timeGap < 60000) {//小于1分钟
                    return timeGap / 1000 + "秒前";
                } else if (timeGap < 3600000) {//小于1小时
                    return timeGap / 60000 + "分钟前";
                }

                return toHHmm(date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    static SimpleDateFormat HHmmFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
    static SimpleDateFormat MDHHmmFormat = new SimpleDateFormat("M月d日 HH:mm", Locale.CHINA);
    static SimpleDateFormat YYYYMDHHmmFormat = new SimpleDateFormat("yyyy年M月d日 HH:mm", Locale.CHINA);

    public static String toHHmm(Date date) {
        return HHmmFormat.format(date);
    }

    public static String toMMdd(Date date) {
        return MDHHmmFormat.format(date);
    }

    public static String toyyyyMMdd(Date date) {
        return YYYYMDHHmmFormat.format(date);
    }


}
