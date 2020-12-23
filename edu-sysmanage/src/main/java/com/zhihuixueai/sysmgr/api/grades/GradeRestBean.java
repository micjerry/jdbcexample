package com.zhihuixueai.sysmgr.api.grades;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GradeRestBean {
	private long id;
	
	private String name;
	
	private int period_id;
	
	private String period_name;
	
	private Long master_id;

	private String master_name;

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

	public int getPeriod_id() {
		return period_id;
	}

	public void setPeriod_id(int period_id) {
		this.period_id = period_id;
	}

	public String getPeriod_name() {
		return period_name;
	}

	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}

	public Long getMaster_id() {
		return master_id;
	}

	public void setMaster_id(Long master_id) {
		this.master_id = master_id;
	}
	
	public String getMaster_name() {
		return master_name;
	}

	public void setMaster_name(String master_name) {
		this.master_name = master_name;
	}
}
