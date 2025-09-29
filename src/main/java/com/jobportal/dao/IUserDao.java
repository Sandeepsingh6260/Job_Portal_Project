package com.jobportal.dao;
import com.jobportal.model.Company;
import com.jobportal.model.User;

public interface IUserDao {
	public Boolean Register(User user);
	
	public Boolean isEmailExits(String user_email);
	
	public User login(String email);

	boolean UpdateUser(User user);

	boolean UpdateCompany(Company company);
}
