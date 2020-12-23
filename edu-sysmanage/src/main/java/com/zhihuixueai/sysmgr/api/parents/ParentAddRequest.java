package com.zhihuixueai.sysmgr.api.parents;

import java.util.List;

public class ParentAddRequest {
    private String name;

    private String phone;

    private String email;

    private Integer education;
    
    private List<ParentStudentRelation> students;

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

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

	public List<ParentStudentRelation> getStudents() {
		return students;
	}

	public void setStudents(List<ParentStudentRelation> students) {
		this.students = students;
	}
}
