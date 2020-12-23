package com.zhihuixueai.sysmgr.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.area.AreaRestBean;
import com.zhihuixueai.sysmgr.db.dao.api.AreaDao;

@Component
public class AreaDaoImpl implements AreaDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<AreaRestBean> list_provinces() {
		String sql = "select province_id as id, name from  province;";
		
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AreaRestBean>(AreaRestBean.class));

	}

	@Override
	public List<AreaRestBean> list_cities(String provinceid) {
		String sql = "select city_id as id, name from city where province_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{provinceid}, new BeanPropertyRowMapper<AreaRestBean>(AreaRestBean.class));
	}

	@Override
	public List<AreaRestBean> list_counties(String cityid) {
		String sql = "select county_id as id, name from county where city_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{cityid}, new BeanPropertyRowMapper<AreaRestBean>(AreaRestBean.class));
	}

}
