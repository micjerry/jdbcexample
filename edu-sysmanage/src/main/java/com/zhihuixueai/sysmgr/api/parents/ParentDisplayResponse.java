package com.zhihuixueai.sysmgr.api.parents;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhihuixueai.sysmgr.api.StatusCode;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ParentDisplayResponse {
    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private ParentRestBean parent;

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

    public ParentRestBean getParent() {
        return parent;
    }

    public void setParent(ParentRestBean parent) {
        this.parent = parent;
    }
}
