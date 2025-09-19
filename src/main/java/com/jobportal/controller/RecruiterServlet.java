package com.jobportal.controller;

import java.io.IOException;
import java.util.UUID;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RecruitersServlet
 */
@WebServlet("/RecruitersServlet")
public class RecruiterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecruiterServlet() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("recruiter_name");
        String email = request.getParameter("recruiter_email");
        String password = request.getParameter("recruiter_password");
        String confirmPassword = request.getParameter("confirm_password");
        String companyName = request.getParameter("company_name");
        String location = request.getParameter("location");
        
        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                !password.equals(confirmPassword) ||
                companyName == null || companyName.trim().isEmpty() ||
                location == null || location.trim().isEmpty()) 
            {
                
                request.setAttribute("errorMessage", " Please fill all required fields and confirm password correctly.");
                request.getRequestDispatcher("recruiter.jsp").forward(request, response);
                return;
            }
        
        String user_id = UUID.randomUUID().toString();
        
	}

}
