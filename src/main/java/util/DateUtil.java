package util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转化-工具类
 * 目录:
 * 1.String toStrFromDate(date,format)******Date-->String(推荐使用)
 * 2.Date toDateFromStr(str,format)******String-->Date(推荐使用)
 * 3.Date toDateFromDate(date,format)***********Date-->Date(推荐使用)
 * 4.String toStrFromStr(str,format)********String-->String(推荐使用)
 * 5.String toStrFromObject(obj,format)*************Object-->String(推荐使用)
 * 6.Date toDateFromObject(obj)
 * 7.String toStrFromStrT(str,format)
 * **************************************************************************
 * 8.Date getTimesByHour            获得传入时间的0-24点
 * 9.Date getDateByWeek             获得指定日期所在周的时间
 * 10.Date getFirstDayByMonth       获得本月第一天0点时间
 * 11.Date getLastDayByMonth        获得本月最后一天24点时间
 * 12.Date getFirstDayOfMonth       获取指定月第一天的日期
 * 13.Date getLastDayOfMonth        获取指定月最后一天的日期
 * 14.Date getDateDiff              获取指定日历字段加上该字段相应量级的时间
 * 15.int getYear                   获取年份
 * 16.int getMonth                  获取月份
 * 17.int getDaysByMonth            获取本月的天数
 * 18.int getMonthNew               比较日期大小(???)
 * 19.int getMonthsBetween          获取相差的月份
 * 20.int getYearsBetween           获取相差的年份
 * 21.int getDaysBetween            获取相差的天数
 * 23.int getDaysBetween            获取相差的天数
 * 24.LocalDate getLocalDate        将Date格式的日期转换为LocalDate格式
 * 25.boolean isSameDate            判断是不是同一天
 * 26.long getTimesBetweenNew          计算里两个日期相差的时间的long值
 * 27.boolean isValidDate           判断是否符合日期规则
 * 28.boolean isLeapYear            判断是不是闰年
 * **************************************************************************
 *
 * @Deprecated 1.getStrFromDate           改为toStrFromDate()
 * 2.getStrFromDateExceptTime 改为toStrFromDate()
 * 3.getDateFromStr           改为toDateFromStr()
 * 4.getFormatDate            改为toDateFromStr()
 * 5.getFormatDateStr         改为toStrFromDate()
 * 6.getTimesMorning          改为getTimesByHour()
 * 7.getTimesNight            改为getTimesByHour()
 * 8.getTimesNight(Date)      改为getTimesByHour()
 * 9.getTimesWeekmorning
 * 10.getTimesWeeknight
 * 11.getTimesMonthmorning    改为getFirstDayByMonth()
 * 12.getTimesMonthnight      改为getLastDayByMonth()
 * 13.add                     改为getDateDiff
 * 14.getDateAfter            改为getDateDiff
 * 15.getMonthAfter           改为getDateDiff
 * 16.getMonth                改为getMonthNew
 * 17.getDateLastYear         改为getDateDiff
 * 18.castToDate			  改为toDateFromObject()方法
 * 19.timesBetween            改为timesBetweenNew
 * 20.toStrFormatDate         改为toStrFromStr
 * 21.toStrFormatDate         改为toStrFromStr
 * 22.dealDateFormat          改为toStrFromStrT
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HMSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHMS = "yyyyMMddHHmmss";
    public static final String YMD_T_HMS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String EMPTY_DATE = "1111-11-11 00:00:00";
    public static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 日期转字符串。（按指定格式）
     *
     * @param date
     * @param format 转换的格式(yyyy-MM-dd)
     * @return 字符串
     */
    public static String toStrFromDate(Date date, String format) {
        if (date == null || StringUtil.isEmpty(date.toString()) || EMPTY_DATE.equals(date.toString())) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(format);// 性能更优
        String d = "";

        try {
            d = sdf.format(date);
        } catch (Exception e) {
            logger.error("date = " + date + "时间转换异常:", e.getMessage(), e);
        }
        return d;
    }

    /**
     * 字符串转日期。（按指定格式）
     *
     * @param dateStr
     * @param format  转换的格式(yyyy-MM-dd)
     * @return 日期
     */
    public static Date toDateFromStr(String dateStr, String format) {
        if (StringUtil.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("dateStr = " + dateStr + " 时间转换异常:", e.getMessage(), e);
        }
        return d;
    }

    /**
     * Date 转 Date
     *
     * @param date
     * @param format 转换的格式(yyyy-MM-dd/yyyy-MM-dd HH:mm:ss/.....)
     * @return 日期 Thu Feb 08 11:10:01 CST 2018 -->  Thu Feb 08 00:00:00 CST 2018
     */
    public static Date toDateFromDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = sf.parse(sf.format(date));
        } catch (ParseException e) {
            logger.error("date = " + date + " 时间转换异常:", e.getMessage(), e);
            return null;
        }
        return result;
    }

    /**
     * 字符串转字符串。（按指定格式 相同格式转换 或者 带有时分秒的格式转不带时分秒的格式）
     *
     * @param dateStr
     * @param format
     * @return 2012-02-01 12:12:12,yyyy-MM-dd ---->  2012-02-01
     */
    public static String toStrFromStr(String dateStr, String format) {
        if (StringUtil.isEmpty(dateStr)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(sdf.parse(dateStr));
        } catch (ParseException e) {
            logger.error("dateStr = " + dateStr, " 时间转换异常:", e.getMessage(), e);
            return "";
        }
    }

    /**
     * 将String类型的转化为String类型的日期格式(按照yyyy-MM-dd的格式)
     *
     * @param dateStr
     * @return 2012/12/12  --->  2012-12-12
     */
    public static String toStrFromStr(String dateStr) {

        if (StringUtil.isEmpty(dateStr)) {
            return "";
        }
        dateStr = dateStr.trim();

        if (DateUtil.isValidDate(dateStr, YYYY_MM_DD_HMS)) {// 如果符合yyyy-MM-dd HH:mm:ss格式
            return toStrFromStr(dateStr, YYYY_MM_DD);
        }

        if (DateUtil.isValidDate(dateStr, YMD_T_HMS)) {
            return toStrFromStr(dateStr, YYYY_MM_DD);
        }
        if (DateUtil.isValidDate(dateStr, YYYY_MM_DD)) {// 如果符合yyyy-MM-dd格式
            return dateStr;
        }


        if (dateStr.matches("\\d{4}/\\d{1,2}/\\d{1,2}")) {// 如果符合yyyy/MM/dd格式
            String year = dateStr.substring(0, 4);
            String[] mm = dateStr.substring(5, dateStr.length()).split("/");
            String month = StringUtils.leftPad(mm[0], 2, "0");
            String day = StringUtils.leftPad(mm[1], 2, "0");
            String ymd = year + "-" + month + "-" + day;
            if (DateUtil.isValidDate(ymd, YYYY_MM_DD)) {
                return ymd;
            }
        }
        if (dateStr.matches("\\d{4}\\d{2}\\d{2}")) {// 如果符合yyyyMMdd格式
            String year = dateStr.substring(0, 4);
            String month = dateStr.substring(4, 6);
            String day = dateStr.substring(6, 8);
            String ymd = year + "-" + month + "-" + day;
            if (DateUtil.isValidDate(ymd, YYYY_MM_DD)) {
                return ymd;
            }
        }
        if (dateStr.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {// 如果符合yyyy年MM月dd日格式
            String year = dateStr.substring(0, 4);
            String[] mm = dateStr.substring(5, dateStr.length()).split("月");
            String month = StringUtils.leftPad(mm[0], 2, "0");
            String day = StringUtils.leftPad(mm[1].replace("日", ""), 2, "0");
            String ymd = year + "-" + month + "-" + day;
            if (DateUtil.isValidDate(ymd, YYYY_MM_DD)) {
                return ymd;
            }
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
            return dateFormat.format(new Date(Long.parseLong(dateStr)));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 把各种Object的数据类型转换为Date数据
     *
     * @param obj
     * @return 2012-12-12  -->  2012-12-12
     */
    public static final String toStrFromObject(Object obj, String format) {
        String dataStr = StringUtil.toString(obj);
        return toStrFromStr(dataStr, format);
    }

    /**
     * Object的数据类型转换为Date数据
     *
     * @param value
     * @return 2012-12-12   ->   2012-12-12
     */
    public static final Date toDateFromObject(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        long longValue = 0;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.indexOf('/') != -1) {
                strVal = strVal.replace("/", "-");
            }
            if (strVal.indexOf('-') != -1) {
                String format;
                if (strVal.length() == 10) {
                    format = YYYY_MM_DD;
                } else if (strVal.length() == YYYY_MM_DD_HMS.length()) {
                    format = YYYY_MM_DD_HMS;
                } else {
                    format = YYYY_MM_DD_HMSS;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    logger.error("时间转化异常: strVal=" + strVal + "", e.getMessage(), e);
                    return null;
                }
            }
            if (strVal.length() == 8) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(YYYYMMDD);
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    logger.error("时间转化异常: strVal=" + strVal + "", e.getMessage(), e);
                    return null;
                }
            }

            if (strVal.length() == 14) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(YYYYMMDDHMS);
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    logger.error("时间转化异常: strVal=" + strVal + "", e.getMessage(), e);
                    return null;
                }
            }

            if (strVal.length() == 0) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue <= 0) {
            return null;
        }

        return new Date(longValue);
    }

    /**
     * 特殊处理-日期格式转换yyyy-MM-dd'T'HH:mm:ss  (yyyy-MM-dd'T'HH:mm:ss) TO  yyyy-MM-dd HH:mm:ss
     *
     * @param oldDateStr
     * @param format     return 2012-12-12T12:12:12 --->  2012-12-12 12:12:12
     */
    public static String toStrFromStrT(String oldDateStr, String format) {
        DateFormat df = new SimpleDateFormat(YMD_T_HMS);  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        try {
            Date date = df.parse(oldDateStr);
//			SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
//			Date date1 =  df1.parse(date.toString());
            DateFormat df2 = new SimpleDateFormat(format);
            return df2.format(date);
        } catch (ParseException e) {
            logger.error("oldDateStr = " + oldDateStr + " 时间转换异常:", e.getMessage(), e);
            return "";
        }
    }


    /**
     * 日期字符串格式统一转为yyyy-MM-dd格式
     *
     * @param dateStr
     * @return 二零一二年十二月二十二号 -> 2012-12-20
     */
    public static String formatDate(String dateStr) {
        dateStr = dateStr.replace("/", "-");
        dateStr = dateStr.replace("／", "-");
        dateStr = dateStr.replace("年", "-");
        dateStr = dateStr.replace("十月", "10-");
        dateStr = dateStr.replace("十一月", "11-");
        dateStr = dateStr.replace("十二月", "12-");
        dateStr = dateStr.replace("月", "-");
        dateStr = dateStr.replace("二十日", "20");
        dateStr = dateStr.replace("三十日", "30");
        dateStr = dateStr.replace("十日", "10");
        dateStr = dateStr.replace("二十号", "20");
        dateStr = dateStr.replace("三十号", "30");
        dateStr = dateStr.replace("十号", "10");
        dateStr = dateStr.replace("一十", "1");
        dateStr = dateStr.replace("二十", "2");
        dateStr = dateStr.replace("三十", "3");
        dateStr = dateStr.replace("十", "1");
        dateStr = dateStr.replace("壹拾", "1");
        dateStr = dateStr.replace("贰拾", "2");
        dateStr = dateStr.replace("叁拾", "3");
        dateStr = dateStr.replace("拾", "");
        dateStr = dateStr.replace("一", "1");
        dateStr = dateStr.replace("壹", "1");
        dateStr = dateStr.replace("二", "2");
        dateStr = dateStr.replace("贰", "2");
        dateStr = dateStr.replace("三", "3");
        dateStr = dateStr.replace("叁", "3");
        dateStr = dateStr.replace("四", "4");
        dateStr = dateStr.replace("肆", "4");
        dateStr = dateStr.replace("五", "5");
        dateStr = dateStr.replace("伍", "5");
        dateStr = dateStr.replace("六", "6");
        dateStr = dateStr.replace("陆", "6");
        dateStr = dateStr.replace("七", "7");
        dateStr = dateStr.replace("柒", "7");
        dateStr = dateStr.replace("八", "8");
        dateStr = dateStr.replace("捌", "8");
        dateStr = dateStr.replace("玖", "9");
        dateStr = dateStr.replace("九", "9");
        dateStr = dateStr.replace("零", "0");
        dateStr = dateStr.replace("〇", "0");
        dateStr = dateStr.replace("○", "0");
        dateStr = dateStr.replace("o", "0");
        dateStr = dateStr.replace("拾", "1");
        dateStr = dateStr.replace("日", "");
        dateStr = dateStr.replace("号", "");
        return dateStr;
    }

    /**
     * 获得传入时间的0-24点
     *
     * @param day
     * @param hour 【0-24】
     * @return 24点为23:59:59 | 2018-02-07  ->  Thu Feb 08 00:00:00 CST 2018
     */
    public static Date getTimesByHour(Date day, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);

        if (hour == 24) {
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.MILLISECOND, 59);
        } else {
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }

        return cal.getTime();
    }
    /**
     * 获得指定日期所在周的时间
     *
     * @param date
     * @param day  [1-7] 周一到周日
     * @return 日期
     */
    public static Date getDateByWeek(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (day == 7) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            c.add(Calendar.DAY_OF_WEEK, 6);
        } else {
            c.set(Calendar.DAY_OF_WEEK, day + 1);
        }
        return c.getTime();
    }

    /**
     * 获得本月第一天0点时间
     *
     * @return Thu Feb 01 00:00:00 CST 2018
     */
    public static Date getFirstDayByMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获得本月最后一天24点时间
     *
     * @return Wed Feb 28 23:59:59 CST 2018
     */
    public static Date getLastDayByMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


    /**
     * 获取指定月的第一天
     *
     * @param date new Date()-->2018 2月
     * @return Thu Feb 01 00:00:00 CST 2018
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date new Date()-->2018 2月
     * @return Wed Feb 28 23:59:59 CST 2018
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获取指定月上个月第一天
     * @param dateStr
     * @param format
     * @return
     */
    public static String getMonthStart(String dateStr,String format){
        Date date = toDateFromStr(dateStr,format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.add(Calendar.DATE,1);
        return toStrFromDate(cal.getTime(), format);
    }

    /**
     * 获取指定月上个月最后一天
     * @param dateStr
     * @param format
     * @return
     */
    public static String getMonthEnd(String dateStr,String format){
        Date date = toDateFromStr(dateStr,format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DATE,-1);
        return toStrFromDate(cal.getTime(), format);
    }


    /**
     * 获取指定月下个月第一天
     * @param dateStr
     * @return
     */
    public static  String getNextMonthStart(String dateStr,String format){
        Date date = toDateFromStr(dateStr,format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.add(Calendar.DATE,1);
        return toStrFromDate(cal.getTime(), format);
    }

    /**
     * 获取指定日历字段加上该字段相应量级的时间
     *
     * @param date
     * @param format （Calendar.YEAR | Calendar.MONTH | Calendar.DATE）
     * @param num    数量（可为负数）
     * @return (Date)2012-02-01,Calendar.Year,1  --->  (Date)2013-02-01
     */
    public static Date getDateDiff(Date date, int format, int num) {
        if (date == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(format, num);
        return now.getTime();
    }

    /**
     * 获取当前的年份
     *
     * @return
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前的月份
     *
     * @return
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当月的天数
     *
     * @return
     */
    public static int getDaysByMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 比较两个日期的大小,如果结束日期大于开始日期返回0,如果小于等于的话返回1
     * 但是由于开始经过调整,开始时间一般都小于结束时间,所以大多数情况都返回0
     * 只有在开始日期和结束日期只有相差一个月以内的时候会返回1(因为开始日期计算的时候需要加一个月)
     *
     * @param start
     * @param end
     * @return end<=start  -->  1 | end>staet  -->  0
     */
    public static int getMonthNew(Date start, Date end) {
        //确保start的时间小于end的日期
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }

        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sFormat.parse(sFormat.format(start));
            end = sFormat.parse(sFormat.format(end));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            logger.error("时间转换异常: start=" + start + "end=" + end + " ", e.getMessage(), e);
            e.printStackTrace();
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        //开始日期加上一个月,这里就有可能出现开始日期大于结束日期的情况
        startCalendar.add(Calendar.MONTH, 1);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        if (endCalendar.compareTo(startCalendar) <= 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 计算两个日期相差几个月
     *
     * @param date1
     * @param date2
     * @return 如果返回-1说明日期有问题
     */
    public static int getMonthsBetween(String date1, String date2) {
        if (StringUtil.isEmpty(date1) || StringUtil.isEmpty(date2)) {
            return -1;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(toDateFromStr(date1, YYYY_MM_DD));
        c2.setTime(toDateFromStr(date2, YYYY_MM_DD));
        int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return Math.abs(year * 12 + result);
    }

    /**
     * 计算两个日期相差的年份
     *
     * @param date1
     * @param date2
     * @return 2012-01-01,2013-02-01  -->  1
     */
    public static int getYearsBetween(String date1, String date2) {
        if (StringUtil.isEmpty(date1) || StringUtil.isEmpty(date2)) {
            return -1;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(toDateFromStr(date1, YYYY_MM_DD));
        c2.setTime(toDateFromStr(date2, YYYY_MM_DD));
        return Math.abs(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR));
    }

    /**
     * 计算两个日期之间相差的时间(long)
     *
     * @param fromDate
     * @param toDate
     * @return 相差时间(绝对值)
     */
    public static long getTimesBetweenNew(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null) {
            return -1;
        }
        return Math.abs(toDate.getTime() - fromDate.getTime());
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDaysBetween(Date beginDate, Date endDate) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        long beginTime = beginCalendar.getTime().getTime();
        long endTime = endCalendar.getTime().getTime();
        int betweenDays = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));

        endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);
        if (beginCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH))//比较两日期的DAY_OF_MONTH是否相等
            return betweenDays + 1; //相等说明确实跨越了一天
        else
            return betweenDays + 0; //不相等说明确实未跨天或者间隔超过一天以上
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param dateStr1 String
     * @param dateStr2 String
     * @param format   String
     * @return 如果返回-1 说明日期格式不正确
     */
    public static int getDaysBetween(String dateStr1, String dateStr2, String format) {
        Date date1 = toDateFromStr(dateStr1, format);
        Date date2 = toDateFromStr(dateStr2, format);
//		if (date1 == null || date2 == null){
//			return -1;
//		}
//		LocalDate ld1 = getLocalDate(date1);
//		LocalDate ld2 = getLocalDate(date2);
        return getDaysBetween(date1, date2);
    }

    /**
     * 将Date格式的日期转换为LocalDate格式(只用于以上两个方法做日期转换用)
     *
     * @param date
     * @return
     */
    public static LocalDate getLocalDate(Date date) {
        //获取该日期下的格林尼治标准时间
        Instant instant = date.toInstant();
        //获取当前时区
        ZoneId zoneId = ZoneId.systemDefault();
        //通过当前时区的时间转化为LocalDate日期格式
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * 判断两个日期是否相同(年月日)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    /**
     * 判断时间格式
     * 只有符合格式才为true
     *
     * @param str
     * @param format
     * @return boolean 2012-12-12,yyyy-MM-dd HH:mm:ss ---> false
     */
    public static boolean isValidDate(String str, String format) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            dateFormat.setLenient(false);
            dateFormat.parse(str);
        } catch (Exception e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
//            logger.error("str=" + str + " 时间转化异常:", e.getMessage(), e);
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 判断是否是闰年
     *
     * @param dateStr
     * @return
     */
    public static boolean isLeapYear(String dateStr) {
        if (StringUtil.isEmpty(dateStr)) {
            return false;
        }
        try {
            int year = Integer.parseInt(dateStr.substring(0, 4));
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("数字转换异常:dateStr=" + dateStr, e.getMessage(), e);
        }
        return false;
    }

    /**
     * 比较日期大小
     * @param before
     * @param after
     * @return
     */
    public static int compareDate(String before,String after){
        Date d1 = toDateFromStr(before,YYYY_MM_DD);
        Date d2 = toDateFromStr(after,YYYY_MM_DD);
        if (d1.getTime() > d2.getTime()) {
            return 1;
        } else if (d1.getTime() < d2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Date类型按照yyyy-MM-dd HH:mm:ss的格式转为String(现在改为toStrFromDate()方法)
     *
     * @param date
     * @return
     */
    @Deprecated
    public static String getStrFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HMS);
        if (date == null || StringUtil.isEmpty(date.toString()) || EMPTY_DATE.equals(date.toString())) {
            return "您是第一次登录";
        }
        return sdf.format(date);
    }

    /**
     * Date类型按照yyyy-MM-dd的格式转为String(现在改为toStrFromDate()方法)
     *
     * @param date
     * @return
     */
    @Deprecated
    public static String getStrFromDateExceptTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null || StringUtil.isEmpty(date.toString()) || EMPTY_DATE.equals(date.toString())) {
            return "";
        }
        return sdf.format(date);
    }

    /**
     * 将String按照yyyy-MM-dd HH:mm:ss的类型转为Date(现在改为toDateFromStr()方法)
     *
     * @param dateStr
     * @return
     */
    @Deprecated
    public static Date getDateFromStr(String dateStr) {

        return toDateFromStr(dateStr, YYYY_MM_DD_HMS);
    }

    /**
     * 将String按照一定格式转为Date(现在改为toDateFromStr()方法)
     *
     * @param dateStr
     * @param format
     * @return
     */
    @Deprecated
    public static Date getFormatDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("dateStr = " + dateStr + " 时间转换异常:", e.getMessage(), e);
            return d;
        }
        return d;
    }

    /**
     * 把Date的日期按照一定格式转化为字符串类型的日期(现在改为toStrFromDate)
     *
     * @param date
     * @param format 转换的格式(yyyy-MM-dd/yyyy-MM-dd HH:mm:ss....)
     * @return
     */
    @Deprecated
    public static String getFormatDateStr(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date == null || StringUtil.isEmpty(date.toString()) || EMPTY_DATE.equals(date.toString())) {
            return "";
        }
        return sdf.format(date);
    }

    /**
     * 获得当天0点时间(统一改为getTimesByHour())
     *
     * @return
     */
    @Deprecated
    public static Date getTimesMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得当天24点时间(统一改为getTimesByHour())
     *
     * @return
     */
    @Deprecated
    public static Date getTimesNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得传入时间的24点(统一改为getTimesByHour())
     *
     * @param day
     * @return
     */
    @Deprecated
    public static Date getTimesNight(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得本周一0点时间
     *
     * @return
     */
    @Deprecated
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获得本周日24点时间
     *
     * @return
     */
    @Deprecated
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    /**
     * 获得本月第一天0点时间(改为getFirstDayByMonth())
     *
     * @return
     */
    @Deprecated
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


    /**
     * 获得本月最后一天24点时间(改为getLastDayByMonth())
     *
     * @return
     */
    @Deprecated
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    /**
     * 获取指定日历字段加上该字段相应量级的时间(统一改为getDateDiff())
     *
     * @param date
     * @param field
     * @param amount
     * @return
     */
    @Deprecated
    public static Date add(Date date, int field, int amount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 得到几天后的时间(统一改为getDateDiff())
     *
     * @param d
     * @param day
     * @return
     */
    @Deprecated
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 获取几个月之后的时间(统一改为getDateDiff())
     *
     * @return
     */
    @Deprecated
    public static Date getMonthAfter(int n) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, n);
//        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
//        String lastMonth = dft.format(cal.getTime());
//        return lastMonth;
        return cal.getTime();
    }

    /**
     * 比较大小
     */
    @Deprecated
    public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }

        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sFormat.parse(sFormat.format(start));
            end = sFormat.parse(sFormat.format(end));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        startCalendar.add(Calendar.MONTH, 1);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        if (endCalendar.compareTo(startCalendar) <= 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 得到一年前的时间(统一改为getDateDiff()
     *
     * @param d
     * @return
     */
    @Deprecated
    public static Date getDateLastYear(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        return cal.getTime();
    }

    /**
     * Object  ->  Date(改为toDateFromObject())
     *
     * @param value
     * @return
     */
    @Deprecated
    public static final Date castToDate(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        long longValue = 0;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.indexOf('/') != -1) {
                strVal = strVal.replace("/", "-");
            }
            if (strVal.indexOf('-') != -1) {
                String format;
                if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                try {
                    return (Date) dateFormat.parse(strVal);
                } catch (ParseException e) {
                    return null;
                }
            }
            if (strVal.length() == 8) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                try {
                    return (Date) dateFormat.parse(strVal);
                } catch (ParseException e) {
                    return null;
                }
            }

            if (strVal.length() == 0) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue <= 0) {
            return null;
        }

        return new Date(longValue);
    }

    /**
     * 计算两个日期之间相差的时间(改为timesBetweenNew())
     *
     * @param fromDate 较小的时间
     * @param toDate   较大的时间
     * @return 相差天数
     */
    @Deprecated
    public static long timesBetween(Date fromDate, Date toDate) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(toDate);
            long time2 = cal.getTimeInMillis();

            return time2 - time1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将日期格式格式统一转换为"yyyy-MM-dd"(改为toStrFromStr)
     * 如果遇到异常，则返回原字符串
     *
     * @param str
     * @return
     */
    @Deprecated
    public static String toStrFormatDate(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(sdf.parse(str));
        } catch (ParseException e) {
            return str;
        }
    }

    @Deprecated
    public static String toStrFormatDate(String str, String format) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(sdf.parse(str));
        } catch (ParseException e) {
            return str;
        }
    }

    /**
     * 改为(toStrFromStrT)
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss  (yyyy-MM-dd'T'HH:mm:ss) TO  yyyy-MM-dd HH:mm:ss
     *
     * @throws ParseException
     */
    @Deprecated
    public static String dealDateFormat(String oldDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        try {
            Date date = df.parse(oldDateStr);
//			SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
//			Date date1 =  df1.parse(date.toString());
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df2.format(date);
        } catch (ParseException e) {
            return oldDateStr;
        }
    }

    public static String toHourFromMinutes(int minutes) {
        DecimalFormat df = new DecimalFormat("#0.00");
        double d = minutes / 60.0;
        if ((d * 1000) % 10 == 0) {
            return (int) d + "";
        } else {
            return df.format(d) + "";
        }
    }

    public static void main(String[] args) throws Exception {


        System.out.println(new Date("Mon Jan 02 00:00:01 CST 2012").compareTo(new Date("Mon Jan 02 00:00:00 CST 2012")));
        //System.out.println(toStrFromStr("2018-01-02 10:00:00",DateUtil2.YYYY_MM_DD));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr1 = "2011-05-09";
        String dataStr2 = "2012-06-11";
        Date d1 = sdf.parse(dataStr1);
        Date d2 = sdf.parse(dataStr2);

        long l1 = System.currentTimeMillis();
        System.out.println("时间差:" + getDaysBetween(d1, d2));
        for (int i = 0; i < 1000000; i++) {
            getDaysBetween(d1, d2);
        }
        long l2 = System.currentTimeMillis();
        System.out.print("耗时:");
        System.out.println(l2 - l1);

        long l3 = System.currentTimeMillis();

        long l4 = System.currentTimeMillis();
        System.out.print("耗时:");
        System.out.println(l4 - l3);

        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    }
}
