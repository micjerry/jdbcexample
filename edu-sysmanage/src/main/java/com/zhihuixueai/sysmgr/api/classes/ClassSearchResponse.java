package com.zhihuixueai.sysmgr.api.classes;

import java.util.List;

import com.zhihuixueai.sysmgr.api.StatusCode;

public class ClassSearchResponse {
    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private int page_index;

    private int total_results;

    private List<ClassRestBean> calsses;

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

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<ClassRestBean> getCalsses() {
        return calsses;
    }

    public void setCalsses(List<ClassRestBean> calsses) {
        this.calsses = calsses;
    }
}
