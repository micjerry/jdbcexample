package com.zhihuixueai.sysmgr.tools;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhihuixueai.sysmgr.api.ConditionConst;
import com.zhihuixueai.sysmgr.api.commons.Condition;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;

public class PermissionUtils {
	private static final Logger logger = LoggerFactory.getLogger(PermissionUtils.class);
	
	public static List<Condition> getPermissionCoditions(SchoolPermission permission) {
		if (permission == null)
			return null;
		
		if (permission.isSuperuser()) {
			return null;
		}
		
		
		ArrayList<Condition> conditions = new ArrayList<Condition>();
		
		conditions.add(ConditionConst.makeSchoolCondition(permission.getSchool_id()));
		
		if (permission.isSchooladmin()) {
			return conditions;
		}
		
		conditions.add(ConditionConst.makeClassContion(permission.getClasses()));
		
		return conditions;
	}
	
	public static boolean isPermited(SchoolPermission permission, long classid) {
		if (null == permission) {
			return false;
		}
		
		if (permission.isSuperuser() || permission.isSchooladmin()) {
			return true;
		}
		
		List<Long> my_classes = permission.getClasses();
		if (my_classes == null || my_classes.isEmpty()) {
			logger.info("user: {} has any classes", permission.getUserid());
			return false;
		}
		
		if (my_classes.contains(classid)) {
			return true;
		} else {
			logger.info("user: {} has no permit on class: {}", permission.getUserid(), classid);
		}
		
		return false;
	}
}
