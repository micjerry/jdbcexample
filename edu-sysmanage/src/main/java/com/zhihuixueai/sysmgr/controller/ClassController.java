package com.zhihuixueai.sysmgr.controller;

import com.zhihuixueai.sysmgr.api.classes.ClassDisplayResponse;
import com.zhihuixueai.sysmgr.api.classes.ClassRequest;
import com.zhihuixueai.sysmgr.api.classes.ClassSearchResponse;
import com.zhihuixueai.sysmgr.api.commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zhihuixueai.sysmgr.service.api.ClassService;

@RestController
public class ClassController {
	@Autowired
	private ClassService service;

	@RequestMapping(value = { "/sysmanage/classes/add" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonAddResponse> add(@RequestBody ClassRequest request,
												 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonAddResponse response = service.add(request, userid);
		return new ResponseEntity<CommonAddResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/classes/del" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> del(@RequestBody CommonDeleteRequest request,
													@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.del(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/classes/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody ClassRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/classes/search" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<ClassSearchResponse> query(@RequestBody CommonSearchRequest request,
													   @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		ClassSearchResponse response = service.query(request, userid);
		return new ResponseEntity<ClassSearchResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/classes/display" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<ClassDisplayResponse> display(@RequestBody CommonDisplayRequest request,
														@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		ClassDisplayResponse response = service.display(request, userid);
		return new ResponseEntity<ClassDisplayResponse>(response, null, HttpStatus.OK);
	}
}
