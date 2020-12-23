package com.zhihuixueai.sysmgr.service.fuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParentFuzzy {
	private static Map<String, List<String>> fuzzies;
	
	static {
		fuzzies = new HashMap<String, List<String>>();
		ArrayList<String> students_fields = new ArrayList<String>();
		students_fields.add("student_number");
		students_fields.add("student_name");
		fuzzies.put("student_fuzzy", students_fields);
		
		ArrayList<String> parent_fields = new ArrayList<String>();
		parent_fields.add("name");
		parent_fields.add("phone");
		fuzzies.put("parent_fuzzy", parent_fields);
	}
	
	public static Map<String, List<String>> getFuzzies() {
		return fuzzies;
	}
}
