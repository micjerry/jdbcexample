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

import com.zhihuixueai.sysmgr.api.semesters.SemesterRequest;
import com.zhihuixueai.sysmgr.api.semesters.SemesterRestBean;
import com.zhihuixueai.sysmgr.db.dao.api.SemesterDao;

@Component
public class SemesterDaoImpl implements SemesterDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public long add(SemesterRequest request, long schoolid, String operid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_semester").declareParameters(
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_schoolyearid", Types.BIGINT),
						new SqlParameter("in_createid", Types.VARCHAR),
						new SqlParameter("in_startime", Types.TIMESTAMP),
						new SqlParameter("in_endime", Types.TIMESTAMP)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_schoolid", schoolid);
		paras.addValue("in_name", request.getName());
		paras.addValue("in_schoolyearid", request.getSchoolyear_id());
		paras.addValue("in_createid", operid);
		paras.addValue("in_startime", request.getStart_time());
		paras.addValue("in_endime", request.getEnd_time());
		
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return 0;
		
		return (long)result.get("out_id");
	}

	@Override
	public boolean del(long id, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_del_semester").declareParameters(
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
	public boolean mod(SemesterRequest request, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_mod_semester").declareParameters(
						new SqlParameter("in_id", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_schoolyearid", Types.BIGINT),
						new SqlParameter("in_startime", Types.TIMESTAMP),
						new SqlParameter("in_endime", Types.TIMESTAMP),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_id", request.getId());
		paras.addValue("in_schoolid", schoolid);
		paras.addValue("in_name", request.getName());
		paras.addValue("in_schoolyearid", request.getSchoolyear_id());
		paras.addValue("in_startime", request.getStart_time());
		paras.addValue("in_endime", request.getEnd_time());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return true;
		
		return false;
	}

	@Override
	public List<SemesterRestBean> query(long schoolid) {
		String sql = "select id, name, schoolyear_id, schoolyear_name, start_time, end_time, create_time from v_terms where school_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{schoolid}, new BeanPropertyRowMapper<SemesterRestBean>(SemesterRestBean.class));
	}

}
