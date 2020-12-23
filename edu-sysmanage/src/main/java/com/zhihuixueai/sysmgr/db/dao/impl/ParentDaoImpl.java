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

import com.zhihuixueai.sysmgr.api.parents.ParentAddRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentDelRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentModRequest;
import com.zhihuixueai.sysmgr.api.parents.ParentRestBean;
import com.zhihuixueai.sysmgr.api.parents.ParentStudentRelation;
import com.zhihuixueai.sysmgr.db.dao.api.ParentDao;

@Component
public class ParentDaoImpl implements ParentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public long add(ParentAddRequest request, String operid, long school_id, String userid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_parent").declareParameters(
						new SqlParameter("in_createdid", Types.VARCHAR),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_email", Types.VARCHAR),
						new SqlParameter("in_phone", Types.VARCHAR),
						new SqlParameter("in_userid", Types.VARCHAR),
						new SqlParameter("in_education", Types.INTEGER),
						new SqlOutParameter("out_id", Types.BIGINT),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_createdid", operid);
		paras.addValue("in_name", request.getName());
		paras.addValue("in_email", request.getEmail());
		paras.addValue("in_phone", request.getPhone());
		paras.addValue("in_userid", userid);
		paras.addValue("in_education", request.getEducation());

		Map<String, Object> result = call.execute(paras);
		
		return (long)result.get("out_id");
	}

	@Override
	public long add_relation(ParentStudentRelation relation, long school_id, long parent_id) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_studentparent").declareParameters(
						new SqlParameter("in_parentid", Types.BIGINT),
						new SqlParameter("in_relation", Types.INTEGER),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_studentid", Types.BIGINT),
						new SqlOutParameter("out_id", Types.BIGINT),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_parentid", parent_id);
		paras.addValue("in_relation", relation.getRelation());
		paras.addValue("in_schoolid", school_id);
		paras.addValue("in_studentid", relation.getId());

		Map<String, Object> result = call.execute(paras);
		
		return (long)result.get("out_id");
	}

	@Override
	public boolean del_relation(ParentDelRequest request, long school_id) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_del_studentparent").declareParameters(
						new SqlParameter("in_studentid", Types.BIGINT),
						new SqlParameter("in_parentid", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_studentid", request.getStudent_id());
		paras.addValue("in_parentid", request.getId());
		paras.addValue("in_schoolid", school_id);
		
		call.execute(paras);
		
		return true;
	}

	@Override
	public boolean mod(ParentModRequest request, long school_id) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_mod_parent").declareParameters(
						new SqlParameter("in_id", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_email", Types.VARCHAR),
						new SqlParameter("in_phone", Types.VARCHAR),
						new SqlParameter("in_education", Types.INTEGER),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_id", request.getId());
		paras.addValue("in_name", request.getName());
		paras.addValue("in_email", request.getEmail());
		paras.addValue("in_phone", request.getPhone());
		paras.addValue("in_education", request.getEducation());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return true;
		
		return false;
	}

	@Override
	public boolean mod_relation(ParentModRequest reqeust, long school_id) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_mod_studentparent").declareParameters(
						new SqlParameter("in_parentid", Types.BIGINT),
						new SqlParameter("in_relation", Types.INTEGER),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_studentid", Types.BIGINT),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_parentid", reqeust.getId());
		paras.addValue("in_relation", reqeust.getRelation());
		paras.addValue("in_schoolid", school_id);
		paras.addValue("in_studentid", reqeust.getStudent_id());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return true;
		
		return false;
	}

	@Override
	public List<ParentRestBean> query(String sql) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<ParentRestBean>(ParentRestBean.class));
	}

	@Override
	public ParentRestBean display(long parent_id, long student_id, long school_id) {
		String sql = "select id, name, student_id, student_name, student_number, grade_name, class_name, phone, email, relation, education, class_id from v_studentparents where id = ? and student_id = ? and school_id = ?;";
		return jdbcTemplate.queryForObject(sql, new Object[]{parent_id, student_id, school_id}, new BeanPropertyRowMapper<ParentRestBean>(ParentRestBean.class));
	}

	@Override
	public String getView() {
		return "v_studentparents";
	}

	@Override
	public String getFields() {
		return "select id, name, student_id, student_name, student_number, grade_name, class_name, phone, email, relation, education, class_id from ";
	}

}
