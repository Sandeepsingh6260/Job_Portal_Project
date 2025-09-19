package com.jobportal.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

import com.jobportal.enums.RoleType;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.payload.request.UserRequest;
import com.jobportal.service.ICompanyService;
import com.jobportal.service.IUserService;
import com.jobportal.service.impl.CompanyServiceImpl;
import com.jobportal.service.impl.UserServiceImpl;
import com.jobportal.util.AppUtil;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	IUserService userService ;
	ICompanyService companyService;

	public AuthenticationServlet() {
		super();
		userService = new UserServiceImpl();
		companyService=new CompanyServiceImpl();
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String action = request.getParameter("action");
		System.out.println("action ====>   "+action);
		if ("signup".equals(action)) {
			signup(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User req = new User();
		String email = request.getParameter("user_email");
		if(email!=null&&email.isBlank()) {
			 if(!AppUtil.isValidEmail(email)) {
				 
				 
			 }
		}
		else {
			
		}
		req.setUser_id(UUID.randomUUID().toString());
		req.setUser_name(request.getParameter("user_name"));
		req.setUser_email(email);
		req.setUser_password(request.getParameter("user_password"));
		req.setLocation(request.getParameter("location"));
		req.setIsDeleted(false);
		req.setUser_role(RoleType.valueOf(request.getParameter("user_role").toUpperCase()));

		if (req.getUser_role() == RoleType.JOB_SEEKER) {
			req.setSkills(request.getParameter("skills"));
			req.setExperience(request.getParameter("experience"));
			req.setResumePath(request.getParameter("resume_path"));
		} else if (req.getUser_role() == RoleType.RECRUITER) {
			Company company=new Company();
			company.setCompany_id(UUID.randomUUID().toString());
			company.setCompany_name(request.getParameter("company_name"));
			company.setCompany_description(request.getParameter("company_description"));
			company.setCompany_location(request.getParameter("company_location"));
			company.setIsDeleted(false);
			Boolean save = companyService.save(company);
			if (save != null) {
				response.getWriter().println("Signup successful: " + company.getCompany_name());
			} else {
				response.getWriter().println("Signup failed!");
			}
		}

		User user = userService.signup(req);
		if (user != null) {
			response.getWriter().println("Signup successful: " + user.getUser_name());
		} else {
			response.getWriter().println("Signup failed!");
		}
	}
}
