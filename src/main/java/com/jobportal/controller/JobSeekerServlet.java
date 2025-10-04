package com.jobportal.controller;

import java.io.IOException;

import java.io.InputStream;
import java.util.*;

import com.jobportal.enums.StatusType;
import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.Resume;
import com.jobportal.model.User;
import com.jobportal.service.IApplicationService;
import com.jobportal.service.IJobService;
import com.jobportal.service.IResumeService;
import com.jobportal.service.impl.ApplicationServiceImpl;
import com.jobportal.service.impl.JobServiceImpl;
import com.jobportal.service.impl.ResumeServiceImpl;
import com.jobportal.util.CloudinaryUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


@WebServlet("/JobSeekerServlet")

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)

public class JobSeekerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IJobService jobService = new JobServiceImpl();
    private IResumeService resumeService = new ResumeServiceImpl();
    private IApplicationService applicationService = new ApplicationServiceImpl();

    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
  System.out.println("JobSeekerServlet ------  do get ");
  
        User user = getLoggedInUser(request, response);
        
       // System.out.println("Job seeker user do get"+user);
        
        
        if (user == null) return;

        String action = Optional.ofNullable(request.getParameter("action")).orElse("viewDashboard");
        
         System.out.println("action"+action);
         
        try {
            switch (action) {
                case "viewJobs":
                    viewAllJobs(request, response, user);
                    break;
                case "downloadResume":
                    downloadResume(request, response, user);
                    break;
                case "viewApplications":
                    viewApplications(request, response, user);
                    break;
                case "viewDashboard":
                	viewDashboard(request, response, user);
                	break;
                default:
                    viewDashboard(request, response, user);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            setSessionMessage(request.getSession(), "Error processing request: " + e.getMessage(), "danger");
            response.sendRedirect("JobSeekerServlet?action=viewDashboard");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	  System.out.println("JobSeekerServlet ------  do post ");

        User user = getLoggedInUser(request, response);
        
        System.out.println("user print do post method  ---->  "+user);

        if (user == null) return;

        String action = request.getParameter("action");
        
       System.out.println("do post ---->  "+action);

        try {
            switch (action != null ? action : "") {
                case "uploadResume":
                case "updateResume":
                    uploadOrUpdateResume(request, response, user);
                    break;
                case "applyJob":
                    applyForJob(request, response, user);
                    break;
                case "deleteResume":
                    deleteResume(request, response, user);
                    break;
                case "withdrawApplication":
                    withdrawApplication(request, response, user);
                    break;
                default:
                    response.sendRedirect("JobSeekerServlet?action=viewDashboard");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setSessionMessage(request.getSession(), "Error processing request: " + e.getMessage(), "danger");
            response.sendRedirect("JobSeekerServlet?action=viewDashboard");
        }
    }

    // Helper: get logged-in user
       
    private User getLoggedInUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	System.out.println("-------------->  getLoggedInUser");
    	
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
    	System.out.println(user);

        if (user == null || "JOB_SEEKER".equals(user.getUser_role())) 
        {
        	System.out.println("get logged in user in condition  ====>> "+user);
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return null;
        }
        
        return user;
    }

    private void viewDashboard(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
    	
    	System.out.println("method view dashboard enter");
    	
        List<Job> allJobs = jobService.getAllJobs();
        Resume resume = resumeService.getResumeByUserId(user.getUser_id());

        request.setAttribute("allJobs", allJobs);
        request.setAttribute("resume", resume);
        request.setAttribute("activeTab", "dashboard");
        request.setAttribute("user", user);
        
       System.out.println(" ---------->  view dashboard == jobseeker  enter == ");

        request.getRequestDispatcher("jobSeeker.jsp").forward(request, response);
        return;
    }


    private void viewAllJobs(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

    	System.out.println("----------> view job enter==========");
    	
        List<Job> allJobs = jobService.getAllJobs();
        
        Resume resume = resumeService.getResumeByUserId(user.getUser_id());
        List<String> appliedJobIds = applicationService != null
                ? applicationService.getAppliedJobIds(user.getUser_id())
                : new ArrayList<>();
    
        
        request.setAttribute("allJobs", allJobs);
        request.setAttribute("resume", resume);
        request.setAttribute("appliedJobIds", appliedJobIds);
        request.setAttribute("activeTab", "jobs");

        request.getRequestDispatcher("browseJobs.jsp").forward(request, response);
    }

    private void viewApplications(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        List<Application> applications = applicationService != null
                ? applicationService.getApplicationsByUser(user.getUser_id())
                : new ArrayList<>();
        Resume resume = resumeService.getResumeByUserId(user.getUser_id());

        request.setAttribute("applications", applications);
        request.setAttribute("resume", resume);
        request.setAttribute("activeTab", "applications");

        request.getRequestDispatcher("/jobseeker/dashboard.jsp").forward(request, response);
    }

    private void uploadOrUpdateResume(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
    	
    	System.out.println("resume upload method ");
    	
        HttpSession session = request.getSession();
        try {
            String skills = request.getParameter("skills");
            int experienceYears = Integer.parseInt(request.getParameter("experience_years"));
            Part filePart = request.getPart("resumeFile");

            if (filePart == null || filePart.getSize() == 0) {
                setSessionMessage(session, "Please select a resume file", "danger");
                response.sendRedirect("JobSeekerServlet?action=viewDashboard");
                return;
            }
            System.out.println(filePart);
            InputStream inputStream = filePart.getInputStream();

            Map result = CloudinaryUtil.uploadFile(inputStream, "job-portal/resumes", null);
          
            String resumeUrl = (String) result.get("secure_url");

            System.out.println("Uploaded Resume URL: " + resumeUrl);
            
        	System.out.println(resumeUrl);

            Resume resume = resumeService.getResumeByUserId(user.getUser_id());
            if (resume != null)
            {
                if (resume.getFile_path() != null) {
                    String oldPublicId = CloudinaryUtil.extractPublicIdFromUrl(resume.getFile_path());
                    if (oldPublicId != null) CloudinaryUtil.getCloudinary().uploader().destroy(oldPublicId, new HashMap<>());
                }
                resume.setSkills(skills);
                resume.setExperience_years(experienceYears);
                resume.setFile_path(resumeUrl);
                resumeService.updateResume(resume);
                setSessionMessage(session, "Resume updated successfully!", "success");
            } else {
                resume = new Resume();
                resume.setResume_id(generateResumeId());
                resume.setUser_id(user.getUser_id());
                resume.setSkills(skills);
                resume.setExperience_years(experienceYears);
                resume.setFile_path(resumeUrl);
                resume.setStatus(true);
                resumeService.saveResume(resume);
                setSessionMessage(session, "Resume uploaded successfully!", "success");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            setSessionMessage(session, "Error uploading resume: " + e.getMessage(), "danger");
        }
         response.sendRedirect("JobSeekerServlet?action=viewDashboard");
    }

    private void applyForJob(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
    	
    	System.out.println("---------> job apply enter 1 ");
    	
    	
        HttpSession session = request.getSession();
        try {
            String jobId = request.getParameter("jobId");
            
            System.out.println("job id from apply method ----------"+jobId);
            
            if (jobId == null || jobId.trim().isEmpty()) {
                setSessionMessage(session, "Invalid job selection", "danger");
                response.sendRedirect("JobSeekerServlet?action=viewJobs");
                return;
            }

            Resume resume = resumeService.getResumeByUserId(user.getUser_id());
            
            System.out.println("resume  from apply method ----------"+resume);
            
            
            if (resume == null) 
            {
                session.setAttribute("applyJobId", jobId);
                setSessionMessage(session, "Please upload your resume before applying", "warning");
                response.sendRedirect("JobSeekerServlet?action=viewDashboard");
                return;
            }

            boolean alreadyApplied = applicationService.hasUserAppliedForJob(user.getUser_id(), jobId);
            if (alreadyApplied) {
                setSessionMessage(session, "You have already applied for this job", "warning");
                response.sendRedirect("JobSeekerServlet?action=viewJobs");
                return;
            }

            Application app = new Application();
            app.setId(generateApplicationId());
            app.setUser_id(user.getUser_id());
            app.setJob_id(jobId);
            
            String companyId=applicationService.getCompanyIdByJobId(jobId);
            
            System.out.println("company id ---> "+companyId);
            
            app.setCompany_id(companyId);
            app.setStatusType(StatusType.PENDING);
            
           
            boolean success = applicationService.applyForJob(app);
            System.out.println("success-->"+success);         
            
          setSessionMessage(session, success ? "Applied successfully!" : "Error applying for job", success ? "success" : "danger");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            setSessionMessage(session, "Error applying for job: " + e.getMessage(), "danger");
        }
        response.sendRedirect("JobSeekerServlet?action=viewJobs");
    }

    private void deleteResume(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        try {
//            Resume resume = resumeService.getResumeByUserId(user.getUser_id());
//            if (resume != null) {
//                if (resume.getFile_path() != null) {
//                    String publicId = CloudinaryUtil.extractPublicIdFromUrl(resume.getFile_path());
//                    if (publicId != null) CloudinaryUtil.getCloudinary().uploader().destroy(publicId, new HashMap<>());
//                }
//                resumeService.deleteResume(resume.getResume_id());
//                setSessionMessage(session, "Resume deleted successfully!", "success");
//            } else {
//                setSessionMessage(session, "No resume found to delete", "warning");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            setSessionMessage(session, "Error deleting resume: " + e.getMessage(), "danger");
//        }
//        response.sendRedirect("JobSeekerServlet?action=viewDashboard");
    }

    private void withdrawApplication(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            String applicationId = request.getParameter("applicationId");
            Application application = applicationService.getApplicationById(applicationId);
            if (application != null && user.getUser_id().equals(application.getUser_id())) {
                boolean success = applicationService.withdrawApplication(applicationId);
                setSessionMessage(session, success ? "Application withdrawn successfully!" : "Error withdrawing application", success ? "success" : "danger");
            } else {
                setSessionMessage(session, "Application not found", "danger");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setSessionMessage(session, "Error withdrawing application: " + e.getMessage(), "danger");
        }
        response.sendRedirect("JobSeekerServlet?action=viewApplications");
    }

    private void downloadResume(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        Resume resume = resumeService.getResumeByUserId(user.getUser_id());
        if (resume != null && resume.getFile_path() != null) {
            response.sendRedirect(resume.getFile_path() + "?fl_attachment");
        } else {
            response.sendRedirect("JobSeekerServlet?action=viewDashboard");
        }
    }

    // Utility methods
    private void setSessionMessage(HttpSession session, String message, String type) {
        session.setAttribute("message", message);
        session.setAttribute("messageType", type);
    }

    private String generateResumeId() {
        return "RES_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String generateApplicationId() {
        return "APP_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
