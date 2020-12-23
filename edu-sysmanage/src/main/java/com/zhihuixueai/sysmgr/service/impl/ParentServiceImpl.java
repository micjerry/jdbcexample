package com.zhihuixueai.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.QuerySqler;
import com.zhihuixueai.sysmgr.api.RoleConst;
import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.commons.Condition;
import com.zhihuixueai.sysmgr.api.parents.ParentAddRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDelRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDisplayRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDisplayResponse;
import com.zhihuixueai.sysmgr.api.parents.ParentModRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentRestBean;
import com.zhihuixueai.sysmgr.api.parents.ParentSearchResponse;
import com.zhihuixueai.sysmgr.api.parents.ParentStudentRelation;
import com.zhihuixueai.sysmgr.db.dao.api.CommonDao;
import com.zhihuixueai.sysmgr.db.dao.api.ParentDao;
import com.zhihuixueai.sysmgr.db.dao.api.UserDao;
import com.zhihuixueai.sysmgr.db.model.users.SysUser;
import com.zhihuixueai.sysmgr.permission.api.PermissionService;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;
import com.zhihuixueai.sysmgr.service.api.ParentService;
import com.zhihuixueai.sysmgr.service.checkers.ParentRequestChecker;
import com.zhihuixueai.sysmgr.service.fuzzy.ParentFuzzy;
import com.zhihuixueai.sysmgr.tools.AuthInfo;
import com.zhihuixueai.sysmgr.tools.ConditionFormat;
import com.zhihuixueai.sysmgr.tools.PassUtils;
import com.zhihuixueai.sysmgr.tools.PermissionUtils;

@Component
public class ParentServiceImpl implements ParentService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ParentDao parentDao;
	
	@Override
	public CommonAddResponse add(ParentAddRequest request, String operid) {
		CommonAddResponse response = new CommonAddResponse();

		ParentRequestChecker checker = new ParentRequestChecker();
		if (!checker.add_check(request)) {
			logger.error("invalid request");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
			return response;
		}

		SchoolPermission permission = permissionService.getPermision(operid);

		long school_id = permission.getSchool_id();

		if (school_id == 0) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg("school not exist");
			return response;
		}
		
		SysUser user = new SysUser();
		
		user.setUsername(request.getPhone());
		user.setMobilephone(request.getPhone());
		user.setNick_name(request.getName());
		user.setRole_id(RoleConst.PARENT);
		
		AuthInfo.makeUserAuth(user, PassUtils.genPass(request.getPhone()));
		
		if (StringUtils.isEmpty(user.getPassword())) {
			logger.error("make auth failed.");
			response.setCode(StatusCode.OTHER);
			response.setMsg(StatusCode.OTHER_MSG + ": make auth failed.");
			return response; 
		}
		
		String userid =  userDao.add(user);
		if (StringUtils.isEmpty(userid)) {
			logger.error("create acount for teacher: {} phone: {} failed.", request.getName(), request.getPhone());
			response.setCode(StatusCode.OTHER);
			response.setMsg(StatusCode.OTHER_MSG + ": create acount failed.");
			return response; 
		}
		
		long parent_id = parentDao.add(request, operid, school_id, userid);
		
		if (parent_id == 0) {
			logger.error("create parent failed phone: {} .", request.getPhone());
			response.setCode(StatusCode.OTHER);
			response.setMsg(StatusCode.OTHER_MSG + ": create parent failed.");
			return response; 
		}
		
		for (ParentStudentRelation relation: request.getStudents()) {
			parentDao.add_relation(relation, school_id, parent_id);
		}
		
		response.setId(parent_id);

		return response;
	}

	@Override
	public CommonModDelResponse mod(ParentModRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();

		ParentRequestChecker checker = new ParentRequestChecker();
		if (!checker.mod_check(request)) {
			logger.error("invalid request");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
			return response;
		}


		SchoolPermission permission = permissionService.getPermision(operid);

		long school_id = permission.getSchool_id();

		if (school_id == 0) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg("school not exist");
			return response;
		}
		
		ParentRestBean parent = parentDao.display(request.getId(), request.getStudent_id(), school_id);
		
		if (null == parent) {
			logger.error("no parent id: {}", request.getId());
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG);
			return response;
		}
		
		if (!PermissionUtils.isPermited(permission, parent.getClass_id())) {
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG);
			return response;
		}
		
		if (!parentDao.mod_relation(request, school_id)) {
			logger.error("relation does not exist");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg("relation does not exist");
			return response;
		}
		
		if (!parentDao.mod(request, school_id)) {
			logger.error("parent does not exist");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg("parent does not exist");
			return response;
		}
		
		return response;
	}

	@Override
	public CommonModDelResponse del(ParentDelRequest request, String operid) {
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
		
		parentDao.del_relation(request, school_id);
		return response;
	}

	@Override
	public ParentSearchResponse query(CommonSearchRequest request, String operid) {
		ParentSearchResponse response = new ParentSearchResponse();
    	
    	SchoolPermission permission = permissionService.getPermision(operid);
    	
		if (!permission.isSchooladmin()) {
			response.setCode(StatusCode.FORBIDDEN);
            response.setMsg(StatusCode.FORBIDDEN_MSG);
            return response;
		}
		
		ArrayList<Condition> conditions = new ArrayList<Condition>();
		conditions.addAll(request.getConditions());
		
		List<Condition> permit_conditions = PermissionUtils.getPermissionCoditions(permission);
		if (permit_conditions != null && !permit_conditions.isEmpty()) {
			conditions.addAll(permit_conditions);
		}	
		
		QuerySqler sqler = ConditionFormat.formatSearchSql(conditions, request.getPage_index(), request.getPage_number(), parentDao.getView(), ParentFuzzy.getFuzzies(), parentDao.getFields());
		
		logger.debug("query: {}", sqler.getQuery());
		logger.debug("count: {}", sqler.getCount());
		
		List<ParentRestBean> parents = parentDao.query(sqler.getQuery());
		int total = commonDao.count(sqler.getCount());
		
		response.setPage_index(request.getPage_index());
		response.setTotal_results(total);
		response.setParents(parents);
		
        return response;
	}

	@Override
	public ParentDisplayResponse display(ParentDisplayRequest request, String operid) {
		ParentDisplayResponse response = new ParentDisplayResponse();
		ParentRequestChecker checker = new ParentRequestChecker();
		
    	if (!checker.displayCheck(request) ) {
			logger.error("invalid reqeust id is 0");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
			return response;
		}
		
		SchoolPermission permission = permissionService.getPermision(operid);
		
		
		long school_id = permission.getSchool_id();
		
		if (school_id == 0) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg("school not exist");
			return response;
		}
		
		ParentRestBean parent = parentDao.display(request.getId(), request.getStudent_id(), school_id);
		
		if (null == parent) {
			logger.error("no parent id: {}", request.getId());
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG);
			return response;
		}
		
		if (!PermissionUtils.isPermited(permission, parent.getClass_id())) {
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG);
			return response;
		}
	
		response.setParent(parent);
		
		return response;
	}
}
