package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.notice.NoticeDisplayResponse;
import com.zhihuixueai.sysmgr.api.notice.NoticeRequest;
import com.zhihuixueai.sysmgr.api.notice.NoticeSearchResponse;

public interface NoticeService {

    public CommonAddResponse add(NoticeRequest request, String operid);

    public CommonModDelResponse del(CommonDeleteRequest request, String operid);

    public CommonModDelResponse mod(NoticeRequest request, String operid);

    public NoticeSearchResponse query(CommonSearchRequest request, String operid);

    public NoticeDisplayResponse display(CommonDisplayRequest request, String operid);

    public CommonModDelResponse publish(CommonDeleteRequest request, String operid);

}
