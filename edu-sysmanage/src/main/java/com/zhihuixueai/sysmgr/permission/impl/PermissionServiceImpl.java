package com.zhihuixueai.sysmgr.permission.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.RoleConst;
import com.zhihuixueai.sysmgr.cache.api.CommonCache;
import com.zhihuixueai.sysmgr.permission.api.PermissionService;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;

@Component
public class PermissionServiceImpl implements PermissionService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonCache commonCache;

	@Override
	public SchoolPermission getPermision(String userid) {
		
		SchoolPermission permission = new SchoolPermission();
		permission.setUserid(userid);
		
		List<String> roles = commonCache.getRolesByUser(userid);
		
		if (roles == null || roles.isEmpty()) {
			logger.error("user: {} has no roles", userid);
			permission.setSchool_id(0);
			permission.setSuperuser(false);
			return permission;
		}
		
		for (String role: roles) {
			if (role.equals(RoleConst.SUPER_ROOT)) {
				permission.setSuperuser(true);
			}
			
			if (role.equals(RoleConst.PLAT_ADMIN)) {
				permission.setSuperuser(true);
			}
			
			if (role.equals(RoleConst.SCHOOL_ADMIN_ID)) {
				permission.setSchooladmin(true);
			}
		}
		
		if (permission.isSuperuser()) {
			return permission;
		}
		
		long school_id = commonCache.getSchooByUser(userid);
		permission.setSchool_id(school_id);
		if (school_id == 0) {
			return permission;
		}
		
		if (permission.isSchooladmin()) {
			return permission;
		}
		
		long teacher_id = commonCache.getTeacherByUser(userid);
		permission.setTeacher_id(teacher_id);
		if (teacher_id == 0) {
			return permission;
		}
		
		ArrayList<Long> my_classes = new ArrayList<Long>();
		List<Long> manage_classes = commonCache.getMangeClassByTeacher(school_id, teacher_id);
		
		if (manage_classes != null && !manage_classes.isEmpty()) {
			my_classes.addAll(manage_classes);
		}
		
		List<Long> teaching_classes = commonCache.getTeachingClassByTeacher(teacher_id);
		
		if (teaching_classes != null && !teaching_classes.isEmpty()) {
			for (Long tclass: teaching_classes) {
				if (!my_classes.contains(tclass)) {
					my_classes.add(tclass);
				}
			}
		}

		permission.setClasses(my_classes);
		return permission;
	}

}
