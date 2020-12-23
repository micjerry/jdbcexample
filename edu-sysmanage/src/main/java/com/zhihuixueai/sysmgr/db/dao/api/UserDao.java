package com.zhihuixueai.sysmgr.db.dao.api;

import com.zhihuixueai.sysmgr.db.model.users.SysUser;

public interface UserDao {
	public String add(SysUser user);
	
	public boolean reset(SysUser user);
}
