package com.zzw.utils.datetoweek;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeekOfDate {

    private static final String[] weekDays = {"周日","周一","周二","周三","周四","周五","周六"};
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //获取当前日期的具体的星期几
    public static String getWeekOfDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(w < 0){
            w = 0;
        }
        return weekDays[w];
    }

    //获取当前日期的具体的星期几
    public static String getWeekOfDate(String date){
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(w < 0){
            w = 0;
        }
        return weekDays[w];
    }


    //获取当前周的星期日的具体的date
    public static String getThisWeekDay(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return sdf.format(cal.getTime());
    }


    /**
     * 这个函数获取本周第一天到今天位置的所有的日期的字符串，应该是最重要的函数之一了
     * */
    //编写一个函数获取到本周的所有截止到今天为止的日期
    public static List<String> getDates(Date date){
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //本周的第一天就是周日的日期就是指这个了
        String sundayStr = sdf.format(cal.getTime());
        //知道为什么还要用这一步吗？因为calendar自动生成的星期天它的时间不是00:00:00这会导致比较的时候不足n天的天数的！！！
        sundayStr += " 00:00:00";
        Date sunday = null;
        try {
            sunday = sdf.parse(sundayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dates.add(sdf.format(cal.getTime()));

        int days = (int) ((date.getTime() - sunday.getTime()) / (1000*3600*24));
        //获取今天和本周的第一天的相差的天数

        long tmp = sunday.getTime();
        for (int i = 0; i < days; i++) {
            tmp += 1000*3600*24;
            Date tmpDate = new Date(tmp);
            dates.add(sdf.format(tmpDate));
        }

        return dates;
    }




}
