package com.jobportal.service;

import com.jobportal.model.User;
import com.jobportal.payload.request.UserRequest;

public interface IUserService {
	User signup(User request);

	User login(String email, String password);

}
