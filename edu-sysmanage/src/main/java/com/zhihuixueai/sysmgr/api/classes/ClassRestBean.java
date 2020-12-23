package com.zhihuixueai.sysmgr.api.classes;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassRestBean {
	private long id;

	private Long grade_id;

	private String name;

	private String grade_name;

	private Long headteacher_id;

	private String headteacher_name;

	private Long assteacher_id;

	private String assteacher_name;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getHeadteacher_name() {
		return headteacher_name;
	}

	public void setHeadteacher_name(String headteacher_name) {
		this.headteacher_name = headteacher_name;
	}

	public String getAssteacher_name() {
		return assteacher_name;
	}

	public void setAssteacher_name(String assteacher_name) {
		this.assteacher_name = assteacher_name;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
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
