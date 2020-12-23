package com.zhihuixueai.sysmgr.db.dao.impl;

import java.sql.Types;
import java.util.Base64;
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

import com.zhihuixueai.common.util.aes.AESManager;
import com.zhihuixueai.sysmgr.api.students.StudentParentBean;
import com.zhihuixueai.sysmgr.api.students.StudentRequest;
import com.zhihuixueai.sysmgr.api.students.StudentRestBean;
import com.zhihuixueai.sysmgr.api.students.UpdatePassBean;
import com.zhihuixueai.sysmgr.db.dao.api.StudentsDao;

@Component
public class StudentsDaoImpl implements StudentsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public long add(StudentRequest request, String operid, String userid, long school) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_student").declareParameters(
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_studentnumber", Types.VARCHAR),
						new SqlParameter("in_recordnumber", Types.VARCHAR),
						new SqlParameter("in_examnumber", Types.VARCHAR),
						new SqlParameter("in_schoolyear", Types.BIGINT),
						new SqlParameter("in_classid", Types.BIGINT),
						new SqlParameter("in_schoolid", Types.BIGINT),
						new SqlParameter("in_userid", Types.VARCHAR),
						new SqlParameter("in_createdid", Types.VARCHAR),
						new SqlParameter("in_labels", Types.VARCHAR),
						new SqlOutParameter("out_id", Types.BIGINT),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_name", request.getName());
		paras.addValue("in_studentnumber", request.getStudent_number());
		paras.addValue("in_recordnumber", StringUtils.defaultString(request.getRecord_number()));
		paras.addValue("in_examnumber", StringUtils.defaultString(request.getExam_number()));
		paras.addValue("in_schoolyear", request.getSchoolyear_id());
		paras.addValue("in_classid", request.getClass_id());
		paras.addValue("in_schoolid", school);
		paras.addValue("in_userid", userid);
		paras.addValue("in_createdid", operid);
		paras.addValue("in_labels", StringUtils.defaultString(request.getLables()));
		
		Map<String, Object> result = call.execute(paras);
		
		if (result == null) {
			return 0;
		}
		
		int exist = (int)result.get("out_exist");
		if (exist == 1)
			return 0;
		
		return (long)result.get("out_id");
	}
	
	@Override
	public boolean del(long student_id) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_del_student").declareParameters(
						new SqlParameter("in_studentid", Types.BIGINT)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_studentid", student_id);
		
		call.execute(paras);
		
		return true;
	}
	
	@Override
	public boolean mod(StudentRequest request) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_mod_student").declareParameters(
						new SqlParameter("in_id", Types.BIGINT),
						new SqlParameter("in_name", Types.VARCHAR),
						new SqlParameter("in_stdnumber", Types.VARCHAR),
						new SqlParameter("in_recnumber", Types.VARCHAR),
						new SqlParameter("in_exanumber", Types.VARCHAR),
						new SqlParameter("in_schoolyear", Types.BIGINT),
						new SqlParameter("in_classid", Types.BIGINT),
						new SqlParameter("in_labels", Types.VARCHAR),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_id", request.getId());
		paras.addValue("in_name", request.getName());
		paras.addValue("in_stdnumber", request.getStudent_number());
		paras.addValue("in_recnumber", StringUtils.defaultString(request.getRecord_number()));
		paras.addValue("in_exanumber", StringUtils.defaultString(request.getExam_number()));
		paras.addValue("in_schoolyear", request.getSchoolyear_id());
		paras.addValue("in_classid", request.getClass_id());
		paras.addValue("in_labels", request.getLables());
		
		Map<String, Object> result = call.execute(paras);
		
		int exist = (int)result.get("out_exist");
		if (exist == 0)
			return false;
		
		return true;
	}
	
	@Override
	public List<StudentRestBean> query(String sql) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<StudentRestBean>(StudentRestBean.class));
	}
	
	@Override
	public StudentRestBean display(long student_id) {
		String sql = "select * from v_students where id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{student_id}, new BeanPropertyRowMapper<StudentRestBean>(StudentRestBean.class));
	}
	
	@Override
	public List<StudentParentBean> list_parents(long student_id) {
		String sql = "select b.id, b.parent_name as name, b.mobile as phone from parent_student a left join parent b on a.parent_id = b.id where a.student_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{student_id}, new BeanPropertyRowMapper<StudentParentBean>(StudentParentBean.class));
	}

	@Override
	public String getView() {
		return "v_students";
	}

	@Override
	public void updatePass() {
		String sql = "select user_id, username, salt from sys_user;";
		List<UpdatePassBean> passes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<UpdatePassBean>(UpdatePassBean.class));
		
		if (passes == null)
			return;
		
		for (UpdatePassBean pass : passes) {
			if (pass.getUsername() == null)
				continue;
			
			if (pass.getUsername().equalsIgnoreCase("admin"))
				continue;
			
			if (StringUtils.isEmpty(pass.getSalt()) || StringUtils.isEmpty(pass.getUser_id()))
				continue;
			
			String key = pass.getUser_id().substring(0, 16);
			String iv = pass.getSalt();
			
			String username = pass.getUsername();
			while (username.length() < 6) {
				username = username + username;
			}
			int beginIndex = username.length() - 6;
			String rawpass = username.substring(beginIndex, username.length());
			
			try {
				byte[] cipherText = AESManager.encrypt(key.getBytes(), iv.getBytes(), rawpass.getBytes());
				String cipherStr = new String(cipherText, "ISO-8859-1");
				
				String dbpass = Base64.getEncoder().encodeToString(cipherStr.getBytes("ISO-8859-1"));
				
				String updatesql = "UPDATE sys_user SET password = ? WHERE user_id = ?;";
				jdbcTemplate.update(updatesql, dbpass, pass.getUser_id());
				
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
	}
}
