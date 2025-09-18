package com.jobportal.payload.request;

import com.jobportal.enums.RoleType;

public class UserRequest {

	private String user_name ;
	private String user_email ;
	private String user_password ;
	private  RoleType user_role;
    private String location;
    private String skills;
    private String experience;
    private String resumePath;   
    private String company_name;
	private String company_description;
	private String company_location;
	public UserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserRequest(String user_name, String user_email, String user_password, RoleType user_role, String location,
			String skills, String experience, String resumePath, String company_name, String company_description,
			String company_location) {
		super();
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_password = user_password;
		this.user_role = user_role;
		this.location = location;
		this.skills = skills;
		this.experience = experience;
		this.resumePath = resumePath;
		this.company_name = company_name;
		this.company_description = company_description;
		this.company_location = company_location;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public RoleType getUser_role() {
		return user_role;
	}
	public void setUser_role(RoleType user_role) {
		this.user_role = user_role;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getResumePath() {
		return resumePath;
	}
	public void setResumePath(String resumePath) {
		this.resumePath = resumePath;
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
	public String getCompany_location() {
		return company_location;
	}
	public void setCompany_location(String company_location) {
		this.company_location = company_location;
	}
	@Override
	public String toString() {
		return "UserRequest [user_name=" + user_name + ", user_email=" + user_email + ", user_password=" + user_password
				+ ", user_role=" + user_role + ", location=" + location + ", skills=" + skills + ", experience="
				+ experience + ", resumePath=" + resumePath + ", company_name=" + company_name
				+ ", company_description=" + company_description + ", company_location=" + company_location + "]";
	}
	
}
