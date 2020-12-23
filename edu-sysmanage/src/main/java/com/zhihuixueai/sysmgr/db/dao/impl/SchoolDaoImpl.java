package com.zhihuixueai.sysmgr.db.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.RoleConst;
import com.zhihuixueai.sysmgr.api.schools.SchoolDisplayBean;
import com.zhihuixueai.sysmgr.api.schools.SchoolRequest;
import com.zhihuixueai.sysmgr.api.schools.SchoolRestBean;
import com.zhihuixueai.sysmgr.db.dao.api.SchoolDao;
import com.zhihuixueai.sysmgr.db.model.schools.SchoolAdmin;

@Component
public class SchoolDaoImpl implements SchoolDao {
	
	private static final String VIEW_NAME = "v_school";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public long add(SchoolRequest request, String operid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_school").declareParameters(
						new SqlParameter("in_createdid", Types.VARCHAR),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_provinceid", Types.VARCHAR),
						new SqlParameter("in_cityid", Types.VARCHAR),
						new SqlParameter("in_countyid", Types.VARCHAR),
						new SqlParameter("in_contactperson", Types.VARCHAR),
						new SqlParameter("in_contactphone", Types.VARCHAR),
						new SqlParameter("in_maxaccount", Types.INTEGER),
						new SqlOutParameter("out_id", Types.BIGINT),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_createdid", operid);
		paras.addValue("in_name", request.getName());
		paras.addValue("in_provinceid", request.getProvince_id());
		paras.addValue("in_cityid", request.getCity_id());
		paras.addValue("in_countyid", StringUtils.defaultString(request.getCounty_id()));
		paras.addValue("in_contactperson", StringUtils.defaultString(request.getContact_person()));
		paras.addValue("in_contactphone", StringUtils.defaultString(request.getContact_phone()));
		paras.addValue("in_maxaccount", request.getMax_account());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return 0;
		
		return (long)result.get("out_id");
	}

	@Override
	public boolean disable(long id, int status) {	
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_disable_school").declareParameters(
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_status", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_schoolid", id);
		paras.addValue("in_status", status);
		
		call.execute(paras);
		
		return true;
	}

	@Override
	public boolean mod(SchoolRequest request) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_mod_school").declareParameters(
						new SqlParameter("in_id", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_provinceid", Types.VARCHAR),
						new SqlParameter("in_cityid", Types.VARCHAR),
						new SqlParameter("in_countyid", Types.VARCHAR),
						new SqlParameter("in_contactperson", Types.VARCHAR),
						new SqlParameter("in_contactphone", Types.VARCHAR),
						new SqlParameter("in_maxaccount", Types.INTEGER),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_id", request.getId());
		paras.addValue("in_name", request.getName());
		paras.addValue("in_provinceid", request.getProvince_id());
		paras.addValue("in_cityid", request.getCity_id());
		paras.addValue("in_countyid", StringUtils.defaultString(request.getCounty_id()));
		paras.addValue("in_contactperson", request.getContact_person());
		paras.addValue("in_contactphone", request.getContact_phone());
		paras.addValue("in_maxaccount", request.getMax_account());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 0)
			return false;
		
		return true;
	}

	@Override
	public List<SchoolRestBean> query(String sql) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<SchoolRestBean>(SchoolRestBean.class));
	}

	@Override
	public SchoolDisplayBean display(long id) {
		String sql = "select * from v_school where id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<SchoolDisplayBean>(SchoolDisplayBean.class));
	}
	
	@Override
	public SchoolAdmin getAdmin(long id) {
		String sql = "select a.user_id, c.username from sys_user_role a left join teacher b on a.user_id = b.user_id left join sys_user c on a.user_id = c.user_id where b.school_id = ? and a.role_id = ? limit 1;";
		
		return jdbcTemplate.queryForObject(sql, new Object[]{id, RoleConst.SCHOOL_ADMIN_ID}, new BeanPropertyRowMapper<SchoolAdmin>(SchoolAdmin.class));
	}
	
	@Override
	public String getView() {
		return VIEW_NAME;
	}

}
