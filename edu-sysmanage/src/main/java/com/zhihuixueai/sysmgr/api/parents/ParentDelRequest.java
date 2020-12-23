package com.zhihuixueai.sysmgr.api.parents;

public class ParentDelRequest {
	private Long id;
	
	private Long student_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}
}
