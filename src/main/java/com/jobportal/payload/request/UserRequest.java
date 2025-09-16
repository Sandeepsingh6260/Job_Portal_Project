package com.jobportal.payload.request;

import com.jobportal.enums.RoleType;

public class UserRequest {

	private String user_name ;
	private String user_email ;
	private String user_password ;
	private  RoleType role;
	public UserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserRequest( String user_name, String user_email, String user_password, RoleType role) {
		super();
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_password = user_password;
		this.role = role;
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
	public RoleType getRole() {
		return role;
	}
	public void setRole(RoleType role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [user_name=" + user_name + ", user_email=" + user_email
				+ ", user_password=" + user_password + ", role=" + role + "]";
	}
	
}
