package com.zhihuixueai.sysmgr.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zhihuixueai.sysmgr.api.QuerySqler;
import com.zhihuixueai.sysmgr.api.commons.Condition;

public class ConditionFormat {
	private static final String SPACE = " ";
	private static final String S_Q = "'";
	private static final String SQL_FUZ = "%";
	private static final String SQL_END= ";";
	private static final String SQL_AND = " and ";
	private static final String SQL_OR = " or ";
	
	public static final String MATCH_EQ = "eq";
	public static final String MATCH_NE = "ne";
	public static final String MATCH_LT = "lt";
	public static final String MATCH_LE = "le";
	public static final String MATCH_GT = "gt";
	public static final String MATCH_GE = "ge";
	public static final String MATCH_LIKE = "like";
	public static final String MATCH_CT = "ct";
	public static final String MATCH_IN = "in";
	
	private static Map<String, String> sqlmatch;
	static {
		sqlmatch = new HashMap<String, String>();
		sqlmatch.put(MATCH_EQ, "=");
		sqlmatch.put(MATCH_NE, "<>");
		sqlmatch.put(MATCH_LT, "<");
		sqlmatch.put(MATCH_LE, "<=");
		sqlmatch.put(MATCH_GT, ">");
		sqlmatch.put(MATCH_GE, ">=");
		sqlmatch.put(MATCH_LIKE, "like");
		sqlmatch.put(MATCH_CT, "REGEXP");
		sqlmatch.put(MATCH_IN, "in");
	}
	
	private static String formatConditions(List<Condition> conditions, Map<String, List<String>> fuzzies) {	
		if (conditions == null || conditions.isEmpty()) {
			return null;
		}
		
		ArrayList<Condition> normal_conditions = new ArrayList<Condition>();
		ArrayList<Condition> fuzzy_conditions = null;
		
		for (Condition cond : conditions) {
			String match = sqlmatch.get(cond.getMatch().toLowerCase());
			
			if (match.equals(MATCH_LIKE)) {
				if (fuzzy_conditions == null) {
					fuzzy_conditions = new ArrayList<Condition>();
				}
				
				fuzzy_conditions.add(cond);
				continue;
			} else {
				if ((cond.getKey() == null) || (cond.getValue() == null) || (cond.getMatch() == null)) {
					continue;
				}
				normal_conditions.add(cond);
			}
		}
		
		// format nomal conditions
		int add_conds = 0;
		StringBuilder sqlb = new StringBuilder();
		for (int i = 0; i < normal_conditions.size(); i++) {
			Condition cond = normal_conditions.get(i);
			String key = cond.getKey();
			String match = sqlmatch.get(cond.getMatch().toLowerCase());
			String value = cond.getValue();
			
			if (null == match || match.isEmpty())
				continue;
			
			if (null == key || key.isEmpty())
				continue;
			
			if (null == value || value.isEmpty())
				continue;
			
			sqlb.append(key.toLowerCase());
			sqlb.append(SPACE);
			sqlb.append(match);
			sqlb.append(SPACE);
			
			if (match.equals(MATCH_IN)) {
				sqlb.append(value);
			} else {
				sqlb.append(S_Q);
				sqlb.append(value);
				sqlb.append(S_Q);
			}
			
			add_conds++;
			if (i != (normal_conditions.size() -1)) {
			    sqlb.append(SQL_AND);
			}
		}
		
		//format fuzzies
		
		if (fuzzy_conditions == null || fuzzy_conditions.isEmpty()) {
			return sqlb.toString();
		}
		
		ArrayList<Condition> formated_conditions = new ArrayList<Condition>();
		
		for (int i = 0; i < fuzzy_conditions.size(); i++) {
			Condition cond = fuzzy_conditions.get(i);
			List<String> table_keys = (fuzzies == null) ? null : fuzzies.get(cond.getKey().toLowerCase());
			
			if (table_keys == null) {
				formated_conditions.add(cond);
			} else {
				for (String table_key : table_keys) {
					Condition new_cond = new Condition();
					new_cond.setKey(table_key);
					new_cond.setValue(cond.getValue());
					formated_conditions.add(new_cond);
				}
			}
		}
		
		if (formated_conditions.isEmpty()) {
			return sqlb.toString();
		}
		
		if (add_conds > 0) {
			sqlb.append(" and (");
		}
		
		for (int i = 0; i < formated_conditions.size(); i++) {
			Condition cond = formated_conditions.get(i);
			
			sqlb.append(cond.getKey());
			sqlb.append(SPACE);
			sqlb.append(MATCH_LIKE);
			sqlb.append(SPACE);
			sqlb.append(S_Q);
			sqlb.append(SQL_FUZ);
			sqlb.append(cond.getValue());
			sqlb.append(SQL_FUZ);
			sqlb.append(S_Q);
			
			if (i != (formated_conditions.size() -1)) {
			    sqlb.append(SQL_OR);
			}
		}
		
		if (add_conds > 0) {
			sqlb.append(")");
		}
		
		
		return sqlb.toString();
	}
	
	public static QuerySqler formatSearchSql(List<Condition> conditions, int index, int pagenumber, String table_name, Map<String, List<String>> fuzzies, String fields) {
		
		QuerySqler sqler = new QuerySqler();
		
		if (index <= 0 ) {
			index = 1;
		}
		
		if (pagenumber <= 0) {
			pagenumber = 20;
		}
		
		int offset = (index- 1) * pagenumber;
		
		StringBuilder sqlquery = new StringBuilder();
		
		if (StringUtils.isEmpty(fields)) {
			sqlquery.append("select * from ");
		} else {
			sqlquery.append(fields);
		}
		
		sqlquery.append(table_name);
		
		StringBuilder sqlcount = new StringBuilder();
		sqlcount.append("select count(*) AS total from ");
		sqlcount.append(table_name);
		
		if (conditions == null || conditions.isEmpty()) {
			sqler.setQuery(sqlquery.toString());
			sqler.setCount(sqlcount.toString());
			return sqler;
		} 
		
		sqlquery.append(" where ");
		sqlcount.append(" where ");
		
		String coditions = formatConditions(conditions, fuzzies);
		
		sqlquery.append(coditions);
		sqlquery.append(String.format(" limit %d offset %d", pagenumber, offset));
		sqlquery.append(SQL_END);
		
		sqler.setQuery(sqlquery.toString());
		
		sqlcount.append(coditions);
		sqlcount.append(SQL_END);
		sqler.setCount(sqlcount.toString());
		return sqler;
	}
}
