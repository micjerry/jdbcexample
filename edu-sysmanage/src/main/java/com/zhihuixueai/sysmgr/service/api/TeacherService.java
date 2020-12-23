package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonDeleteRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonDisplayRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.teachers.SubjectListResponse;
import com.zhihuixueai.sysmgr.api.teachers.SubjectsRequest;
import com.zhihuixueai.sysmgr.api.teachers.TeacherDisplayResponse;
import com.zhihuixueai.sysmgr.api.teachers.TeacherRequest;
import com.zhihuixueai.sysmgr.api.teachers.TeacherSearchResponse;


public interface TeacherService {
    public CommonAddResponse add(TeacherRequest request, String operid);

    public CommonModDelResponse mod(TeacherRequest request, String operid);

    public CommonModDelResponse del(CommonDeleteRequest request, String operid);

    public TeacherSearchResponse query(CommonSearchRequest request, String operid);

    public TeacherDisplayResponse display(CommonDisplayRequest request, String operid);
    
    public CommonModDelResponse setSubjects(SubjectsRequest request, String operid);
    
    public SubjectListResponse getSubjects(CommonDeleteRequest request, String operid);
}
