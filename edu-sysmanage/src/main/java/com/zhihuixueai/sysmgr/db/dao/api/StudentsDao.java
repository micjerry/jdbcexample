package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.students.StudentParentBean;
import com.zhihuixueai.sysmgr.api.students.StudentRequest;
import com.zhihuixueai.sysmgr.api.students.StudentRestBean;

public interface StudentsDao {
	public long add(StudentRequest request, String operid, String userid, long school);
	
	public boolean del(long student_id);
	
	public boolean mod(StudentRequest request);
	
	public List<StudentRestBean> query(String sql);
	
	public StudentRestBean display(long student_id);
	
	public List<StudentParentBean> list_parents(long student_id);
	
	public String getView();
	
	public void updatePass();
}
