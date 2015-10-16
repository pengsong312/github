package com.pm.help.utils;


public class Check {
	/***************************************************************************
	 * 字符串处理方法
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/***************************************************************************
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj, String objName) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/***************************************************************************
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) throws NullPointerException {
		if (isNull(s)) {
			return true;
		}

		if (s.length() == 0) {
			return true;
		}
		return false;
	}

	/***************************************************************************ڸ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean checkLength(String s, int length)
			throws NullPointerException {
		if (isNull(s)) {
			return false;
		}

		if (s.length() == length) {
			return true;
		} else {
			return false;
		}

	}

	/***************************************************************************
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if (isNull(obj1) || isNull(obj2)) {
			return false;
		}

		if (obj1.equals(obj2)) {
			return true;
		}

		return false;
	}

	/***************************************************************************
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, String obj1Name, Object obj2,
			String obj2Name) {
		if (equals(obj1, obj2)) {
			return true;
		}

		return false;
	}

	/***************************************************************************
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(int obj1, String obj1Name, int obj2,
			String obj2Name) {
		if (obj1 == obj2) {
			return true;
		}

		return false;
	}

	/***************************************************************************ֵ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBoolean(String s) {
		if (isNull(s)) {
			return false;
		}

		if (s.toLowerCase().equals("false") || s.toLowerCase().equals("true")) {
			return true;
		}
		return false;
	}

	/**************************************************************************ֵ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDouble(String s) {
		if (isNull(s)) {
			return false;
		}

		if (s.indexOf(".") != s.lastIndexOf(".") || s.indexOf(".") == 0
				|| s.indexOf(".") == s.length() - 1) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != '.') {
				if (c > '9' || c < '0') {
					return false;
				}
			}
		}

		return true;
	}

	/***************************************************************************
	 *
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDigit(String s) {
		if (isNull(s) || isEmpty(s)) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > '9' || c < '0') {
				return false;
			}
		}

		return true;
	}

	/***************************************************************************
	 * (yyyy-mm-dd)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDate(String s) {
		if (!checkLength(s, 10)) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (i == 4 || i == 7) {
				if (c != '-') {
					return false;
				}
			} else {
				if (c > '9' || c < '0') {
					return false;
				}
			}
		}

		return true;
	}

	/***************************************************************************
	 * (hh:mm:ss)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isTime(String s) {
		if (!checkLength(s, 8)) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (i == 2 || i == 5) {
				if (c != ':') {
					return false;
				}
			} else {
				if (c > '9' || c < '0') {
					return false;
				}
			}
		}

		return true;
	}


	public static boolean isSBC(String s) {
		int nLen = s.getBytes().length;

		if ((nLen & 1) != 0) {
			return false;
		}

		for (int i = 0; i < nLen / 2; i++) {
			int lc = (byte) s.getBytes()[2 * i];
			lc = (lc >= 0 ? lc : 256 + lc);

			int rc = (byte) s.getBytes()[2 * i + 1];
			rc = (rc >= 0 ? rc : 256 + rc);

			if (lc <= 127 || rc <= 127) {
				return false;
			}
		}

		return true;
	}
}
