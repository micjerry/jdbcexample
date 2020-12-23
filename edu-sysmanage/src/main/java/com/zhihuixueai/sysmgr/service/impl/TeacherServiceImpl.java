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
import com.zhihuixueai.sysmgr.api.commons.CommonDeleteRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonDisplayRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;
import com.zhihuixueai.sysmgr.api.commons.Condition;
import com.zhihuixueai.sysmgr.api.teachers.SubjectListResponse;
import com.zhihuixueai.sysmgr.api.teachers.SubjectsRequest;
import com.zhihuixueai.sysmgr.api.teachers.TeacherDisplayBean;
import com.zhihuixueai.sysmgr.api.teachers.TeacherDisplayResponse;
import com.zhihuixueai.sysmgr.api.teachers.TeacherRequest;
import com.zhihuixueai.sysmgr.api.teachers.TeacherRestBean;
import com.zhihuixueai.sysmgr.api.teachers.TeacherSearchResponse;
import com.zhihuixueai.sysmgr.db.dao.api.CommonDao;
import com.zhihuixueai.sysmgr.db.dao.api.TeacherDao;
import com.zhihuixueai.sysmgr.db.dao.api.UserDao;
import com.zhihuixueai.sysmgr.db.model.teachers.TeacherModel;
import com.zhihuixueai.sysmgr.db.model.users.SysUser;
import com.zhihuixueai.sysmgr.permission.api.PermissionService;
import com.zhihuixueai.sysmgr.permission.api.SchoolPermission;
import com.zhihuixueai.sysmgr.service.api.TeacherService;
import com.zhihuixueai.sysmgr.service.checkers.TeacherRequestChecker;
import com.zhihuixueai.sysmgr.service.fuzzy.TeacherFuzzy;
import com.zhihuixueai.sysmgr.tools.AuthInfo;
import com.zhihuixueai.sysmgr.tools.BeanTransfer;
import com.zhihuixueai.sysmgr.tools.ConditionFormat;
import com.zhihuixueai.sysmgr.tools.PassUtils;
import com.zhihuixueai.sysmgr.tools.PermissionUtils;

@Component
public class TeacherServiceImpl implements TeacherService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonDao commonDao;
	  
	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private PermissionService permissionService;

	@Override
	public CommonAddResponse add(TeacherRequest request, String operid) {
		CommonAddResponse response = new CommonAddResponse();

		TeacherRequestChecker checker = new TeacherRequestChecker();
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
		
		if (!permission.isSchooladmin()) {
			response.setCode(StatusCode.FORBIDDEN);
            response.setMsg(StatusCode.FORBIDDEN_MSG);
            return response;
		}
		
		SysUser user = new SysUser();
		
		user.setUsername(request.getPhone());
		user.setMobilephone(request.getPhone());
		user.setNick_name(request.getName());
		user.setRole_id(RoleConst.SCHOOL_TEACHER);
		user.setEmail(request.getEmail());
		
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
		
		TeacherModel teacher = BeanTransfer.getTeacher(request, operid, school_id, userid);
		
		long teacherid = teacherDao.add(teacher);
		
		if (teacherid == 0) {
			response.setCode(StatusCode.REPEAT);
            response.setMsg(StatusCode.REPEAT_MSG);
            return response;
		}
		
		response.setId(teacherid);

		return response;
	}

	@Override
	public CommonModDelResponse mod(TeacherRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();

		TeacherRequestChecker checker = new TeacherRequestChecker();
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
		
        if (!permission.isSchooladmin()) {
			response.setCode(StatusCode.FORBIDDEN);
            response.setMsg(StatusCode.FORBIDDEN_MSG);
            return response;
		}
        
        TeacherModel teacher = BeanTransfer.getTeacher(request, operid, school_id, null);
        
        if (!teacherDao.mod(teacher)) {
        	response.setCode(StatusCode.NOTEXIST);
			response.setMsg("teacher not exist");
			return response;
        }
        
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
		
		teacherDao.del(request.getId(), school_id);
		return response;
	}

	@Override
	public TeacherSearchResponse query(CommonSearchRequest request, String operid) {
		TeacherSearchResponse response = new TeacherSearchResponse();
    	
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
		
		QuerySqler sqler = ConditionFormat.formatSearchSql(conditions, request.getPage_index(), request.getPage_number(), teacherDao.getView(), TeacherFuzzy.getFuzzies(), teacherDao.getFileds());
		
		logger.debug("query: {}", sqler.getQuery());
		logger.debug("count: {}", sqler.getCount());
		
		List<TeacherRestBean> teachers = teacherDao.query(sqler.getQuery());
		int total = commonDao.count(sqler.getCount());
		
		response.setPage_index(request.getPage_index());
		response.setTotal_results(total);
		response.setTeachers(teachers);
		
        return response;
	}

	@Override
	public TeacherDisplayResponse display(CommonDisplayRequest request, String operid) {
		TeacherDisplayResponse response = new TeacherDisplayResponse();
    	
    	if (request.getId() == 0 ) {
			logger.error("invalid reqeust id is 0");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + " id is 0");
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
		
		TeacherDisplayBean teacher = teacherDao.display(request.getId(), school_id);
		
		if (null == teacher) {
			logger.error("no teacher id: {}", request.getId());
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG);
			return response;
		}
		
		
		List<CommonSubjectBean> subjects = teacherDao.getSubjects(request.getId(), school_id);
		
		teacher.setSubjects(subjects);
		
		response.setTeacher(teacher);
		
		return response;
	}

	@Override
	public CommonModDelResponse setSubjects(SubjectsRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();
		
		TeacherRequestChecker checker = new TeacherRequestChecker();
		if (!checker.check_subjects(request)) {
			logger.error("invalid reqeust");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + checker.errStr());
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
		
		teacherDao.clearSubjects(request.getId(), school_id);
		
		for (CommonSubjectBean subject: request.getSubjects()) {
			teacherDao.addSubject(subject, request.getId(), school_id);
		}
		
		return response;
	}

	@Override
	public SubjectListResponse getSubjects(CommonDeleteRequest request, String operid) {
		SubjectListResponse response = new SubjectListResponse();
		
		if (request.getId() == 0) {
			logger.error("invalid reqeust id is 0");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + " invalid id");
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
		
		List<CommonSubjectBean> subjects = teacherDao.getSubjects(request.getId(), school_id);
		
		response.setSubjects(subjects);
		
		return response;
	}
}
