package com.zhihuixueai.sysmgr.tools;

import org.apache.commons.lang3.StringUtils;

import com.zhihuixueai.sysmgr.api.CommonConst;

public class PhoneNumberFormat {
	
	private static final String COUNTRY_CODE1 = "0086";
	private static final String COUNTRY_CODE2 = "+86";
	
	public static String formatPhone(String phone) {
		if (phone == null)
			return null;
		
		String result =  phone;
		
		if (phone.startsWith(COUNTRY_CODE1)) {
			result = phone.substring(COUNTRY_CODE1.length());
		}
		
		if (phone.startsWith(COUNTRY_CODE2)) {
			result = phone.substring(COUNTRY_CODE2.length());
		}
		
		return result;
	}

	public static boolean isPhoneNO(String phone) {
		if (StringUtils.isEmpty(phone))
			return false;
		
		if (phone.length() > CommonConst.NUM_MAX_LENGTH)
			return false;
		
		String telRegex = "[1]\\d{10}";
		return formatPhone(phone).matches(telRegex);
	}
}
