package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.db.model.LongId;
import com.zhihuixueai.sysmgr.db.model.StringId;

public interface CommonDao {
	public int count(String sql);
	
	public long getSchoolByUser(String userid);
	
	public List<StringId> getRolesByUser(String userid);
	
	public long getTeacherByUser(String userid);
	
	public List<LongId> getMangeClassByTeacher(long schoolid, long teacherid);
	
	public List<LongId> getTeachingClassByTeacher(long teacherid);
	
	public long getClassidByStudent(long studentid);
}
