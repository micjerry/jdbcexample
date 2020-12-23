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
import com.zhihuixueai.sysmgr.api.teachers.SubjectListResponse;
import com.zhihuixueai.sysmgr.api.teachers.SubjectsRequest;
import com.zhihuixueai.sysmgr.api.teachers.TeacherDisplayResponse;
import com.zhihuixueai.sysmgr.api.teachers.TeacherRequest;
import com.zhihuixueai.sysmgr.api.teachers.TeacherSearchResponse;
import com.zhihuixueai.sysmgr.service.api.TeacherService;

@RestController
public class TeacherController {
	@Autowired
	private TeacherService service;

	@RequestMapping(value = { "/sysmanage/teachers/add" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonAddResponse> add(@RequestBody TeacherRequest request,
												 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonAddResponse response = service.add(request, userid);
		return new ResponseEntity<CommonAddResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/teachers/del" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> del(@RequestBody CommonDeleteRequest request,
														@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.del(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/teachers/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody TeacherRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/teachers/search" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<TeacherSearchResponse> query(@RequestBody CommonSearchRequest request,
													   @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		TeacherSearchResponse response = service.query(request, userid);
		return new ResponseEntity<TeacherSearchResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/teachers/display" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<TeacherDisplayResponse> display(@RequestBody CommonDisplayRequest request,
														  @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		TeacherDisplayResponse response = service.display(request, userid);
		return new ResponseEntity<TeacherDisplayResponse>(response, null, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/sysmanage/teachers/setsubjects" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> setsubjects( @RequestBody SubjectsRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.setSubjects(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/sysmanage/teachers/displaysubjects" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<SubjectListResponse> getsubjects( @RequestBody CommonDeleteRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		SubjectListResponse response = service.getSubjects(request, userid);
		return new ResponseEntity<SubjectListResponse>(response, null, HttpStatus.OK);
	}


}
