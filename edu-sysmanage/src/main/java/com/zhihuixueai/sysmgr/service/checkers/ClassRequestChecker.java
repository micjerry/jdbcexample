package com.zhihuixueai.sysmgr.service.checkers;

import com.zhihuixueai.sysmgr.api.CommonConst;
import com.zhihuixueai.sysmgr.api.classes.ClassRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//private Long id;
//private String name;*
//private Long grade_id;
//private Long headteacher_id;
//private Long assteacher_id;
public class ClassRequestChecker {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String errStr;

    public String  errStr() {
        return errStr;
    }

    public boolean add_check(ClassRequest request) {
        return commonCheck(request);
    }

    public boolean mod_check(ClassRequest request) {
        if (null == request.getId() || request.getId() == 0) {
            logger.error("invalid request class id is empty");
            return false;
        }

        return commonCheck(request);
    }

    public boolean commonCheck(ClassRequest request) {
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

        return true;
    }

}
