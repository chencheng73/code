package com.zfpt.framework.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 项目名称：sg_purchase 
 * 类名称：DateUtils 
 * 类描述：时间转换工具类 
 * 创建人：chens 
 * 创建时间：2013-9-17
 * @version
 */
public class DateUtils  {
	
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 方法名称: currentDatetime ； 
	 * 方法描述: 返回当前时间 日期 时间格式yyyy-MM-dd HH:mm:ss 
	 * 返回类型:String ；
	 * @throws
	 */
	public static String currentDatetime() {
		return datetimeFormat.format(now());
	}

	/**
	 *方法名称: formatDatetime ；
	 *方法描述: 转换时间格式 ； 日期时间格式yyyy-MM-dd HH:mm:ss 
	 *返回类型:String ； 
	 * @throws
	 */
	public static String formatDatetime(Date date) {
		return datetimeFormat.format(date);
	}

	
	/**
	 * 方法名称: formatDatetime ； 
	 * 方法描述: 转换时间格式 ； 日期时间格式yyyy-MM-dd HH:mm:ss 
	 * 返回类型:String ；  
	 * @throws
	 */
	public static String formatDatetime(Date date, String pattern) {
		SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat.clone();
		customFormat.applyPattern(pattern);
		return customFormat.format(date);
	}

	/**
	 * 方法名称: parseDatetime ； 
	 * 方法描述: 根据自定义pattern将字符串日期转换成java.util.Date类型 ； 
	 * 返回类型:Date ；
	 * @throws
	 */
	public static Date parseDatetime(String datetime, String pattern)
			throws ParseException {
		SimpleDateFormat format = (SimpleDateFormat) datetimeFormat.clone();
		format.applyPattern(pattern);
		return format.parse(datetime);
	}

	/**
	 * 方法名称: currentDate ； 
	 * 方法描述: 获取当前日期 ； 
	 * 返回类型: String ；
	 * @throws
	 */
	public static String currentDate() {
		return dateFormat.format(now());
	}

	/**
	 * 方法名称: getCurrentTimeMillis ； 
	 * 方法描述: 获取时间戳 ； 
	 * 返回类型: long ； 
	 * @throws
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 方法名称: month ； 
	 * 方法描述: 获取当前月份； 
	 * 返回类型: int ；
	 * @throws
	 */
	public static int month() {
		return calendar().get(Calendar.MONTH) + 1;
	}

	/**
	 * 方法名称: calendar ； 
	 * 方法描述: 获取当前日期 ； 
	 * 返回类型: Calendar ；
	 * 创建人：chens ；
	 * 创建时间：2013-9-17 下午10:18:15；
	 * @throws
	 */
	protected static Calendar calendar() {
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	protected static Date now() {
		return new Date();
	}

	/**
	 * 方法名称: addHour； 
	 * 方法描述:设置过期时间； 
	 * 返回类型: String ； 
	 * 创建人：chens ； 
	 * 创建时间：2013-10-16上午12:38:31；
	 * @throws
	 */
	public static String addHour(Date currentDate,int hour)  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.HOUR, hour);
		return formatDatetime(cal.getTime());
	}
	 
	 
	 /**
	  * 方法名称: getSysTimestamp 
	  * 方法描述: 获取时间戳   
	  * 返回类型: Timestamp 
	  * @throws
	  */
	 public static Timestamp getSysTimestamp(){
        return new Timestamp(System.currentTimeMillis());
	 }
	
	 
	public static String getCurrentTime( String pattern ){
		return formatDatetime( new Date() , pattern ) ;
	}
	
}
