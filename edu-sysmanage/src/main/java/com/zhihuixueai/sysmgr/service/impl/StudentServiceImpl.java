package com.zhihuixueai.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.QuerySqler;
import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonDeleteRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonDisplayRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.commons.Condition;
import com.zhihuixueai.sysmgr.api.students.StudentDisplayResponse;
import com.zhihuixueai.sysmgr.api.students.StudentParentBean;
import com.zhihuixueai.sysmgr.api.students.StudentRequest;
import com.zhihuixueai.sysmgr.api.students.StudentRestBean;
import com.zhihuixueai.sysmgr.api.students.StudentSearchResponse;
import com.zhihuixueai.sysmgr.cache.api.CommonCache;
import com.zhihuixueai.sysmgr.db.dao.api.CommonDao;
import com.zhihuixueai.sysmgr.db.dao.api.StudentsDao;
import com.zhihuixueai.sysmgr.db.dao.api.UserDao;
import com.zhihuixueai.sysmgr.db.model.users.SysUser;
import com.zhihuixueai.sysmgr.permission.api.PermissionService;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;
import com.zhihuixueai.sysmgr.service.api.StudentService;
import com.zhihuixueai.sysmgr.service.checkers.StudentRequestChecker;
import com.zhihuixueai.sysmgr.service.fuzzy.StudentFuzzy;
import com.zhihuixueai.sysmgr.tools.AuthInfo;
import com.zhihuixueai.sysmgr.tools.ConditionFormat;
import com.zhihuixueai.sysmgr.tools.PassUtils;
import com.zhihuixueai.sysmgr.tools.PermissionUtils;

@Component
public class StudentServiceImpl implements StudentService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CommonCache commonCache;
	
	@Autowired
	private StudentsDao studentDao;
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public CommonAddResponse add(StudentRequest request, String operid) {
		
		CommonAddResponse response = new CommonAddResponse();
		
		StudentRequestChecker checker = new StudentRequestChecker();
		
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
		
		if (!PermissionUtils.isPermited(permission, request.getClass_id())) {
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG);
			return response;
		}
		
		SysUser user = new SysUser();
		user.setUsername(request.getStudent_number());
		user.setNick_name(request.getName());
			
		AuthInfo.makeUserAuth(user, PassUtils.genPass(request.getStudent_number()));
		
		String userid =  userDao.add(user);
		if (StringUtils.isEmpty(userid)) {
			logger.error("add student user failed: {}.", user.getUsername());
			response.setCode(StatusCode.OTHER);
			response.setMsg(StatusCode.OTHER_MSG + "add student user failed: " + user.getUsername());
			return response;
		}
		
		
		long student_id = studentDao.add(request, operid, userid, school_id);
		
		if (student_id == 0) {
			response.setCode(StatusCode.REPEAT);
			response.setMsg(StatusCode.REPEAT_MSG);
			return response;
		}
		
		response.setId(student_id);
		
		return response;
	}

	@Override
	public CommonModDelResponse del(CommonDeleteRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();
		if (request.getId() == 0 ) {
			logger.error("invalid request id is empty");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": invalid request id is empty");
			return response;
		}
		
		SchoolPermission permission = permissionService.getPermision(operid);
		
		long classid = commonCache.getClassidByStudent(request.getId());
		
		if (!PermissionUtils.isPermited(permission, classid)) {
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG);
			return response;
		}
		
		studentDao.del(request.getId());
		commonCache.clearClassidByStudent(request.getId());
		return response;
	}

	@Override
	public CommonModDelResponse mod(StudentRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();
		
		StudentRequestChecker checker = new StudentRequestChecker();
		
		if (!checker.mod_check(request)) {
			logger.error("invalid request");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
			return response;
		}
		
		SchoolPermission permission = permissionService.getPermision(operid);
		
		if (!PermissionUtils.isPermited(permission, request.getClass_id())) {
			logger.error("user: {} has no access to student: {}", operid, request.getId());
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG + String.format(" user has no access to student: {}", request.getId()));
			return response;
		}
		
		studentDao.mod(request);
		return response;
	}

	@Override
	public StudentSearchResponse query(CommonSearchRequest request, String operid) {
		StudentSearchResponse response = new StudentSearchResponse();
		
		SchoolPermission permission = permissionService.getPermision(operid);
		
		ArrayList<Condition> conditions = new ArrayList<Condition>();
		conditions.addAll(request.getConditions());
		
		List<Condition> permit_conditions = PermissionUtils.getPermissionCoditions(permission);
		if (permit_conditions != null && !permit_conditions.isEmpty()) {
			conditions.addAll(permit_conditions);
		}
		
		QuerySqler sqler = ConditionFormat.formatSearchSql(conditions, request.getPage_index(), request.getPage_number(), studentDao.getView(), StudentFuzzy.getFuzzies(), null);
		
		List<StudentRestBean> students = studentDao.query(sqler.getQuery());
		int total = commonDao.count(sqler.getCount());
		
		response.setPage_index(request.getPage_index());
		response.setTotal_results(total);
		response.setStudents(students);
		
		return response;
	}

	@Override
	public StudentDisplayResponse display(CommonDisplayRequest request, String operid) {
		StudentDisplayResponse response = new StudentDisplayResponse();
		
		if (request.getId() == 0 ) {
			logger.error("invalid reqeust id is 0");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": id is 0");
			return response;
		}
		
		SchoolPermission permission = permissionService.getPermision(operid);
		StudentRestBean student = studentDao.display(request.getId());
		
		if (null == student) {
			logger.error("no student id: {}", request.getId());
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG + String.format(" no student id: {}", request.getId()));
			return response;
		}
		
		if (!PermissionUtils.isPermited(permission, student.getClass_id())) {
			logger.error("user:{} has no acces for class: {}", operid, request.getId());
			response.setCode(StatusCode.FORBIDDEN);
			response.setMsg(StatusCode.FORBIDDEN_MSG + String.format("user has no acces for class: {}", request.getId()));
			return response;
		}
		
		List<StudentParentBean> parents = studentDao.list_parents(request.getId());
		if (parents != null && !parents.isEmpty()) {
			student.setParents(parents);
		}
		
		response.setStudent(student);
		
		return response;
	}

	@Override
	public CommonModDelResponse updatepass() {
		
		CommonModDelResponse response = new CommonModDelResponse();
		studentDao.updatePass();
		return response;
	}
}
