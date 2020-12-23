package com.zhihuixueai.sysmgr.api.commons;

import java.util.List;

public class CommonSearchRequest {
	
	private int page_index = 1;
	
	private int page_number = 20;
	
	private List<Condition> conditions;

	public int getPage_index() {
		return page_index;
	}

	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	public int getPage_number() {
		return page_number;
	}

	public void setPage_number(int page_number) {
		this.page_number = page_number;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
	
}
