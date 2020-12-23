package com.zhihuixueai.sysmgr.controller;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.grades.GradeModRequest;
import com.zhihuixueai.sysmgr.api.grades.GradeSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zhihuixueai.sysmgr.service.api.GradeService;

@RestController
public class GradeController {
	@Autowired
	private GradeService service;

	@RequestMapping(value = { "/sysmanage/grades/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody GradeModRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/grades/list" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<GradeSearchResponse> query(@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		GradeSearchResponse response = service.query(userid);
		return new ResponseEntity<GradeSearchResponse>(response, null, HttpStatus.OK);
	}
}
