package com.zhihuixueai.sysmgr.api.schools;

public class SchoolDisableRequest {
	private long id;
	
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
