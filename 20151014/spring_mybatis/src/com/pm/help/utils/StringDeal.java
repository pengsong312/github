package com.pm.help.utils;

/*
 *  作    者：  pengsong
 *  功能说明：  字符串处理类 
 *            
 *  日    期：  2015-08-24
 *  修改历史： 
 *            
 *  版权所有： 东方网景信息技术有限公司
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class StringDeal {
	/***************************************************************************
	 * 返回当前日期，格式为yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDate1() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();

		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * 返回当前日期，格式为yyyyMMdd
	 * 
	 * @return
	 */
	public static String getCurrentDate2() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = new Date();

		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * 把形如yyyymmdd转换成yyyy-mm-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String transferDateFormat1(String date) {
		if (date!=null) {
			date = date.trim();
		} else {
			return null;
		}

		if (!Check.isDigit(date) || !Check.checkLength(date, 8)) {
			return date;
		}

		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
				+ date.substring(6);
	}

	/***************************************************************************
	 * 把形如yyyy-mm-dd转换成yyyymmdd
	 * 
	 * @param date
	 * @return
	 */
	public static String transferDateFormat2(String date) {
		if (!Check.isNull(date)) {
			date = date.trim();
		} else {
			return null;
		}

		if (!Check.isDate(date)) {
			return date;
		}

		return replaceAll(date, "-", "");
	}

	/***************************************************************************
	 * 把形如yyyy-mm-dd格式的时间字符串转换日期类对象
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date formatDate(String date) {
		if (!Check.isNull(date)) {
			date = date.trim();
		} else {
			return null;
		}

		if (!Check.isDate(date)) {
			return null;
		}

		return java.sql.Date.valueOf(date);
	}

	/***************************************************************************
	 * 把形如hhmmss转换成hh:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static String transferTimeFormat1(String time) {
		if (!Check.isNull(time)) {
			time = time.trim();
		} else {
			return null;
		}
		if (!Check.isDigit(time) || !Check.checkLength(time, 6)) {
			return time;
		}

		return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
				+ time.substring(4);
	}

	/***************************************************************************
	 * 把形如hh:mm:ss转换成hhmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String transferTimeFormat2(String time) {
		if (!Check.isNull(time)) {
			time = time.trim();
		} else {
			return null;
		}
		if (!Check.isTime(time)) {
			return time;
		}

		return replaceAll(time, ":", "");
	}

	/***************************************************************************
	 * 把形如hh:mm:ss格式的时间字符串转换时间类对象
	 * 
	 * @param time
	 * @return
	 */
	public static java.sql.Time formatTime(String time) {
		if (!Check.isNull(time)) {
			time = time.trim();
		} else {
			return null;
		}

		if (!Check.isTime(time)) {
			return null;
		}

		return java.sql.Time.valueOf(time);
	}

	/***************************************************************************
	 * 返回当前时间，格式为HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date currentDate = new Date();

		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * 返回当前日期和时间，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentDateAndTime1() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * 返回当前日期和时间，格式为yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getCurrentDateAndTime2() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date currentDate = new Date();
		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * 跟当前时间比较
	 * 
	 * @param oldCalendar
	 * @param yearOffset
	 * @param monthOffset
	 * @param dayOffset
	 * @return
	 */
	public static boolean beforeCurrentTime(Calendar calendar, int yearOffset,
			int monthOffset, int dayOffset, int hourOffset, int minuteOffset,
			int secondOffset) {
		// 校验参数
		if (Check.isNull(calendar)) {
			return false;
		}

		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.add(Calendar.YEAR, -yearOffset);
		currentCalendar.add(Calendar.MONTH, -monthOffset);
		currentCalendar.add(Calendar.DATE, -dayOffset);
		currentCalendar.add(Calendar.HOUR, -hourOffset);
		currentCalendar.add(Calendar.MINUTE, -minuteOffset);
		currentCalendar.add(Calendar.SECOND, -secondOffset);

		return currentCalendar.after(calendar);
	}

	/***************************************************************************
	 * 解析格式如yyyy-MM-dd HH:mm:ss的时间字符串
	 * 
	 * 格式错误将返回null
	 * 
	 * @param time
	 * @return
	 */
	public static Calendar parseTime(String time) {
		// 校验参数
		if (Check.isNull(time)) {
			return null;
		}

		Calendar calendar = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = dateFormat.parse(time);
			calendar = Calendar.getInstance();
			calendar.setTime(date);

		} catch (ParseException pe) {

		}

		return calendar;
	}

	/***************************************************************************
	 * 在大写字母后面增加下划线
	 * 
	 * @param s
	 * @return
	 */
	public static String addUnderlineBeforeCapital(String s) {
		// 校验参数
		if (Check.isNull(s)) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			// 首字母是大写的除外
			if (i != 0 && Character.isUpperCase(s.charAt(i))) {
				// 前一个字符是下划线的除外
				if (s.charAt(i - 1) != '_') {
					stringBuffer.append('_');
				}
			}
			stringBuffer.append(s.charAt(i));
		}

		return stringBuffer.toString();
	}

	/***************************************************************************
	 * 把形如aBc的格式转换成 A_BC格式，用于转换java命名规则和数据库设计命名规则 如果是全大写形式则不转换，遇到$符号则替换成_
	 * 
	 * @param s
	 * @return
	 */
	public static String javaToDatabase(String s) {
		if (!Check.isNull(s)) {
			if (s.indexOf("_") > -1) {
				// if exist '_', not change
				return s;
			}
			String s_temp = replaceAll(s, "$", "_"); // 去$符号

			// 如果全是大字字母，则认为是数据库命名方式，无需转换
			if (s_temp.toUpperCase().equals(s_temp)) {
				return s_temp;
			}
			return addUnderlineBeforeCapital(s_temp).toUpperCase();
		}
		return null;
	}

	/***************************************************************************
	 * 把数据库的格式转换成java的格式
	 * 
	 * @param s
	 * @return
	 */
	public static String databaseToJava(String s) {
		if (Check.isNull(s)) {
			return null;
		}

		s = s.toLowerCase();
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char code = s.charAt(i);
			if (code == '_') {
				if (i == 0 || i == 1 || i == s.length() - 1) {
					result.append('$');
				} else if (i != s.length() - 1 && s.charAt(i + 1) <= '9'
						&& s.charAt(i + 1) >= '0') {
					result.append('$');
				}
				if (i < s.length() - 1) {
					result.append(Character.toUpperCase(s.charAt(i + 1)));
				}
				i++;
			} else {
				result.append(code);
			}
		}
		return result.toString();
	}

	/***************************************************************************
	 * 替换字符
	 * 
	 * @param obj
	 *            待处理的字符串
	 * @param src
	 *            要替换的字符串
	 * @param target
	 *            用来替换的字符串
	 * @return
	 */
	public static String replaceAll(String obj, String src, String target) {
		// 参数检查
		if (Check.isNull(obj) || Check.isNull(src) || Check.isNull(target)) {
			return obj;
		}
		// 分割
		StringTokenizer sR = new StringTokenizer(obj, src);
		StringBuffer ret = new StringBuffer();
		int iTokens = sR.countTokens();

		int j = 1;
		// 取代
		while (sR.hasMoreTokens()) {

			if (j < iTokens) {
				ret.append(sR.nextToken());
				ret.append(target);
			} else {
				ret.append(sR.nextToken());
			}
			j++;
		}
		// 尾部
		if (obj.endsWith(src)) {
			ret.append(target);
		}
		// 头部
		if (obj.startsWith(src)) {
			ret.insert(0, target);
		}
		return ret.toString();
	}

	/***************************************************************************
	 * 去掉不需要的字符、空格
	 * 
	 * @param s
	 *            待处理的字符串
	 * @param c
	 *            要替换的字符串数组
	 * 
	 */
	public static String filt(String s, String[] c) {
		if (Check.isNull(s, "待处理的字符串") || Check.isNull(c, "要替换的字符串数组")) {
			return s;
		}

		for (int i = 0; i < c.length; i++) {
			s = replaceAll(s, c[i], "");
		}

		return s;
	}

	/***************************************************************************
	 * 合并2个选择条件
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String unitTwoSelCond(String s1, String s2) {
		// 检查参数
		if (Check.isNull(s1, "s1") || Check.isNull(s2, "s2")) {
			return null;
		}

		// 任一空则返回对方
		if (s1.equals(""))
			return s2;
		if (s2.equals(""))
			return s1;

		//
		String s1_temp1 = s1.trim();
		String s2_temp1 = s2.trim();
		String s1_temp = s1_temp1.toUpperCase();
		String s2_temp = s2_temp1.toUpperCase();
		if (s1_temp.indexOf("WHERE") == 0) {
			if (s2_temp.indexOf("WHERE") == 0) {
				return s1_temp1 + " AND " + s2_temp1.substring(5);
			} else {
				return s1_temp1 + " AND " + s2_temp1;
			}
		} else {
			if (s2_temp.indexOf("WHERE") == 0) {
				return s2_temp1 + " AND " + s1_temp1;
			} else {
				return "WHERE " + s1_temp1 + " AND " + s2_temp1;
			}
		}
	}

	/***************************************************************************
	 * 合并2个排序条件
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String unitTwoSortCond(String s1, String s2) {
		// 检查参数
		if (Check.isNull(s1, "s1") || Check.isNull(s2, "s2")) {
			return null;
		}

		// 任一空则返回对方
		if (s1.equals(""))
			return s2;
		if (s2.equals(""))
			return s1;

		//
		String s1_temp1 = s1.trim();
		String s2_temp1 = s2.trim();
		String s1_temp = s1_temp1.toUpperCase();
		String s2_temp = s2_temp1.toUpperCase();
		if (s1_temp.indexOf("ORDER") == 0) {
			if (s2_temp.indexOf("ORDER") == 0) {
				return s1_temp1 + ", " + s2_temp1.substring(8);
			} else {
				return s1_temp1 + ", " + s2_temp1;
			}
		} else {
			if (s2_temp.indexOf("ORDER") == 0) {
				return s2_temp1 + ", " + s1_temp1;
			} else {
				return "ORDER BY " + s1_temp1 + ", " + s2_temp1;
			}
		}
	}

	/***************************************************************************
	 * 转换选择条件（java to db）
	 * 
	 * @param selCond
	 * @return
	 */
	public static String transSelCond(String selCond) {
		// 检查参数
		if (Check.isNull(selCond, "selCond")) {
			return null;
		}

		// 
		String[] temp_1 = selCond.split("'", -1);

		for (int i = 0; i < temp_1.length; i += 2) {
			// 在db2 语句中单引号应该是双数的

			String[] temp_11 = temp_1[i].split(" ", -1);
			StringBuffer temp_111 = new StringBuffer();
			for (int j = 0; j < temp_11.length; j++) {
				temp_11[j] = javaToDatabase(temp_11[j]);
				temp_111.append(' ');
				temp_111.append(temp_11[j]);
			}

			temp_1[i] = temp_111.toString();
		}

		StringBuffer temp_2 = new StringBuffer();
		for (int i = 0; i < temp_1.length; i++) {
			temp_2.append(temp_1[i]);
			temp_2.append('\'');
		}

		temp_2.deleteCharAt(temp_2.length() - 1).toString();

		return temp_2.toString();
	}

	/***************************************************************************
	 * 转码（ISO8859-1 -> GBK） 若已是GBK编码则不再进行转码
	 * 
	 * @param s
	 * @return
	 */
	public static String transCode(String s) {
		if (Check.isNull(s, "s")) {
			return null;
		}

		try {
			byte[] b = s.getBytes("ISO-8859-1");
			if (s.indexOf("?") > -1) {
				String s_temp = replaceAll(s, "?", "");
				byte[] b_temp = s_temp.getBytes("ISO-8859-1");
				for (int i = 0; i < b_temp.length; i++) {
					if (b_temp[i] == 63) {
						return s;
					}
				}

			} else {
				for (int i = 0; i < b.length; i++) {
					if (b[i] == 63) {
						return s;
					}
				}
			}
			return new String(b, "GBK");
		} catch (Exception e) {
		}
		return s;
	}

	/***************************************************************************
	 * 填充字符串
	 * 
	 * @param fillType
	 *            (Left or Right)
	 * @param src
	 * @param target
	 * @param length
	 * @return
	 */
	public static String buildFillString(String fillType, String src,
			String fillString, int length) {
		if (Check.isNull(fillType, "fillType")
				|| Check.isNull(fillString, "fillString")
				|| Check.isNull(src, "src")) {
			return null;
		}

		if (!Check.equals(fillType, "Left") && !Check.equals(fillType, "Right")) {
			return null;
		}

		int fillLength = length - src.getBytes().length;
		if (fillLength == 0) {
			// not need to fill
			return src;
		} else if (fillLength < 0) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer();
		while (stringBuffer.length() < fillLength) {
			stringBuffer.append(fillString);
		}
		stringBuffer.delete(fillLength, stringBuffer.length());

		if (fillType.equalsIgnoreCase("Left")) {
			return stringBuffer + src;
		} else if (fillType.equalsIgnoreCase("Right")) {
			return src + stringBuffer;
		}

		return null;
	}

	/***************************************************************************
	 * 去除填充符
	 * 
	 * @param fillType
	 *            (Left or Right)
	 * @param src
	 * @param fillString
	 * @return
	 */
	public static String deleteFillString(String fillType, String src,
			String fillString) {
		if (Check.isNull(fillType, "fillType")
				|| Check.isNull(fillString, "fillString")
				|| Check.isNull(src, "src")) {
			return null;
		}

		if (!Check.equals(fillType, "Left") && !Check.equals(fillType, "Right")) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer();
		while (stringBuffer.length() < src.length()) {
			stringBuffer.append(fillString);
		}

		if (fillType.equals("Left")) {
			for (int i = 0; i < src.length(); i++) {
				if (stringBuffer.charAt(i) != src.charAt(i)) {
					return src.substring(i, src.length());
				}
			}
		} else if (fillType.equals("Right")) {
			for (int i = src.length() - 1; i >= 0; i--) {
				if (stringBuffer.charAt(i) != src.charAt(i)) {
					return src.substring(0, i + 1);
				}
			}
		}

		return null;
	}

	/***************************************************************************
	 * 字符转换，半角转为全角
	 * 
	 * @param type
	 *            (0 or 1)
	 * @param src
	 * @return
	 */
	public static String StrChange(String src) {
		if (Check.isNull(src, "src")) {
			return src;
		}
		String strcn = "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ｀１２３４５６７８９０－＝＼～！＊＃＄％＾＠＆（）＿＋｜［］｛｝；＇：＂，。／＜＞　？	";
		String stren = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=\\~!*#$%^@&()_+|[]{};':\",./<> ?	";

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < src.length(); i++) {
			int k = stren.indexOf(src.charAt(i));
			if (k != -1) {
				stringBuffer.append(strcn.charAt(k));
			} else {
				stringBuffer.append(src.charAt(i));
			}
		}
		return stringBuffer.toString();
	}

	public static boolean checkIsNum(String src) {
		for (int i = 0; i < src.length(); i++) {
			if (!((src.charAt(i)) >= '0' && (src.charAt(i)) <= '9')) {
				return false;
			}
		}
		return true;
	}

	/***************************************************************************
	 * 字符截取，如果出错那么返回NULL
	 * 
	 * @param type
	 *            (0 or 1)
	 * @param src
	 * @return
	 */
	public static String msSustring(String src, int beginIndex, int endIndex) {
		if (src == null || src.length() == 0) {
			return null;
		} else if (beginIndex > endIndex) {
			return null;
		} else if (endIndex > src.length()) {
			if (beginIndex <= src.length()) {
				return src.substring(beginIndex);
			} else {
				return null;
			}
		} else {
			return src.substring(beginIndex, endIndex);
		}
	}

	public static String datebaseToJava(String ret) {
		String tmp = "";
		ret = ret.toLowerCase();
		String[] tmpArr = ret.split("_");
		for (int i = 0; i < tmpArr.length; i++) {
			String innerTmp = tmpArr[i];
			innerTmp = innerTmp.substring(0, 1).toUpperCase()
					+ innerTmp.substring(1, innerTmp.length());
			tmp += innerTmp;
		}
		return tmp;
	}
	
	
	/**
	 * 替换字符串中一串空格，为一个空格
	 * sunzg
	 */
	public static String trimX(String x)
	{
		x = x.trim();
		String y = "";
		char o  = ' '  ;
		for (int i = 0 ;i < x.length();i++)
		{
			if (o == x.charAt(i)&& o == ' ' )
				continue;
			o = x.charAt(i);
			y = y+ x.charAt(i);
		}
		return y;
	}
	

	/**
	 *  UNIX系统 与 Windows 目录转换 \\ ， /
	 * @param sourceName
	 * @return
	 * @throws Exception
	 */
	public static String UnixWinChange(String  sourceName) 
	{
		return sourceName.replace('\\', '/');
	}
	
	         
}
