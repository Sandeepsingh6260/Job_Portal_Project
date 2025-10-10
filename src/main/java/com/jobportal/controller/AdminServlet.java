package com.jobportal.controller;

import java.io.IOException;
import java.util.List;

import com.jobportal.model.User;
import com.jobportal.service.IAdminService;
import com.jobportal.service.impl.AdminServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IAdminService adminService;

    @Override
    public void init() throws ServletException {
        super.init();
        adminService = new AdminServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("GET Action: " + action);

        // Handle logout without requiring authentication
        if ("logout".equals(action)) {
            logout(request, response);
            return;
        }

        if (!isAdminAuthenticated(request)) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        try {
            if (action == null || action.equals("viewdashboard")) {
                showDashboard(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action not found: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/admin/admin_dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("POST Action: " + action);

        // Handle logout without requiring authentication
        if ("logout".equals(action)) {
            logout(request, response);
            return;
        }

        if (!isAdminAuthenticated(request)) {
            sendJsonResponse(response, false, "Authentication required");
            return;
        }

        try {
            if ("manageUser".equals(action)) {
                manageUser(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action not found: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, false, "Error processing request: " + e.getMessage());
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("--> showDashboard method enter");

        try {
            // Get statistics
            int totalJobSeekers = adminService.getTotalJobSeekers();
            int totalRecruiters = adminService.getTotalRecruiters();
            int activeJobs = adminService.getActiveJobsCount();
            int pendingApprovals = adminService.getPendingApprovalsCount();
            
            System.out.println("Stats - JobSeekers: " + totalJobSeekers + 
                             ", Recruiters: " + totalRecruiters + 
                             ", ActiveJobs: " + activeJobs + 
                             ", PendingApprovals: " + pendingApprovals);

            List<User> jobSeekers = adminService.getAllJobSeekers();
            List<User> recruiters = adminService.getAllRecruiters();
            
            System.out.println("JobSeekers list size: " + (jobSeekers != null ? jobSeekers.size() : "null"));
            System.out.println("Recruiters list size: " + (recruiters != null ? recruiters.size() : "null"));

            request.setAttribute("totalJobSeekers", totalJobSeekers);
            request.setAttribute("totalRecruiters", totalRecruiters);
            request.setAttribute("activeJobs", activeJobs);
            request.setAttribute("pendingApprovals", pendingApprovals);
            request.setAttribute("jobSeekers", jobSeekers);
            request.setAttribute("recruiters", recruiters);

            request.getRequestDispatcher("/admin/admin_dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading dashboard data: " + e.getMessage());
            request.getRequestDispatcher("/admin/admin_dashboard.jsp").forward(request, response);
        }
    }

    private void manageUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userType = request.getParameter("userType");
        String userId = request.getParameter("userId");
        String action = request.getParameter("manageAction"); // Changed to manageAction to avoid conflict

        System.out.println("manageUser - userType: " + userType + ", userId: " + userId + ", action: " + action);

        if (userId == null || userId.trim().isEmpty() || 
            userType == null || userType.trim().isEmpty() || 
            action == null || action.trim().isEmpty()) {
            sendJsonResponse(response, false, "Invalid parameters provided");
            return;
        }

        try {
            boolean success = false;
            String message = "";

            switch (action.toLowerCase()) {
                case "activate":
                    success = adminService.activateUser(userId);
                    message = success ? "User activated successfully" : "Failed to activate user";
                    break;
                case "block":
                    success = adminService.blockUser(userId);
                    message = success ? "User blocked successfully" : "Failed to block user";
                    break;
                default:
                    message = "Invalid action specified";
                    break;
            }

            sendJsonResponse(response, success, message);

        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, false, "Error managing user: " + e.getMessage());
        }
    }

    /**
     * Handles user logout by invalidating the session and redirecting to login page
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        
        System.out.println("=== LOGOUT PROCESS STARTED ===");
        System.out.println("Logout attempt - Session: " + session);
        
        if (session != null) {
            User user = (User) session.getAttribute("user");
            System.out.println("Logging out user: " + (user != null ? user.getUser_name() : "Unknown"));
            System.out.println("User role: " + (user != null && user.getUser_role() != null ? user.getUser_role().name() : "Unknown"));
            session.invalidate();
            System.out.println("Session invalidated successfully");
        } else {
            System.out.println("No active session found");
        }
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        
        System.out.println("Redirecting to login page...");
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        
        System.out.println("=== LOGOUT PROCESS COMPLETED ===");
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = String.format("{\"success\": %b, \"message\": \"%s\"}", success, message);
        System.out.println("Sending JSON response: " + json);
        response.getWriter().write(json);
    }

    private boolean isAdminAuthenticated(HttpServletRequest request) {
        System.out.println("Checking admin authentication...");
        HttpSession session = request.getSession(false);
        
        if (session == null) {
            System.out.println("No session found");
            return false;
        }
         
        User user = (User) session.getAttribute("user");
        boolean isAdmin = user != null && 
                         user.getUser_role() != null && 
                         "ADMIN".equalsIgnoreCase(user.getUser_role().name());
        
        System.out.println("User authenticated as admin: " + isAdmin);
        if (user != null) {
            System.out.println("User role: " + (user.getUser_role() != null ? user.getUser_role().name() : "null"));
        }
        
        return isAdmin;
    }
}