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
import com.zhihuixueai.sysmgr.api.semesters.SemesterRequest;
import com.zhihuixueai.sysmgr.api.semesters.SemesterRestBean;
import com.zhihuixueai.sysmgr.api.semesters.SemesterSearchResponse;
import com.zhihuixueai.sysmgr.db.dao.api.SemesterDao;
import com.zhihuixueai.sysmgr.permission.api.PermissionService;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;
import com.zhihuixueai.sysmgr.service.api.SemesterService;
import com.zhihuixueai.sysmgr.service.checkers.SemesterRequestChecker;

@Component
public class SemesterServiceImpl implements SemesterService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SemesterDao semesterDao;
  
    @Autowired
   	private PermissionService permissionService;

    @Override
    public CommonAddResponse add(SemesterRequest request, String operid) {
        CommonAddResponse response = new CommonAddResponse();

        SemesterRequestChecker checker = new SemesterRequestChecker();
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
		
		long yearid = semesterDao.add(request, school_id, operid);
		
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
		
		semesterDao.del(request.getId(), school_id);
		return response;
    }

    @Override
    public CommonModDelResponse mod(SemesterRequest request, String operid) {
        CommonModDelResponse response = new CommonModDelResponse();

        SemesterRequestChecker checker = new SemesterRequestChecker();
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
		
		semesterDao.mod(request, school_id);
		
        return response;
    }

    @Override
    public SemesterSearchResponse query(String operid) {
    	SemesterSearchResponse response = new SemesterSearchResponse();

        SchoolPermission permission = permissionService.getPermision(operid);
		
		long school_id = permission.getSchool_id();
		
		if (school_id == 0) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg("school not exist");
			return response;
		}
		
		List<SemesterRestBean> semesters = semesterDao.query(school_id);
    	response.setSemesters(semesters);
    	
        return response;
    }
}
