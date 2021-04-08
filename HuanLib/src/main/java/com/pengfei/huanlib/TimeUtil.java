
package com.pengfei.huanlib;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Keep;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author wanghuanlong
 */
@Keep
public final class TimeUtil {
    private TimeUtil() {
    }

    private static final String TAG = "TimeUtil";

    public static final String DATE_FORMAT_1 = "yy/MM/dd";

    public static final String DATE_FORMAT_2 = "yyyy.MM.dd HH:mm";

    public static String getStandardTime(long t) {
        StringBuffer sb = new StringBuffer();

        long time = System.currentTimeMillis() - t * 1000;
        calculateTime(sb, time);
        return sb.toString();
    }

    public static String getAgoTime(long t) {
        StringBuffer sb = new StringBuffer();
        long time = t * 1000;
        calculateTime(sb, time);
        return sb.toString();
    }

    public static String getPositionTime(long t) {
        StringBuffer sb = new StringBuffer();
        long time = t * 1000;
        calculateTime(sb, time);
        String timeStr = sb.toString();
        if (timeStr.contains("前")) {
            timeStr = timeStr.substring(0, timeStr.length() - 1);
        }
        return timeStr;
    }

    public static String getDayTime(long timestamp) {
        if (timestamp <= 0) {
            return "无";
        }

        if (timestamp < 60) {
            return timestamp + "秒";
        }

        if (timestamp < 60 * 60) {
            return (int) Math.floor((timestamp / 60)) + "分钟";
        }

        if (timestamp < 60 * 60 * 24) {
            return (int) Math.floor((timestamp / (60 * 60))) + "小时";
        }

        if (timestamp < 60 * 60 * 24 * 7) {
            return (int) Math.floor((timestamp / (60 * 60 * 24))) + "天";
        }

        if (timestamp < 60 * 60 * 24 * 7 * 30) {
            return (int) Math.floor((timestamp / (60 * 60 * 24 * 7))) + "周";
        }

        if (timestamp < 60 * 60 * 24 * 7 * 30 * 12) {
            return (int) Math.floor((timestamp / (60 * 60 * 24 * 7 * 30))) + "月";
        }

        return (int) Math.floor((timestamp / (60 * 60 * 24 * 7 * 30 * 12))) + "年";
    }

    private static void calculateTime(StringBuffer sb, long time) {
        long mill = (long) Math.ceil(time / 1000); // 秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f); // 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f); // 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f); // 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!"刚刚".equals(sb.toString())) {
            sb.append("前");
        }
    }

    public static String getDateToString(String timeStr) {
        long time = Long.parseLong(timeStr);
        Date d = new Date(time * 1000);
        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT_1);
        return sf.format(d);
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_2);
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    public static String dateLongToStr(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_2);
        return formatter.format(date);
    }

    public static String getDateStr(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }

    public static String getLong2tDateStr(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return formatter.format(date);
    }

    public static String getCurrentDateStr() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static String getCurrentShortData() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }

    public static String getCurrentYear() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(date);
    }

    public static int getCurrentMonth() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int month = c.get(Calendar.MONTH) + 1;
        return month;
    }

    public static String getShortDate(String dateStr, int type) {
        String date = "";
        if (!TextUtils.isEmpty(dateStr)) {
            String[] dates = dateStr.split(" ");
            if (dates != null && dates.length > 0) {
                if (type == 0) {
                    date = dates[0];
                } else {
                    date = dates[1];
                }

            }
        }
        return date;
    }

    public static boolean isSameDate(String date1, String date2) {
        boolean isSameDate = false;
        if (!TextUtils.isEmpty(date1) && !TextUtils.isEmpty(date2)) {
            String[] dates2 = date2.split(" ");
            if (date1.equals(dates2[0])) {
                isSameDate = true;
            } else {
                isSameDate = false;
            }

        }
        return isSameDate;
    }

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) {
        String res = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToLong(String s) {
        long ts = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = simpleDateFormat.parse(s);
            ts = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return ts;
        }
    }

    public static String getCurToAgoTime(long t) {
        StringBuffer sb = new StringBuffer();
        calculateTime(sb, t);
        return sb.toString();
    }

    public static String getLong2tDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static long stringToLong(String strTime, String formatType) throws ParseException {
        Date date = stringToDate(strTime, formatType);  // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date);  // date类型转成long类型
            return currentTime;
        }
    }

    public static Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static Date longToDate(long currentTime, String formatType) throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static int compareDate(Date dt1, Date dt2) {
        if (dt1.getTime() > dt2.getTime()) {
            Log.i(TAG, "compareDate: dt1 在dt2前");
            return 1;
        } else if (dt1.getTime() < dt2.getTime()) {
            Log.i(TAG, "compareDate: dt1在dt2后");
            return -1;
        }
        // 相等
        return 0;
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTimes(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    public static boolean isDistanceOneDay(Date one, Date two) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return day >= 1;
    }

    public static String getDistance(Date one, Date two) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "剩" + hour + ":" + min;
    }

    public static int daysOfTwo(Date fDate, Date oDate) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(oDate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        // 同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                // 闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else {
                    // 不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            // 不同年
            return day2 - day1;
        }
    }

    public static long getPublicDate(String d, String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(d + " " + time, pos);

        return date.getTime();
    }
}
