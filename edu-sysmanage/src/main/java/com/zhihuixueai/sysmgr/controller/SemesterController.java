package com.zhihuixueai.sysmgr.controller;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.semesters.SemesterRequest;
import com.zhihuixueai.sysmgr.api.semesters.SemesterSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zhihuixueai.sysmgr.service.api.SemesterService;

@RestController
public class SemesterController {
	@Autowired
	private SemesterService service;

	@RequestMapping(value = { "/sysmanage/semesters/add" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonAddResponse> add(@RequestBody SemesterRequest request,
												 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonAddResponse response = service.add(request, userid);
		return new ResponseEntity<CommonAddResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/semesters/del" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> del(@RequestBody CommonDeleteRequest request,
													@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.del(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/semesters/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody SemesterRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/semesters/list" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<SemesterSearchResponse> query(@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		SemesterSearchResponse response = service.query(userid);
		return new ResponseEntity<SemesterSearchResponse>(response, null, HttpStatus.OK);
	}
}
