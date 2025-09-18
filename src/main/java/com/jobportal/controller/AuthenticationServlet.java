package com.jobportal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;


/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action= request.getParameter("action");
		System.out.println("=============>>  this is new  "+action);
		switch (action) {
		case "signup": {
			signup(request,response);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}	
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
			private void signup(HttpServletRequest request, HttpServletResponse response) {
				if(request.getParameter("user_role").equalsIgnoreCase("job_seeker"))
				{
				    String[] skillsArray = request.getParameterValues("skills");
				 
				    String user_name= request.getParameter("user_name");
				    String user_email = request.getParameter("user_email");
				    String user_password = request.getParameter("user_password");
				    String location = request.getParameter("location");
				    String user_role = request.getParameter("user_role");
				    				    
				    String skillsCsv = null;
				    if (skillsArray != null) {
				        skillsCsv = String.join(",", skillsArray);
				    }
				    
				    String experience = request.getParameter("experience");
				    
				    
				    System.out.println("Name: " + user_name);
				    System.out.println("Email: " + user_email);
				    System.out.println("Password: " + user_password);
				    System.out.println("Location: " + location);
				    System.out.println("Role: " + user_role);
				    System.out.println("Experience: " + experience);
				    System.out.println("Skills CSV: " + skillsCsv);
				    
			    }
				else if(request.getParameter("user_role").equalsIgnoreCase("recruiter"))
				{
						String user_name= request.getParameter("user_name");
					    String user_email = request.getParameter("user_email");
					    String user_password = request.getParameter("user_password");
					    String location = request.getParameter("location");
					    String user_role = request.getParameter("user_role");
					    String company_name = request.getParameter("company_name");
					    String company_location = request.getParameter("company_location");
					    String company_description = request.getParameter("company_description");
					    
					    
					    System.out.println("Name: " + user_name);
					    System.out.println("Email: " + user_email);
					    System.out.println("Password: " + user_password);
					    System.out.println("Location: " + location);
					    System.out.println("Role: " + user_role);
					    System.out.println("Experience: " + company_name);
					    System.out.println("Skills CSV: " + company_location);
					    System.out.println("Name: " + company_description);
					    
			        }
			   }
			}
