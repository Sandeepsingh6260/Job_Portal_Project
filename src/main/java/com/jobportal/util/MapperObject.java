package com.jobportal.util;

import com.jobportal.model.User;
import com.jobportal.payload.response.UserResponse;

public class MapperObject {
	
	public UserResponse userToUserResponse(User user)
	{
		UserResponse response = new UserResponse();
		response.setUser_email(user.getUser_email());
		response.setUser_id(user.getUser_id());
		response.setUser_name(user.getUser_name());
		response.setUser_role(user.getUser_role());
		response.setIsDeleted(user.getIsDeleted());
		response.setCompany_id(user.getCompany_id());	
		return null;		
	}

}
