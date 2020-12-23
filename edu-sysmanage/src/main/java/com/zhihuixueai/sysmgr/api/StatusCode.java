package com.zhihuixueai.sysmgr.api;

public class StatusCode {
	public static final int SUCCESS = 0;
	
	public static final int NOTEXIST = 1;
	
	public static final int FORBIDDEN = 2;
	
	public static final int BAD_REQUEST = 3;
	
	public static final int REPEAT = 4;
	
	public static final int OTHER = 99;
	
	public static String SUCCESS_MSG = "ok";
	
	public static String NOTEXIST_MSG = "not exist";
	
	public static String FORBIDDEN_MSG = "forbidden";
	
	public static String BAD_REQUEST_MSG = "bad request";
	
	public static String REPEAT_MSG = "already exist";
	
	public static String OTHER_MSG = "unkown";
	
	
	public static String getMsg(int status) {
		switch (status) {
			case SUCCESS:
				return SUCCESS_MSG;
			case NOTEXIST:
				return NOTEXIST_MSG;
			case FORBIDDEN:
				return FORBIDDEN_MSG;
			case BAD_REQUEST:
				return BAD_REQUEST_MSG;
			case REPEAT:
				return REPEAT_MSG;
			default:
				return OTHER_MSG;
			
		}
	}
}
