package com.zhihuixueai.sysmgr.service.impl;

import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;
import com.zhihuixueai.sysmgr.db.dao.api.SubjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.subjects.SubjectSearchResponse;
import com.zhihuixueai.sysmgr.service.api.SubjectService;

import java.util.List;

@Component
public class SubjectServiceImpl implements SubjectService{
	@Autowired
	private SubjectDao subjectDao;

	@Override
	public SubjectSearchResponse list() {
		SubjectSearchResponse response = new SubjectSearchResponse();

		List<CommonSubjectBean> subjects = subjectDao.list();
		if (null == subjects || subjects.isEmpty()) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG);
			return response;
		}

		response.setSubjects(subjects);
		return response;
	}

}
