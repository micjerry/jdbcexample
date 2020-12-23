package com.zhihuixueai.sysmgr.controller;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearRequest;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zhihuixueai.sysmgr.service.api.SchoolyearService;

@RestController
public class SchoolyearController {
	@Autowired
	private SchoolyearService service;

	@RequestMapping(value = { "/sysmanage/schoolyears/add" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonAddResponse> add(@RequestBody SchoolYearRequest request,
												 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonAddResponse response = service.add(request, userid);
		return new ResponseEntity<CommonAddResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/schoolyears/del" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> del(@RequestBody CommonDeleteRequest request,
													@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.del(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/schoolyears/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody SchoolYearRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/schoolyears/list" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<SchoolYearSearchResponse> query(@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		SchoolYearSearchResponse response = service.query(userid);
		return new ResponseEntity<SchoolYearSearchResponse>(response, null, HttpStatus.OK);
	}
}
