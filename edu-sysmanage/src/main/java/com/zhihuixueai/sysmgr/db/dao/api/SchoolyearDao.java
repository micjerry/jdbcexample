package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearRequest;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolyearRestBean;

public interface SchoolyearDao {
	public long add(SchoolYearRequest request, long schoolid, String operid);
	
	public boolean del(long id, long schoolid);
	
	public int mod(SchoolYearRequest request, long schoolid);
	
	public List<SchoolyearRestBean> query(long schoolid);
	
}
