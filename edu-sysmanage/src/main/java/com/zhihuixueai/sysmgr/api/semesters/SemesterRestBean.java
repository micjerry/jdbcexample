package com.zhihuixueai.sysmgr.api.semesters;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SemesterRestBean {
	private long id;
	
	private String name;
	
	private long schoolyear_id;
	
	private String schoolyear_name;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp start_time;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp end_time;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp create_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSchoolyear_id() {
		return schoolyear_id;
	}

	public void setSchoolyear_id(long schoolyear_id) {
		this.schoolyear_id = schoolyear_id;
	}

	public String getSchoolyear_name() {
		return schoolyear_name;
	}

	public void setSchoolyear_name(String schoolyear_name) {
		this.schoolyear_name = schoolyear_name;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}
