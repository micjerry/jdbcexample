package com.zhihuixueai.sysmgr.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.grades.GradeModRequest;
import com.zhihuixueai.sysmgr.api.grades.GradeRestBean;
import com.zhihuixueai.sysmgr.api.grades.GradeSearchResponse;
import com.zhihuixueai.sysmgr.db.dao.api.GradeDao;
import com.zhihuixueai.sysmgr.permission.api.PermissionService;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;
import com.zhihuixueai.sysmgr.service.api.GradeService;
import com.zhihuixueai.sysmgr.service.checkers.GradeRequestChcker;

@Component
public class GradeServiceImpl implements GradeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
	private PermissionService permissionService;
    
    @Autowired
    private GradeDao gradeDao;
    
    @Override
    public CommonModDelResponse mod(GradeModRequest request, String operid) {
        CommonModDelResponse response = new CommonModDelResponse();

        GradeRequestChcker checker = new GradeRequestChcker();
        if (!checker.mod_check(request)) {
            logger.error("invalid request");
            response.setCode(StatusCode.BAD_REQUEST);
            response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
            return response;
        }
        
        SchoolPermission permission = permissionService.getPermision(operid);
    	if (permission == null) {
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG);
			return response;
		}

    	if (permission.getSchool_id() == 0) {
    		logger.error("no school for user:{}", operid);
    		response.setCode(StatusCode.NOTEXIST);
			response.setMsg("no school");
			return response;
    	}
    	
    	boolean result = gradeDao.update_master(permission.getSchool_id(), request.getId(), request.getMaster_id());

    	if (!result) {
    		response.setCode(StatusCode.OTHER);
			response.setMsg(StatusCode.OTHER_MSG);
			return response;
    	}
    	
        return response;
    }

    @Override
    public GradeSearchResponse query(String operid) {
    	GradeSearchResponse response = new GradeSearchResponse();
    	
    	SchoolPermission permission = permissionService.getPermision(operid);
    	if (permission == null) {
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG);
			return response;
		}
    	
    	if (permission.getSchool_id() == 0) {
    		logger.error("no school for user:{}", operid);
    		response.setCode(StatusCode.NOTEXIST);
			response.setMsg("no school");
			return response;
    	}
    	
    	List<GradeRestBean> grades = gradeDao.getGrades(permission.getSchool_id());
    	
    	if (grades == null || grades.isEmpty()) {
    		logger.error("no grae for school:{}", permission.getSchool_id());
    		response.setCode(StatusCode.NOTEXIST);
    		response.setMsg(StatusCode.NOTEXIST_MSG);
    		return response;
    	}
    	
    	response.setGrades(grades);
        return response;
    }
}
