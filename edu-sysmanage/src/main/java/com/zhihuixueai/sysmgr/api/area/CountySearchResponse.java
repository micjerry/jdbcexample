package com.zhihuixueai.sysmgr.api.area;

import java.util.List;

import com.zhihuixueai.sysmgr.api.StatusCode;

public class CountySearchResponse {

    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private List<AreaRestBean> counties;

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

    public List<AreaRestBean> getCounties() {
        return counties;
    }

    public void setCounties(List<AreaRestBean> counties) {
        this.counties = counties;
    }
}
