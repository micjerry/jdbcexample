package com.zhihuixueai.sysmgr.api.schoolyears;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SchoolyearRestBean {
	private long id;
	
	private String schoolyear;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp create_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSchoolyear() {
		return schoolyear;
	}

	public void setSchoolyear(String schoolyear) {
		this.schoolyear = schoolyear;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}
