package com.zhihuixueai.sysmgr.api.students;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StudentRestBean {
	private Long id;
	
	private String name;
	
	private String student_number;
	
	private String record_number;
	
	private String exam_number;

	private String schoolyear_name;
	
	private Integer class_id;
	
	private String class_name;

	private String grade_name;
	
	private String lables;
	
	private Long schoolyear_id;
	
	private List<StudentParentBean> parents;

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

	public String getStudent_number() {
		return student_number;
	}

	public void setStudent_number(String student_number) {
		this.student_number = student_number;
	}

	public String getRecord_number() {
		return record_number;
	}

	public void setRecord_number(String record_number) {
		this.record_number = record_number;
	}

	public String getExam_number() {
		return exam_number;
	}

	public void setExam_number(String exam_number) {
		this.exam_number = exam_number;
	}

	public String getSchoolyear_name() {
		return schoolyear_name;
	}

	public void setSchoolyear_name(String schoolyear_name) {
		this.schoolyear_name = schoolyear_name;
	}

	public Integer getClass_id() {
		return class_id;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getLables() {
		return lables;
	}

	public void setLables(String lables) {
		this.lables = lables;
	}

	public Long getSchoolyear_id() {
		return schoolyear_id;
	}

	public void setSchoolyear_id(Long schoolyear_id) {
		this.schoolyear_id = schoolyear_id;
	}

	public List<StudentParentBean> getParents() {
		return parents;
	}

	public void setParents(List<StudentParentBean> parents) {
		this.parents = parents;
	}
}
