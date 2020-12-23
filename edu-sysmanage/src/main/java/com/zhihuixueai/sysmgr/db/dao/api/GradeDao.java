package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.grades.GradeRestBean;

public interface GradeDao {
	public boolean addSchoolGrade(long schoolid);
	
	public List<GradeRestBean> getGrades(long schoolid);
	
	public boolean update_master(long schoolid, long gradeid, long masterid);
}
