package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearRequest;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearSearchResponse;

public interface SchoolyearService {

    public CommonAddResponse add(SchoolYearRequest request, String operid);

    public CommonModDelResponse del(CommonDeleteRequest request, String operid);

    public CommonModDelResponse mod(SchoolYearRequest request, String operid);

    public SchoolYearSearchResponse query(String operid);
}
