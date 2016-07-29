/**
 *
 */
package com.itguai.blank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author jianyunCheng
 */
public class DateUtil {
    public static String getTimeStrFromMimS(long misecond, String formatstr) {
        SimpleDateFormat format = new SimpleDateFormat(formatstr);
        return format.format(misecond);
    }

    public static String getTimeStrFromMimSYMD(long misecond) {
        String retString = getTimeStrFromMimS(misecond, "yyyy-MM-dd");
        return retString;
    }

    public static String getTimeStrFromMimS(long misecond) {
        SimpleDateFormat format = new SimpleDateFormat();
        return format.format(misecond);
    }

    public static String getDateStr(long second) {
        SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
        sdf.applyPattern("yyyy年MM月dd日");
        return sdf.format(second);
    }

    public static String getDateCompleteStr(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
        sdf.applyPattern("MM月dd日 HH:mm");
        return sdf.format(millis * 1000);
    }

    public static String getRegularDataStr(long second) {
        SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
        sdf.applyPattern("yyyy-MM-dd HH:mm");
        return sdf.format(second);
    }

    public static String getDateMonth(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
        sdf.applyPattern("MM");
        return switchDate(sdf.format(millis * 1000));
    }

    public static String getDateDay(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
        sdf.applyPattern("dd");
        return sdf.format(millis * 1000);
    }

    public static long getTelentTime(int mil) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, mil);
        return cal.getTimeInMillis();
    }

    public static String switchDate(String date) {
        if (date.equals("01"))
            return "一月";
        else if (date.equals("02"))
            return "二月";
        else if (date.equals("03"))
            return "三月";
        else if (date.equals("04"))
            return "四月";
        else if (date.equals("05"))
            return "五月";
        else if (date.equals("06"))
            return "六月";
        else if (date.equals("07"))
            return "七月";
        else if (date.equals("08"))
            return "八月";
        else if (date.equals("09"))
            return "九月";
        else if (date.equals("10"))
            return "十月";
        else if (date.equals("11"))
            return "十一月";
        else
            return "十二月";
    }

    public static long getMiliSecFromDateStr(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time = sdf.parse(dateStr).getTime();
            return time;
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getTimeInterval(long millis) {
        long interval = System.currentTimeMillis()/1000 - millis ;
        long mint = interval;
        int hor = (int) mint / 3600;
        int secd = (int) mint % 3600;
        int day = hor / 24;
        if (day > 0 || hor > 23) {
            if (day <= 5) {
                return day + "天前";
            } else {
                return getDateStr(millis);
            }
        } else {
            if (hor < 1) {
                if (secd <= 3)
                    return "刚刚";
                else if (secd > 3 && secd < 60)
                    return Math.abs(secd) + "秒前";
                else
                    return secd / 60 + "分钟前";
            } else {
                return hor + "小时前";
            }
        }
    }
}
