package com.jobportal.serviceimpl;

import java.util.UUID;
import com.jobportal.daoimpl.SeekerDaoImpl;
import com.jobportal.enums.RoleType;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.payload.request.UserRequest;
import com.jobportal.service.IUserService;

public class UserServiceImpl implements IUserService {
    CompnayDaoImpl companyDao = new CompnayDaoImpl();

    @Override
    public User signup(UserRequest request) {
    	System.out.println("Signup Service    "+request);
        try {
            RoleType role = request.getUser_role();
            User user = new User();
            user.setUser_id(UUID.randomUUID().toString());
            user.setUser_name(request.getUser_name());
            user.setUser_email(request.getUser_email());
            user.setUser_password(request.getUser_password());
            user.setLocation(request.getLocation());
            user.setUser_role(role);

            if(role == RoleType.JOB_SEEKER){
                user.setSkills(request.getSkills());
                user.setExperience(null);  
                user.setResumePath(null);  
            } else if(role == RoleType.RECRUITER){
                String companyId = UUID.randomUUID().toString();
                Company company = new Company(
                    companyId,
                    request.getCompany_name(),
                    request.getCompany_description(),
                    request.getCompany_location()
                );

                boolean companySaved = companyDao.save(company);
                System.out.println("Company saved? " + companySaved);

                if(!companySaved){
                    System.out.println("Recruiter registration failed: company insert failed!");
                    return null; 
                }
                System.err.println("companyId      "+companyId);
                user.setCompany_id(companyId);
            }
            SeekerDaoImpl dao = new SeekerDaoImpl();
            boolean userRegistered = dao.Register(user);
            System.out.println("User registered? " + userRegistered);  

            if(userRegistered){ 
                return user; 
            } else {
                System.out.println("User insert failed!");
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
