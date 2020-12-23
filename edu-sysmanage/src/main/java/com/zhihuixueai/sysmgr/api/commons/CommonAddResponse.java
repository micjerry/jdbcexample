package com.zhihuixueai.sysmgr.api.commons;

import com.zhihuixueai.sysmgr.api.StatusCode;

public class CommonAddResponse {
	private int code = StatusCode.SUCCESS;
	
	private String msg = StatusCode.SUCCESS_MSG;
	
	private long id;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
