package com.zhihuixueai.sysmgr.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.area.AreaRestBean;
import com.zhihuixueai.sysmgr.api.area.CityRequest;
import com.zhihuixueai.sysmgr.api.area.CitySearchResponse;
import com.zhihuixueai.sysmgr.api.area.CountyRequest;
import com.zhihuixueai.sysmgr.api.area.CountySearchResponse;
import com.zhihuixueai.sysmgr.api.area.ProvinceSearchResponse;
import com.zhihuixueai.sysmgr.db.dao.api.AreaDao;
import com.zhihuixueai.sysmgr.service.api.AreaService;

@Component
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaDao areaDao;
	
    @Override
    public ProvinceSearchResponse province(String operid) {
    	ProvinceSearchResponse response = new ProvinceSearchResponse();
    	
    	List<AreaRestBean> provinces = areaDao.list_provinces();
    	if (null == provinces || provinces.isEmpty()) {
    		response.setCode(StatusCode.NOTEXIST);
    		response.setMsg(StatusCode.NOTEXIST_MSG);
    		return response;
    	}
    	
    	response.setProvinces(provinces);
        return response;
    }

    @Override
    public CitySearchResponse city(CityRequest request, String operid) {
    	CitySearchResponse response = new CitySearchResponse();
    	if (StringUtils.isEmpty(request.getProvince_id())) {
    		response.setCode(StatusCode.BAD_REQUEST);
    		response.setMsg(StatusCode.BAD_REQUEST_MSG + " province_id is empty");
    		return response;
    	}
    	
    	List<AreaRestBean> cities = areaDao.list_cities(request.getProvince_id());
    	response.setCities(cities);
        return response;
    }

    @Override
    public CountySearchResponse county(CountyRequest request, String operid) {
    	CountySearchResponse response = new CountySearchResponse();
    	if (StringUtils.isEmpty(request.getCity_id())) {
    		response.setCode(StatusCode.BAD_REQUEST);
    		response.setMsg(StatusCode.BAD_REQUEST_MSG + " city_id is empty");
    		return response;
    	}
    	
    	List<AreaRestBean> counties = areaDao.list_counties(request.getCity_id());
    	response.setCounties(counties);
        return response;
    }
}
