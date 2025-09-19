package com.jobportal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.UUID;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/JobSeekerServlet")
public class JobSeekerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobSeekerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("joobseeker ");
		String user_id=UUID.randomUUID().toString();
		String resume_id=UUID.randomUUID().toString();
		
		String name = request.getParameter("user_name");
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        String confirmPassword = request.getParameter("confirm_password");
        String skills = request.getParameter("skills");
        String experienceYears = request.getParameter("experience_years");
        String location = request.getParameter("location");
        
        Part resumePart = request.getPart("resume");
        
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Name is required.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }
        
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Name is required.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }
        
        if (password == null || password.length() < 6) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }
        
        if (skills == null || skills.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Skills are required.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }

        if (experienceYears == null || !experienceYears.matches("\\d+")) {
            request.setAttribute("errorMessage", "Experience must be a valid number.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }
        
        if (Integer.parseInt(experienceYears) < 0) {
            request.setAttribute("errorMessage", "Experience cannot be negative.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }
        
        if (location == null || location.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Location is required.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }
        
        
        if (resumePart == null || resumePart.getSize() == 0) {
            request.setAttribute("errorMessage", "Resume is required.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }
        
        String fileName = resumePart.getSubmittedFileName();
        
        if (!fileName.endsWith(".pdf")) {
            request.setAttribute("errorMessage", "Only PDF files allowed.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }

        
        if (resumePart.getSize() > 2 * 1024 * 1024) {
            request.setAttribute("errorMessage", "File size must not exceed 2MB.");
            request.getRequestDispatcher("jobseeker.jsp").forward(request, response);
            return;
        }    
		
	}

}
