package com.zhihuixueai.sysmgr.api.teachers;

import java.util.List;

import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;

public class TeacherDisplayBean {
	private Long id;
	
	private String name;
	
	private String phone;
	
	private String email;
	
	private Integer status;
	
	private List<CommonSubjectBean> subjects;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CommonSubjectBean> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<CommonSubjectBean> subjects) {
		this.subjects = subjects;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
