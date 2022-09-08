package com.sa.su.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sa.su.model.Periodo;

public final class DateUtil {
	  private static final String yyyyMMdd = "yyyyMMdd";
	  private static final SimpleDateFormat FORMAT_yyyyMMdd = new SimpleDateFormat(yyyyMMdd);
	  
	  public static String format_yyyyMMdd(Date date){
		  return FORMAT_yyyyMMdd.format(date);		  
	  }
	  public static Date parse_yyyyMMdd(String date) throws ParseException{
		  return FORMAT_yyyyMMdd.parse(date);		  
	  }
		public static Date parse(String mask, String date) throws ParseException {
			if (date != null && !date.isEmpty())
				return new SimpleDateFormat(mask).parse(date);
			return null;
		}

		public static String format(String mask, Date date) {
			if (date != null)
				return new SimpleDateFormat(mask).format(date);

			return null;
		}
		
		public static boolean isDateInPeriodo(Date date, Periodo periodo) throws ParseException {
			Date startDate = DateUtil.getStartDatePeriodoActual(periodo);
			Date endDate = DateUtil.getEndDatePeriodoActual(periodo);
			
			return (date.before(endDate) || date.equals(endDate)) && (date.after(startDate) || date.equals(startDate));
		}

		public static Date getStartDatePeriodoActual(Periodo periodo) throws ParseException {
			Calendar cal = Calendar.getInstance();
//			RESET THE TIME
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.clear(Calendar.MINUTE);
			cal.clear(Calendar.SECOND);
			cal.clear(Calendar.MILLISECOND);
			if (periodo.getDescripcion().trim().equalsIgnoreCase(Constants.SEMANAL)) {
				cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
			}else{
				if (periodo.getDescripcion().trim().equalsIgnoreCase(Constants.MENSUAL)) {
					cal.set(Calendar.DAY_OF_MONTH, 1);
				}else{
					if (periodo.getDescripcion().trim().equalsIgnoreCase(Constants.ANUAL)) {
						cal.set(Calendar.DAY_OF_YEAR, 1); 
					}
				}
			}
			return cal.getTime();
		}

		public static Date getEndDatePeriodoActual(Periodo periodo) throws ParseException {
			Calendar cal = Calendar.getInstance();
//			RESET THE TIME
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.clear(Calendar.MINUTE);
			cal.clear(Calendar.SECOND);
			cal.clear(Calendar.MILLISECOND);
			if (periodo.getDescripcion().trim().equalsIgnoreCase(Constants.SEMANAL)) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}else{
				if (periodo.getDescripcion().trim().equalsIgnoreCase(Constants.MENSUAL)) {
					cal.add(Calendar.MONTH, 1);
					cal.set(Calendar.DATE, 1);
					cal.add(Calendar.DATE, -1);
				}else{
					if (periodo.getDescripcion().trim().equalsIgnoreCase(Constants.ANUAL)) {
						cal.set(Calendar.MONTH, 11); // 11 = december
						cal.set(Calendar.DAY_OF_MONTH, 31); 
					}
				}
			}
		    cal.add(Calendar.DATE, 1);
		    cal.add(Calendar.MILLISECOND, -1);
			return cal.getTime();
		}
}
