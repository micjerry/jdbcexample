package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonDeleteRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonDisplayRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolDisableRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolDisplayResponse;
import com.zhihuixueai.sysmgr.api.schools.SchoolRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolSearchResponse;

public interface SchoolService {
	public CommonAddResponse add(SchoolRequest request, String operid);
	
	public CommonModDelResponse mod(SchoolRequest request, String operid);
	
	public CommonModDelResponse disable(SchoolDisableRequest request, String operid);
	
	public SchoolSearchResponse query(CommonSearchRequest request, String operid);
	
	public SchoolDisplayResponse display(CommonDisplayRequest request, String operid);
	
	public CommonModDelResponse resetadmin(CommonDeleteRequest request, String operid);
}
