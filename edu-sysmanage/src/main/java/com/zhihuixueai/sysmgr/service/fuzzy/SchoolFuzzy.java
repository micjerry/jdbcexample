package com.zhihuixueai.sysmgr.service.fuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolFuzzy {
	private static Map<String, List<String>> fuzzies;
	
	static {
		fuzzies = new HashMap<String, List<String>>();
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("name");
		fuzzies.put("fuzzy", fields);
	}
	
	public static Map<String, List<String>> getFuzzies() {
		return fuzzies;
	}
}
