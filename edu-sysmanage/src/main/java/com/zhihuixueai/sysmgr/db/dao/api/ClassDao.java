package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.classes.ClassRequest;
import com.zhihuixueai.sysmgr.api.classes.ClassRestBean;

public interface ClassDao {
	public long add(ClassRequest request, long schoolid, String operid);
	
	public boolean mod(ClassRequest request, long schoolid);
	
	public boolean del(long classid, long schoolid);
	
	public List<ClassRestBean> query(String sql);
	
	public ClassRestBean display(long classid);
	
	public String getView();
	
	public String getFileds();
}
