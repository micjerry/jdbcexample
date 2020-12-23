package com.zhihuixueai.sysmgr.api;

import java.util.List;

import com.zhihuixueai.sysmgr.api.commons.Condition;
import com.zhihuixueai.sysmgr.tools.ConditionFormat;

public class ConditionConst {

	public static Condition makeSchoolCondition(long school_id) {
		Condition school_codition = new Condition();
		
		school_codition.setKey("school_id");
		school_codition.setMatch(ConditionFormat.MATCH_EQ);
		school_codition.setValue(String.valueOf(school_id));
		return school_codition;
	}
	
	public static Condition makeClassContion(List<Long> classes) {
		Condition class_codition = new Condition();
		class_codition.setKey("class_id");
		class_codition.setMatch(ConditionFormat.MATCH_IN);
		
		if (classes == null || classes.isEmpty()) {
			class_codition.setValue("(0)");
		} else {
			StringBuilder cbs = new StringBuilder();
			cbs.append("(");
			for (int i = 0; i < classes.size(); i++) {
				long cid = classes.get(i);
				cbs.append(String.valueOf(cid));
				if (i != (classes.size() - 1)) {
					cbs.append(",");
				}
			}
			cbs.append(")");
			
			class_codition.setValue(cbs.toString());
		}
			
		return class_codition;
	}
}
