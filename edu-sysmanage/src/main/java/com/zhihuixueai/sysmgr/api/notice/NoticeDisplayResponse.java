package com.zhihuixueai.sysmgr.api.notice;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NoticeDisplayResponse {
    private int code;

    private String msg;

    private NoticeRestBean notice;

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

    public NoticeRestBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeRestBean notice) {
        this.notice = notice;
    }
}
