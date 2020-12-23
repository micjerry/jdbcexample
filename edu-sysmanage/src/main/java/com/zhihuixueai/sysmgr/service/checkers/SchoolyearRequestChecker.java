package com.zhihuixueai.sysmgr.service.checkers;

import com.zhihuixueai.sysmgr.api.CommonConst;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//private Long id;
//private String schoolyear; *
public class SchoolyearRequestChecker {
    private final Logger logger = LoggerFactory.getLogger(SchoolyearRequestChecker.class);
    private String errStr;

    public String  errStr() {
        return errStr;
    }

    public boolean add_check(SchoolYearRequest request) {
        return commonCheck(request);
    }

    public boolean mod_check(SchoolYearRequest request) {
        if (null == request.getId() || request.getId() == 0) {
            logger.error("invalid request schoolyear id is empty");
            return false;
        }

        return commonCheck(request);
    }

    public boolean commonCheck(SchoolYearRequest request) {
        if (StringUtils.isEmpty(request.getSchoolyear())) {
            errStr = "invalid request schoolyear is empty";
            logger.error(errStr);
            return false;
        }
        if (request.getSchoolyear().length() > CommonConst.NUM_MAX_LENGTH) {
            errStr = "invalid request name max lenght:" + CommonConst.NUM_MAX_LENGTH + ", request: " + request.getSchoolyear().length();
            logger.error(errStr);
            return false;
        }


        return true;
    }

}
