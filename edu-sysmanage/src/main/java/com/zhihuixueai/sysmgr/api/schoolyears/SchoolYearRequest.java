package com.zhihuixueai.sysmgr.api.schoolyears;

public class SchoolYearRequest {
    private Long id;

    private String schoolyear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(String schoolyear) {
        this.schoolyear = schoolyear;
    }
}
