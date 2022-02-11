package cn.usoul.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 */
public class DateUtil {

    /**
     * yyyyMMdd
     * @return
     */
    public static int today(){
        return toIntDate(LocalDate.now());
    }

    /**
     * 时间转换成数字
     * 20220101
     * @param localDate
     * @return
     */
    public static int toIntDate(LocalDate localDate){
        return Integer.parseInt(String.format("%d%02d%02d",localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth()));
    }

    /**
     * 时间转换成数字
     * 20220101
     * @param localDateTime
     * @return
     */
    public static int toIntDate(LocalDateTime localDateTime){
        return toIntDate(localDateTime.toLocalDate());
    }

    /**
     * 时间转换成数字
     * 20220101000000
     * @param localDateTime
     * @return
     */
    public static long toLongDatetime(LocalDateTime localDateTime){
        return Integer.parseInt(String.format("%d%02d%02d%02d%02d%02d%02d",localDateTime.getYear(),localDateTime.getMonthValue(),localDateTime.getDayOfMonth()));
    }

    /**
     * 字符串转时间
     * @param datetime   yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime parseDateTimeString(String datetime){
        LocalDateTime localDateTime=LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTime;
    }

    /**
     * 返回指定时间的0点
     * @param date yyyy-MM-dd
     * @return
     */
    public static LocalDateTime parseDateString(String date){
        LocalDateTime localDateTime=LocalDateTime.parse(date+"T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return localDateTime;
    }

}
