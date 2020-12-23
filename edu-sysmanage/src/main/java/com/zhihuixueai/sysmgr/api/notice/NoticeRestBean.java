package com.zhihuixueai.sysmgr.api.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NoticeRestBean {
    private Long id;

    private String title;

    private String sub_title;

    private Integer notice_type;

    private String create_id;

    private String create_name;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp create_time;

    private Integer status;

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

    public String getCreate_id() {
        return create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }

    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
