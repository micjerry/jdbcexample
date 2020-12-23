package com.zhihuixueai.sysmgr.api.schoolyears;

import java.util.List;

import com.zhihuixueai.sysmgr.api.StatusCode;

public class SchoolYearSearchResponse {

    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private List<SchoolyearRestBean> schoolyears;

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

    public List<SchoolyearRestBean> getSchoolyears() {
        return schoolyears;
    }

    public void setSchoolyears(List<SchoolyearRestBean> schoolyears) {
        this.schoolyears = schoolyears;
    }
}
