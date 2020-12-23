package com.zhihuixueai.sysmgr.service.impl;

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
import com.zhihuixueai.sysmgr.api.schools.SchoolDisableRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolDisplayBean;
import com.zhihuixueai.sysmgr.api.schools.SchoolDisplayResponse;
import com.zhihuixueai.sysmgr.api.schools.SchoolRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolRestBean;
import com.zhihuixueai.sysmgr.api.schools.SchoolSearchResponse;
import com.zhihuixueai.sysmgr.db.dao.api.CommonDao;
import com.zhihuixueai.sysmgr.db.dao.api.SchoolDao;
import com.zhihuixueai.sysmgr.db.dao.api.TeacherDao;
import com.zhihuixueai.sysmgr.db.dao.api.UserDao;
import com.zhihuixueai.sysmgr.db.model.schools.SchoolAdmin;
import com.zhihuixueai.sysmgr.db.model.teachers.TeacherModel;
import com.zhihuixueai.sysmgr.db.model.users.SysUser;
import com.zhihuixueai.sysmgr.service.api.SchoolService;
import com.zhihuixueai.sysmgr.service.checkers.CommonChecker;
import com.zhihuixueai.sysmgr.service.checkers.SchoolRequestChecker;
import com.zhihuixueai.sysmgr.service.fuzzy.SchoolFuzzy;
import com.zhihuixueai.sysmgr.tools.AuthInfo;
import com.zhihuixueai.sysmgr.tools.ConditionFormat;
import com.zhihuixueai.sysmgr.tools.PassUtils;

@Component
public class SchoolServiceImpl implements SchoolService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SchoolDao schoolDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private CommonDao commonDao;

	@Override
	public CommonAddResponse add(SchoolRequest request, String operid) {
		CommonAddResponse response = new CommonAddResponse();

		SchoolRequestChecker checker = new SchoolRequestChecker();
		if (!checker.add_check(request)) {
			logger.error("invalid request");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
			return response;
		}
		
		// add school
		long schoolid = schoolDao.add(request, operid);
		if (schoolid == 0) {
			logger.error("school exist: {}.", request.getName());
			response.setCode(StatusCode.REPEAT);
			response.setMsg(StatusCode.REPEAT_MSG);
			return response; 
		}
		
		// add admin
		SysUser user = new SysUser();
		
		user.setUsername(request.getAdmin_account());
		user.setMobilephone(request.getAdmin_account());
		user.setRole_id(RoleConst.SCHOOL_ADMIN_ID);
		
		AuthInfo.makeUserAuth(user, PassUtils.genPass(request.getAdmin_account()));
		
		if (StringUtils.isEmpty(user.getPassword())) {
			logger.error("make auth failed.");
			response.setCode(StatusCode.OTHER);
			response.setMsg(StatusCode.OTHER_MSG + ": make auth failed.");
			return response; 
		}
		
		String userid =  userDao.add(user);
		
		if (StringUtils.isEmpty(userid)) {
			logger.error("add admin user failed: {}.", user.getUsername());
			response.setCode(StatusCode.OTHER);
			response.setMsg(StatusCode.OTHER_MSG + ": add admin user failed " + user.getUsername());
			return response;
		}
		
		// add teacher
		TeacherModel teacher = new TeacherModel();
		teacher.setCreated_id(operid);
		teacher.setMobile(user.getMobilephone());
		teacher.setSchool_id(schoolid);
		teacher.setTeacher_name(user.getUsername());
		teacher.setUser_id(userid);
		
		teacherDao.add(teacher);
		
		response.setId(schoolid);;
		return response;
	}

	@Override
	public CommonModDelResponse mod(SchoolRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();

		SchoolRequestChecker checker = new SchoolRequestChecker();
		if (!checker.mod_check(request)) {
			logger.error("invalid request");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + checker.errStr());
			return response;
		}
		
		if (!schoolDao.mod(request)) {
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG);
		}
		
		return response;
	}

	@Override
	public CommonModDelResponse disable(SchoolDisableRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();
		if (!schoolDao.disable(request.getId(), request.getStatus())) {
			response.setCode(StatusCode.OTHER);
			response.setMsg(StatusCode.OTHER_MSG);
		}
		return response;
	}

	@Override
	public SchoolSearchResponse query(CommonSearchRequest request, String operid) {
		// TODO Auto-generated method stub v_school
		SchoolSearchResponse response = new SchoolSearchResponse();
		QuerySqler sqler = ConditionFormat.formatSearchSql(request.getConditions(), request.getPage_index(), request.getPage_number(), schoolDao.getView(), SchoolFuzzy.getFuzzies(), null);
		
		//logger.debug("query: {}", sqler.getQuery());
		//logger.debug("count: {}", sqler.getCount());
		
		List<SchoolRestBean> schools = schoolDao.query(sqler.getQuery());
		int total = commonDao.count(sqler.getCount());
		
		response.setPage_index(request.getPage_index());
		response.setTotal_results(total);
		response.setSchools(schools);
		
		return response;
	}

	@Override
	public SchoolDisplayResponse display(CommonDisplayRequest request, String operid) {
		SchoolDisplayResponse response = new SchoolDisplayResponse();
		if (!CommonChecker.check_display(request)) {
			logger.error("invalid request id is empty");
			response.setCode(StatusCode.BAD_REQUEST);
			response.setMsg(StatusCode.BAD_REQUEST_MSG + ": " + "invalid request id is empty");
			return response;
		}
		
		SchoolDisplayBean school = schoolDao.display(request.getId());
		
		if (school == null) {
			logger.error("invalid request school NOTEXIST");
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG + ": " + "invalid request school NOTEXIST");
			return response;
		}
		
		response.setSchool(school);
		return response;
	}
	
	@Override
	public CommonModDelResponse resetadmin(CommonDeleteRequest request, String operid) {
		CommonModDelResponse response = new CommonModDelResponse();
		
		SchoolAdmin admin = schoolDao.getAdmin(request.getId());
		
		if (admin == null || StringUtils.isEmpty(admin.getUser_id())) {
			logger.error("invalid request admin_userid NOTEXIST");
			response.setCode(StatusCode.NOTEXIST);
			response.setMsg(StatusCode.NOTEXIST_MSG + ": admin_userid NOTEXIST");
			return response;
		}
		
		SysUser user = new SysUser();
		user.setUser_id(admin.getUser_id());
		
		
		AuthInfo.makeUserAuth(user, PassUtils.genPass(admin.getUsername()));
		
		userDao.reset(user);
		
		return response;
	}
}
