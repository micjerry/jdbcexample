package com.zhihuixueai.sysmgr.api.teachers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhihuixueai.sysmgr.api.StatusCode;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeacherDisplayResponse {
    private int code= StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private TeacherDisplayBean teacher;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TeacherDisplayBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDisplayBean teacher) {
        this.teacher = teacher;
    }
}
