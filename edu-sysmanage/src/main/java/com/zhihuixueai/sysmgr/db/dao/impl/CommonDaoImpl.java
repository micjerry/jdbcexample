package com.zhihuixueai.sysmgr.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.db.dao.api.CommonDao;
import com.zhihuixueai.sysmgr.db.model.LongId;
import com.zhihuixueai.sysmgr.db.model.StringId;

@Component
public class CommonDaoImpl implements CommonDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int count(String sql) {
		try {
	        return jdbcTemplate.queryForObject(sql, Integer.class);
	    } catch (EmptyResultDataAccessException e) {
	        return 0;
	    }
	}
	
	@Override
	public long getSchoolByUser(String userid) {
		String sql = "select school_id from teacher where user_id = ? LIMIT 1;";
		
		try {
	        return jdbcTemplate.queryForObject(sql, new Object[]{userid}, Long.class);
	    } catch (EmptyResultDataAccessException e) {
	        return 0;
	    }
	}
	
	@Override
	public List<StringId> getRolesByUser(String userid) {
		String sql = "select role_id as id from sys_user_role where user_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{userid}, new BeanPropertyRowMapper<StringId>(StringId.class));
	}
	
	@Override
	public long getTeacherByUser(String userid) {
		String sql = "select id from teacher where user_id = ? limit 1;";
		try {
	        return jdbcTemplate.queryForObject(sql, new Object[]{userid}, Long.class);
	    } catch (EmptyResultDataAccessException e) {
	        return 0;
	    }
	}
	
	@Override
	public List<LongId> getMangeClassByTeacher(long schoolid, long teacherid) {
		String sql = "select a.id from class_info a left join period_grade b ON a.grade_id = b.grade_id WHERE b.school_id = ? AND a.main_teacher_id = ? or a.counselor_teacher_id = ? or b.grade_lead_id = ?;";
		
		return jdbcTemplate.query(sql, new Object[]{schoolid, teacherid, teacherid, teacherid}, new BeanPropertyRowMapper<LongId>(LongId.class));
	}
	
	@Override
	public List<LongId> getTeachingClassByTeacher(long teacherid) {
		String sql = "select class_id as id from teacher_class_subject where teacher_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{teacherid}, new BeanPropertyRowMapper<LongId>(LongId.class));
	}
	
	@Override
	public long getClassidByStudent(long studentid) {
		String sql = "select class_id from student where id = ? limit 1;";
		try {
	        return jdbcTemplate.queryForObject(sql, new Object[]{studentid}, Long.class);
	    } catch (EmptyResultDataAccessException e) {
	        return 0;
	    }
	}
}
