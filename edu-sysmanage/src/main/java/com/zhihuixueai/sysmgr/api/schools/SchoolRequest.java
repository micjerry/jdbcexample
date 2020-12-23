package com.zhihuixueai.sysmgr.api.schools;

public class SchoolRequest {
	private Long id;
	
	private String admin_account;
	
	private String name;
	
	private String province_id;
	
	private String city_id;
	
	private String county_id;
	
	private String contact_person;
	
	private String contact_phone;
	
	private Integer max_account;
	
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdmin_account() {
		return admin_account;
	}

	public void setAdmin_account(String admin_account) {
		this.admin_account = admin_account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince_id() {
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public Integer getMax_account() {
		return max_account;
	}

	public void setMax_account(Integer max_account) {
		this.max_account = max_account;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
