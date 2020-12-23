package com.zhihuixueai.sysmgr.tools;

import org.apache.commons.lang3.StringUtils;

public class PassUtils {
	private static final int DEFAULT_PASS_LEN = 6;
	
	public static String genPass(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		
		String rawpass = username;
		while (rawpass.length() < DEFAULT_PASS_LEN) {
			rawpass = rawpass + username;
		}
		
		return rawpass.substring(rawpass.length() - 6, rawpass.length());
	}
}
