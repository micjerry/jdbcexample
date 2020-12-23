package com.zhihuixueai.sysmgr.api.schools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhihuixueai.sysmgr.api.StatusCode;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SchoolDisplayResponse {
	private int code = StatusCode.SUCCESS;
	
	private String msg = StatusCode.SUCCESS_MSG;
	
	private SchoolDisplayBean school;

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

	public SchoolDisplayBean getSchool() {
		return school;
	}

	public void setSchool(SchoolDisplayBean school) {
		this.school = school;
	}
}
