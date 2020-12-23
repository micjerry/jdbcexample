package com.zhihuixueai.sysmgr.service.checkers;

import com.zhihuixueai.sysmgr.api.commons.CommonDisplayRequest;

public class CommonChecker {
	public static boolean check_display(CommonDisplayRequest request) {
		if (request.getId() == 0) {
			return false;
		}
		
		return true;
	}
}
