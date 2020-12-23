package com.zhihuixueai.sysmgr.api.notice;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NoticeSearchResponse {
    private int code;

    private String msg;

    private int page_index;

    private int total_results;

    private List<NoticeRestBean> notices;

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

    public List<NoticeRestBean> getNotices() {
        return notices;
    }

    public void setNotices(List<NoticeRestBean> notices) {
        this.notices = notices;
    }
}
