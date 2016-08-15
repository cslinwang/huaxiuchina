package com.huaxiuchina.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDate {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public static String getDate() throws Exception {
		Date now = new Date();
		String dateNowStr = sdf.format(now);
		return dateNowStr;
	}

	public static String lastDate(String dateNowStr) throws Exception {
		Date day_1 = sdf.parse(dateNowStr);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(day_1);
		rightNow.add(Calendar.DATE, -1);
		Date last = rightNow.getTime();
		String lastStr = sdf.format(last);
		return lastStr;
	}

	
	public static String nextDate(String dateNowStr) throws Exception {
		Date day_1 = sdf.parse(dateNowStr);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(day_1);
		rightNow.add(Calendar.DATE, 1);
		Date next = rightNow.getTime();
		String nextStr = sdf.format(next);
		int week=rightNow.get(Calendar.DAY_OF_WEEK); 
		return nextStr;
	}
	public static void main(String[] args) throws Exception {

		new GetDate().nextDate(new GetDate().getDate());
	}
}
