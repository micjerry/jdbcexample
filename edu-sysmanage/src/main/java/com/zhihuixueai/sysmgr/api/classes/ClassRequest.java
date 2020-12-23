package com.zhihuixueai.sysmgr.api.classes;

public class ClassRequest {
	private Long id;
	
	private String name;
	
	private Long grade_id;
	
	private Long headteacher_id;
	
	private Long assteacher_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
	}

	public Long getHeadteacher_id() {
		return headteacher_id;
	}

	public void setHeadteacher_id(Long headteacher_id) {
		this.headteacher_id = headteacher_id;
	}

	public Long getAssteacher_id() {
		return assteacher_id;
	}

	public void setAssteacher_id(Long assteacher_id) {
		this.assteacher_id = assteacher_id;
	}
}
