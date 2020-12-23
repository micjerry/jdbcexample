package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.schools.SchoolDisplayBean;
import com.zhihuixueai.sysmgr.api.schools.SchoolRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolRestBean;
import com.zhihuixueai.sysmgr.db.model.schools.SchoolAdmin;

public interface SchoolDao {
	public long add(SchoolRequest request, String operid);
	
	public boolean disable(long id, int status);
	
	public boolean mod(SchoolRequest request);
	
	public List<SchoolRestBean> query(String sql);
	
	public SchoolDisplayBean display(long id); 
	
	public SchoolAdmin getAdmin(long id);
	
	public String getView();
}
