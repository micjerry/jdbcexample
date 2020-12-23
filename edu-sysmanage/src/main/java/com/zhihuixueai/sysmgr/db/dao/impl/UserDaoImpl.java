package com.zhihuixueai.sysmgr.db.dao.impl;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.db.dao.api.UserDao;
import com.zhihuixueai.sysmgr.db.model.users.SysUser;

@Component
public class UserDaoImpl implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String add(SysUser user) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_add_account").declareParameters(
						new SqlParameter("in_username", Types.VARCHAR),
						new SqlParameter("in_password", Types.VARCHAR),
						new SqlParameter("in_userid", Types.VARCHAR),
						new SqlParameter("in_nickname", Types.VARCHAR),
						new SqlParameter("in_gender", Types.INTEGER),
						new SqlParameter("in_email", Types.VARCHAR),
						new SqlParameter("in_mobilephone", Types.VARCHAR),
						new SqlParameter("in_salt", Types.VARCHAR),
						new SqlParameter("in_roleid", Types.VARCHAR),
						new SqlOutParameter("out_id", Types.VARCHAR),
						new SqlOutParameter("out_exist", Types.INTEGER)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_username", user.getUsername());
		paras.addValue("in_password", user.getPassword());
		paras.addValue("in_userid", user.getUser_id());
		paras.addValue("in_nickname", StringUtils.defaultString(user.getNick_name()));
		paras.addValue("in_gender", user.getGender());
		paras.addValue("in_email",  StringUtils.defaultString(user.getEmail()));
		paras.addValue("in_mobilephone", StringUtils.defaultString(user.getMobilephone()));
		paras.addValue("in_salt", user.getSalt());
		paras.addValue("in_roleid", user.getRole_id());
		
		Map<String, Object> result = call.execute(paras);
		
		return (String)result.get("out_id");
	}
	
	@Override
	public boolean reset(SysUser user) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_reset_account").declareParameters(
						new SqlParameter("in_userid", Types.VARCHAR),
						new SqlParameter("in_password", Types.VARCHAR),
						new SqlParameter("in_salt", Types.VARCHAR)
				);
		
		MapSqlParameterSource paras = new MapSqlParameterSource();
		paras.addValue("in_userid", user.getUser_id());
		paras.addValue("in_password", user.getPassword());
		paras.addValue("in_salt", user.getSalt());
		
		call.execute(paras);
		
		return true;	
	}
}
