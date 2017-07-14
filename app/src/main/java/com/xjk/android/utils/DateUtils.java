package com.xjk.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	private static String[] WEEK = new String[] { "天", "一", "二", "三", "四", "五", "六" };

	private static final long ONE_SECOND = 1000;
	private static final long ONE_MINUTE = ONE_SECOND * 60;
	private static final long ONE_HOUR = ONE_MINUTE * 60;
	private static final long ONE_DAY = ONE_HOUR * 24;

	/**
	 * String 转换 Date
	 */
	public static Date string2Date(String str, String format) {
		try {
			return new SimpleDateFormat(format,Locale.CHINA).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * Date（long） 转换 String
	 */
	public static String date2String(long time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);
		return sdf.format(time);
	}

	/**
	 * Date（long） 转换 String
	 * 2016年11月7日 18:23
	 */
	public static String date2String(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		return sdf.format(time);
	}

	public static String date2FullString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		return sdf.format(time);
	}

	/**
	 *
	 * @param time
	 * @return
     */
	public static String date2MMDDHHmm(long time){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm",Locale.CHINA);
		return sdf.format(time);
	}
	/**
	 * long 去除 时分秒 时分秒全部为0
	 */
	public static long getYearMonthDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取目标时间和当前时间之间的差距
	 */
	public static String getTimestampString(Date date) {
		Date curDate = new Date();
		long splitTime = curDate.getTime() - date.getTime();
		if (splitTime < (30 * ONE_DAY)) {
			if (splitTime < ONE_MINUTE) {
				return "刚刚";
			}
			if (splitTime < ONE_HOUR) {
				return String.format(Locale.CHINA,"%d分钟前", splitTime / ONE_MINUTE);
			}

			if (splitTime < ONE_DAY) {
				return String.format(Locale.CHINA,"%d小时前", splitTime / ONE_HOUR);
			}

			return String.format(Locale.CHINA,"%d天前", splitTime / ONE_DAY);
		}
		String result = "yyyy年MM月dd日 HH:mm";
		return (new SimpleDateFormat(result, Locale.CHINA)).format(date);
	}

	/**
	 * 获取目标时间和当前时间之间的差距
	 */
	public static String getTimestampString(long date) {
		long splitTime = System.currentTimeMillis() - date;
		if (splitTime < (30 * ONE_DAY)) {
			if (splitTime < ONE_MINUTE) {
				return "刚刚";
			}
			if (splitTime < ONE_HOUR) {
				return String.format(Locale.CHINA,"%d分钟前", splitTime / ONE_MINUTE);
			}

			if (splitTime < ONE_DAY) {
				return String.format(Locale.CHINA,"%d小时前", splitTime / ONE_HOUR);
			}

			return String.format(Locale.CHINA,"%d天前", splitTime / ONE_DAY);
		}
		String result = "yyyy年MM月dd日 HH:mm";
		return (new SimpleDateFormat(result, Locale.CHINA)).format(date);
	}

	/**
	 * 24小时制 转换 12小时制
	 */
	public static String time24To12(String time) {
		String str[] = time.split(":");
		int h = Integer.valueOf(str[0]);
		int m = Integer.valueOf(str[1]);
		String sx;
		if (h < 1) {
			h = 12;
			sx = "上午";
		} else if (h < 12) {
			sx = "上午";
		} else if (h < 13) {
			sx = "下午";
		} else {
			sx = "下午";
			h -= 12;
		}
		return String.format(Locale.CHINA,"%d:%02d%s", h, m, sx);
	}

	/**
	 * Date 转换 HH
	 */
	public static String date2HH(Date date) {
		return new SimpleDateFormat("HH",Locale.CHINA).format(date);
	}

	/**
	 * Date 转换 HH:mm:ss
	 */
	public static String date2HHmm(Date date) {
		return new SimpleDateFormat("HH:mm",Locale.CHINA).format(date);
	}

	/**
	 * Date 转换 HH:mm:ss
	 */
	public static String date2HHmmss(Date date) {
		return new SimpleDateFormat("HH:mm:ss",Locale.CHINA).format(date);
	}

	/**
	 * Date 转换 MM.dd
	 */
	public static String date2MMdd(Date date) {
		return new SimpleDateFormat("MM.dd",Locale.CHINA).format(date);
	}

	/**
	 * Date 转换 yyyy.MM.dd
	 */
	public static String date2yyyyMMdd(Date date) {
		return new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA).format(date);
	}

	/**
	 * Date 转换 MM月dd日 星期
	 */
	public static String date2MMddWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return new SimpleDateFormat("MM月dd日 星期",Locale.CHINA).format(date) + WEEK[dayOfWeek - 1];
	}

	/**
	 * Date 转换 yyyy年MM月dd日 星期
	 */
	public static String date2yyyyMMddWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return new SimpleDateFormat("yyyy年MM月dd日 星期",Locale.CHINA).format(date) + WEEK[dayOfWeek - 1];
	}

}
