package com.jobportal.filter;

import java.io.IOException;

import com.jobportal.enums.RoleType;
import com.jobportal.model.User;
import com.jobportal.service.IUserService;
import com.jobportal.service.impl.UserServiceImpl;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter({"/AuthenticationServlet/*", "/JobSeekerServlet/*", "/RecruiterServlet/*", "/AdminServlet/*"})
public class ServletFilter extends HttpFilter implements Filter {
    
    private static final long serialVersionUID = 1L;
    private IUserService userService;

    public ServletFilter() {
        super();
        userService = new UserServiceImpl();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        System.out.println("========= doFilter method called ========");
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String path = httpRequest.getServletPath();
        
        // Allow public resources
        if (isPublicResource(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Check if user is logged in
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        
        if (user == null) {
            // Not logged in - redirect to login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login.jsp");
            return;
        }
        
        // NEW: Check if user is blocked in database
        if (isUserBlocked(user.getUser_id())) {
            // User is blocked - invalidate session and redirect to login
            System.out.println("User is blocked, redirecting to login: " + user.getUser_id());
            if (session != null) {
                session.invalidate();
            }
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login.jsp?error=blocked");
            return;
        }
        
        // Check if user has access to the requested resource
        if (!hasAccess(user.getUser_role(), path)) {
            // No access - redirect to appropriate dashboard
            redirectToDashboard(user.getUser_role(), httpResponse, httpRequest.getContextPath());
            return;
        }
        
        // User has access - continue
        chain.doFilter(request, response);
    }
    
    private boolean isPublicResource(String path) {
        return path.startsWith("/auth/") ||
               path.equals("/AuthenticationServlet") ||
               path.startsWith("/css/") ||
               path.startsWith("/js/") ||
               path.startsWith("/images/") ||
               path.equals("/index.jsp") ||
               path.equals("/");
    }
    
    private boolean hasAccess(RoleType role, String path) {
        switch (role) {
            case ADMIN:
                return true;
                
            case RECRUITER:
                return !path.contains("Admin") && 
                       !path.contains("JobSeekerServlet");
                       
            case JOB_SEEKER:
                return !path.contains("Admin") && 
                       !path.contains("Recruiter");
                       
            default:
                return false;
        }
    }
    
    private void redirectToDashboard(RoleType role, HttpServletResponse response, String contextPath) 
            throws IOException {
        switch (role) {
            case ADMIN:
                response.sendRedirect(contextPath + "/AdminServlet?action=viewdashboard");
                break;
            case RECRUITER:
                response.sendRedirect(contextPath + "/Recruiter.jsp");
                break;
            case JOB_SEEKER:
                response.sendRedirect(contextPath + "/JobSeekerServlet?action=viewDashboard");
                break;
            default:
                response.sendRedirect(contextPath + "/auth/login.jsp");
                break;
        }
    }
 
    
    private boolean isUserBlocked(String userId) {
        try {
            System.out.println("Checking block status for user ID: " + userId);
            boolean isBlocked = userService.isUserBlocked(userId);
            
            System.out.println("User block status: " + isBlocked);
            return isBlocked;
            
        } catch (Exception e) {
            System.err.println("Error checking user block status: " + e.getMessage());
            e.printStackTrace();
            return false; 
        }
    }
}