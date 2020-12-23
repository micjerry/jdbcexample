package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;
import com.zhihuixueai.sysmgr.api.teachers.TeacherDisplayBean;
import com.zhihuixueai.sysmgr.api.teachers.TeacherRestBean;
import com.zhihuixueai.sysmgr.db.model.teachers.TeacherModel;

public interface TeacherDao {
	public long add(TeacherModel teacher);
	
	public boolean mod(TeacherModel teacher);
	
	public boolean del(long teacherid, long schoolid);
	
	public List<TeacherRestBean> query(String sql);
	
	public TeacherDisplayBean display(long teacherid, long schoolid);
	
	public boolean clearSubjects(long teacherid, long schoolid);
	
	public boolean addSubject(CommonSubjectBean subject, long teacherid, long schoolid);
	
	public List<CommonSubjectBean> getSubjects(long teacherid, long schoolid);
	
	public String getView();
	
	public String getFileds();
}
