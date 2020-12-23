package com.zhihuixueai.sysmgr.api.area;

import java.util.List;

import com.zhihuixueai.sysmgr.api.StatusCode;

public class CitySearchResponse {

    private int code = StatusCode.SUCCESS;

    private String msg = StatusCode.SUCCESS_MSG;

    private List<AreaRestBean> cities;

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

    public List<AreaRestBean> getCities() {
        return cities;
    }

    public void setCities(List<AreaRestBean> cities) {
        this.cities = cities;
    }
}
