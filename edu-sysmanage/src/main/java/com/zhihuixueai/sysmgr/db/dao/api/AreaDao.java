package com.zhihuixueai.sysmgr.db.dao.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.area.AreaRestBean;

public interface AreaDao {
	public List<AreaRestBean> list_provinces();
	
	public List<AreaRestBean> list_cities(String provinceid);
	
	public List<AreaRestBean> list_counties(String cityid);
}
