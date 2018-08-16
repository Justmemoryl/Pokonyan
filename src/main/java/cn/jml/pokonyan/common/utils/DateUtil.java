package cn.jml.pokonyan.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具类
 * 
 * @version 1.0 created by chenzhenwei on 2018年6月19日 下午5:44:53
 */
public class DateUtil {
    /** 格式：yyyy-MM-dd HH:mm:ss.S */
    public static final String FULL_STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    /** 格式：yyyy-MM-dd HH:mm:ss */
    public static final String STANDARD_DATETIME_FORMAT      = "yyyy-MM-dd HH:mm:ss";
    /** 格式：yyyy-MM-dd HH:mm */
    public static final String INCOMPLETE_DATETIME_FORMAT    = "yyyy-MM-dd HH:mm";
    /** 格式：yyyyMMddHHmmss */
    public static final String SHORT_DATETIME_FORMAT         = "yyyyMMddHHmmss";
    /** 格式：yyyyMMddHHmmssS */
    public static final String SHORT_FULL_DATETIME_FORMAT    = "yyyyMMddHHmmssSSS";
    /** 格式：yyyyMMddHHmm */
    public static final String DYNAMICS_DATETIME_FORMAT      = "yyyy-MM-dd HH:mm";
    /** 格式：yyyy-MM-dd */
    public static final String STANDARD_DATE_FORMAT          = "yyyy-MM-dd";
    /** 格式：yyyyMMdd */
    public static final String SHORT_DATE_FORMAT             = "yyyyMMdd";
    /** 格式：MMdd */
    public static final String SHORT_MMDD_FORMAT             = "MMdd";
    /** 格式：HHmmss */
    public static final String SHORT_TIME_FORMAT             = "HHmmss";
    /** 格式: yyyy-MM-dd'T'HH:mm:ss'Z' */
    public static final String UTC_DATETIME_FORMAT           = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String SHORT_YYMM_FORMAT             = "yyyyMM";
    /** 格式：MM-dd */
    public static final String TIME_FORMAT                   = "HH:mm";

    private DateUtil() {}

