package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentAddRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDelRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDisplayRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDisplayResponse;
import com.zhihuixueai.sysmgr.api.parents.ParentModRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentSearchResponse;

public interface ParentService {
    public CommonAddResponse add(ParentAddRequest request, String operid);

    public CommonModDelResponse mod(ParentModRequest request, String operid);

    public CommonModDelResponse del(ParentDelRequest request, String operid);

    public ParentSearchResponse query(CommonSearchRequest request, String operid);

    public ParentDisplayResponse display(ParentDisplayRequest request, String operid);

}
