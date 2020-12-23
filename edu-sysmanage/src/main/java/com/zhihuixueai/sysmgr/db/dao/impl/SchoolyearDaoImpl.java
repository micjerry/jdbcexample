package com.zhihuixueai.sysmgr.db.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.StatusCode;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolYearRequest;
import com.zhihuixueai.sysmgr.api.schoolyears.SchoolyearRestBean;
import com.zhihuixueai.sysmgr.db.dao.api.SchoolyearDao;

@Component
public class SchoolyearDaoImpl implements SchoolyearDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public long add(SchoolYearRequest request, long schoolid, String operid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_schoolyear").declareParameters(
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_createid", Types.VARCHAR)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_schoolid", schoolid);
		paras.addValue("in_name", request.getSchoolyear());
		paras.addValue("in_createid", operid);
		
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return 0;
		
		return (long)result.get("out_id");
	}

	@Override
	public boolean del(long id, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_del_schoolyear").declareParameters(
						new SqlParameter("in_id", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_id", id);
		paras.addValue("in_schoolid", schoolid);
		
		call.execute(paras);
		
		return true;
	}

	@Override
	public int mod(SchoolYearRequest request, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_mod_schoolyear").declareParameters(
						new SqlParameter("in_id", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_id", request.getId());
		paras.addValue("in_schoolid", schoolid);
		paras.addValue("in_name", request.getSchoolyear());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return StatusCode.SUCCESS;
		
		if (exist == 0) {
			return StatusCode.NOTEXIST;
		}
		
		if (exist == 2) {
			return StatusCode.REPEAT;
		}
		
		return StatusCode.OTHER;
	}

	@Override
	public List<SchoolyearRestBean> query(long schoolid) {
		String sql = "select id, year_name as schoolyear, created as create_time from school_year where school_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{schoolid}, new BeanPropertyRowMapper<SchoolyearRestBean>(SchoolyearRestBean.class));
	}

}
