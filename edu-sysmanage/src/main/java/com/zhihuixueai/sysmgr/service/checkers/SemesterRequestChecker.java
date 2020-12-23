package com.zhihuixueai.sysmgr.service.checkers;

import com.zhihuixueai.sysmgr.api.CommonConst;
import com.zhihuixueai.sysmgr.api.semesters.SemesterRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//private Long id;
//private String name; *
//private Long schoolyear_id; *
//private String schoolyear_name; *
//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//private Timestamp start_time;
//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//private Timestamp end_time;
public class SemesterRequestChecker {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String errStr;

    public String  errStr() {
        return errStr;
    }

    public boolean add_check(SemesterRequest request) {
        return commonCheck(request);
    }

    public boolean mod_check(SemesterRequest request) {
        if (null == request.getId() || request.getId() == 0) {
            logger.error("invalid request semester id is empty");
            return false;
        }

        return commonCheck(request);
    }

    public boolean commonCheck(SemesterRequest request) {
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

        if (null == request.getSchoolyear_id() || request.getSchoolyear_id() == 0) {
            logger.error("invalid request schoolyear_id is empty");
            return false;
        }

        return true;
    }


}
