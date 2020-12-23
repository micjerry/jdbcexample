package com.zhihuixueai.sysmgr.tools;

import com.zhihuixueai.sysmgr.api.teachers.TeacherRequest;
import com.zhihuixueai.sysmgr.db.model.teachers.TeacherModel;

public class BeanTransfer {
	public static TeacherModel getTeacher(TeacherRequest request, String operid, long school_id, String userid) {
		if (null == request)
			return null;
		
		TeacherModel teacher = new TeacherModel();
		teacher.setCreated_id(operid);
		teacher.setEmail(request.getEmail());
		
		if (request.getId() != null) {
			teacher.setId(request.getId());
		}
		
		teacher.setMobile(request.getPhone());
		teacher.setSchool_id(school_id);
		teacher.setTeacher_name(request.getName());
		teacher.setUser_id(userid);
		
		return teacher;
	}
}
