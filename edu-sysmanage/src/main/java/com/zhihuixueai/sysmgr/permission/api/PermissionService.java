package com.zhihuixueai.sysmgr.permission.api;

public interface PermissionService {
	
	// user is system admin or not
	public SchoolPermission getPermision(String userid);
	
}
