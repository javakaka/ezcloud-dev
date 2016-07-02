package com.ezcloud.framework.util;
import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
/**
 * 参考 httpclient项目的 类：org.apache.http.client.utils.DateUtils
 * 
 */

import com.ezcloud.framework.vo.DataSet;
import com.ezcloud.framework.vo.Row;

/**
 * 时间工具类
 * @author Administrator
 */
public class DateUtil {
	public static String _DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static String _DATE = "yyyy-MM-dd";
	public static String _TIME = "HH:mm:ss";
	public static String getCurrentDate() {
		return getCurrentDateTime(_DATE);
	}

	public static String getCurrentDateTime() {
		return getCurrentDateTime(_DATETIME);
	}

	public static String getCurrentDateTime(final String sFormat) {
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(sFormat);
		return oSimpleDateFormat.format(new Date());
	}

	public static Date getDate(Date paramDate, int paramInt1, int paramInt2) {
		Calendar oCalendar;
		oCalendar = Calendar.getInstance();
		oCalendar.setTime(paramDate);
		if (paramInt1 == 12)
			oCalendar.add(12, paramInt2);
		else if (paramInt1 == 10)
			oCalendar.add(10, paramInt2);
		else if (paramInt1 == 5)
			oCalendar.add(5, paramInt2);
		else if (paramInt1 == 13)
			oCalendar.add(13, paramInt2);
		return oCalendar.getTime();
	}

	/**
	 * 计算日期差，返回毫秒数
	 * @param paramDate1
	 * @param paramDate2
	 * @return
	 */
	public static long spend(Date paramDate1, Date paramDate2) {
		return paramDate1.getTime() - paramDate2.getTime();
	}
	
	/**
	 * 根据指定的时间格式返回日期对象
	 * @param dateString
	 * @param sFormat
	 * @return
	 */
	public static Date toDate(String dateString, String sFormat) {
		try {
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(sFormat);
			Date oDate = oSimpleDateFormat.parse(dateString);
			return oDate;
		} catch (ParseException e) {
		}
		return null;
	}
	
	/**
	 * 返回指定格式的日期字符串
	 * @param paramDate
	 * @param sFormat
	 * @return
	 */
	public static String toString(Date paramDate, String sFormat) {
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(sFormat);
		return oSimpleDateFormat.format(paramDate);
	}