    /**
     * 格式完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
     *
     * @param date
     *            日期时间
     * @return 完全日期时间字符串
     */
    public static String formatFullStandardDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_STANDARD_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     *            日期时间
     * @return 日期时间字符串
     */
    public static String formatStandardDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DYNAMICS_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     *            日期时间
     * @return 日期时间字符串
     */
    public static String formatStandardDateTime2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param date
     *            日期时间
     * @return 日期时间字符串
     */
    public static String formatShortDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期时间字符串（短格式：yyyyMMddHHmmssS）
     *
     * @param date
     *            日期时间
     * @return 日期时间字符串
     */
    public static String formatShortFullDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_FULL_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param date
     *            日期时间
     * @param zone
     *            时区
     * @return 日期时间字符串
     */
    public static String formatShortDateTime(Date date, TimeZone zone) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        sdf.setTimeZone(zone);
        return sdf.format(date);
    }

    /**
     * 格式日期字符串（标准格式：yyyy-MM-dd）
     *
     * @param date
     *            日期
     * @return 日期字符串
     */
    public static String formatStandardDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期字符串（短格式：yyyyMMdd）
     *
     * @param date
     *            日期
     * @return 日期字符串
     */
    public static String formatShortDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期字符串（短格式：yyyyMMdd）
     *
     * @param date
     *            日期
     * @param zone
     *            时区
     * @return 日期字符串
     */
    public static String formatShortDate(Date date, TimeZone zone) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        sdf.setTimeZone(zone);
        return sdf.format(date);
    }

    /**
     * 格式日期字符串（短格式：MMdd）
     *
     * @param date
     *            日期
     * @return 日期字符串
     */
    public static String formatShortMMDDDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_MMDD_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式时间字符串（短格式：HHmmss）
     *
     * @param date
     *            时间
     * @return 时间字符串
     */
    public static String formatShortTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_TIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * add by wangqi
     * 把日期按照格式格式化
     */
    public static String formatTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 解析完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
     *
     * @param dateTimeStr
     *            完全日期时间字符串
     * @return 日期时间
     * @throws ParseException
     */
    public static Date parseFullStandardDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_STANDARD_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 解析日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param dateTimeStr
     *            日期时间字符串
     * @return 日期时间
     * @throws ParseException
     */
    public static Date parseStandardDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 解析日期时间字符串 (不完整格式：yyyy-MM-dd HH:mm)
     *
     * @param dateTimeStr
     * @return
     * @throws ParseException
     */
    public static Date parseIncompleteDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(INCOMPLETE_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }

    public static String formatIncompleteDateTime(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(INCOMPLETE_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 解析日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param dateTimeStr
     *            日期时间字符串
     * @return 日期时间
     * @throws ParseException
     */
    public static Date parseShortDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 解析日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param dateTimeStr
     *            日期时间字符串
     * @param zone
     *            时区
     * @return 日期时间
     * @throws ParseException
     */
    public static Date parseShortDateTime(String dateTimeStr, TimeZone zone) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        sdf.setTimeZone(zone);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 解析日期字符串（标准格式：yyyy-MM-dd）
     *
     * @param dateStr
     *            日期字符串
     * @return 日期
     * @throws ParseException
     */
    public static Date parseStandardDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 解析日期字符串（短格式：yyyyMMdd）
     *
     * @param dateStr
     *            日期字符串
     * @return 日期
     * @throws ParseException
     */
    public static Date parseShortDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 解析日期字符串（短格式：yyyyMMdd）
     *
     * @param dateStr
     *            日期字符串
     * @param zone
     *            时区
     * @return 日期
     * @throws ParseException
     */
    public static Date parseShortDate(String dateStr, TimeZone zone) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        sdf.setTimeZone(zone);
        return sdf.parse(dateStr);
    }

    /**
     * 解析日期字符串（短格式：MMdd）
     *
     * @param dateStr
     *            日期字符串
     * @return 日期
     * @throws ParseException
     */
    public static Date parseShortMMDDDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_MMDD_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 解析时间字符串（短格式：HHmmss）
     *
     * @param dateStr
     *            时间字符串
     * @return 时间
     * @throws ParseException
     */
    public static Date parseShortTime(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_TIME_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 获取传入日期的上一天
     *
     * @param date
     * @return 日期
     */
    public static Date getStandardDatePre(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取传入日期的下一天
     *
     * @param date
     * @return 日期
     */
    public static Date getStandardDateNext(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * 获取指定日期的0时0分0秒
     *
     * @param date
     * @return
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     *
     * @Title: getYearAndMonth
     * @author weiqingping
     * @dateTime 2017年4月13日 上午11:10:39
     * @email weiqingping@migu.cn
     * @Description: TODO 获取某事件的年月 格式为yyyyMM
     * @param @param date
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String getYearAndMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_YYMM_FORMAT);

        return sdf.format(date);
    }

    /**
     *
     * @Title: getCurrentYearAndMonth
     * @author weiqingping
     * @dateTime 2017年4月13日 上午11:13:55
     * @email weiqingping@migu.cn
     * @Description: TODO 获取当年时间的年月 格式为 yyyyMM
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public final static String getCurrentYearAndMonth() {
        return getYearAndMonth(new Date());
    }

    /**
     *
     * @Title: getLastYearAndMonth
     * @author weiqingping
     * @dateTime 2017年4月27日 下午3:16:56
     * @email weiqingping@migu.cn
     * @Description: 获取上个月的年
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public final static String getLastYearAndMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);

        return getYearAndMonth(cal.getTime());
    }

    /**
     * 获取当天的0时0分0秒
     *
     * @return
     */
    public static Date getStartTimeOfToday() {
        return getStartTimeOfDay(new Date());
    }

    /**
     * 获取当前日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @return 日期时间字符串
     */
    public static String getStandardNowDateTime() {
        return formatStandardDateTime(new Date());
    }

    /**
     * 获取当前日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @return 日期时间字符串
     */
    public static String getStandardNowDateTime2() {
        return formatStandardDateTime2(new Date());
    }

    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(new Date());
    }

    // add by wangqi,根据格式获取时间
    public static String getNowTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    // add by wangqi
    /* 时间比大小 */
    public static int timeCompare(String t1, String t2) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(formatter.parse(t1));
        c2.setTime(formatter.parse(t2));
        int result = c1.compareTo(c2);
        return result;
    }
    // add by wangqi

    /**
     * 往前推n月的月份
     * 
     * @param backNum
     * @return
     */
    public final static String getBeforeYearAndMonth(int backNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -backNum);

        return getYearAndMonth(cal.getTime());
    }

    /**
     * 获取当前日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @return 日期时间字符串
     */
    public static String getShortNowDateTime() {
        return formatShortDateTime(new Date());
    }

    /**
     * 获取当前日期字符串（标准格式：yyyy-MM-dd）
     *
     * @return 日期字符串
     */
    public static String getStandardNowDate() {
        return formatStandardDate(new Date());
    }

    /**
     * 获取当前日期字符串（短格式：yyyyMMdd）
     *
     * @return 日期字符串
     */
    public static String getShortNowDate() {
        return formatShortDate(new Date());
    }

    /**
     * 获取当前日期时间字符串（格式：yyyy-MM-dd'T'HH:mm:ss'Z'）
     * <p>
     * UTC时间对应的时区是GMT-0:00
     *
     * @return 日期时间字符串
     */
    public static String getUTCDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getUTCTimeStr());
    }

    /**
     * 更新时间 添中或减少(time为负数)
     * 
     * @param d
     *            需要修改的时候
     * @param type
     *            修改的时间类型 Calendar.HOUR_OF_DAY、Calendar.MINUTE之类的
     * @param time
     *            添加的数量
     * @return 添加后的时候
     */
    public static Date addTime(Date d, int type, int time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(type, time);
        return cal.getTime();
    }

    /**
     *
     * @Title: compareDate
     * @author weiqingping
     * @dateTime 2017年4月13日 下午1:43:28
     * @email weiqingping@migu.cn
     * @Description: TODO比较时间
     * @param @param smdate
     * @param @param bdate
     * @param @return
     * @param @throws ParseException 设定文件
     * @return int 返回类型
     * @throws
     */
    public final static int compareDate(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        Date begainDate = sdf.parse(smdate);
        Date enfDate = sdf.parse(bdate);
        return begainDate.compareTo(enfDate);
    }

    /**
     *
     * @Title: getStandardDateAfter
     * @author weiqingping
     * @dateTime 2017年4月27日 下午12:07:57
     * @email weiqingping@migu.cn
     * @Description: 获取 days天后的时间
     * @param @param date
     * @param @param days 为正数则为 days天后 为负数 则为days天前
     * @param @return 设定文件
     * @return Date 返回类型
     * @throws
     */
    public static Date getStandardDateAfter(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 根据格式转换日期
     * add by wangqi
     * 
     * @param createTime
     * @param formate
     * @return
     */
    public static Date parseTime(String createTime, String formate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.parse(createTime);

    }

    /**
     * add by wangqi
     * 计算两个日期的间隔天数
     * bdate - smdate 计算的时间
     * 
     * @param formate
     * @return
     */
    public static int daysBeteen(String smdate, String bdate, String formate) throws ParseException {
        if (StringUtils.isEmpty(formate)) {
            formate = SHORT_DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));

    }

    /*
     * public static void main(String[] args)
     * {
     * try
     * {
     * System.out.println(DateUtils.formatIncompleteDateTime(
     * DateUtils.addTime(DateUtils.parseShortDateTime("20170221190725"), Calendar.HOUR_OF_DAY, 8)));
     * System.out.println(getNowTime() );
     * }
     * catch (ParseException e)
     * {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * }
     * }
     */

    /**
     * add by wangqi 根据格式，把字符时间转成时间戳
     * 
     * @param time
     * @param format
     * @return
     * @throws ParseException
     */
    public static long toUnixTime(String time, String format) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        Date date = formatDate.parse(time);
        return date.getTime();
    }

    /**
     * 获取与当前时间间隔days（前或后）天的指定格式字符串
     * 
     * @param format
     * @param days
     * @return
     */
    public static String getTimeFormatAfter(String format, int days) {
        Date date = getStandardDateAfter(new Date(), days);
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 获取月份的开始时间
     * 
     * @param date
     * @return
     */
    public static Date getStartTimeOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getStartTimeOfCurrentMonth() {
        return getStartTimeOfMonth(new Date());
    }

    // add by wangqi 获取当天最后的时间戳
    public static long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);

        return todayEnd.getTimeInMillis();
    }

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String getUTCTimeStr() {
        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day);
        UTCTimeBuffer.append(" ").append(hour).append(":").append(minute);
        try {
            format.parse(UTCTimeBuffer.toString());
            return UTCTimeBuffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // add by wq
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (StringUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    // 验证日期格式 add by wnagqi
    public static boolean isValidDate(String str, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = (Date) formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }

    }
}
