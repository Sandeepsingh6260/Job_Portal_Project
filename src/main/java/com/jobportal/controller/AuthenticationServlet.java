package com.jobportal.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jobportal.enums.RoleType;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.service.ICompanyService;
import com.jobportal.service.IUserService;
import com.jobportal.service.impl.CompanyServiceImpl;
import com.jobportal.service.impl.UserServiceImpl;
import com.jobportal.util.AppConstant;
import com.jobportal.util.AppUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IUserService userService;
    private ICompanyService companyService;
    private BCryptPasswordEncoder encoder;

    public AuthenticationServlet() {
        super();
        userService = new UserServiceImpl();
        companyService = new CompanyServiceImpl();
        encoder=new BCryptPasswordEncoder();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        System.out.println(action);
        if ("signup".equalsIgnoreCase(action)) {
            signup(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            try {
                login(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if("update".equalsIgnoreCase(action)) {
        	
        	update(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String name = request.getParameter("user_name");
        String location = request.getParameter("location");
        String companyName = request.getParameter("company_name");
        String companyLocation = request.getParameter("company_location");
        String phone = request.getParameter("phoneNo");
        String companyDesc = request.getParameter("company_description");
        String currentPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");
        String confirmPassword = request.getParameter("confirm_password");

        User user = (User) session.getAttribute("session");
        if (user == null) {
            response.sendRedirect("auth/login.jsp");
            return;
        }

        Company company = (Company) session.getAttribute("companySession");

        boolean hasError = false;

        // Password validation
        if (currentPassword != null && !currentPassword.isEmpty()) {
            if (!encoder.matches(currentPassword, user.getUser_password())) {
                session.setAttribute("passwordInvalidError", "Current password is incorrect");
                hasError = true;
            }
            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("passwordInvalidError1", "New passwords do not match");
                hasError = true;
            }
            if (!AppUtil.isValidPassword(newPassword)) {
                session.setAttribute("passwordInvalidError1", "New password not valid");
                hasError = true;
            }
            if (!hasError) {
                user.setUser_password(encoder.encode(newPassword));
            }
        }

        // Update user fields
        user.setUser_name(name);
        user.setLocation(location);

        if (user.getUser_role() == RoleType.RECRUITER) {
            if (company != null) {
                company.setCompany_name(companyName);
                company.setCompany_location(companyLocation);
                company.setMobile(phone);
                company.setCompany_description(companyDesc);
            }
        }

        // अगर कोई validation error है → उसी page पर form open रखना
        if (hasError) {
            session.setAttribute("editFormOpen", true);  // ये flag JSP में check होगा
            session.setAttribute("session", user);
            session.setAttribute("companySession", company);
            response.sendRedirect("myprofile.jsp");  // form खुला रहे
            return;
        }

        // DB update
        boolean updated = userService.UpdateUserAndCompany(user, company);
        if (updated) {
            // password session में न रखे → नया object बनाएँ
            User safeUser = new User();
            safeUser.setUser_id(user.getUser_id());
            safeUser.setUser_name(user.getUser_name());
            safeUser.setUser_email(user.getUser_email());
            safeUser.setLocation(user.getLocation());
            safeUser.setUser_role(user.getUser_role());
            safeUser.setCompany_id(user.getCompany_id());
            safeUser.setIsDeleted(user.getIsDeleted());
            // password deliberately नहीं रखा

            session.setAttribute("session", safeUser); // safe user session में
            if (company != null) session.setAttribute("companySession", company); // पूरा company data session में
            session.setAttribute("successMsg", "Profile updated successfully");
        } else {
            session.setAttribute("errorMsg", "Failed to update profile");
        }

        response.sendRedirect("myprofile.jsp");
    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
        String action = request.getParameter("action");

        if ("logout".equalsIgnoreCase(action)) {
            logout(request, response);
        } else {
            doPost(request, response);
        }
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // false = don’t create new session
        if (session != null) {
            session.invalidate(); // destroy session
        }
        response.sendRedirect("auth/login.jsp"); // redirect to login page
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
        user.setUser_password(encoder.encode(password));
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
        
        

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        boolean hasError = false;

        // Validate email
        if (email == null || email.isBlank()) {
            session.setAttribute("emailError", AppConstant.EMAIL_REQUIRED);
            hasError = true;
        }

        // Validate password
        if (password == null || password.isBlank()) {
            session.setAttribute("passwordError", AppConstant.PASSWORD_REQUIRED);
            hasError = true;
        }

        // If validation failed, redirect back
        if (hasError) {
            session.setAttribute("user_email_val", email); // preserve input
            response.sendRedirect("auth/login.jsp");
            return;
        }

        // Fetch user
        User user = userService.login(email);

        if (user == null) {
            session.setAttribute("loginError", "Invalid Email!");
            session.setAttribute("user_email_val", email);
            response.sendRedirect("auth/login.jsp");
            return;
        }

        // Check password
        if (!encoder.matches(password, user.getUser_password())) {
            session.setAttribute("passwordInvalidError", AppConstant.INVALID_PASSWORD);
            session.setAttribute("user_email_val", email);
            response.sendRedirect("auth/login.jsp");
            return;
        }

        // Login successful
        session.setAttribute("session", user);

        Company company = companyService.getCompanyById(user.getCompany_id());
        session.setAttribute("companySession", company);

        if (user.getUser_role() == RoleType.JOB_SEEKER) {
            response.sendRedirect("jobSeeker.jsp");
        } else {
            response.sendRedirect("Recruiter.jsp");
        }
    }
}