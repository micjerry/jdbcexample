package com.zhihuixueai.sysmgr.service.checkers;

import com.zhihuixueai.sysmgr.api.CommonConst;
import com.zhihuixueai.sysmgr.api.students.StudentRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//    private Long id;
//    private String name; *
//    private String student_number; *
//    private String record_number;
//    private String exam_number;
//    private Long schoolyear_id;
//    private Long class_id; *
//    private String lables;
public class StudentRequestChecker {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String errStr;

    public String  errStr() {
        return errStr;
    }

    public boolean add_check(StudentRequest request) {
        return commonCheck(request);
    }

    public boolean mod_check(StudentRequest request) {
        if (null == request.getId() || request.getId() == 0) {
            logger.error("invalid request student id is empty");
            return false;
        }

        return commonCheck(request);
    }

    public boolean commonCheck(StudentRequest request) {
        if (StringUtils.isEmpty(request.getName())) {
            errStr = "invalid request name is empty";
            logger.error(errStr);
            return false;
        }
        if (request.getName().length() > CommonConst.NAME_MAX_LENGTH) {
            errStr = "invalid request name max lenght:" + CommonConst.NAME_MAX_LENGTH + ", request: " + request.getName().length();
            logger.error(errStr);
            return false;
        }

        if (StringUtils.isEmpty(request.getStudent_number())) {
            errStr = "invalid request student_number is empty";
            logger.error(errStr);
            return false;
        }
        if (request.getStudent_number().length() > CommonConst.NUM_MAX_LENGTH) {
            errStr = "invalid request student_number max lenght:" + CommonConst.NUM_MAX_LENGTH + ", request:" + request.getStudent_number().length();
            logger.error(errStr);
            return false;
        }

        if (request.getRecord_number().length() > CommonConst.NUM_MAX_LENGTH) {
            errStr = "invalid request record_number max lenght:" + CommonConst.NUM_MAX_LENGTH + ", request:" + request.getRecord_number().length();
            logger.error(errStr);
            return false;
        }

        if (request.getExam_number().length() > CommonConst.NUM_MAX_LENGTH) {
            errStr = "invalid request exam_number max lenght:" + CommonConst.NUM_MAX_LENGTH + ", request:" + request.getExam_number().length();
            logger.error(errStr);
            return false;
        }

        if (null == request.getClass_id() || 0 == request.getClass_id()) {
            errStr = "invalid request class_id is empty";
            logger.error(errStr);
            return false;
        }

        return true;
    }





}
