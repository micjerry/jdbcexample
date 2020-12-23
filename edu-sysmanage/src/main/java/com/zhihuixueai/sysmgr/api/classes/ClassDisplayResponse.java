package com.zhihuixueai.sysmgr.api.classes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassDisplayResponse {
    private int code;

    private String msg;

    private ClassRestBean calss_info;

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

    public ClassRestBean getCalss_info() {
        return calss_info;
    }

    public void setCalss_info(ClassRestBean class_info) {
        this.calss_info = class_info;
    }
}
