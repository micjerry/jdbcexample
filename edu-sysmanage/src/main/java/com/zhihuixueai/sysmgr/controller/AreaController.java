package com.zhihuixueai.sysmgr.controller;

import com.zhihuixueai.sysmgr.api.area.*;
import com.zhihuixueai.sysmgr.api.commons.EduHttpHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zhihuixueai.sysmgr.service.api.AreaService;

@RestController
public class AreaController {
	@Autowired
	private AreaService service;

	@RequestMapping(value = { "/sysmanage/areas/province" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ProvinceSearchResponse> province(@RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		ProvinceSearchResponse response = service.province(userid);
		return new ResponseEntity<ProvinceSearchResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/areas/city" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CitySearchResponse> city(@RequestBody CityRequest request,
												   @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CitySearchResponse response = service.city(request, userid);
		return new ResponseEntity<CitySearchResponse>(response, null, HttpStatus.OK);
	}

	@RequestMapping(value = { "/sysmanage/areas/county" }, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<CountySearchResponse> county(@RequestBody CountyRequest request,
													   @RequestHeader(value = EduHttpHeader.X_FORWARDED_USER, required = false) String userid) {

		CountySearchResponse response = service.county(request, userid);
		return new ResponseEntity<CountySearchResponse>(response, null, HttpStatus.OK);
	}
	
}
