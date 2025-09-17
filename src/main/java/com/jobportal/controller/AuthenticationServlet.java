package com.jobportal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import com.jobportal.daoimpl.SeekerDaoImpl;
import com.jobportal.enums.RoleType;
import com.jobportal.model.User;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		System.out.println("auth servlet---->>  "+action);
		switch (action) {
			case "signup":
				signup(request, response);
				break;
			default:
				throw new IllegalArgumentException("Unexpected action: " + action);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void signup(HttpServletRequest request, HttpServletResponse response) {
		RoleType user_role = RoleType.valueOf(request.getParameter("user_role"));
		User user = null;

		if(user_role.equals(RoleType.JOB_SEEKER)) {
		    String[] skillsArray = request.getParameterValues("skills");
		    String skillsCsv = (skillsArray != null) ? String.join(",", skillsArray) : null;

		    user = new User(
		        UUID.randomUUID().toString(),
		        request.getParameter("user_name"),
		        request.getParameter("user_email"),
		        request.getParameter("user_password"),
		        request.getParameter("location"),
		        skillsCsv,
		        request.getParameter("experience"),
		        null,  // resumePath
		        user_role,
		        null   // company_id
		    );
		}
		else if(user_role.equals(RoleType.RECRUITER))
		{
		    user = new User(
		        UUID.randomUUID().toString(),
		        request.getParameter("user_name"),
		        request.getParameter("user_email"),
		        request.getParameter("user_password"),
		        request.getParameter("location"),
		        null,  // skills
		        null,  // experience
		        null,  // resumePath
		        user_role,
		        UUID.randomUUID().toString()  // company_id
		    );
		}

		SeekerDaoImpl dao = new SeekerDaoImpl();
		if(dao.Register(user)) {
		    System.out.println(user_role + " registered successfully.");
		} else {
		    System.out.println("Registration failed for " + user_role);
		}
	}
}
