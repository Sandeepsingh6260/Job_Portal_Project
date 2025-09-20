package com.jobportal.model;

import com.jobportal.audit.Auditable;

public class Company extends Auditable {
	private String company_id;
	private String company_name;
	private String company_description;
	private String company_location;
	private String phoneNo;

	public Company() {
		super();
	}

	
	public Company(String company_id, String company_name, String company_description, String company_location,
			String phoneNo) {
		super();
		this.company_id = company_id;
		this.company_name = company_name;
		this.company_description = company_description;
		this.company_location = company_location;
		this.phoneNo = phoneNo;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public void setCompany_description(String company_description) {
		this.company_description = company_description;
	}

	public void setCompany_location(String company_location) {
		this.company_location = company_location;
	}

	public String getCompany_name() {
		return company_name;
	}

	public String getCompany_description() {
		return company_description;
	}

	public String getCompany_location() {
		return company_location;
	}

	@Override
	public String toString() {
		return "Company [company_id=" + company_id + ", company_name=" + company_name + ", company_description="
				+ company_description + ", company_location=" + company_location + "]";
	}



}
