package com.zhihuixueai.sysmgr.service.checkers;

import com.zhihuixueai.sysmgr.api.CommonConst;
import com.zhihuixueai.sysmgr.api.notice.NoticeRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//private Long id;
//private String title; *
//private String sub_title; *
//private Integer notice_type;
//private List<Long> receiver_ids; *
//private List<Integer> receiver_types;
//private String body;
public class NoticeRequestChecker {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String errStr;

    public String  errStr() {
        return errStr;
    }

    public boolean add_check(NoticeRequest request) {
        return commonCheck(request);
    }

    public boolean mod_check(NoticeRequest request) {
        if (null == request.getId() || request.getId() == 0) {
            logger.error("invalid request teacher id is empty");
            return false;
        }

        return commonCheck(request);
    }

    public boolean commonCheck(NoticeRequest request) {
        if (StringUtils.isEmpty(request.getTitle())) {
            errStr = "invalid request title is empty";
            logger.error(errStr);
            return false;
        }
        if (request.getTitle().length() > CommonConst.TITLE_MAX_LENGTH) {
            errStr = "invalid request title max lenght:" + CommonConst.TITLE_MAX_LENGTH + ", request: " + request.getTitle().length();
            logger.error(errStr);
            return false;
        }

        if (StringUtils.isEmpty(request.getSub_title())) {
            errStr = "invalid request sub_title is empty";
            logger.error(errStr);
            return false;
        }
        if (request.getSub_title().length() > CommonConst.TITLE_MAX_LENGTH) {
            errStr = "invalid request sub_title max lenght:" + CommonConst.TITLE_MAX_LENGTH + ", request: " + request.getSub_title().length();
            logger.error(errStr);
            return false;
        }

        if (request.getReceiver_ids().isEmpty()) {
            errStr = "invalid request receiver_ids is empty";
            logger.error(errStr);
            return false;
        }

        return true;
    }


}
