package com.zhihuixueai.sysmgr.service.api;

import com.zhihuixueai.sysmgr.api.area.*;

public interface AreaService {
    public ProvinceSearchResponse province(String operid);

    public CitySearchResponse city(CityRequest request, String operid);

    public CountySearchResponse county(CountyRequest request, String operid);

}
