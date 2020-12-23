package com.zhihuixueai.sysmgr.api.area;


import java.util.List;

import com.zhihuixueai.sysmgr.api.StatusCode;

public class ProvinceSearchResponse {

    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private List<AreaRestBean> provinces;

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

    public List<AreaRestBean> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<AreaRestBean> provinces) {
        this.provinces = provinces;
    }
}
