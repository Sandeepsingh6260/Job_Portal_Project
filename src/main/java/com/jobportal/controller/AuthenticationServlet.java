package com.jobportal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
<<<<<<< Updated upstream
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
		// TODO Auto-generated method stub
		System.out.println("==============>>  "+UUID.randomUUID());
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
=======

    private static final long serialVersionUID = 1L;
    IUserService userService1;
    ICompanyService companyService;

    public AuthenticationServlet() {
        super();
        userService1 = new UserServiceImpl();
        companyService = new CompanyServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("signup".equals(action)) {
            signup(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            try {
                login(request,response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String name = request.getParameter("user_name");
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        String confirmPassword = request.getParameter("confirm_password");
        String location = request.getParameter("location");
        String role = request.getParameter("user_role");

        boolean hasError = false;

        if (name == null || name.isBlank()) {
            session.setAttribute("nameError", AppConstant.NAME_REQUIRED);
            hasError = true;
        } else if (!AppUtil.isValidName(name)) {
            session.setAttribute("nameError", AppConstant.NAME_NOT_VALID);
            hasError = true;
        }

        if (email == null || email.isBlank()) {
            session.setAttribute("emailError", AppConstant.EMAIL_REQUIRED);
            hasError = true;
        } else if (!AppUtil.isValidEmail(email)) {
            session.setAttribute("emailError", AppConstant.EMAIL_INVALID);
            hasError = true;
        }

        if (password == null || password.isBlank()) {
            session.setAttribute("passwordError", AppConstant.PASSWORD_REQUIRED);
            hasError = true;
        } else if (!AppUtil.isValidPassword(password)) {
            session.setAttribute("passwordError", AppConstant.PASSWORD_NOT_VALID);
            hasError = true;
        }

        if (confirmPassword == null || confirmPassword.isBlank()) {
            session.setAttribute("confirmPasswordError", AppConstant.PASSWORD_REQUIRED);
            hasError = true;
        } else if (!password.equals(confirmPassword)) {
            session.setAttribute("confirmPasswordError", "Passwords do not match!");
            hasError = true;
        }

        if (location == null || location.isBlank()) {
            session.setAttribute("locationError", AppConstant.LOCATION_REQUIRED);
            hasError = true;
        } else if (!AppUtil.isValidLocation(location)) {
            session.setAttribute("locationError", AppConstant.LOCATION_NOT_VALID);
            hasError = true;
        }
        
        // Recruiter specific validation
        if ("recruiter".equalsIgnoreCase(role)) {
            String companyName = request.getParameter("company_name");
            String companyLocation = request.getParameter("company_location");

            if (companyName == null || companyName.isBlank()) {
                session.setAttribute("companyNameError", AppConstant.COMPANY_NAME_REQUIRED);
                hasError = true;
            }
            if (companyLocation == null || companyLocation.isBlank()) {
                session.setAttribute("companyLocationError", AppConstant.COMPANY_LOCATION_REQUIRED);
                hasError = true;
            }
        }

        // Preserve entered values
        session.setAttribute("user_name_val", name);
        session.setAttribute("user_email_val", email);
        session.setAttribute("location_val", location);
        session.setAttribute("user_role_val", role);
        session.setAttribute("company_name_val", request.getParameter("company_name"));
        session.setAttribute("company_location_val", request.getParameter("company_location"));
        session.setAttribute("company_description_val", request.getParameter("company_description"));

        if (hasError) {
            response.sendRedirect("auth/Signup.jsp");
            return;
        }

        // Create User object
        User req = new User();
        req.setUser_id(UUID.randomUUID().toString());
        req.setUser_name(name);
        req.setUser_email(email);
        req.setUser_password(password);
        req.setLocation(location);
        req.setIsDeleted(false);
        req.setUser_role(RoleType.valueOf(role.toUpperCase()));

        if (req.getUser_role() == RoleType.RECRUITER) {
            Company company = new Company();
            company.setCompany_id(UUID.randomUUID().toString());
            company.setCompany_name(request.getParameter("company_name"));
            company.setCompany_description(request.getParameter("company_description"));
            company.setCompany_location(request.getParameter("company_location"));
            company.setIsDeleted(false);
            companyService.save(company);
        }

        User user = userService1.signup(req);

        if (user != null) {
            session.setAttribute("successMsg", "Signup successful: " + user.getUser_name());
        } else {
            session.setAttribute("errorMsg", "Signup failed!");
        }

        response.sendRedirect("auth/Signup.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        boolean hasError = false;

        if (email == null || email.isBlank()) {
            session.setAttribute("emailError", AppConstant.EMAIL_REQUIRED);
            hasError = true;
        }
        if (password == null || password.isBlank()) {
            session.setAttribute("passwordError", AppConstant.PASSWORD_REQUIRED);
            hasError = true;
        }

        session.setAttribute("user_email_val", email);

        if (hasError) {
            response.sendRedirect("auth/login.jsp");
            return;
        }

        User user = userService1.login(email, password);
>>>>>>> Stashed changes

        if (user != null) {
            session.setAttribute("session", user);
            if (user.getUser_role() == RoleType.JOB_SEEKER) {
                response.sendRedirect("jobSeeker.jsp");
            } else {
                response.sendRedirect("Recruiter.jsp");
            }
        } else {
            session.setAttribute("loginError", "Invalid Email or Password!");
            response.sendRedirect("auth/login.jsp");
        }
    }
}
