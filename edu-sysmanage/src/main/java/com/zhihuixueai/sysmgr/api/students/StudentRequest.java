package com.zhihuixueai.sysmgr.api.students;

public class StudentRequest {
    private Long id;

    private String name;

    private String student_number;

    private String record_number;

    private String exam_number;

    private Long schoolyear_id;

    private Long class_id;

    private String lables;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getRecord_number() {
        return record_number;
    }

    public void setRecord_number(String record_number) {
        this.record_number = record_number;
    }

    public String getExam_number() {
        return exam_number;
    }

    public void setExam_number(String exam_number) {
        this.exam_number = exam_number;
    }

    public Long getSchoolyear_id() {
        return schoolyear_id;
    }

    public void setSchoolyear_id(Long schoolyear_id) {
        this.schoolyear_id = schoolyear_id;
    }

    public Long getClass_id() {
        return class_id;
    }

    public void setClass_id(Long class_id) {
        this.class_id = class_id;
    }

    public String getLables() {
        return lables;
    }

    public void setLables(String lables) {
        this.lables = lables;
    }
}
