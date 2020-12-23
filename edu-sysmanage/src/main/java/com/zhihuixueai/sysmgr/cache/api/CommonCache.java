package com.zhihuixueai.sysmgr.cache.api;

import java.util.List;

public interface CommonCache {
	public long getSchooByUser(String userid);
	
	public void clearSchoolByUser(String userid);
	
	public List<String> getRolesByUser(String userid);
	
	public void clearRolesByUser(String userid);
	
	public long getTeacherByUser(String userid);
	
	public void clearTeacherByUser(String userid);
	
    public List<Long> getMangeClassByTeacher(long schoolid, long teacherid);
    
    public void clearManageClassByTeacher(long teacherid);
	
	public List<Long> getTeachingClassByTeacher(long teacherid);
	
	public void clearTeachingClassByTeacher(long teacherid);
	
	public long getClassidByStudent(long studentid);
	
	public void clearClassidByStudent(long studentid);
}
