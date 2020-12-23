package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.classes.ClassDisplayResponse;
import com.zhihuixueai.sysmgr.api.classes.ClassRequest;
import com.zhihuixueai.sysmgr.api.classes.ClassSearchResponse;
import com.zhihuixueai.sysmgr.api.commons.*;


public interface ClassService {
    public CommonAddResponse add(ClassRequest request, String operid);

    public CommonModDelResponse del(CommonDeleteRequest request, String operid);

    public CommonModDelResponse mod(ClassRequest request, String operid);

    public ClassSearchResponse query(CommonSearchRequest request, String operid);

    public ClassDisplayResponse display(CommonDisplayRequest request, String operid);

}
