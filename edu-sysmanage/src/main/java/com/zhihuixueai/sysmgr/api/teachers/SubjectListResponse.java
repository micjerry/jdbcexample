package com.zhihuixueai.sysmgr.api.teachers;

import java.util.List;

import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;

public class SubjectListResponse {
    private int code;

    private String msg;
    
    private List<CommonSubjectBean> subjects;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<CommonSubjectBean> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<CommonSubjectBean> subjects) {
		this.subjects = subjects;
	}
}
