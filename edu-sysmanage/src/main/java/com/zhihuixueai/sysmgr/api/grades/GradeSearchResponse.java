package com.zhihuixueai.sysmgr.api.grades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhihuixueai.sysmgr.api.StatusCode;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GradeSearchResponse {
	private int code = StatusCode.SUCCESS;
	
	private String msg = StatusCode.SUCCESS_MSG;
	
	private List<GradeRestBean> grades;

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

	public List<GradeRestBean> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeRestBean> grades) {
		this.grades = grades;
	}
}
