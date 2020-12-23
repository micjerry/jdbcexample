package com.zhihuixueai.sysmgr.permission.api;

import java.util.List;

public class SchoolPermission {
	private String userid;
	
	private boolean superuser;
	
	private boolean schooladmin;
	
	private long school_id;
	
	private long teacher_id;
	
	private List<String> roles;
	
	private List<Long> classes;

	public long getSchool_id() {
		return school_id;
	}

	public void setSchool_id(long school_id) {
		this.school_id = school_id;
	}

	public long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<Long> getClasses() {
		return classes;
	}

	public void setClasses(List<Long> classes) {
		this.classes = classes;
	}

	public boolean isSuperuser() {
		return superuser;
	}

	public void setSuperuser(boolean superuser) {
		this.superuser = superuser;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean isSchooladmin() {
		return schooladmin;
	}

	public void setSchooladmin(boolean schooladmin) {
		this.schooladmin = schooladmin;
	}
}
