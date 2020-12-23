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
import com.zhihuixueai.sysmgr.api.commons.CommonModDelResponse;
import com.zhihuixueai.sysmgr.api.commons.CommonSearchRequest;
import com.zhihuixueai.sysmgr.api.commons.EduHttpHeader;
import com.zhihuixueai.sysmgr.api.parents.ParentAddRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDelRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDisplayRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDisplayResponse;
import com.zhihuixueai.sysmgr.api.parents.ParentModRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentSearchResponse;
import com.zhihuixueai.sysmgr.service.api.ParentService;

@RestController
public class ParentController {
	@Autowired
	private ParentService service;

	@RequestMapping(value = { "/sysmanage/parents/add" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonAddResponse> add(@RequestBody ParentAddRequest request,
												 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonAddResponse response = service.add(request, userid);
		return new ResponseEntity<CommonAddResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/parents/del" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> del(@RequestBody ParentDelRequest request,
														@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.del(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/parents/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody ParentModRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/parents/search" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<ParentSearchResponse> query(@RequestBody CommonSearchRequest request,
													  @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		ParentSearchResponse response = service.query(request, userid);
		return new ResponseEntity<ParentSearchResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/parents/display" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<ParentDisplayResponse> display(@RequestBody ParentDisplayRequest request,
														 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		ParentDisplayResponse response = service.display(request, userid);
		return new ResponseEntity<ParentDisplayResponse>(response, null, HttpStatus.OK);
	}

}
