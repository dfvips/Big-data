package com.zdata.sycm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 时间工具类
 * 
 * @author huangzhuozhen
 * @time 2019年3月25日 14:44:57
 *
 */
public class TimeUtil {
	
    
    private static final String Monday = "1";
    private static final String Tuesday = "2";
    private static final String Wednesday = "3";
    private static final String Thursday = "4";
    private static final String Friday = "5";
    private static final String Saturday = "6";
    private static final String Sunday = "7";
    
    private static final String MondayStr = "一";
    private static final String TuesdayStr = "二";
    private static final String WednesdayStr = "三";
    private static final String ThursdayStr = "四";
    private static final String FridayStr = "五";
    private static final String SaturdayStr = "六";
    private static final String SundayStr = "日";
    
	
	private static final String SDF_yMd = "yyyy-MM-dd ";
	
	private static final SimpleDateFormat Work_Base_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat Work_SimpleBase_SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final long Minutes = 60*1000;
	/**
	 * 
	 * 根据传入格式,返回时间值
	 * <br>格式如:yyyy-MM-dd HH:mm:ss
	 * 
	 * @param format
	 * @return
	 */
	public static String getNowTime(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String nowTime = sdf.format(new Date());//获取系统当前时间
		return nowTime;
	}
	
	/**
	 * 
	 * 返回yyyy-MM-dd HH:mm:ss格式数据
	 * 
	 * @return
	 */
	public static String getNowTime(){
		return getNowTime("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * 传入旧的时间,返回格式化后的时间
	 * <br>注意旧时间的精度必须大于等于格式化的精度
	 * 
	 * @param oldTime 旧时间
	 * @param format 格式化模板
	 * @return
	 */
	public static String getFormateTime(String oldTime,String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = Work_Base_SDF.parse(oldTime);
		} catch (ParseException e) {
			try {
				date = Work_SimpleBase_SDF.parse(oldTime);
			} catch (ParseException e1) {
			}
		}
		String retTime = formatter.format(date);
		return retTime;
	}
	
	/**
	 * 获取传入的时间与当前时间的差值,单位为分钟
	 * @param time 格式为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long getDifferenceTime(String time){
		long differenceTime = 0;
		Date nowDate = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat(SDF_yMd + time);
//		String plantTime = sdf.format(nowDate);
		String plantTime = time;
		try {
			Calendar plantCal = Calendar.getInstance();
			Date plantDate = Work_Base_SDF.parse(plantTime);
			plantCal.setTime(plantDate);
			
			Calendar currCal = Calendar.getInstance();
			currCal.setTime(nowDate);
			differenceTime = (plantCal.getTimeInMillis() - currCal.getTimeInMillis())/Minutes;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return differenceTime;
	}
	
	/**
	 * 获取传入的时间与当前时间的差值,单位为分钟
	 * @param time 格式为 HH:mm:ss
	 * @return
	 */
	public static long getDifferenceTimeToday(String time){
		long differenceTime = 0;
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(SDF_yMd + time);
		String plantTime = sdf.format(nowDate);
		try {
			Calendar plantCal = Calendar.getInstance();
			Date plantDate = Work_Base_SDF.parse(plantTime);
			plantCal.setTime(plantDate);
			
			Calendar currCal = Calendar.getInstance();
			currCal.setTime(nowDate);
			
			differenceTime = (plantCal.getTimeInMillis() - currCal.getTimeInMillis())/Minutes;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return differenceTime;
	}
	
	
    /**
     * 传入日期是否与当天的星期相同
     * @param week
     * @return
     */
    public static boolean checkTodayWeek(String week){
    	if(week == null || week.equals("")){
    		return false;
    	}
    	Calendar today = Calendar.getInstance();
    	int calendarWeekDay = today.get(Calendar.DAY_OF_WEEK);
    	int weekInt = 0;
    	
    	switch (week) {
		case Monday:
			weekInt = Calendar.MONDAY;
			break;
		case Tuesday:
			weekInt = Calendar.TUESDAY;
			break;
		case Wednesday:
			weekInt = Calendar.WEDNESDAY;
			break;
		case Thursday:
			weekInt = Calendar.THURSDAY;
			break;
		case Friday:
			weekInt = Calendar.FRIDAY;
			break;
		case Saturday:
			weekInt = Calendar.SATURDAY;
			break;
		case Sunday:
			weekInt = Calendar.SUNDAY;
			break;
		}
    	if(calendarWeekDay == weekInt){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 
     * 获取传入日期为星期几
     * 
     * @param specialDay
     * @return
     */
    public static int getRealWeek(Calendar specialDay){
    	Integer calendarWeekDay = specialDay.get(Calendar.DAY_OF_WEEK);
    	String realWeek = null;
    	switch (calendarWeekDay) {
    	case Calendar.MONDAY:
    		realWeek = Monday;
    		break;
    	case Calendar.TUESDAY:
    		realWeek = Tuesday;
    		break;
    	case Calendar.WEDNESDAY:
    		realWeek = Wednesday;
    		break;
    	case Calendar.THURSDAY:
    		realWeek = Thursday;
    		break;
    	case Calendar.FRIDAY:
    		realWeek = Friday;
    		break;
    	case Calendar.SATURDAY:
    		realWeek = Saturday;
    		break;
    	case Calendar.SUNDAY:
    		realWeek = Sunday;
    		break;
    	}
    	return realWeek==null?0:Integer.valueOf(realWeek);
    }
	
    /**
     * 
     * 获取传入日期为星期几
     * 
     * @param calendarWeekDay
     * @return
     */
    public static int getRealWeek(Integer calendarWeekDay){
    	String realWeek = null;
    	switch (calendarWeekDay) {
    	case Calendar.MONDAY:
    		realWeek = Monday;
    		break;
    	case Calendar.TUESDAY:
    		realWeek = Tuesday;
    		break;
    	case Calendar.WEDNESDAY:
    		realWeek = Wednesday;
    		break;
    	case Calendar.THURSDAY:
    		realWeek = Thursday;
    		break;
    	case Calendar.FRIDAY:
    		realWeek = Friday;
    		break;
    	case Calendar.SATURDAY:
    		realWeek = Saturday;
    		break;
    	case Calendar.SUNDAY:
    		realWeek = Sunday;
    		break;
    	}
    	return realWeek==null?0:Integer.valueOf(realWeek);
    }
    
    /**
     * 
     * 获取传入日期为星期几
     * 
     * @param calendarWeekDay
     * @return
     */
    public static String getRealWeekStr(Integer calendarWeekDay){
    	String realWeek = null;
    	switch (calendarWeekDay) {
    	case Calendar.MONDAY:
    		realWeek = MondayStr;
    		break;
    	case Calendar.TUESDAY:
    		realWeek = TuesdayStr;
    		break;
    	case Calendar.WEDNESDAY:
    		realWeek = WednesdayStr;
    		break;
    	case Calendar.THURSDAY:
    		realWeek = ThursdayStr;
    		break;
    	case Calendar.FRIDAY:
    		realWeek = FridayStr;
    		break;
    	case Calendar.SATURDAY:
    		realWeek = SaturdayStr;
    		break;
    	case Calendar.SUNDAY:
    		realWeek = SundayStr;
    		break;
    	}
    	return realWeek;
    }
	
	/**
	 * 
	 * 获取当月的最大天数
	 * 
	 * @param realYear
	 * @param realMonth
	 * @return
	 */
	public static int getMaxDayOfMonth(Integer realYear, Integer realMonth){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, realYear);
		cal.set(Calendar.MONTH, realMonth);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 
	 * 获取当月的第一天对应的星期
	 * 
	 * @param realYear
	 * @param realMonth
	 * @return
	 */
	public static int getMinWeekOfMonth(int realYear, int realMonth){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, realYear);
		cal.set(Calendar.MONTH, realMonth-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 获取某天对应的星期
	 * @param realYear
	 * @param realMonth
	 * @param realDay
	 * @return
	 */
	public static int getWeekOfMonthByDay(int realYear, int realMonth,int realDay){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, realYear);
		cal.set(Calendar.MONTH, realMonth-1);
		cal.set(Calendar.DAY_OF_MONTH, realDay);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 计算时间差，毫秒单位
	 * @param 开始时间
	 * @param 结束时间
	 * rxh
	 */
	public static long getDistanceTime(String time1, String time2) {
    	long minutes = 0L;
        try {
            Date d1 = Work_SimpleBase_SDF.parse(time1);
            Date d2 = Work_SimpleBase_SDF.parse(time2);
            minutes = d2.getTime()-d1.getTime();
        } catch (ParseException e) {
			e.printStackTrace();
        }
        return minutes;
    }
	
	/**
	 * 根据年月 获取该月所有日   传入格式  yyyy-MM 
	 * @param startTime 月份  2019-04
	 * rxh
	 */
	public static List<String> getMonthFullDay(String startTime){
		try {
			int year = Integer.parseInt(startTime.split("-")[0]);
			int month = Integer.parseInt(startTime.split("-")[1]);
			return getMonthFullDay(year, month,null);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据年月日 获取该月到指定日期前一天的所有日   传入格式  yyyy-MM-dd 
	 * @param startTime 月份   2019-04-26
	 * rxh
	 */
	public static List<String> getMonthAppointDay(String startTime){
		try {
			int year = Integer.parseInt(startTime.split("-")[0]);
			int month = Integer.parseInt(startTime.split("-")[1]);
			int day = Integer.parseInt(startTime.split("-")[2]);
			day = day -1;
			return getMonthFullDay(year, month,day);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据年月 获取该月所有日
	 * @param year
	 * @param month
	 * rxh
	 */
	public static List<String> getMonthFullDay(int year , int month,Integer day){
	    List<String> fullDayList = new ArrayList<>(32);
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, month-1 );
	    cal.set(Calendar.DAY_OF_MONTH,1); // 当月1号
	    int count = 0;
	    if (day != null) {
	    	count = day;
		}else {
			count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//当月的最大天数
		}
	    for (int j = 1; j <= count ; j++) {
	        fullDayList.add(Work_SimpleBase_SDF.format(cal.getTime()));
	        cal.add(Calendar.DAY_OF_MONTH,1);
	    }
	    return fullDayList;
	}
	/**
	 * 根据年月日 获取该月所有周以及包含的日期
	 * rxh
	 */
	public static List<Map<Integer, List<String>>> getMonthFullWeek(String startTime){
		List<String> monthFullDay = getMonthFullDay(startTime);
		List<Map<Integer, List<String>>> weekList = new ArrayList<>();
		Map<Integer, List<String>> map =null;
		List<String> list = null;
		Integer oldWeek = null;
		for (String day : monthFullDay) {
			Integer week = getWeekOfYear(day);
			if (week != oldWeek) {
				map = new HashMap<>();
				list = new ArrayList<>();
				map.put(week, list);
				weekList.add(map);
				oldWeek = week;
			}
			list.add(day);
		}		
		return weekList;
	}
	/**
	 * 根据年月日 获取该周开始日 和结束日
	 * rxh
	 */
	@SuppressWarnings("static-access")
	public static String[] getWeekFullDay(String startTime) {
		String[] strs = new String[2];
		try {
			Calendar calendar = Calendar.getInstance();
			Date d = Work_SimpleBase_SDF.parse(startTime);
			calendar.setTime(d);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			strs[0] = Work_SimpleBase_SDF.format(calendar.getTime());
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 6);
			strs[1] = Work_SimpleBase_SDF.format(calendar.getTime());
			return strs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据年月日  获取日期属于当年第几周
	 * rxh
	 */
	public static Integer getWeekOfYear(String startTime){
		try {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			Date d = Work_SimpleBase_SDF.parse(startTime);
			cal.setTime(d);			
		    return cal.get(Calendar.WEEK_OF_YEAR);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据年   获取该年所有月
	 * @param year
	 * rxh
	 */
	public static List<String> getYearFullMonth(String startTime){
		int year = Integer.parseInt(startTime.split("-")[0]);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
	    List<String> fullDayList = new ArrayList<>();
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, 0);
	    int count = cal.getActualMaximum(Calendar.MONTH)+1;
	    for (int j = 1; j <= count ; j++) {
	        fullDayList.add(sf.format(cal.getTime()));
	        cal.add(Calendar.MONTH,1);
	    }
	    return fullDayList;
	}
}
