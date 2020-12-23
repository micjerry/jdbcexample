package com.zhihuixueai.sysmgr.api.semesters;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class SemesterRequest {
    private Long id;

    private String name;

    private Long schoolyear_id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp start_time;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp end_time;

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

    public Long getSchoolyear_id() {
        return schoolyear_id;
    }

    public void setSchoolyear_id(Long schoolyear_id) {
        this.schoolyear_id = schoolyear_id;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }
}
