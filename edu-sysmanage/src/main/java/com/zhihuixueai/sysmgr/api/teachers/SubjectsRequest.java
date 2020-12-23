package com.zhihuixueai.sysmgr.api.teachers;

import java.util.List;

import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;

public class SubjectsRequest {
	private Long id;
	
	private List<CommonSubjectBean> subjects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CommonSubjectBean> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<CommonSubjectBean> subjects) {
		this.subjects = subjects;
	}
}
