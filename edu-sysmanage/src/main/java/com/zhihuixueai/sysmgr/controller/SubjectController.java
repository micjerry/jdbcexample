package com.zhihuixueai.sysmgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhihuixueai.sysmgr.api.commons.EduHttpHeader;
import com.zhihuixueai.sysmgr.api.subjects.SubjectSearchResponse;
import com.zhihuixueai.sysmgr.service.api.SubjectService;

@RestController
public class SubjectController {
	
	@Autowired
	private SubjectService service;
	
	@RequestMapping(value = { "/sysmanage/subjects/list" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<SubjectSearchResponse> list(@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {
		SubjectSearchResponse response = service.list();
		return new ResponseEntity<SubjectSearchResponse>(response, null, HttpStatus.OK);
	}
}
