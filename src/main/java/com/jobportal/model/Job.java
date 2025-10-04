package com.jobportal.model;

import com.jobportal.audit.Auditable;

public class Job extends Auditable {
private String id;
private String title;
private String description;
private String user_id;
private String location;
private Double salary;
private String job_type;
private String experience_required;
private String mobile_no;
private String company_name;


	public Job() {
		super();
	}


	public Job(String id, String title, String description, String user_id, String location, Double salary,
			String job_type, String experience_required,String mobile_no) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.user_id = user_id;
		this.location = location;
		this.salary = salary;
		this.job_type = job_type;
		this.experience_required = experience_required;
		this.mobile_no = mobile_no;

	}
	
	public Job(String id, String title, String description, String user_id, String location, Double salary, String job_type,
			String experience_required, String mobile_no, String company_name) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.user_id = user_id;
		this.location = location;
		this.salary = salary;
		this.job_type = job_type;
		this.experience_required = experience_required;
		this.mobile_no = mobile_no;
		this.company_name = company_name;
	}
	
	
	

	public String getCompany_name() {
		return company_name;
	}


	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}


	public String getMobile_no() {
		return mobile_no;
	}


	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Double getSalary() {
		return salary;
	}


	public void setSalary(Double salary) {
		this.salary = salary;
	}


	public String getJob_type() {
		return job_type;
	}


	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}


	public String getExperience_required() {
		return experience_required;
	}


	public void setExperience_required(String experience_required) {
		this.experience_required = experience_required;
	}


	@Override
	public String toString() {
		return "Job [id=" + id + ", title=" + title + ", description=" + description + ", user_id=" + user_id
				+ ", location=" + location + ", salary=" + salary + ", job_type=" + job_type + ", experience_required="
				+ experience_required + ", mobile_no=" + mobile_no + "]";
	}
}
