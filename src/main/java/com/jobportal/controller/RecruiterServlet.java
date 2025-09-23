package com.jobportal.controller;

import java.io.IOException;
import java.util.List;

import com.jobportal.model.Job;
import com.jobportal.service.IJobService;
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

    @Override
    public void init() throws ServletException {
        super.init();
        jobService = new JobServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("RecruiterServlet doGet method called");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("edit".equalsIgnoreCase(action)) {
                String jobId = request.getParameter("job_id");
                System.out.println("Edit action for job ID: " + jobId);
                
                Job job = jobService.getJobById(jobId);
                System.out.println("Job found for edit: " + (job != null ? job.getTitle() : "null"));
                
                request.setAttribute("updateJob", job);
            } 
            
            else if ("delete".equalsIgnoreCase(action)) {
                String jobId = request.getParameter("job_id");
                System.out.println("Delete action for job ID: " + jobId);
                boolean deleted = jobService.deleteJob(jobId);
                if (deleted) {
                    session.setAttribute("successMsg", "Job deleted successfully!");
                } else {
                    session.setAttribute("errorMsg", "Failed to delete job!");
                }
            }

            // Pagination manageJob
            int currentPage = 1;
            int recordsPerPage = 5;
            if(request.getParameter("page") != null) {
                currentPage = Integer.parseInt(request.getParameter("page"));
            }

            List<Job> allJobs = jobService.getAllJobs();
            System.out.println("Total jobs retrieved from service: " + (allJobs != null ? allJobs.size() : "null"));

            int startIndex = (currentPage - 1) * recordsPerPage;
            int endIndex = Math.min(startIndex + recordsPerPage, allJobs.size());
            int totalPages = (int) Math.ceil(allJobs.size() * 1.0 / recordsPerPage);

            List<Job> jobsPage = allJobs.subList(startIndex, endIndex);
            System.out.println("Jobs for page " + currentPage + ": " + jobsPage.size() + " items");

            request.setAttribute("jobs", jobsPage);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("manageJobs.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Something went wrong: " + e.getMessage());
            response.sendRedirect("manageJobs.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("RecruiterServlet doPost method called");

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("jobpost".equalsIgnoreCase(action)) {
                handlePostJob(request, response);
                return;
            }
            else if ("update".equalsIgnoreCase(action)) {
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
                }
            } else if ("delete".equalsIgnoreCase(action)) {
                String jobId = request.getParameter("job_id");
                System.out.println("Delete action for job ID: " + jobId);
                boolean deleted = jobService.deleteJob(jobId);
                if (deleted) {
                    session.setAttribute("successMsg", "Job deleted successfully!");
                } else {
                    session.setAttribute("errorMsg", "Failed to delete job!");
                }
            }

            response.sendRedirect("RecruiterServlet");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Something went wrong: " + e.getMessage());
            response.sendRedirect("manageJobs.jsp");
        }
    }

    private void handlePostJob(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String userId = (String) session.getAttribute("user_id");
        if (userId == null) {
            session.setAttribute("errorMsg", "Please login first!");
            try {
                response.sendRedirect("./auth/login.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            try {
                response.sendRedirect("./JobPost.jsp");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}