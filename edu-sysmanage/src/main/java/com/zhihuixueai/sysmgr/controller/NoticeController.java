package com.zhihuixueai.sysmgr.controller;

import com.zhihuixueai.sysmgr.api.commons.*;
import com.zhihuixueai.sysmgr.api.notice.NoticeDisplayResponse;
import com.zhihuixueai.sysmgr.api.notice.NoticeRequest;
import com.zhihuixueai.sysmgr.api.notice.NoticeSearchResponse;
import com.zhihuixueai.sysmgr.service.api.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class NoticeController {
	@Autowired
	private NoticeService service;

	@RequestMapping(value = { "/sysmanage/notices/add" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonAddResponse> add(@RequestBody NoticeRequest request,
												 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonAddResponse response = service.add(request, userid);
		return new ResponseEntity<CommonAddResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/notices/del" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> del(@RequestBody CommonDeleteRequest request,
													@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.del(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/notices/modify" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> mod( @RequestBody NoticeRequest request,
													 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.mod(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/notices/search" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<NoticeSearchResponse> query(@RequestBody CommonSearchRequest request,
													  @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		NoticeSearchResponse response = service.query(request, userid);
		return new ResponseEntity<NoticeSearchResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/notices/display" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<NoticeDisplayResponse> display(@RequestBody CommonDisplayRequest request,
														 @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		NoticeDisplayResponse response = service.display(request, userid);
		return new ResponseEntity<NoticeDisplayResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/notices/publish" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CommonModDelResponse> publish(@RequestBody CommonDeleteRequest request,
													@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CommonModDelResponse response = service.publish(request, userid);
		return new ResponseEntity<CommonModDelResponse>(response, null, HttpStatus.OK);
	}
}
