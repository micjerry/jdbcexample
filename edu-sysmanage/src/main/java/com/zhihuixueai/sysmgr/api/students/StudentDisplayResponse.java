package com.zhihuixueai.sysmgr.api.students;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StudentDisplayResponse {
    private int code;

    private String msg;

    private StudentRestBean student;

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

    public StudentRestBean getStudent() {
        return student;
    }

    public void setStudent(StudentRestBean student) {
        this.student = student;
    }
}
