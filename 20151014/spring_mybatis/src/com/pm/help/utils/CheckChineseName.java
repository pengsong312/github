package com.pm.help.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckChineseName {
	
	public boolean checkName(String str) {
		boolean flag = false;
        String regx = regx = "(^[a-z0-9A-Z])[a-z0-9A-Z_]+([a-z0-9-A-Z])";
        /**
		 * 编号不能为中文
		 */
		Pattern pattern = Pattern.compile(regx);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
	    // 字符串是否与正则表达式相匹配
	    flag = matcher.matches();
	    
	    return flag;
	}
	public static void main(String[] args) {
		boolean flag = false;
		String str  = "454hh";
		String regx = "(^[a-z0-9A-Z])[a-z0-9A-Z_]+([a-z0-9-A-Z])"; 
		Pattern pattern = Pattern.compile(regx);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
	    // 字符串是否与正则表达式相匹配
	    flag = matcher.matches();
	    System.out.println(flag);
	}
}
