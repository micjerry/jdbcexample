package com.zhihuixueai.sysmgr.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.grades.GradeRestBean;
import com.zhihuixueai.sysmgr.db.dao.api.GradeDao;

@Component
public class GradeDaoImpl implements GradeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean addSchoolGrade(long schoolid) {
		
		String sql = "INSERT INTO period_grade(grade_id, next_grade_id, period_id, school_id) SELECT grade_id, next_grade_id, period_id, ? FROM period_grade_default;";
		return jdbcTemplate.execute(sql, (PreparedStatementCallback<Boolean>) ps -> {
			ps.setLong(1, schoolid);
            return ps.execute();
        });
	}

	@Override
	public List<GradeRestBean> getGrades(long schoolid) {
		String sql = "select id, name, master_id, master_name, period_id, period_name from v_grades where school_id = ?;";
		return jdbcTemplate.query(sql, new Object[]{schoolid}, new BeanPropertyRowMapper<GradeRestBean>(GradeRestBean.class));
	}
	
	@Override
	public boolean update_master(long schoolid, long gradeid, long masterid) {
		String sql = "update period_grade set grade_lead_id = ? where grade_id = ? and school_id = ?;";
		jdbcTemplate.update(sql, masterid, gradeid, schoolid);
		return true;
	}
}
