package com.jobportal.model;

import com.jobportal.audit.Auditable;
import com.jobportal.enums.RoleType;

public class User extends Auditable {

    private String user_id;
	private String user_name;
    private String user_email;
    private String user_password;
    private String location;  
    private RoleType user_role;
    private String company_id;
    
    public User() {
		super();
	}

	// Full constructor
	public User(String user_id, String user_name, String user_email, String user_password, String location,
			 RoleType user_role, String company_id) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_password = user_password;
		this.location = location;
		this.user_role = user_role;
		this.company_id = company_id;
	}

	

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	

	public RoleType getUser_role() {
		return user_role;
	}

	public void setUser_role(RoleType user_role) {
		this.user_role = user_role;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", user_email=" + user_email
				+ ", user_password=" + user_password + ", location=" + location + ", user_role=" + user_role
				+ ", company_id=" + company_id + "]";
	}

	
	
}
