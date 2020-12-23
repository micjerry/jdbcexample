package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.students.StudentDisplayResponse;
import com.zhihuixueai.sysmgr.api.students.StudentRequest;
import com.zhihuixueai.sysmgr.api.students.StudentSearchResponse;

public interface StudentService {
    public CommonAddResponse add(StudentRequest request, String operid);

    public CommonModDelResponse del(CommonDeleteRequest request, String operid);

    public CommonModDelResponse mod(StudentRequest request, String operid);

    public StudentSearchResponse query(CommonSearchRequest request, String operid);

    public StudentDisplayResponse display(CommonDisplayRequest request, String operid);
    
    public CommonModDelResponse updatepass();

}
