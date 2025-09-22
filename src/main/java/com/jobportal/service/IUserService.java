package com.jobportal.service;

import com.jobportal.model.User;


public interface IUserService {
	User signup(User request);

	User login(String email, String password);
	

}
