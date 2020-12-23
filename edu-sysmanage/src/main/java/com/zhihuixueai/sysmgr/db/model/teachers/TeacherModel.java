package com.zhihuixueai.sysmgr.db.model.teachers;

public class TeacherModel {
	private long id;
	
	private String created_id;
	
	private int status;
	
	private String email;
	
	private String mobile;
	
	private long school_id;
	
	private String teacher_name;
	
	private String user_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreated_id() {
		return created_id;
	}

	public void setCreated_id(String created_id) {
		this.created_id = created_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getSchool_id() {
		return school_id;
	}

	public void setSchool_id(long school_id) {
		this.school_id = school_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
