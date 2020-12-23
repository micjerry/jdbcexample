package com.zhihuixueai.sysmgr.api.subjects;

import java.util.List;

import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;

public class SubjectSearchResponse {
    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;
    
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
