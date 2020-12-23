package com.zhihuixueai.sysmgr.controller;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.students.StudentDisplayResponse;
import com.zhihuixueai.sysmgr.api.students.StudentRequest;
import com.zhihuixueai.sysmgr.api.students.StudentSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zhihuixueai.sysmgr.service.api.StudentService;

@RestController
public class StudentController {
	@Autowired
	private StudentService service;

	@RequestMapping(value = { "/sysmanage/students/add" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonAddResponse> add(@RequestBody StudentRequest request,
												 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonAddResponse response = service.add(request, userid);
		return new ResponseEntity<CommonAddResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/students/del" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> del(@RequestBody CommonDeleteRequest request,
														@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.del(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/students/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody StudentRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/students/search" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<StudentSearchResponse> query(@RequestBody CommonSearchRequest request,
													  @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		StudentSearchResponse response = service.query(request, userid);
		return new ResponseEntity<StudentSearchResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/students/display" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<StudentDisplayResponse> display(@RequestBody CommonDisplayRequest request,
														 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		StudentDisplayResponse response = service.display(request, userid);
		return new ResponseEntity<StudentDisplayResponse>(response, null, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/sysmanage/students/updatepass" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> updatepass(@RequestBody CommonDisplayRequest request,
														 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.updatepass();
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}
}
