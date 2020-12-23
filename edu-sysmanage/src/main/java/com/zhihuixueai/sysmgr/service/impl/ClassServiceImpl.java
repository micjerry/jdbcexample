package com.zhihuixueai.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.QuerySqler;
import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.classes.ClassDisplayResponse;
import com.zhihuixueai.sysmgr.api.classes.ClassRequest;
import com.zhihuixueai.sysmgr.api.classes.ClassRestBean;
import com.zhihuixueai.sysmgr.api.classes.ClassSearchResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonDeleteRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonDisplayRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.commons.Condition;
import com.zhihuixueai.sysmgr.db.dao.api.ClassDao;
import com.zhihuixueai.sysmgr.db.dao.api.CommonDao;
import com.zhihuixueai.sysmgr.permission.api.PermissionService;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;
import com.zhihuixueai.sysmgr.service.api.ClassService;
import com.zhihuixueai.sysmgr.service.checkers.ClassRequestChecker;
import com.zhihuixueai.sysmgr.service.fuzzy.ClassFuzzy;
import com.zhihuixueai.sysmgr.tools.ConditionFormat;
import com.zhihuixueai.sysmgr.tools.PermissionUtils;

@Component
public class ClassServiceImpl implements ClassService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonDao commonDao;
    
    @Autowired
    private ClassDao classDao;
    
    @Autowired
	private PermissionService permissionService;

    @Override
    public CommonAddResponse add(ClassRequest request, String operid) {
        CommonAddResponse response = new CommonAddResponse();

        ClassRequestChecker checker = new ClassRequestChecker();
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
		
		long classid = classDao.add(request, permission.getSchool_id(), operid);
		
		if (classid == 0) {
			response.setCode(StatusCode.REPEAT);
            response.setMsg(StatusCode.REPEAT_MSG);
            return response;
		}
		
		response.setId(classid);

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
		
		if (!PermissionUtils.isPermited(permission, request.getId())) {
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
		
		classDao.del(request.getId(), school_id);
		return response;
    }

    @Override
    public CommonModDelResponse mod(ClassRequest request, String operid) {
        CommonModDelResponse response = new CommonModDelResponse();

        ClassRequestChecker checker = new ClassRequestChecker();
        if (!checker.mod_check(request)) {
            logger.error("invalid request");
            response.setCode(StatusCode.BAD_REQUEST);
            response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
            return response;
        }
        
		SchoolPermission permission = permissionService.getPermision(operid);
		
		if (!PermissionUtils.isPermited(permission, request.getId())) {
			logger.error("user: {} has no access to class: {}", operid, request.getId());
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
		
		classDao.mod(request, permission.getSchool_id());
		return response;
    }

    @Override
    public ClassSearchResponse query(CommonSearchRequest request, String operid) {
    	ClassSearchResponse response = new ClassSearchResponse();
    	
    	SchoolPermission permission = permissionService.getPermision(operid);
		ArrayList<Condition> conditions = new ArrayList<Condition>();
		conditions.addAll(request.getConditions());
		
		List<Condition> permit_conditions = PermissionUtils.getPermissionCoditions(permission);
		if (permit_conditions != null && !permit_conditions.isEmpty()) {
			conditions.addAll(permit_conditions);
		}	
		
		QuerySqler sqler = ConditionFormat.formatSearchSql(conditions, request.getPage_index(), request.getPage_number(), classDao.getView(), ClassFuzzy.getFuzzies(), classDao.getFileds());
		
		logger.debug("query: {}", sqler.getQuery());
		logger.debug("count: {}", sqler.getCount());
		
		List<ClassRestBean> classes = classDao.query(sqler.getQuery());
		int total = commonDao.count(sqler.getCount());
		
		response.setPage_index(request.getPage_index());
		response.setTotal_results(total);
		response.setCalsses(classes);;
		
        return response;
    }

    @Override
    public ClassDisplayResponse display(CommonDisplayRequest request, String operid) {
    	ClassDisplayResponse response = new ClassDisplayResponse();
    	
    	if (request.getId() == 0 ) {
			logger.error("invalid reqeust id is 0");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + " id is 0");
			return response;
		}
		
		SchoolPermission permission = permissionService.getPermision(operid);
		ClassRestBean class_info = classDao.display(request.getId());
		
		if (null == class_info) {
			logger.error("no student id: {}", request.getId());
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG);
			return response;
		}
		
		if (!PermissionUtils.isPermited(permission, class_info.getId())) {
			logger.error("user:{} has no acces for class: {}", operid, request.getId());
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG);
			return response;
		}
		
		response.setCalss_info(class_info);;
		
		return response;
    }
}
