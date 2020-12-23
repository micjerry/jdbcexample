package com.zhihuixueai.sysmgr.api.semesters;

import java.util.List;

import com.zhihuixueai.sysmgr.api.StatusCode;

public class SemesterSearchResponse {

    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private List<SemesterRestBean> semesters;

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

    public List<SemesterRestBean> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<SemesterRestBean> semesters) {
        this.semesters = semesters;
    }
}
