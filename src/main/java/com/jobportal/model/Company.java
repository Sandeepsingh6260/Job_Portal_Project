package com.jobportal.model;

public class Company {
	private String company_id;
	private String company_name;
	private String company_description;

	public Company() {
		super();
	}

	public Company(String company_id, String company_name, String company_description) {
		super();
		this.company_id = company_id;
		this.company_name = company_name;
		this.company_description = company_description;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_description() {
		return company_description;
	}

	public void setCompany_description(String company_description) {
		this.company_description = company_description;
	}

	@Override
	public String toString() {
		return "Company [company_id=" + company_id + ", company_name=" + company_name + ", company_description="
				+ company_description + "]";
	}

}
