package com.jobportal.service;

import com.jobportal.model.Company;
import com.jobportal.model.User;


public interface IUserService {
	
	User signup(User request);

	User login(String email);

	boolean UpdateUserAndCompany(User user, Company company);

	boolean isUserBlocked(String userId);
}
