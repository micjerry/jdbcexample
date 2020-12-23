package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonDeleteRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.semesters.SemesterRequest;
import com.zhihuixueai.sysmgr.api.semesters.SemesterSearchResponse;

public interface SemesterService {

    public CommonAddResponse add(SemesterRequest request, String operid);

    public CommonModDelResponse del(CommonDeleteRequest request, String operid);

    public CommonModDelResponse mod(SemesterRequest request, String operid);

    public SemesterSearchResponse query(String operid);

}
