package com.zhihuixueai.sysmgr.service.checkers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhihuixueai.sysmgr.api.CommonConst;
import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;
import com.zhihuixueai.sysmgr.api.teachers.SubjectsRequest;
import com.zhihuixueai.sysmgr.api.teachers.TeacherRequest;
import com.zhihuixueai.sysmgr.tools.EmailFormat;
import com.zhihuixueai.sysmgr.tools.PhoneNumberFormat;


//private Long id;
//private String name;*
//private String phone;*
//private String email;
//private List<String> role_ids;
//private List<CommonSubjectRestBean>  subjects;
public class TeacherRequestChecker {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String errStr;

    public String  errStr() {
        return errStr;
    }

    public boolean add_check(TeacherRequest request) {
        return commonCheck(request);
    }

    public boolean mod_check(TeacherRequest request) {
        if (null == request.getId() || request.getId() == 0) {
            logger.error("invalid request teacher id is empty");
            return false;
        }

        return commonCheck(request);
    }

    public boolean commonCheck(TeacherRequest request) {
        if (StringUtils.isEmpty(request.getName())) {
            errStr = "invalid request name is empty";
            logger.error(errStr);
            return false;
        }
        if (request.getName().length() > CommonConst.NAME_MAX_LENGTH) {
            errStr = "invalid request name max lenght:" + CommonConst.NAME_MAX_LENGTH + ", request: " + request.getName().length();
            logger.error(errStr);
            return false;
        }

        if (StringUtils.isEmpty(request.getPhone())) {
            errStr = "invalid request phone is empty";
            logger.error(errStr);
            return false;
        }
        if (request.getPhone().length() > CommonConst.NUM_MAX_LENGTH) {
            errStr = "invalid request phone max lenght:" + CommonConst.NUM_MAX_LENGTH + ", request:" + request.getPhone().length();
            logger.error(errStr);
            return false;
        }
        if (!PhoneNumberFormat.isPhoneNO(request.getPhone())) {
            errStr = "invalid request phone format error";
            logger.error(errStr);
            return false;
        }

        if (!StringUtils.isEmpty(request.getEmail())) {
            if (request.getEmail().length() > CommonConst.ACCOUNT_MAX_LENGTH) {
                errStr = "invalid request email max lenght:" + CommonConst.ACCOUNT_MAX_LENGTH + ", request:" + request.getEmail().length();
                logger.error(errStr);
                return false;
            }

            if (!EmailFormat.isEmail(request.getEmail())) {
                errStr = "invalid request email format error";
                logger.error(errStr);
                return false;
            }
        }

        return true;
    }
    
    public boolean check_subjects(SubjectsRequest request) {
    	if (request.getSubjects() == null || request.getSubjects().isEmpty()) {
    		errStr = "invalid request no subjects";
            logger.error(errStr);
    		return false;
    	}
    	
    	if (request.getId() == null || request.getId() == 0) {
    		errStr = "id is invalid";
            logger.error(errStr);
    		return false;
    	}
    	
    	int default_number = 0;
    	
    	for (CommonSubjectBean subject: request.getSubjects()) {
    		if (subject.getClass_id() == null || subject.getId() == null) {
    			errStr = "invalid subject";
                logger.error(errStr);
    			return false;
    		}
    		
    		if (subject.getIs_default() == null) {
    			subject.setIs_default(0);
    		}
    		
    		if (subject.getIs_default() != 1 && subject.getIs_default() != 0) {
    			errStr = "invalid subject is_default";
                logger.error(errStr);
    			return false;
    		}
    		
    		if (subject.getIs_default() == 1) {
    			default_number++;
    		}
    	}
    	
    	if (default_number != 1) {
    		errStr = "default subject number must be 1";
            logger.error(errStr);
    		return false;
    	}
    	
    	return true;
    }


}
