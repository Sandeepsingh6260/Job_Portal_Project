package com.jobportal.service.impl;



import com.jobportal.dao.IUserDao;
import com.jobportal.daoimpl.CompnayDaoImpl;
import com.jobportal.daoimpl.UserDaoImpl;

import com.jobportal.model.User;

import com.jobportal.service.IUserService;

public class UserServiceImpl implements IUserService {
    CompnayDaoImpl companyDao ;
    IUserDao userService ;
    

    public UserServiceImpl() {
		super();
		userService=new UserDaoImpl();
		companyDao= new CompnayDaoImpl();
	}


	@Override
    public User signup(User request) {
    	System.out.println("Signup Service    "+request);
        try {
   
            boolean userRegistered = userService.Register(request);
            System.out.println("User registered? " + userRegistered);  

            if(userRegistered){ 
                return request; 
            } else {
                System.out.println("User insert failed!");
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
	
	
	@Override
	public User login(String email, String password) {
	    return userService.login(email, password);  
	}


}
