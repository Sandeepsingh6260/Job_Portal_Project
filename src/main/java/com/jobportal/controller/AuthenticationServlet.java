package com.jobportal.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.jobportal.enums.RoleType;
import com.jobportal.model.User;
import com.jobportal.payload.request.UserRequest;
import com.jobportal.service.IUserService;
import com.jobportal.serviceimpl.UserServiceImpl;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	IUserService userService ;

	public AuthenticationServlet() {
		super();
		userService = new UserServiceImpl();	
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
		UserRequest req = new UserRequest();
		req.setUser_name(request.getParameter("user_name"));
		req.setUser_email(request.getParameter("user_email"));
		req.setUser_password(request.getParameter("user_password"));
		req.setLocation(request.getParameter("location"));
		req.setUser_role(RoleType.valueOf(request.getParameter("role").toUpperCase()));

		if (req.getUser_role() == RoleType.JOB_SEEKER) {
			req.setSkills(request.getParameter("skills"));
			req.setExperience(request.getParameter("experience"));
			req.setResumePath(request.getParameter("resume_path"));
		} else if (req.getUser_role() == RoleType.RECRUITER) {
			req.setCompany_name(request.getParameter("company_name"));
			req.setCompany_description(request.getParameter("company_description"));
			req.setCompany_location(request.getParameter("company_location"));
		}

		User user = userService.signup(req);
		if (user != null) {
			response.getWriter().println("Signup successful: " + user.getUser_name());
		} else {
			response.getWriter().println("Signup failed!");
		}
	}
}
