package com.zhihuixueai.sysmgr.api.notice;

import java.util.List;

public class NoticeRequest {

    private Long id;

    private String title;

    private String sub_title;

    private Integer notice_type;

    private List<Long> receiver_ids;

    private List<Integer> receiver_types;

    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public Integer getNotice_type() {
        return notice_type;
    }

    public void setNotice_type(Integer notice_type) {
        this.notice_type = notice_type;
    }

    public List<Long> getReceiver_ids() {
        return receiver_ids;
    }

    public void setReceiver_ids(List<Long> receiver_ids) {
        this.receiver_ids = receiver_ids;
    }

    public List<Integer> getReceiver_types() {
        return receiver_types;
    }

    public void setReceiver_types(List<Integer> receiver_types) {
        this.receiver_types = receiver_types;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
