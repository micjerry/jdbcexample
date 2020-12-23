package com.zhihuixueai.sysmgr.service.checkers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhihuixueai.sysmgr.api.CommonConst;
import com.zhihuixueai.sysmgr.api.parents.ParentAddRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDisplayRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentModRequest;
import com.zhihuixueai.sysmgr.tools.EmailFormat;
import com.zhihuixueai.sysmgr.tools.PhoneNumberFormat;

//private Long id;
//private String name; *
//private Long student_id; *
//private String phone;
//private String email;
//private Integer relation; *
//private Integer education;
public class ParentRequestChecker {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String errStr;

	public String errStr() {
		return errStr;
	}

	public boolean add_check(ParentAddRequest request) {
		return commonCheck(request);
	}

	public boolean mod_check(ParentModRequest request) {
		if (null == request.getId() || request.getId() == 0) {
			errStr = "invalid request id is empty";
			logger.error(errStr);
			return false;
		}

		if (request.getEducation() == null || request.getEducation() > 9 || request.getEducation() < 0) {
			errStr = "invalid request education is invalid";
			logger.error(errStr);
			return false;
		}

		if (request.getRelation() == null || request.getRelation() > 6 || request.getRelation() < 0) {
			errStr = "invalid request relation is invalid";
			logger.error(errStr);
			return false;
		}

		if (StringUtils.isEmpty(request.getPhone())) {
			errStr = "invalid request phone is empty";
			logger.error(errStr);
			return false;
		}

		request.setPhone(PhoneNumberFormat.formatPhone(request.getPhone()));
		if (!PhoneNumberFormat.isPhoneNO(request.getPhone())) {
			errStr = "invalid request phone format error";
			logger.error(errStr);
			return false;
		}

		if (request.getStudent_id() == null || request.getStudent_id() == 0) {
			errStr = "invalid request student_id is empty";
			logger.error(errStr);
			return false;
		}

		if (StringUtils.isEmpty(request.getName())) {
			errStr = "invalid request name is empty";
			logger.error(errStr);
			return false;
		}

		return true;
	}

	public boolean displayCheck(ParentDisplayRequest request) {
		if (null == request.getId() || request.getId() == 0) {
			errStr = "invalid request id is empty";
			logger.error(errStr);
			return false;
		}

		if (null == request.getStudent_id()|| request.getStudent_id() == 0) {
			errStr = "invalid request student_id is empty";
			logger.error(errStr);
			return false;
		}

		return true;

	}

	public boolean commonCheck(ParentAddRequest request) {
		if (StringUtils.isEmpty(request.getName())) {
			errStr = "invalid request name is empty";
			logger.error(errStr);
			return false;
		}

		if (request.getName().length() > CommonConst.NAME_MAX_LENGTH) {
			errStr = "invalid request name max lenght:" + CommonConst.NAME_MAX_LENGTH + ", request: "
					+ request.getName().length();
			logger.error(errStr);
			return false;
		}

		if (!StringUtils.isEmpty(request.getPhone())) {
			request.setPhone(PhoneNumberFormat.formatPhone(request.getPhone()));
			if (!PhoneNumberFormat.isPhoneNO(request.getPhone())) {
				errStr = "invalid request phone format error";
				logger.error(errStr);
				return false;
			}
		}

		if (!StringUtils.isEmpty(request.getEmail())) {
			if (request.getEmail().length() > CommonConst.ACCOUNT_MAX_LENGTH) {
				errStr = "invalid request email max lenght:" + CommonConst.ACCOUNT_MAX_LENGTH + ", request:"
						+ request.getEmail().length();
				logger.error(errStr);
				return false;
			}

			if (!EmailFormat.isEmail(request.getEmail())) {
				errStr = "invalid request email format error";
				logger.error(errStr);
				return false;
			}
		}

		if (request.getEducation() == null || request.getEducation() > 9 || request.getEducation() < 0) {
			errStr = "invalid request education is invalid";
			logger.error(errStr);
			return false;
		}

		return true;
	}

}
