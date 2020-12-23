package com.zhihuixueai.sysmgr.api.parents;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhihuixueai.sysmgr.api.StatusCode;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ParentSearchResponse {
    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private int page_index;

    private int total_results;

    private List<ParentRestBean> parents;

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

    public List<ParentRestBean> getParents() {
        return parents;
    }

    public void setParents(List<ParentRestBean> parents) {
        this.parents = parents;
    }
}
