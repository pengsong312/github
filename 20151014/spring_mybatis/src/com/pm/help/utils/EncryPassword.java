package com.pm.help.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 *  作    者：  pengsong
 *  功能说明：  action拦截器        
 *  日    期：  2015-08-13
 *  版权： 北京网景信息技术有限公司
 */
public class EncryPassword {
	
	private static final String  LOGO_MD5 = "MD5";
	private static final String CHARSET_DEFAULT = "UTF-8";
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

//	密码MD5加密
	public static String encryPassword(String pwd) {
		MessageDigest md5 = null;
		byte[] pData = null;
		byte[] pResult = null;
		char[] newPwd = null;
		byte bb = '\u0000';
		int j=0,k = 0;
		try {
			pData = pwd.getBytes(CHARSET_DEFAULT);
			md5 = MessageDigest.getInstance(LOGO_MD5);
			md5.update(pData);
			pResult = md5.digest();
			j = pResult.length;
			newPwd = new char[j * 2];
			for(int i = 0;i<j;i++){
				bb = pResult[i];
				newPwd[k++] = HEX_DIGITS[bb >>> 4&0xf];
				newPwd[k++] = HEX_DIGITS[bb & 0xf];
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(newPwd);
	}
}
