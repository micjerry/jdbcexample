package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.grades.GradeModRequest;
import com.zhihuixueai.sysmgr.api.grades.GradeSearchResponse;

public interface GradeService {

    public CommonModDelResponse mod(GradeModRequest request, String operid);

    public GradeSearchResponse query(String operid);

}
