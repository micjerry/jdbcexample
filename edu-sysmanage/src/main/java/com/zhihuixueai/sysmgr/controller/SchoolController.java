package com.zhihuixueai.sysmgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhihuixueai.sysmgr.api.commons.CommonAddResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonDeleteRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonDisplayRequest;
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.commons.EduHttpHeader;
import com.zhihuixueai.sysmgr.api.schools.SchoolDisableRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolDisplayResponse;
import com.zhihuixueai.sysmgr.api.schools.SchoolRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolSearchResponse;
import com.zhihuixueai.sysmgr.service.api.SchoolService;

@RestController
public class SchoolController {
	@Autowired
	private SchoolService service;
	
	@RequestMapping(value = { "/sysmanage/schools/add" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonAddResponse> add( @RequestBody SchoolRequest request,
			@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {
		
		CommonAddResponse response = service.add(request, userid);
		return new ResponseEntity<CommonAddResponse>(response, null, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/sysmanage/schools/disable" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> disable( @RequestBody SchoolDisableRequest request,
			@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {
		
		CommonModDelResponse response = service.disable(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/sysmanage/schools/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody SchoolRequest request,
			@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {
		
		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/sysmanage/schools/search" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<SchoolSearchResponse> query( @RequestBody CommonSearchRequest request,
			@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {
		
		SchoolSearchResponse response = service.query(request, userid);
		return new ResponseEntity<SchoolSearchResponse>(response, null, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/sysmanage/schools/display" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<SchoolDisplayResponse> display( @RequestBody CommonDisplayRequest request,
			@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {
		
		SchoolDisplayResponse response = service.display(request, userid);
		return new ResponseEntity<SchoolDisplayResponse>(response, null, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/sysmanage/schools/resetadmin" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> resetadmin( @RequestBody CommonDeleteRequest request,
			@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {
		
		CommonModDelResponse response = service.resetadmin(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}
}
