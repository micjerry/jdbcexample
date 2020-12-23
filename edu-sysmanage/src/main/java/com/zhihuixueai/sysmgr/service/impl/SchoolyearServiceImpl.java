package com.zhihuixueai.sysmgr.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonDeleteRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearRequest;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearSearchResponse;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolyearRestBean;
import com.zhihuixueai.sysmgr.db.dao.api.SchoolyearDao;
import com.zhihuixueai.sysmgr.permission.api.PermissionService;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;
import com.zhihuixueai.sysmgr.service.api.SchoolyearService;
import com.zhihuixueai.sysmgr.service.checkers.SchoolyearRequestChecker;

@Component
public class SchoolyearServiceImpl implements SchoolyearService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SchoolyearDao schoolyearDao;
    
    @Autowired
	private PermissionService permissionService;

    @Override
    public CommonAddResponse add(SchoolYearRequest request, String operid) {
        CommonAddResponse response = new CommonAddResponse();

        SchoolyearRequestChecker checker = new SchoolyearRequestChecker();
        if (!checker.add_check(request)) {
            logger.error("invalid request");
            response.setCode(StatusCode.BAD_REQUEST);
            response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
            return response;
        }

        SchoolPermission permission = permissionService.getPermision(operid);
        
        if (null == permission) {
        	 logger.error("invalid no permission user:{}", operid);
        	 response.setCode(StatusCode.FORBIDDEN);
             response.setMsg(StatusCode.FORBIDDEN_MSG);
             return response;
        }
        
		long school_id = permission.getSchool_id();
		
		if (school_id == 0) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg("school not exist");
			return response;
		}
		
		if (!permission.isSchooladmin()) {
			response.setCode(StatusCode.FORBIDDEN);
            response.setMsg(StatusCode.FORBIDDEN_MSG);
            return response;
		}
		
		long yearid = schoolyearDao.add(request, school_id, operid);
		
		if (yearid == 0) {
			response.setCode(StatusCode.REPEAT);
            response.setMsg(StatusCode.REPEAT_MSG);
            return response;
		}
		
		response.setId(yearid);

        return response;
    }

    @Override
    public CommonModDelResponse del(CommonDeleteRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();
		if (request.getId() == 0 ) {
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG);
			return response;
		}
		
		SchoolPermission permission = permissionService.getPermision(operid);
		
		if (!permission.isSchooladmin()) {
			response.setCode(StatusCode.FORBIDDEN);
            response.setMsg(StatusCode.FORBIDDEN_MSG);
            return response;
		}
		
		long school_id = permission.getSchool_id();
		
		if (school_id == 0) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg("school not exist");
			return response;
		}
		
		schoolyearDao.del(request.getId(), school_id);
		return response;
    }

    @Override
    public CommonModDelResponse mod(SchoolYearRequest request, String operid) {
        CommonModDelResponse response = new CommonModDelResponse();

        SchoolyearRequestChecker checker = new SchoolyearRequestChecker();
        if (!checker.mod_check(request)) {
            logger.error("invalid request");
            response.setCode(StatusCode.BAD_REQUEST);
            response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
            return response;
        }

        SchoolPermission permission = permissionService.getPermision(operid);
		
        if (!permission.isSchooladmin()) {
			response.setCode(StatusCode.FORBIDDEN);
            response.setMsg(StatusCode.FORBIDDEN_MSG);
            return response;
		}
		
		long school_id = permission.getSchool_id();
		
		if (school_id == 0) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg("school not exist");
			return response;
		}
		
		int status = schoolyearDao.mod(request, school_id);
		
		if (status != StatusCode.SUCCESS) {
			response.setCode(status);
			response.setMsg(StatusCode.getMsg(status));
		}
		
        return response;
    }

    @Override
    public SchoolYearSearchResponse query(String operid) {
    	SchoolYearSearchResponse response = new SchoolYearSearchResponse();

        SchoolPermission permission = permissionService.getPermision(operid);
		
		long school_id = permission.getSchool_id();
		
		if (school_id == 0) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg("school not exist");
			return response;
		}
		
		List<SchoolyearRestBean> schoolyears = schoolyearDao.query(school_id);
    	response.setSchoolyears(schoolyears);
        return response;
    }
}
