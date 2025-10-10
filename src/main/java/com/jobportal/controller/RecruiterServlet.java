package com.jobportal.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.jobportal.dao.IApplicationDao;
import com.jobportal.daoimpl.ApplicationDaoImpl;
import com.jobportal.enums.StatusType;
import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.service.IApplicationService;
import com.jobportal.service.IJobService;
import com.jobportal.service.impl.ApplicationServiceImpl;
import com.jobportal.service.impl.JobServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RecruiterServlet")
public class RecruiterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IJobService jobService;
    private IApplicationService applicationService;
    private IApplicationDao applicationDao;

    @Override
    public void init() throws ServletException {
        super.init();
        jobService = new JobServiceImpl();
        applicationService = new ApplicationServiceImpl();
        applicationDao = new ApplicationDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("RecruiterServlet doGet method called");
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("RecruiterServlet doPost method called");
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Action received: " + action);

        if (action == null) {
            System.out.println("action null - redirecting to dashboard");
            getDashboardCounts(request, response);
            return;
        }

        switch (action.toLowerCase()) {
            case "edit":
                handleEdit(request, response);
                break;
                
            case "delete":
                handleDelete(request, response);
                break;
                
            case "update":
                handleUpdate(request, response);
                break;
                
            case "jobpost":
                handlePostJob(request, response);
                break;
                
            case "managejob":
                jobList(request, response);
                break;
                
            case "searchjobs":
                searchJobs(request, response);
                break;
                
            case "viewapplications":
                viewApplications(request, response);
                break; 
                
            case "searchapplications":
                searchApplications(request, response);
                break;
                
            case "filterapplications":
                filterApplications(request, response);
                break;
                
            case "manageapplication":
                manageApplication(request, response);
                break;
                
            case "dashboard":
                getDashboardCounts(request, response);
                break;
                
            default:
                getDashboardCounts(request, response);
                break;
        }
    }

    // New search methods
    private void searchJobs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("searchJobs method called");
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        
        if (userId == null) {
            response.sendRedirect("./auth/login.jsp");
            return;
        }
        
        String keyword = request.getParameter("keyword");
        System.out.println("Search keyword for jobs: " + keyword);
        
        List<Job> jobs;
        if (keyword != null && !keyword.trim().isEmpty()) {
            jobs = jobService.searchJobs(keyword.trim(), userId);
            request.setAttribute("searchKeyword", keyword);
            request.setAttribute("searchType", "jobs");
        } else {
            jobs = jobService.getAllJobs();
        }
        
        // Pagination
        int currentPage = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        
        int startIndex = (currentPage - 1) * recordsPerPage;
        int endIndex = Math.min(startIndex + recordsPerPage, jobs.size());
        int totalPages = (int) Math.ceil(jobs.size() * 1.0 / recordsPerPage);
        
        startIndex = Math.max(0, Math.min(startIndex, jobs.size()));
        endIndex = Math.max(startIndex, Math.min(endIndex, jobs.size()));
        
        List<Job> jobsPage = jobs.subList(startIndex, endIndex);
        
        request.setAttribute("jobs", jobsPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalJobs", jobs.size());
        
        request.getRequestDispatcher("ManageJobs.jsp").forward(request, response);
    }
    
    
    
    private void searchApplications(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("searchApplications method called");
        
        HttpSession session = request.getSession();
        String recruiterId = (String) session.getAttribute("user_id");
        
        if (recruiterId == null) {
            response.sendRedirect("./auth/login.jsp");
            return;
        }
        
        String keyword = request.getParameter("keyword");
        System.out.println("Search keyword for applications: " + keyword);
        
        List<Application> applications;
        if (keyword != null && !keyword.trim().isEmpty()) {
            applications = applicationService.searchApplications(keyword.trim(), recruiterId);
            request.setAttribute("searchKeyword", keyword);
            request.setAttribute("searchType", "applications");
        } else {
            applications = applicationService.getApplicationsByUser(recruiterId);
        }
        
        request.setAttribute("applications", applications);
        request.setAttribute("totalApplications", applications.size());
        
        request.getRequestDispatcher("viewapplications.jsp").forward(request, response);
    }
    
    private void filterApplications(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("filterApplications method called");
        
        HttpSession session = request.getSession();
        String recruiterId = (String) session.getAttribute("user_id");
        
        if (recruiterId == null) {
            response.sendRedirect("./auth/login.jsp");
            return;
        }
        
        String status = request.getParameter("status");
        System.out.println("Filter applications by status: " + status);
        
        List<Application> applications;
        if (status != null && !status.trim().isEmpty() && !status.equals("ALL")) {
            applications = applicationService.searchApplicationsByStatus(status.trim(), recruiterId);
            request.setAttribute("filterStatus", status);
            request.setAttribute("searchType", "filter");
        } else {
            applications = applicationService.getApplicationsByUser(recruiterId);
        }
        
        request.setAttribute("applications", applications);
        request.setAttribute("totalApplications", applications.size());
        
        request.getRequestDispatcher("viewapplications.jsp").forward(request, response);
    }

    // ... REST OF YOUR EXISTING METHODS (manageApplication, viewApplications, handleEdit, etc.) ...
    // Keep all your existing methods exactly as they were

    private void manageApplication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String appId = request.getParameter("applicationId");
        String typeParam = request.getParameter("type");

        if (appId == null || typeParam == null) {
            response.sendRedirect("viewApplications.jsp?error=Invalid+Request");
            return;
        }

        StatusType type = StatusType.valueOf(typeParam.toUpperCase());
        
        System.out.println("Updating status of Application ID: " + appId + " to " + type);
        
        boolean isUpdated = applicationService.updateApplicationStatus(appId, type);

        if (isUpdated) 
        {
            response.sendRedirect("RecruiterServlet?action=viewApplications&msg=Status+Updated+Successfully");
        } 
        else {
            response.sendRedirect("RecruiterServlet?action=viewApplications&error=Failed+to+Update+Status");
        }
    }

    private void viewApplications(HttpServletRequest request, HttpServletResponse response) 
    {
        try {
            System.out.println("view application method call");
            
            HttpSession session = request.getSession();            
            String recruiterId = (String) session.getAttribute("user_id");
     
            System.out.println(recruiterId);
            
            if (recruiterId == null)
            {
                response.sendRedirect("./auth/login.jsp");
                return;
            }

            List<Application> applications = applicationService.getApplicationsByUser(recruiterId);

            request.setAttribute("applications", applications);
            request.setAttribute("totalApplications", applications.size());
            
            request.getRequestDispatcher("viewapplications.jsp").forward(request, response);

        }
        catch (Exception e) 
        {
            e.printStackTrace();
            try {
                response.sendRedirect("error.jsp"); 
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    // ... ALL YOUR OTHER EXISTING METHODS (handleEdit, handleDelete, handleUpdate, handlePostJob, jobList, getDashboardCounts) ...
    // Keep them exactly as they were in your previous code

    private void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jobId = request.getParameter("job_id");
        System.out.println("Edit action for job ID: " + jobId);
        
        Job job = jobService.getJobById(jobId);
        System.out.println("Job found for edit: " + (job != null ? job.getTitle() : "null"));
        
        request.setAttribute("updateJob", job);
        jobList(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String jobId = request.getParameter("job_id");
        System.out.println("Delete action for job ID: " + jobId);
        
        boolean deleted = jobService.deleteJob(jobId);
        if (deleted) {
            session.setAttribute("successMsg", "Job deleted successfully!");
        } else {
            session.setAttribute("errorMsg", "Failed to delete job!");
        }
        
        response.sendRedirect("RecruiterServlet?action=managejob");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String jobId = request.getParameter("job_id");
        System.out.println("Update action for job ID: " + jobId);
        
        Job job = jobService.getJobById(jobId);

        if (job != null) {
            job.setTitle(request.getParameter("title"));
            job.setDescription(request.getParameter("description"));
            job.setLocation(request.getParameter("location"));
            job.setSalary(Double.parseDouble(request.getParameter("salary")));
            job.setExperience_required(request.getParameter("experience_required"));
            job.setJob_type(request.getParameter("job_type"));
            job.setMobile_no(request.getParameter("mobile_no"));

            boolean updated = jobService.updateJob(job);
            if (updated) {
                session.setAttribute("successMsg", "Job updated successfully!");
            } else {
                session.setAttribute("errorMsg", "Failed to update job!");
            }
        } else {
            session.setAttribute("errorMsg", "Job not found!");
        }       
        response.sendRedirect("RecruiterServlet?action=managejob");
    }

    private void handlePostJob(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String userId = (String) session.getAttribute("user_id");
        if (userId == null) {
            session.setAttribute("errorMsg", "Please login first!");
            response.sendRedirect("auth/login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String salaryStr = request.getParameter("salary");
        String experience = request.getParameter("experience_required");
        String jobType = request.getParameter("job_type");
        String mobileNo = request.getParameter("mobile_no");

        try {
            Job job = new Job();
            job.setId(java.util.UUID.randomUUID().toString());
            job.setTitle(title);
            job.setDescription(description);
            job.setLocation(location);
            job.setSalary(Double.parseDouble(salaryStr));
            job.setExperience_required(experience);
            job.setJob_type(jobType);
            job.setMobile_no(mobileNo);
            job.setUser_id(userId);

            boolean saved = jobService.savejob(job);
            if (saved) {
                session.setAttribute("successMsg", "Job posted successfully!");
            } else {
                session.setAttribute("errorMsg", "Failed to post job!");
            }

            response.sendRedirect("JobPost.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Error: " + e.getMessage());
            response.sendRedirect("JobPost.jsp");
        }
    }

    private void jobList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        System.out.println("jobList method called");
        
        int currentPage = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } 
            catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        List<Job> allJobs = jobService.getAllJobs();
        System.out.println("Total jobs retrieved from service: " + (allJobs != null ? allJobs.size() : "null"));

        if (allJobs == null) {
            allJobs = new java.util.ArrayList<>();
        }

        int startIndex = (currentPage - 1) * recordsPerPage;
        int endIndex = Math.min(startIndex + recordsPerPage, allJobs.size());
        int totalPages = (int) Math.ceil(allJobs.size() * 1.0 / recordsPerPage);

        
        startIndex = Math.max(0, Math.min(startIndex, allJobs.size()));
        endIndex = Math.max(startIndex, Math.min(endIndex, allJobs.size()));

        List<Job> jobsPage = allJobs.subList(startIndex, endIndex);
        System.out.println("Jobs for page " + currentPage + ": " + jobsPage.size() + " items");

        request.setAttribute("jobs", jobsPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalJobs", allJobs.size());

        request.getRequestDispatcher("ManageJobs.jsp").forward(request, response);
    }
    
    protected void getDashboardCounts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("getDashboardCounts method called");
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session");
        
        if (user == null) {
            System.out.println("User not found in session, redirecting to login");
            response.sendRedirect("auth/login.jsp");
            return;
        }
        
        System.out.println("Getting dashboard counts for user: " + user.getUser_id());
        
        try {
            Map<String, Integer> counts = applicationDao.getDashboardCounts(user.getUser_id());
            
            int jobCount = counts.getOrDefault("job_count", 0);
            int applicationCount = counts.getOrDefault("application_count", 0);
            int shortlistedCount = counts.getOrDefault("shortlisted_count", 0);
            int rejectedCount = counts.getOrDefault("rejected_count", 0);
            
            System.out.println("Dashboard counts - Jobs: " + jobCount + 
                             ", Applications: " + applicationCount + 
                             ", Shortlisted: " + shortlistedCount + 
                             ", Rejected: " + rejectedCount);
            
            request.setAttribute("jobCount", jobCount);
            request.setAttribute("applicationCount", applicationCount);
            request.setAttribute("shortlistedCount", shortlistedCount);
            request.setAttribute("rejectedCount", rejectedCount);
            
            // Get recent applications for the dashboard
            List<Application> recentApplications = applicationDao.getApplicationsById(user.getUser_id());
            request.setAttribute("recentApplications", recentApplications);
            
            // Get today's applications count
            int todayApplications = applicationDao.countTodaysApplications(user.getUser_id());
            request.setAttribute("todayApplications", todayApplications);
            
            request.getRequestDispatcher("recruiter.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in getDashboardCounts: " + e.getMessage());
            e.printStackTrace();
            // Fallback to default values in case of error
            request.setAttribute("jobCount", 0);
            request.setAttribute("applicationCount", 0);
            request.setAttribute("shortlistedCount", 0);
            request.setAttribute("rejectedCount", 0);
            request.setAttribute("recentApplications", new java.util.ArrayList<>());
            request.setAttribute("todayApplications", 0);
            request.getRequestDispatcher("recruiter.jsp").forward(request, response);
        }
    }
}