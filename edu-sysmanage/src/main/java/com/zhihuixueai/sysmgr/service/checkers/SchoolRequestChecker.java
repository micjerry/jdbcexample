package com.zhihuixueai.sysmgr.service.checkers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhihuixueai.sysmgr.api.CommonConst;
import com.zhihuixueai.sysmgr.api.schools.SchoolRequest;
import com.zhihuixueai.sysmgr.tools.PhoneNumberFormat;

public class SchoolRequestChecker {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String errStr;

	public String  errStr() {
		return errStr;
	}
	
	public boolean add_check(SchoolRequest request) {
		if (StringUtils.isEmpty(request.getAdmin_account())) {
			errStr = "invalid request admin_account is empty";
			logger.error(errStr);
			return false;
		}
		
		request.setAdmin_account(PhoneNumberFormat.formatPhone(request.getAdmin_account()));
		
		if ((request.getAdmin_account().length() != CommonConst.PHONE_NUMBER_LENGTH) || !request.getAdmin_account().matches("[0-9]+")) {
			errStr = "invalid request invalid admin phone number:" + request.getAdmin_account();
			logger.error(errStr);
			return false;
		}
		
		return commonCheck(request);
	}
	
	public boolean mod_check(SchoolRequest request) {
		if (null == request.getId() || request.getId() == 0) {
			errStr = "invalid request school id is empty";
			logger.error(errStr);
			return false;
		}
		
		return commonCheck(request);
	}
	
	private boolean commonCheck(SchoolRequest request) {
		if (StringUtils.isEmpty(request.getName())) {
			errStr = "invalid request school name is empty";
			logger.error(errStr);
			return false;
		}
		
		if (StringUtils.isEmpty(request.getContact_person())) {
			errStr = "invalid request contact_person is null";
			logger.error(errStr);
			return false;
		}
		
		if (StringUtils.isEmpty(request.getContact_phone())) {
			errStr = "invalid request contact_phone is null";
			logger.error(errStr);
			return false;
		}
		
		request.setContact_phone(PhoneNumberFormat.formatPhone(request.getContact_phone()));
		
		if (StringUtils.isEmpty(request.getProvince_id())) {
			errStr = "invalid request no province_id";
			logger.error(errStr);
			return false;
		}
		
		if ((request.getProvince_id().length() != CommonConst.AREA_CODE_LENGTH) || !request.getProvince_id().matches("[0-9]+")) {
			errStr = "invalid province_id" + "AREA_CODE_LENGTH :" + CommonConst.AREA_CODE_LENGTH + ", matches number";
			logger.error(errStr);
			return false;
		}
		
		return true;
	}
}
