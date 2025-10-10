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
        encoder = new BCryptPasswordEncoder();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("=============>> GET request with action: " + action);
        
        switch (action != null ? action : "") {
            case "logout": {
                logout(request, response);
                break;
            }
            case "signup": {
                response.sendRedirect("auth/Signup.jsp");
                break;
            }
            default: {
                response.sendRedirect("auth/login.jsp");
                break;
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        System.out.println("Authentication Servlet called with action: " + action);

        switch (action != null ? action.toLowerCase() : "") {
            case "signup": {
                signup(request, response);
                break;
            }
            case "login": {
                login(request, response);
                break;
            }
            case "update": {
                update(request, response);
                break;
            }
            default: {
                // Invalid action - redirect to login with error
                HttpSession session = request.getSession();
                session.setAttribute("loginError", "Invalid action requested!");
                response.sendRedirect("auth/login.jsp");
                break;
            }
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

        if (hasError) {
            session.setAttribute("editFormOpen", true);  
            session.setAttribute("session", user);
            session.setAttribute("companySession", company);
            response.sendRedirect("myprofile.jsp"); 
            return;
        }

        // DB update
        boolean updated = userService.UpdateUserAndCompany(user, company);
        if (updated) {
            // Create a safe user object without password for session
            User safeUser = new User();
            safeUser.setUser_id(user.getUser_id());
            safeUser.setUser_name(user.getUser_name());
            safeUser.setUser_email(user.getUser_email());
            safeUser.setLocation(user.getLocation());
            safeUser.setUser_role(user.getUser_role());
            safeUser.setCompany_id(user.getCompany_id());
            safeUser.setIsDeleted(user.getIsDeleted());

            session.setAttribute("session", safeUser); 
            if (company != null) session.setAttribute("companySession", company); 
            session.setAttribute("successMsg", "Profile updated successfully");
        } else {
            session.setAttribute("errorMsg", "Failed to update profile");
        }

        response.sendRedirect("myprofile.jsp");
    }
    
    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Signup method called");

        HttpSession session = request.getSession();
        
        // Clear previous errors and messages
        clearSessionAttributes(session);

        String name = request.getParameter("user_name");
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        String location = request.getParameter("location");
        String role = request.getParameter("user_role");
        String mobile = request.getParameter("mobile");
        
        System.out.println("name -> " + name + " | email -> " + email + " | password -> " + password + " | location -> "
                + location + " | role -> " + role + " | mobile -> " + mobile);
        
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

        RoleType userRole = null;
        try {
            userRole = RoleType.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            session.setAttribute("roleError", "Invalid role selected!");
            hasError = true;
        }

        // ============== Recruiter-specific validation ==================
        if (userRole == RoleType.RECRUITER) {
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
            if (mobile == null || mobile.isBlank()) {
                session.setAttribute("mobileError", AppConstant.MOBILE_REQUIRED);
                hasError = true;
            } else if (!AppUtil.isValidMobile(mobile)) {
                session.setAttribute("mobileError", AppConstant.MOBILE_NOT_VALID);
                hasError = true;
            }
        }

        // Preserve entered values in session for redisplay
        preserveFormValues(session, request, userRole);

        System.out.println("Validation errors: " + hasError);
        
        if (hasError) {
            response.sendRedirect("auth/Signup.jsp");
            return;
        }

        String companyId = null;

        // =========== Create company only for recruiters ===================
        
        if (userRole == RoleType.RECRUITER) {
            String com_id = UUID.randomUUID().toString();
            Company company = new Company();
            company.setCompany_id(com_id);
            company.setCompany_name(request.getParameter("company_name"));
            company.setCompany_description(request.getParameter("company_description"));
            company.setCompany_location(request.getParameter("company_location"));
            company.setMobile(mobile);
            company.setIsDeleted(false);

            boolean companySaved = companyService.save(company);
            if (companySaved) {
                companyId = com_id;
            } else {
                session.setAttribute("errorMsg", "Failed to create company!");
                response.sendRedirect("auth/Signup.jsp");
                return;
            }
        }

        // Create User object
        User user = new User();
        user.setUser_id(UUID.randomUUID().toString());
        user.setUser_name(name);
        user.setUser_email(email);
        user.setUser_password(encoder.encode(password));
        user.setLocation(location);
        user.setCompany_id(companyId); // null for job seekers
        user.setIsDeleted(false);
        user.setUser_role(userRole);
        
        System.out.println("Creating user: " + user);
        
        User createdUser = userService.signup(user);

        if (createdUser != null)
        {
            session.setAttribute("successMsg", "Signup successful: " + createdUser.getUser_name());
            // Clear preserved values on success
            clearPreservedValues(session);
            response.sendRedirect("auth/login.jsp");
        }
        else {
            session.setAttribute("errorMsg", "Signup failed! Email might already exist.");
            response.sendRedirect("auth/Signup.jsp");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
            
        System.out.println("login enter ");
        // Clear previous errors
        session.removeAttribute("emailError");
        session.removeAttribute("passwordError");
        session.removeAttribute("loginError");

        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        
        
        System.out.println("user_email-->"+email+"user_password "+password);
        
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

        User user = userService.login(email);

        System.out.println("user-->"+user);
        
        if (user != null)
        {
            if (!encoder.matches(password, user.getUser_password())) {
                session.setAttribute("passwordInvalidError", AppConstant.INVALID_PASSWORD);
                session.setAttribute("user_email_val", email);
                response.sendRedirect("auth/login.jsp");
                return;
            }

            // Set session attributes
            
            session.setAttribute("user", user);
            session.setAttribute("user_id", user.getUser_id());
            session.setAttribute("session", user);

            // Get company data for recruiters
            
            if (user.getUser_role() == RoleType.RECRUITER && user.getCompany_id() != null) {
                Company company = companyService.getCompanyById(user.getCompany_id());
                session.setAttribute("companySession", company);
            }

            System.out.println("Login successful: " + user);
            
            redirectBasedOnRole(user.getUser_role(), response, request);
            
        }
        else {
            session.setAttribute("loginError", "Invalid Email or Password!");
            response.sendRedirect("auth/login.jsp");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if(session!=null) {
        session.invalidate();
        }
        response.sendRedirect("auth/login.jsp");
    }
    
    private void redirectBasedOnRole(RoleType role, HttpServletResponse response, HttpServletRequest request) throws IOException {
        System.out.println("redirect role enter---------------> ");
      
        System.out.println(role);

        switch (role) {
            case JOB_SEEKER:
                response.sendRedirect("JobSeekerServlet?action=viewDashboard");
                break;
            case RECRUITER:
                // Get user from session
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                
                // Ensure company data is loaded for recruiters 
                if (user != null && user.getCompany_id() != null) {
                    Company company = companyService.getCompanyById(user.getCompany_id());
                    session.setAttribute("companySession", company);
                }
                response.sendRedirect("Recruiter.jsp");
                break;
                
            case ADMIN:
                HttpSession adminSession = request.getSession();             
                User adminUser = (User) adminSession.getAttribute("user");
                
                if (adminUser != null)
                {
                    adminSession.setAttribute("adminUser", adminUser.getUser_email());
                    adminSession.setAttribute("adminRole", "administrator");
                    adminSession.setMaxInactiveInterval(30 * 60); // 30 minutes                            
                    response.sendRedirect("AdminServlet?action=viewdashboard");
                }
                else 
                {
                    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
                }
                break;


            default:
                response.sendRedirect("auth/login.jsp");
                break;
        }
    }
    

    private void clearSessionAttributes(HttpSession session) {
        // Clear error attributes
        session.removeAttribute("nameError");
        session.removeAttribute("emailError");
        session.removeAttribute("passwordError");
        session.removeAttribute("locationError");
        session.removeAttribute("roleError");
        session.removeAttribute("mobileError");
        session.removeAttribute("companyNameError");
        session.removeAttribute("companyLocationError");
        session.removeAttribute("errorMsg");
        session.removeAttribute("loginError");
    }
    
    private void preserveFormValues(HttpSession session, HttpServletRequest request, RoleType userRole) {
        session.setAttribute("user_name_val", request.getParameter("user_name"));
        session.setAttribute("user_email_val", request.getParameter("user_email"));
        session.setAttribute("location_val", request.getParameter("location"));
        session.setAttribute("user_role_val", request.getParameter("user_role"));
        session.setAttribute("mobile_val", request.getParameter("mobile"));

        if (userRole == RoleType.RECRUITER) {
            session.setAttribute("company_name_val", request.getParameter("company_name"));
            session.setAttribute("company_location_val", request.getParameter("company_location"));
            session.setAttribute("company_description_val", request.getParameter("company_description"));
        }
    }
    
    private void clearPreservedValues(HttpSession session) {
        session.removeAttribute("user_name_val");
        session.removeAttribute("user_email_val");
        session.removeAttribute("location_val");
        session.removeAttribute("user_role_val");
        session.removeAttribute("mobile_val");
        session.removeAttribute("company_name_val");
        session.removeAttribute("company_location_val");
        session.removeAttribute("company_description_val");
    }
}