package com.jobportal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

import com.jobportal.enums.RoleType;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.service.ICompanyService;
import com.jobportal.service.IUserService;
import com.jobportal.service.impl.CompanyServiceImpl;
import com.jobportal.service.impl.UserServiceImpl;
import com.jobportal.util.AppConstant;
import com.jobportal.util.AppUtil;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IUserService userService;
    private ICompanyService companyService;

    public AuthenticationServlet() {
        super();
        userService = new UserServiceImpl();
        companyService = new CompanyServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("signup".equalsIgnoreCase(action)) {
            signup(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            try {
                login(request, response);
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
        String location = request.getParameter("location");
        String role = request.getParameter("user_role");
        String mobile = request.getParameter("mobile");

        boolean hasError = false;

        // ==================== Validation =========================
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

        if (location == null || location.isBlank()) {
            session.setAttribute("locationError", AppConstant.LOCATION_REQUIRED);
            hasError = true;
        } else if (!AppUtil.isValidLocation(location)) {
            session.setAttribute("locationError", AppConstant.LOCATION_NOT_VALID);
            hasError = true;
        }
        
        if (mobile == null || mobile.isBlank()) {
            session.setAttribute("mobileError", AppConstant.MOBILE_REQUIRED);
            hasError = true;
        } else if (!AppUtil.isValidMobile(mobile)) {
            session.setAttribute("mobileError", AppConstant.MOBILE_NOT_VALID);
            hasError = true;
        }

        // Recruiter-specific validation
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
        session.setAttribute("mobile_val", mobile);

        if (hasError) {
            response.sendRedirect("auth/Signup.jsp");
            return;
        }
        RoleType userRole=RoleType.valueOf(role.toUpperCase());
        String com_id = UUID.randomUUID().toString();
        Company company = new Company();
        company.setCompany_id(com_id);

        if (userRole == RoleType.RECRUITER){
            company.setCompany_name(request.getParameter("company_name"));
            company.setCompany_description(request.getParameter("company_description"));
            company.setCompany_location(request.getParameter("company_location"));
            company.setMobile(request.getParameter("mobile"));
        } else {
            company.setCompany_name("Individual User");
            company.setCompany_description("Personal profile");
            company.setCompany_location(location);
            company.setMobile(mobile); 
        }
        company.setIsDeleted(false);
        companyService.save(company);
        
        // =============== User Object बनाएंगे =================
        User user = new User();
        user.setUser_id(UUID.randomUUID().toString());
        user.setUser_name(name);
        user.setUser_email(email);
        user.setUser_password(password);
        user.setLocation(location);
        user.setCompany_id(com_id);   
        user.setIsDeleted(false);
        user.setUser_role(userRole);

        User createdUser = userService.signup(user);
        
        if (createdUser != null) {
            session.setAttribute("successMsg", "Signup successful: " + createdUser.getUser_name());
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

        User user = userService.login(email, password);

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
