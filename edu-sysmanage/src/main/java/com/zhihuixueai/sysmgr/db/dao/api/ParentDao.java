package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.parents.ParentAddRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDelRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentModRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentRestBean;
import com.zhihuixueai.sysmgr.api.parents.ParentStudentRelation;

public interface ParentDao {
	public long add(ParentAddRequest request, String operid, long school_id, String userid);
	
	public long add_relation(ParentStudentRelation relation, long school_id, long parent_id);
	
	public boolean del_relation(ParentDelRequest request, long school_id);
	
	public boolean mod(ParentModRequest request, long school_id);
	
	public boolean mod_relation(ParentModRequest request, long school_id);
	
	public List<ParentRestBean> query(String sql);
	
	public ParentRestBean display(long parent_id, long student_id, long school_id);
	
	public String getView();
	
	public String getFields();
}