	/**
	 * 验证字符串是否是合法的指定格式的日期类型字符串
	 * @param sFormat
	 * @param dateString
	 * @return
	 */
	public static boolean validDate(String sFormat, String dateString) {
		try {
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(sFormat);
			oSimpleDateFormat.parse(dateString);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据日期取得该天对应的星期几的值
	 * iType=1表示取当周的第几天，2表示取当年的第几天
	 * @throws ParseException 
	 */
	public static String getDayNameOfWeekOrYear(String sDate,int iType) 
	{
		String sDayName ="";
		if(sDate == null || sDate.trim().equals(""))
		{
			return null;
		}
		Calendar cal = Calendar.getInstance();
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
		Date date;
		try {
			date = oSimpleDateFormat.parse(sDate);
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    int dow = cal.get(Calendar.DAY_OF_WEEK);
	    int doy = cal.get(Calendar.DAY_OF_YEAR);
	    //取星期几
	    if(iType ==1)
	    {
	    	switch(dow)
	    	{
	    	case 1:
	    		sDayName ="日";
	    		break;
	    	case 2:
	    		sDayName ="一";
	    		break;
	    	case 3:
	    		sDayName ="二";
	    		break;
	    	case 4:
	    		sDayName ="三";
	    		break;
	    	case 5:
	    		sDayName ="四";
	    		break;
	    	case 6:
	    		sDayName ="五";
	    		break;
	    	case 7:
	    		sDayName ="六";
	    		break;
	    	}
	    }
	    else if(iType==2) 
	    {
	    	sDayName =""+doy;	 
	    }
		return sDayName;
	}
	
	/**
	 * 取一个月当中最大的一天
	 * @param sDate
	 * @return
	 */
	public static int getMaxDayOfMonth(String sDate)
	{
		int day =1;
		if(sDate == null || sDate.trim().equals(""))
		{
			return -1;
		}
		Calendar cal = Calendar.getInstance();
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
		Date date;
		try {
			date = oSimpleDateFormat.parse(sDate);
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day =cal.getActualMaximum(Calendar.DATE);
		return day;
	}
	
	/**
	 * 取一个月当中最小的一天
	 * @param sDate
	 * @return
	 */
	public static int getMinDayOfMonth(String sDate)
	{
		int day =1;
		if(sDate == null || sDate.trim().equals(""))
		{
			return -1;
		}
		Calendar cal = Calendar.getInstance();
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
		Date date;
		try {
			date = oSimpleDateFormat.parse(sDate);
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day =cal.getActualMinimum(Calendar.DATE);
		return day;
	}
	
	/**
	 * 获取两个时间相差的分钟数
	 * @param sdate_begin
	 * @param sdate_end
	 * @return
	 */
	public static long getMinuteMinusOfTwoTime(String sdate_begin,String sdate_end)
	{
		long minus =0;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
		Date date_begin =null;
		Date date_end =null;
		try {
			date_begin =oSimpleDateFormat.parse(sdate_begin);
			date_end =oSimpleDateFormat.parse(sdate_end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long date1 =date_begin.getTime();
		long date2 =date_end.getTime();
		minus =date2-date1;
		minus =minus/1000/60;
		return minus;
	}
	
	/**
	 * 获取两个时间相差的秒数
	 * @param sdate_begin
	 * @param sdate_end
	 * @return
	 */
	public static long getSecondMinusOfTwoTime(String sdate_begin,String sdate_end)
	{
		long minus =0;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
		Date date_begin =null;
		Date date_end =null;
		try {
			date_begin =oSimpleDateFormat.parse(sdate_begin);
			date_end =oSimpleDateFormat.parse(sdate_end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long date1 =date_begin.getTime();
		long date2 =date_end.getTime();
		minus =date2-date1;
		minus =minus/1000;
		return minus;
	}
	
	/**
	 * 获取两个时间相差的小时数
	 * @param sdate_begin
	 * @param sdate_end
	 * @return
	 */
	public static long getHourMinusOfTwoTime(String sdate_begin,String sdate_end)
	{
		long minus =0;
		if(sdate_begin.trim().length() == 10)
		{
			sdate_begin +=" 00:00:00";
		}
		else if(sdate_begin.length() == 13)
		{
			sdate_begin +=":00:00";
		}
		else if(sdate_begin.length() == 16)
		{
			sdate_begin +=":00";
		}
		
		if(sdate_end.trim().length() == 10)
		{
			sdate_end +=" 00:00:00";
		}
		else if(sdate_end.length() == 13)
		{
			sdate_end +=":00:00";
		}
		else if(sdate_end.length() == 16)
		{
			sdate_end +=":00";
		}
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
		Date date_begin =null;
		Date date_end =null;
		try {
			date_begin =oSimpleDateFormat.parse(sdate_begin);
			date_end =oSimpleDateFormat.parse(sdate_end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long date1 =date_begin.getTime();
		long date2 =date_end.getTime();
		minus =date2-date1;
		minus =minus/1000/60/60;
		return minus;
	}
	
	public static long getDayMinusOfTwoTime(String sdate_begin,String sdate_end)
	{
		long minus =0;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
		Date date_begin =null;
		Date date_end =null;
		try {
			date_begin =oSimpleDateFormat.parse(sdate_begin);
			date_end =oSimpleDateFormat.parse(sdate_end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long date1 =date_begin.getTime();
		long date2 =date_end.getTime();
		minus =date2-date1;
		minus =minus/1000/60/60/24;
		return minus;
	}
	
	/**
	 * 取指定日期的后一天对应的日期
	 * @param curDate
	 * @param sdf
	 * @return
	 */
	public static Date getNextDate(String curDate,SimpleDateFormat sdf)
	{
		Date date =null;
		if ( curDate == null || curDate.trim().length() == 0 ) {
			return date;
		}
		Calendar calendar =Calendar.getInstance();
		try
		{
			date =sdf.parse(curDate);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if ( date == null ) {
			return date;
		}
		calendar.setTime(date);
		int day =calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day+1);
		date =calendar.getTime();
		return date;
	}
	
	/**
	 * 取指定日期的前一天对应的日期
	 * @param curDate
	 * @param sdf
	 * @return
	 */
	public static Date getPreDate(String curDate,SimpleDateFormat sdf)
	{
		Date date =null;
		if ( curDate == null || curDate.trim().length() == 0 ) {
			return date;
		}
		Calendar calendar =Calendar.getInstance();
		try
		{
			date =sdf.parse(curDate);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if ( date == null ) {
			return date;
		}
		calendar.setTime(date);
		int day =calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day-1);
		date =calendar.getTime();
		return date;
	}
	
	/**
	 *比较两个日期的大小
	 * @param begin
	 * @param end
	 * @param sdf
	 * @return
	 */
	public static long compare(String begin,String end,SimpleDateFormat sdf)
	{
		long f =0;
		Date begin_date =null;
		Date end_date =null;
		try {
			begin_date = sdf.parse(begin);
			end_date =sdf.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long l1 =begin_date.getTime();
		long l2 =end_date.getTime();
		if(l1 < l2)
		{
			f =-1;
		}
		else if(l1 == l2)
		{
			f =0;
		}
		else
		{
			f =1;
		}
		
		return f; 
	}
	
	/**
	 * 检测字符串是否符合指定的日期格式
	 * @param date
	 * @return
	 */
	public static boolean checkDateFormatYYYY_MM_DD(String date)
	{
		boolean f =true;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
		try 
		{
			oSimpleDateFormat.parse(date);
		} catch (ParseException e) 
		{
			f =false;
		}
		return f;
	}
	
	public static boolean checkDateFormatYYYY_MM_DD_HH(String date)
	{
		boolean f =true;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor("yyyy-MM-dd HH");
		try 
		{
			oSimpleDateFormat.parse(date);
		} catch (ParseException e) 
		{
			f =false;
		}
		return f;
	}
	
	public static boolean checkDateFormatYYYY_MM_DD_HH_MM(String date)
	{
		boolean f =true;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor("yyyy-MM-dd HH:mm");
		try 
		{
			oSimpleDateFormat.parse(date);
		} catch (ParseException e) 
		{
			f =false;
		}
		return f;
	}
	
	public static boolean checkDateFormatYYYY_MM_DD_HH_MM_SS(String date)
	{
		boolean f =true;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
		try 
		{
			oSimpleDateFormat.parse(date);
		} catch (ParseException e) 
		{
			f =false;
		}
		return f;
	}
	
	/**
	 * 取指定时间的上一个小时
	 * @param snowtime
	 * @return
	 */
		public static String getPreHourTime(String snowtime)
		{
			String pre =null;
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
			Date nowdate =null;
			try {
				nowdate =oSimpleDateFormat.parse(snowtime);
				nowdate.setHours(nowdate.getHours()-1);
				pre =oSimpleDateFormat.format(nowdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return pre;
		}
		/**
		 * 取指定时间的下一个小时
		 * @param snowtime
		 * @return
		 */
		public static String getNextHourTime(String snowtime)
		{
			String pre =null;
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
			Date nowdate =null;
			try {
				nowdate =oSimpleDateFormat.parse(snowtime);
				nowdate.setHours(nowdate.getHours()+1);
				pre =oSimpleDateFormat.format(nowdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return pre;
		}
		
		/**
		 * 比较时间字符串的大小
		 * @param begin
		 * @param end
		 * @return
		 * @throws ParseException
		 */
		public static long compare (String begin,String end) throws ParseException
		{
			long l=0;
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
			Date d1 =oSimpleDateFormat.parse(begin);
			Date d2 =oSimpleDateFormat.parse(end);
			long l1 =d1.getTime();
			long l2 =d2.getTime();
			l =l1 -l2;
			return l;
		}
		
		/**
		 * 根据指定日期，取该日期所属星期的第一天的日期（星期顺序为日，一，二...六）
		 * @param yyyy_mm_dd
		 * @return
		 * @throws ParseException 
		 */
		public static String getFirstDayOfWeek(String yyyy_mm_dd) throws ParseException
		{
			String date ="";
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
			Date specifiedDate = new Date();
			specifiedDate =oSimpleDateFormat.parse(yyyy_mm_dd);
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(specifiedDate);
			 int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			 cal.add(Calendar.DATE, -day_of_week);
			 date =oSimpleDateFormat.format(cal.getTime());
			return date;
		}
		/**
		 * 根据指定日期，取该日期所属星期的第一天的日期（星期顺序为日，一，二...六）
		 * @param yyyy_mm_dd
		 * @return
		 * @throws ParseException 
		 */
		public static String getFirstDayOfWeekOfCurrentDate() throws ParseException
		{
			String date ="";
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
			Date specifiedDate = new Date();
			String yyyy_mm_dd =getCurrentDate();
			specifiedDate =oSimpleDateFormat.parse(yyyy_mm_dd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(specifiedDate);
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			cal.add(Calendar.DATE, -day_of_week);
			date =oSimpleDateFormat.format(cal.getTime());
			return date;
		}
		
		/**
		 * 根据指定日期，取该日期所属星期的最后一天的日期（星期顺序为日，一，二...六）
		 * @param yyyy_mm_dd
		 * @return
		 * @throws ParseException 
		 */
		public static String getLastDayOfWeek(String yyyy_mm_dd) throws ParseException
		{
			String date ="";
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
			Date specifiedDate = new Date();
			specifiedDate =oSimpleDateFormat.parse(yyyy_mm_dd);
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(specifiedDate);
			 int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			 cal.add(Calendar.DATE, -day_of_week);
			 cal.add(Calendar.DATE, 6);
			 date =oSimpleDateFormat.format(cal.getTime());
			return date;
		}
		/**
		 * 取当前日期所属星期的最后一天的日期（星期顺序为日，一，二...六）
		 * @return
		 * @throws ParseException 
		 */
		public static String getLastDayOfWeekOfCurrentDate() throws ParseException
		{
			String date ="";
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
			Date specifiedDate = new Date();
			String yyyy_mm_dd =getCurrentDate();
			specifiedDate =oSimpleDateFormat.parse(yyyy_mm_dd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(specifiedDate);
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			cal.add(Calendar.DATE, -day_of_week);
			cal.add(Calendar.DATE, 6);
			date =oSimpleDateFormat.format(cal.getTime());
			return date;
		}
		
		/**
		 * 根据指定日期，取该日期所属月份的所属星期是第几个星期
		 * @param yyyy_mm_dd
		 * @return
		 * @throws ParseException 
		 */
		public static int getWeekSequenceOfSpecifiedMonth(String yyyy_mm_dd) throws ParseException
		{
			int iWeekOfMonth =0;
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
			Date specifiedDate = new Date();
			specifiedDate =oSimpleDateFormat.parse(yyyy_mm_dd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(specifiedDate);
			iWeekOfMonth =cal.get(Calendar.WEEK_OF_MONTH);
			return iWeekOfMonth;
		}
		
		/**
		 * 根据指定日期，取该日期所属年份的所属星期是第几个星期
		 * @param yyyy_mm_dd
		 * @return
		 * @throws ParseException 
		 */
		public static int getWeekSequenceOfSpecifiedYear(String yyyy_mm_dd) throws ParseException
		{
			int iWeekOfYear =0;
			final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
			Date specifiedDate = new Date();
			specifiedDate =oSimpleDateFormat.parse(yyyy_mm_dd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(specifiedDate);
			iWeekOfYear =cal.get(Calendar.WEEK_OF_YEAR);
			return iWeekOfYear;
		}
		/**
		 * 取当前日期所属月份的所属星期是第几个星期
		 * //某月中第几周,按这个月1号算，1号起就是第1周，8号起就是第2周。以月份天数为标准 
		 * @return
		 * @throws ParseException 
		 */
		public static int getWeekSequenceOfCurrentMonth() throws ParseException
		{
			int iWeekOfMonth =0;
			iWeekOfMonth =Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
			return iWeekOfMonth;
		}
		
		/**
		 * 取当前日期所属年份的所属星期是第几个星期
		 * @return
		 * @throws ParseException 
		 */
		public static int getWeekSequenceOfCurrentYear() throws ParseException
		{
			int iWeekOfYear =0;
			iWeekOfYear =Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
			return iWeekOfYear;
		}
		
	/**
	 * 把字符串转换成Date对象
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDate(String dateStr)
	{
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
		Date date =null;
		try {
			date = oSimpleDateFormat.parse( dateStr );
		}
		catch (ParseException e) {
			e.printStackTrace();
			date =null;
		}
		return date;
	}
	
	/**
	 * 计算增加指定小时数的时间
	 * @param dateStr
	 * @param hour
	 * @return
	 */
	public static String getDate(String dateStr,int hour,String format)
	{
		String date =null;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
		SimpleDateFormat returnSdf =null;
		if ( format == null || format.trim().length()==0 ) {
			returnSdf =DateFormatHolder.formatFor(_DATETIME);
		}
		else
		{
			returnSdf =new SimpleDateFormat(format);
		}
		Date nowdate =null;
		try {
			nowdate =oSimpleDateFormat.parse(dateStr);
			nowdate.setHours(nowdate.getHours() + hour);
			date =returnSdf.format(nowdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 计算增加指定小时数的时间
	 * @param dateStr
	 * @param hour
	 * @return
	 */
	public static String getDate(String dateStr,int hour)
	{
		String date =null;
		final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATETIME);
		Date nowdate =null;
		try {
			nowdate =oSimpleDateFormat.parse(dateStr);
			nowdate.setHours(nowdate.getHours() + hour);
			date =oSimpleDateFormat.format(nowdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 取上一个月的这一天
	 * @param date
	 * @return
	 */
	public static Calendar getDateOfLastMonth(Calendar date) {  
	    Calendar lastDate = (Calendar) date.clone();  
	    lastDate.add(Calendar.MONTH, -1);  
	    return lastDate;  
	} 
	
	/**
	 * 取下一个月的这一天
	 * @param date
	 * @return
	 */
	public static Calendar getDateOfNextMonth(Calendar date) {  
	    Calendar lastDate = (Calendar) date.clone();  
	    lastDate.add(Calendar.MONTH, 1);  
	    return lastDate;  
	}
	  
	/**
	 * 取上一个月的这一天
	 * @param date
	 * @return 返回日历对象
	 */
	public static Calendar getDateOfLastMonth(String dateStr) {  
	    final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
	    try {  
	        Date date = oSimpleDateFormat.parse(dateStr);  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        return getDateOfLastMonth(c);  
	    } catch (ParseException e) {  
	        throw new IllegalArgumentException("Invalid date format(yyyy-MM-dd): " + dateStr);  
	    }  
	} 
	
	/**
	 * 取上一个月的这一天
	 * @param date
	 * @return 返回字符串
	 */
	public static String getStrOfLastMonth(String dateStr) {  
	    final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
	    try {  
	        Date date = oSimpleDateFormat.parse(dateStr);  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        return oSimpleDateFormat.format(getDateOfLastMonth(c).getTime());  
	    } catch (ParseException e) {  
	        throw new IllegalArgumentException("Invalid date format(yyyy-MM-dd): " + dateStr);  
	    }  
	} 
	
	/**
	 * 取下一个月的这一天
	 * @param dateStr
	 * @return 返回日历对象
	 */
	public static Calendar getDateOfNextMonth(String dateStr) {  
	    final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
	    try {  
	        Date date = oSimpleDateFormat.parse(dateStr);  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        return getDateOfNextMonth(c);  
	    } catch (ParseException e) {  
	        throw new IllegalArgumentException("Invalid date format(yyyy-MM-dd): " + dateStr);  
	    }  
	}
	
	/**
	 * 取下一个月的这一天
	 * @param dateStr
	 * @return 返回字符串
	 */
	public static String getStrOfNextMonth(String dateStr) {  
	    final SimpleDateFormat oSimpleDateFormat = DateFormatHolder.formatFor(_DATE);
	    try {  
	        Date date = oSimpleDateFormat.parse(dateStr);  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        return oSimpleDateFormat.format(getDateOfNextMonth(c).getTime());  
	    } catch (ParseException e) {  
	        throw new IllegalArgumentException("Invalid date format(yyyy-MM-dd): " + dateStr);  
	    }  
	}
	
	@SuppressWarnings("unchecked")
	public static DataSet getEveryMonthPeriod(String start_date,String end_date) throws ParseException
	{
		DataSet ds =new DataSet();
		Row row =null;
		String period_start_date =start_date;
		String period_end_date =start_date;
		boolean boolFind =true;
		long start_com=0;
		long end_com=0;
		start_com =DateUtil.compare(start_date+" 00:00:00", end_date+" 00:00:00");
		if(start_com >= 0)
		{
			return ds;
		}
		do
		{
			period_start_date =period_end_date;
			period_end_date =DateUtil.getStrOfNextMonth(period_start_date);
			start_com =DateUtil.compare(period_start_date+" 00:00:00", period_end_date+" 00:00:00");
			if(start_com < 0)
			{
				row =new Row();
				row.put("start_date",period_start_date );
				row.put("end_date", period_end_date);
				end_com =DateUtil.compare(period_end_date+" 00:00:00", end_date+" 00:00:00");
				if(end_com >= 0)
				{
					row.put("end_date", end_date);
					boolFind =false;
				}
				ds.add(row);
			}
		}
		while(boolFind);
		return ds;
	}
	
	public static Row getAppointedMonthPeriod(String start_date, String end_date, String appointed_date) throws ParseException
	{
		Row row =new Row();
		String period_start_date =start_date;
		String period_end_date =start_date;
		boolean boolFind =true;
		long start_com=0;
		long end_com=0;
		start_com =DateUtil.compare(appointed_date+" 00:00:00", start_date+" 00:00:00");
		end_com =DateUtil.compare(appointed_date+" 00:00:00", end_date+" 00:00:00");
		if(start_com < 0 || end_com>0)
		{
			row.put("period_start_date", "-1");
			row.put("period_end_date", "-1");
			return row;
		}
		
		do
		{
			period_start_date =period_end_date;
			period_end_date =DateUtil.getStrOfNextMonth(period_start_date);
			start_com =DateUtil.compare(appointed_date+" 00:00:00", period_start_date+" 00:00:00");
			end_com =DateUtil.compare(appointed_date+" 00:00:00", period_end_date+" 00:00:00");
			if(start_com >= 0 && end_com <=0)
			{
				boolFind =false;
			}
		}
		while(boolFind);
		row.put("period_start_date", period_start_date);
		row.put("period_end_date", period_end_date);
		return row;
	}
	
	
	/**
     * Clears thread-local variable containing {@link java.text.DateFormat} cache.
     *
     * @since 4.3
     */
    public static void clearThreadLocal() {
        DateFormatHolder.clearThreadLocal();
    }
	
	 /** This class should not be instantiated. */
    private DateUtil() {
    }

    /**
     * A factory for {@link SimpleDateFormat}s. The instances are stored in a
     * threadlocal way because SimpleDateFormat is not threadsafe as noted in
     * {@link SimpleDateFormat its javadoc}.
     *
     */
    final static class DateFormatHolder {

        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>
            THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>() {

            @Override
            protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference<Map<String, SimpleDateFormat>>(
                        new HashMap<String, SimpleDateFormat>());
            }

        };

        /**
         * creates a {@link SimpleDateFormat} for the requested format string.
         *
         * @param pattern
         *            a non-{@code null} format String according to
         *            {@link SimpleDateFormat}. The format is not checked against
         *            {@code null} since all paths go through
         *            {@link DateUtil}.
         * @return the requested format. This simple dateformat should not be used
         *         to {@link SimpleDateFormat#applyPattern(String) apply} to a
         *         different pattern.
         */
        public static SimpleDateFormat formatFor(final String pattern) {
            final SoftReference<Map<String, SimpleDateFormat>> ref = THREADLOCAL_FORMATS.get();
            Map<String, SimpleDateFormat> formats = ref.get();
            if (formats == null) {
                formats = new HashMap<String, SimpleDateFormat>();
                THREADLOCAL_FORMATS.set(
                        new SoftReference<Map<String, SimpleDateFormat>>(formats));
            }

            SimpleDateFormat format = formats.get(pattern);
            if (format == null) {
                format = new SimpleDateFormat(pattern, Locale.US);
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                formats.put(pattern, format);
            }

            return format;
        }

        public static void clearThreadLocal() {
            THREADLOCAL_FORMATS.remove();
        }

    }
    
	public static void main(String s[]) throws ParseException
	{
		
		System.out.println(getCurrentDateTime());
//		System.out.println(getSecondMinusOfTwoTime("2013-07-24 10:00:30","2013-07-24 10:30:50" ));
//		System.out.println(getMaxDayOfMonth("2013-11-01"));
//		System.out.println(getFirstDayOfWeek("2013-11-28"));
//		System.out.println(getLastDayOfWeek("2013-11-28"));
//		System.out.println(getWeekSequenceOfCurrentMonth());
//		System.out.println(getWeekSequenceOfCurrentYear());
//		System.out.println(getFirstDayOfWeekOfCurrentDate());
//		System.out.println(getLastDayOfWeekOfCurrentDate());
//		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date =getNextDate("2016-06-17 00:00:00",sdf);
//		System.out.println(date);
//		System.out.println(sdf.format(date));
//		System.out.println(getNextHourTime( "2016-06-17 00:00:00" ) );
//		System.out.println("==============>>"+getNextDate("2016-10-25 09:30:00",format));
	}
}
