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

import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;
import com.zhihuixueai.sysmgr.api.teachers.TeacherDisplayBean;
import com.zhihuixueai.sysmgr.api.teachers.TeacherRestBean;
import com.zhihuixueai.sysmgr.db.dao.api.TeacherDao;
import com.zhihuixueai.sysmgr.db.model.teachers.TeacherModel;

@Component
public class TeacherDaoImpl implements TeacherDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public long add(TeacherModel teacher) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_teacher").declareParameters(
						new SqlParameter("in_createdid", Types.VARCHAR),
						new SqlParameter("in_email", Types.VARCHAR),
						new SqlParameter("in_mobilephone", Types.VARCHAR),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_userid", Types.VARCHAR),
						new SqlOutParameter("out_id", Types.BIGINT),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_createdid", teacher.getCreated_id());
		paras.addValue("in_email", StringUtils.defaultString(teacher.getEmail()));
		paras.addValue("in_mobilephone", teacher.getMobile());
		paras.addValue("in_schoolid", teacher.getSchool_id());
		paras.addValue("in_name", teacher.getTeacher_name());
		paras.addValue("in_userid", teacher.getUser_id());

		Map<String, Object> result = call.execute(paras);
		
		return (long)result.get("out_id");
	}

	@Override
	public boolean mod(TeacherModel teacher) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_mod_teacher").declareParameters(
						new SqlParameter("in_id", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_email", Types.VARCHAR),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_phone", Types.VARCHAR),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_id", teacher.getId());
		paras.addValue("in_schoolid", teacher.getSchool_id());
		paras.addValue("in_email", teacher.getEmail());
		paras.addValue("in_name", teacher.getTeacher_name());
		paras.addValue("in_phone", teacher.getMobile());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return true;
		
		return false;
	}

	@Override
	public boolean del(long teacherid, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_del_teacher").declareParameters(
						new SqlParameter("in_id", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_id", teacherid);
		paras.addValue("in_schoolid", schoolid);
		
		call.execute(paras);
		
		return true;
	}

	@Override
	public List<TeacherRestBean> query(String sql) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<TeacherRestBean>(TeacherRestBean.class));
	}

	@Override
	public TeacherDisplayBean display(long teacherid, long schoolid) {
		String sql = "select id, name, phone, email, status from v_teachers where id = ? and school_id = ?;";
		return jdbcTemplate.queryForObject(sql, new Object[]{teacherid, schoolid}, new BeanPropertyRowMapper<TeacherDisplayBean>(TeacherDisplayBean.class));
	}

	@Override
	public boolean clearSubjects(long teacherid, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_clear_subject").declareParameters(
						new SqlParameter("in_teacherid", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_teacherid", teacherid);
		paras.addValue("in_schoolid", schoolid);
		
		call.execute(paras);
		
		return true;
	}

	@Override
	public boolean addSubject(CommonSubjectBean subject, long teacherid, long schoolid) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_subject").declareParameters(
						new SqlParameter("in_teacherid", Types.VARCHAR),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_classid", Types.BIGINT),
						new SqlParameter("in_subjectid", Types.BIGINT),
						new SqlParameter("in_default", Types.INTEGER),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_teacherid", teacherid);
		paras.addValue("in_schoolid", schoolid);
		paras.addValue("in_classid", subject.getClass_id());
		paras.addValue("in_subjectid", subject.getId());
		paras.addValue("in_default", subject.getIs_default());

		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return true;
		
		return false;
	}

	@Override
	public String getView() {
		return "v_teachers";
	}

	@Override
	public String getFileds() {
		return "select id, name, phone, email, subject_name, grade_name, class_name, class_id, status from ";
	}

	@Override
	public List<CommonSubjectBean> getSubjects(long teacherid, long schoolid) {
		String sql = "select id, name, class_id, grade_name, class_name, is_default from v_teachersubjects where teacher_id = ? and school_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{teacherid, schoolid}, new BeanPropertyRowMapper<CommonSubjectBean>(CommonSubjectBean.class));
	}
}
