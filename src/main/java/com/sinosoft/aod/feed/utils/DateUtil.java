package com.sinosoft.aod.feed.utils;

import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LONGLEI on 2018/1/19.
 * 日期操作工具类
 */
public class DateUtil {

    private static DateUtil dateUtil;
    private DateUtil(){
    }
    /**
     * 获取DateUtil实例
     */
    public static DateUtil getInstance(){
        if(dateUtil==null){
            synchronized(DateUtil.class){
                if(dateUtil==null){
                    dateUtil=new DateUtil();
                }
            }
        }
        return dateUtil;
    }
    /**
     * 根据传入的日期格式化输出
     * yyyy-MM-dd 的字符串时间
     *
     * @param date      时间
     * @param formatStr yyyy-MM-dd等时间串
     *
     * @return
     */
    public static String formatDate(Date date, String formatStr){
        SimpleDateFormat formatter=new SimpleDateFormat(formatStr);
        String outDate=formatter.format(date);
        return outDate;
    }
    /**
     * 将传入的字符串转化为对应的日期格式
     *
     * @param s          时间字符串
     * @param formateStr 格式化的时间格式
     *
     * @return
     */
    public static Date parseDate(String s,String formateStr){
        SimpleDateFormat formatter=new SimpleDateFormat(formateStr);
        Date date;
        try{
            date=formatter.parse(s);
            return date;
        }catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 比较两个时间的大小
     *
     * @param dateTime1 时间1
     * @param dateTime2 时间2
     *
     * @return 0 表示相等,返回 1 表示日期1>日期2,返回 -1 表示日期1<日期2
     */
    public static int compareDate(Date dateTime1,Date dateTime2){
        return dateTime1.compareTo(dateTime2);
    }
    /**
     * 比较两个时间的大小
     *
     * @param dateTime1 时间1
     * @param dateTime2 时间2
     * @param formatStr 格式化时间
     *
     * @return 0 表示相等,返回 1 表示日期1>日期2,返回 -1 表示日期1<日期2
     */
    public static int compareDate(Date dateTime1,Date dateTime2,String formatStr){
        String t1=formatDate(dateTime1,formatStr);
        String t2=formatDate(dateTime2,formatStr);
        return compareDate(parseDate(t1,formatStr),parseDate(t2,formatStr));
    }
    /**
     * 比较多个时间的大小,获取比时间大的或者时间小的集合
     *
     * @param date      被比较时间
     * @param dateList  比较的时间集合
     * @param formatStr 格式化时间
     * @param flag      true表示获取大的,false表示获取小的
     *
     * @return 时间结果集
     */
    public static List<Date> maxOrMinDate(Date date, List<Date> dateList, String formatStr, boolean flag){
        List<Date> resultDate = Lists.newArrayList();
        String t1=formatDate(date,formatStr);
        if(flag){//获取大的
            for(Date d:dateList){
                String t2=formatDate(d,formatStr);
                if(compareDate(parseDate(t1,formatStr),parseDate(t2,formatStr))==-1){
                    resultDate.add(d);
                }
            }
        }else{//获取小的
            for(Date d:dateList){
                String t2=formatDate(d,formatStr);
                if(compareDate(parseDate(t1,formatStr),parseDate(t2,formatStr))==1){
                    resultDate.add(d);
                }
            }
        }
        return resultDate;
    }
    /**
     * 时间减法
     *
     * @param strDate 时间字符串格式
     * @param day     要增加的天数
     * @param format  时间格式
     *
     * @return
     */
    public static Long addDate(String strDate,int day,String format){
        Date sDate=parseDate(strDate,format);
        Calendar date=Calendar.getInstance();
        date.setTime(sDate);
        date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH)+day);
        return date.getTimeInMillis();
    }
    /**
     * 时间转换为Long类型
     * @param strDate 时间字符串格式
     * @param format  时间格式
     *
     * @return
     */
    public static Long parseDateStr(String strDate,String format){
        Date sDate=parseDate(strDate,format);
        Calendar date=Calendar.getInstance();
        date.setTime(sDate);
        return date.getTimeInMillis();
    }

    public static Long parseDate(Date date,String format){
        //        SimpleDateFormat dft = new SimpleDateFormat(format);
        Calendar cdate=Calendar.getInstance();
        cdate.setTime(date);
        return cdate.getTimeInMillis();
    }


    /**
     * 时间long 类型转换成String 类型
     * @param currentTime
     * @param formatType
     * @return
     */
    public static String longToString(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = formatDate(dateOld, formatType); // 把date类型的时间转换为string
        return sDateTime;
    }

    /**
     * 取某日期下的月份的第一天
     * @return
     */
    public static Long  getMonthFirstDay(String datestr ,String format) {
        Date sDate=DateUtil.getInstance().parseDate(datestr, format);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(sDate);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH,1);//
        return DateUtil.getInstance().parseDate(calendar.getTime(), "yyyy-MM-dd");

    }

    /**
     * 取某日期下的下一月份的第一天
     * @return
     */
    public static Long getLastMonthFirstDay(String datestr,String format) {
        Date sDate=DateUtil.getInstance().parseDate(datestr, format);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(sDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH,1);//
        return DateUtil.getInstance().parseDate(calendar.getTime(), "yyyy-MM-dd");

    }

    /**
     * 日期转换
     * @param time
     * @param format
     * @return
     */
    public static String longToStr(Long time,String format){
        if(time == null || time == 0){
            return "";
        }else{
            return new SimpleDateFormat(format).format(new Date(time));
        }
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * Long时间转换为正常的日期格式
     * @param time
     * @param format
     * @return
     */
    public static String pareDate(Long time,String format){
        if(time == null || time == 0){
            return "";
        }else{
            return new SimpleDateFormat(format).format(new Date(time));
        }
    }

    /**
     * 正常的日期格式转换为Long数据
     * @param dateStr
     * @return
     */
    public  static Long parseDateStr(String dateStr){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return c.getTimeInMillis();
    }

    /**
     * 年月日期格式转换为Long数据
     * @param dateStr
     * @return
     */
    public  static Long parseDateToStr(String dateStr){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return c.getTimeInMillis();
    }

    /**
     * 比较两个日期
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static String getDateBefore(Date d, int day, String dateFormat) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(now.getTime());
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static String getDateAfter(Date d, int day, String dateFormat) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(now.getTime());
    }
}
