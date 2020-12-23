package com.zhihuixueai.sysmgr.db.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.api.commons.CommonSubjectBean;
import com.zhihuixueai.sysmgr.db.dao.api.SubjectDao;

@Component
public class SubjectDaoImpl implements SubjectDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<CommonSubjectBean> list() {
		String sql = "select id, subject as name from subject;";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<CommonSubjectBean>(CommonSubjectBean.class));
	}

}
