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

import com.zhihuixueai.sysmgr.api.classes.ClassRequest;
import com.zhihuixueai.sysmgr.api.classes.ClassRestBean;
import com.zhihuixueai.sysmgr.db.dao.api.ClassDao;

@Component
public class ClassDaoImpl implements ClassDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public long add(ClassRequest request, long schoolid, String operid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_class").declareParameters(
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_createid", Types.VARCHAR),
						new SqlParameter("in_gradeid", Types.BIGINT),
						new SqlParameter("in_masterid", Types.BIGINT),
						new SqlParameter("in_assid", Types.BIGINT),
						new SqlOutParameter("out_id", Types.BIGINT),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_schoolid", schoolid);
		paras.addValue("in_name", request.getName());
		paras.addValue("in_createid", operid);
		paras.addValue("in_gradeid", request.getGrade_id());
		paras.addValue("in_masterid", request.getHeadteacher_id());
		paras.addValue("in_assid", request.getAssteacher_id());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return 0;
		
		return (long)result.get("out_id");
	}

	@Override
	public boolean mod(ClassRequest request, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_mod_class").declareParameters(
						new SqlParameter("in_classid", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_gradeid", Types.BIGINT),
						new SqlParameter("in_masterid", Types.BIGINT),
						new SqlParameter("in_assid", Types.BIGINT),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_classid", request.getId());
		paras.addValue("in_schoolid", schoolid);
		paras.addValue("in_name", request.getName());
		paras.addValue("in_gradeid", request.getGrade_id());
		paras.addValue("in_masterid", request.getHeadteacher_id());
		paras.addValue("in_assid", request.getAssteacher_id());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return true;
		
		return false;
	}

	@Override
	public boolean del(long classid, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_del_class").declareParameters(
						new SqlParameter("in_classid", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_classid", classid);
		paras.addValue("in_schoolid", schoolid);
		
		call.execute(paras);
		
		return true;
	}

	@Override
	public List<ClassRestBean> query(String sql) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClassRestBean>(ClassRestBean.class));
	}

	@Override
	public ClassRestBean display(long classid) {
		String sql = "select * from v_classes where id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{classid}, new BeanPropertyRowMapper<ClassRestBean>(ClassRestBean.class));
	}

	@Override
	public String getView() {
		return "v_classes";
	}

	@Override
	public String getFileds() {
		return "select id, grade_id, name, headteacher_id, assteacher_id, create_time, grade_name, headteacher_name, assteacher_name from ";
	}

}
