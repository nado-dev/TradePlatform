package com.commodity.mylibrary.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aaron
 */
public class ValidateUtil {
	private static final String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";
	private static final String REGEX_EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	/**
			* 判断是否是手机号
	 *
			 * @param tel 手机号
	 * @return boolean true:是  false:否
	 */
	public static boolean isMobile(String tel) {
		if (TextUtils.isEmpty(tel)) {
			return false;
		}
		return Pattern.matches(REGEX_MOBILE, tel);
	}

	/**
	 * 判断是否是邮箱
	 *
	 * @param email 邮箱
	 * @return boolean true:是  false:否
	 */
	public static boolean isEmail(String email) {
		if (TextUtils.isEmpty(email)) {
			return false;
		}
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 判断密码是否符合规范
	 *
	 * @param password 输入的密码
	 * @return boolean true:是  false:否
	 */
	public static boolean isPassword(String password) {
		String regex = "^[a-zA-Z0-9\\u4E00-\\u9FA5]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(password);
		return m.matches();
	}

	// "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"


}
