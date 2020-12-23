package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.semesters.SemesterRequest;
import com.zhihuixueai.sysmgr.api.semesters.SemesterRestBean;

public interface SemesterDao {
	public long add(SemesterRequest request, long schoolid, String operid);
	
	public boolean del(long id, long schoolid);
	
	public boolean mod(SemesterRequest request, long schoolid);
	
	public List<SemesterRestBean> query(long schoolid);
}
