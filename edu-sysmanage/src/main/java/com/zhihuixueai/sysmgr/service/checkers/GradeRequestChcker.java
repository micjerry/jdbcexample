package com.zhihuixueai.sysmgr.service.checkers;

import com.zhihuixueai.sysmgr.api.grades.GradeModRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//private Long id;
//private Long master_id;
public class GradeRequestChcker {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String errStr;

    public String  errStr() {
        return errStr;
    }


    public boolean mod_check(GradeModRequest request) {
        if (null == request.getId() || request.getId() == 0) {
            logger.error("invalid request grade id is empty");
            return false;
        }
        if (null == request.getMaster_id() || request.getMaster_id() == 0) {
            logger.error("invalid request master_id is empty");
            return false;
        }

        return true;
    }

}
