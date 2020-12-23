package com.zhihuixueai.sysmgr.service.impl;

import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.notice.NoticeDisplayResponse;
import com.zhihuixueai.sysmgr.api.notice.NoticeRequest;
import com.zhihuixueai.sysmgr.api.notice.NoticeSearchResponse;
import com.zhihuixueai.sysmgr.service.checkers.NoticeRequestChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.service.api.NoticeService;

@Component
public class NoticeServiceImpl implements NoticeService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CommonAddResponse add(NoticeRequest request, String operid) {
        CommonAddResponse response = new CommonAddResponse();

        NoticeRequestChecker checker = new NoticeRequestChecker();
        if (!checker.add_check(request)) {
            logger.error("invalid request");
            response.setCode(StatusCode.BAD_REQUEST);
            response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
            return response;
        }


        return response;
    }

    @Override
    public CommonModDelResponse del(CommonDeleteRequest request, String operid) {
        return null;
    }

    @Override
    public CommonModDelResponse mod(NoticeRequest request, String operid) {
        CommonModDelResponse response = new CommonModDelResponse();

        NoticeRequestChecker checker = new NoticeRequestChecker();
        if (!checker.mod_check(request)) {
            logger.error("invalid request");
            response.setCode(StatusCode.BAD_REQUEST);
            response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
            return response;
        }



        return response;
    }

    @Override
    public NoticeSearchResponse query(CommonSearchRequest request, String operid) {
        return null;
    }

    @Override
    public NoticeDisplayResponse display(CommonDisplayRequest request, String operid) {
        return null;
    }

    @Override
    public CommonModDelResponse publish(CommonDeleteRequest request, String operid) {
        return null;
    }
}
