package com.jobportal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils;

import com.cloudinary.Cloudinary;
import com.jobportal.model.Resume;
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
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, // 5MB
		maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class JobSeekerServlet extends HttpServlet {
	private JobSeekerService jobSeekerService;
	private ResumeService resumeService;
	private JobApplicationService jobApplicationService;
	private Cloudinary cloudinary;

	@Override
	public void init() throws ServletException {
		super.init();
		jobSeekerService = new JobSeekerService();
		resumeService = new ResumeService();
		jobApplicationService = new JobApplicationService();
		cloudinary = CloudinaryUtil.getCloudinary();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		JobSeeker jobSeeker = (JobSeeker) session.getAttribute("jobSeeker");

		if (jobSeeker == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String action = request.getParameter("action");

		try {
			if ("viewDashboard".equals(action) || action == null) {
				viewDashboard(request, response, jobSeeker);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Error loading dashboard: " + e.getMessage());
			request.setAttribute("messageType", "danger");
			request.getRequestDispatcher("/job-seeker-dashboard.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		JobSeeker jobSeeker = (JobSeeker) session.getAttribute("jobSeeker");

		if (jobSeeker == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		String action = request.getParameter("action");

		try {
			switch (action) {
			case "uploadResume":
				uploadResume(request, response, jobSeeker);
				break;
			case "updateProfile":
				updateProfile(request, response, jobSeeker);
				break;
			case "deleteResume":
				deleteResume(request, response, jobSeeker);
				break;
			default:
				viewDashboard(request, response, jobSeeker);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Error processing request: " + e.getMessage());
			request.setAttribute("messageType", "danger");
			viewDashboard(request, response, jobSeeker);
		}
	}

	private void viewDashboard(HttpServletRequest request, HttpServletResponse response, JobSeeker jobSeeker)
			throws ServletException, IOException {

		try {
			// Get job seeker statistics
			int appliedJobsCount = jobApplicationService.getAppliedJobsCount(jobSeeker.getId());
			int savedJobsCount = jobSeekerService.getSavedJobsCount(jobSeeker.getId());
			int interviewCount = jobApplicationService.getInterviewCount(jobSeeker.getId());
			int profileStrength = calculateProfileStrength(jobSeeker);

			// Get recent applications
			List<JobApplication> recentApplications = jobApplicationService.getRecentApplications(jobSeeker.getId(), 5);

			// Get resume
			Resume resume = resumeService.getResumeByJobSeekerId(jobSeeker.getId());

			// Set attributes for JSP
			request.setAttribute("appliedJobsCount", appliedJobsCount);
			request.setAttribute("savedJobsCount", savedJobsCount);
			request.setAttribute("interviewCount", interviewCount);
			request.setAttribute("profileStrength", profileStrength);
			request.setAttribute("recentApplications", recentApplications);
			request.setAttribute("resume", resume);

			request.getRequestDispatcher("/job-seeker-dashboard.jsp").forward(request, response);

		} catch (Exception e) {
			throw new ServletException("Error loading dashboard", e);
		}
	}

	private void uploadResume(HttpServletRequest request, HttpServletResponse response, JobSeeker jobSeeker)
			throws ServletException, IOException {

		List<String> errors = new ArrayList<>();

		try {
			Part filePart = request.getPart("resumeFile");
			String resumeTitle = request.getParameter("resumeTitle");

			// Server-side validation
			if (filePart == null || filePart.getSize() == 0) {
				errors.add("Please select a resume file");
			}

			if (resumeTitle == null || resumeTitle.trim().isEmpty()) {
				errors.add("Resume title is required");
			} else if (resumeTitle.length() > 100) {
				errors.add("Resume title must be less than 100 characters");
			}

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				viewDashboard(request, response, jobSeeker);
				return;
			}

			// Validate file type and size on server side
			String fileName = filePart.getSubmittedFileName();
			String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
			List<String> allowedExtensions = Arrays.asList(".pdf", ".doc", ".docx");

			if (!allowedExtensions.contains(fileExtension)) {
				errors.add("Invalid file type. Please upload PDF, DOC, or DOCX files only.");
			}

			long fileSize = filePart.getSize();
			long maxFileSize = 5 * 1024 * 1024; // 5MB in bytes

			if (fileSize > maxFileSize) {
				errors.add("File size must be less than 5MB");
			}

			if (fileSize == 0) {
				errors.add("Selected file is empty");
			}

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				viewDashboard(request, response, jobSeeker);
				return;
			}

			// Upload to Cloudinary
			Map uploadResult = cloudinary.uploader().upload(filePart.getInputStream(),
					ObjectUtils.asMap("resource_type", "auto", "folder", "job-portal/resumes", "public_id",
							"resume_" + jobSeeker.getId() + "_" + System.currentTimeMillis()));

			String fileUrl = (String) uploadResult.get("secure_url");
			String publicId = (String) uploadResult.get("public_id");
			double fileSizeMB = fileSize / (1024.0 * 1024.0); // MB

			// Check if resume already exists
			Resume existingResume = resumeService.getResumeByJobSeekerId(jobSeeker.getId());
			if (existingResume != null) {
				// Delete old resume from Cloudinary
				cloudinary.uploader().destroy(existingResume.getPublicId(), ObjectUtils.emptyMap());
				// Update existing resume
				existingResume.setTitle(resumeTitle);
				existingResume.setFileName(fileName);
				existingResume.setFileUrl(fileUrl);
				existingResume.setFileSize(fileSizeMB);
				existingResume.setPublicId(publicId);
				existingResume.setUploadDate(new Date());

				boolean success = resumeService.updateResume(existingResume);

				if (success) {
					request.setAttribute("message", "Resume updated successfully!");
					request.setAttribute("messageType", "success");
				} else {
					// Delete from Cloudinary if database update fails
					cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
					request.setAttribute("message", "Error updating resume. Please try again.");
					request.setAttribute("messageType", "danger");
				}
			} else {
				// Create new Resume object
				Resume resume = new Resume();
				resume.setJobSeekerId(jobSeeker.getId());
				resume.setTitle(resumeTitle);
				resume.setFileName(fileName);
				resume.setFileUrl(fileUrl);
				resume.setFileSize(fileSizeMB);
				resume.setPublicId(publicId);
				resume.setUploadDate(new Date());

				// Save to database
				boolean success = resumeService.saveResume(resume);

				if (success) {
					request.setAttribute("message", "Resume uploaded successfully!");
					request.setAttribute("messageType", "success");
				} else {
					// Delete from Cloudinary if database save fails
					cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
					request.setAttribute("message", "Error uploading resume. Please try again.");
					request.setAttribute("messageType", "danger");
				}
			}

		} catch (Exception e) {
			errors.add("Error uploading resume: " + e.getMessage());
			request.setAttribute("errors", errors);
			request.setAttribute("messageType", "danger");
		}

		viewDashboard(request, response, jobSeeker);
	}

	private void updateProfile(HttpServletRequest request, HttpServletResponse response, JobSeeker jobSeeker)
			throws ServletException, IOException {

		List<String> errors = new ArrayList<>();

		try {
			String fullName = request.getParameter("fullName");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String location = request.getParameter("location");
			String summary = request.getParameter("summary");
			String skills = request.getParameter("skills");

			// Server-side validation
			if (fullName == null || fullName.trim().isEmpty()) {
				errors.add("Full name is required");
			} else if (fullName.length() > 100) {
				errors.add("Full name must be less than 100 characters");
			}

			if (email == null || email.trim().isEmpty()) {
				errors.add("Email is required");
			} else if (!isValidEmail(email)) {
				errors.add("Please enter a valid email address");
			} else if (email.length() > 100) {
				errors.add("Email must be less than 100 characters");
			}

			if (phone == null || phone.trim().isEmpty()) {
				errors.add("Phone number is required");
			} else if (!isValidPhone(phone)) {
				errors.add("Please enter a valid phone number");
			}

			if (location == null || location.trim().isEmpty()) {
				errors.add("Location is required");
			} else if (location.length() > 100) {
				errors.add("Location must be less than 100 characters");
			}

			if (summary == null || summary.trim().isEmpty()) {
				errors.add("Professional summary is required");
			} else if (summary.length() > 1000) {
				errors.add("Professional summary must be less than 1000 characters");
			}

			if (skills == null || skills.trim().isEmpty()) {
				errors.add("Skills are required");
			} else if (skills.length() > 500) {
				errors.add("Skills must be less than 500 characters");
			}

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				viewDashboard(request, response, jobSeeker);
				return;
			}

			// Update job seeker profile information
			jobSeeker.setFullName(fullName.trim());
			jobSeeker.setEmail(email.trim());
			jobSeeker.setPhone(phone.trim());
			jobSeeker.setLocation(location.trim());
			jobSeeker.setSummary(summary.trim());
			jobSeeker.setSkills(skills.trim());

			boolean success = jobSeekerService.updateJobSeeker(jobSeeker);

			if (success) {
				// Update session
				request.getSession().setAttribute("jobSeeker", jobSeeker);
				request.setAttribute("message", "Profile updated successfully!");
				request.setAttribute("messageType", "success");
			} else {
				errors.add("Error updating profile. Please try again.");
				request.setAttribute("errors", errors);
				request.setAttribute("messageType", "danger");
			}

		} catch (Exception e) {
			errors.add("Error updating profile: " + e.getMessage());
			request.setAttribute("errors", errors);
			request.setAttribute("messageType", "danger");
		}

		viewDashboard(request, response, jobSeeker);
	}

	private void deleteResume(HttpServletRequest request, HttpServletResponse response, JobSeeker jobSeeker)
			throws ServletException, IOException {

		List<String> errors = new ArrayList<>();

		try {
			String resumeIdParam = request.getParameter("resumeId");

			if (resumeIdParam == null || resumeIdParam.trim().isEmpty()) {
				errors.add("Resume ID is required");
				request.setAttribute("errors", errors);
				viewDashboard(request, response, jobSeeker);
				return;
			}

			int resumeId = Integer.parseInt(resumeIdParam);
			Resume resume = resumeService.getResumeById(resumeId);

			if (resume != null && resume.getJobSeekerId() == jobSeeker.getId()) {
				// Delete from Cloudinary
				cloudinary.uploader().destroy(resume.getPublicId(), ObjectUtils.emptyMap());

				// Delete from database
				boolean success = resumeService.deleteResume(resume.getId());

				if (success) {
					request.setAttribute("message", "Resume deleted successfully!");
					request.setAttribute("messageType", "success");
				} else {
					errors.add("Error deleting resume. Please try again.");
					request.setAttribute("errors", errors);
					request.setAttribute("messageType", "danger");
				}
			} else {
				errors.add("Resume not found or access denied.");
				request.setAttribute("errors", errors);
				request.setAttribute("messageType", "danger");
			}

		} catch (NumberFormatException e) {
			errors.add("Invalid resume ID format");
			request.setAttribute("errors", errors);
			request.setAttribute("messageType", "danger");
		} catch (Exception e) {
			errors.add("Error deleting resume: " + e.getMessage());
			request.setAttribute("errors", errors);
			request.setAttribute("messageType", "danger");
		}

		viewDashboard(request, response, jobSeeker);
	}

	// Helper methods for validation
	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		return email != null && email.matches(emailRegex);
	}

	private boolean isValidPhone(String phone) {
		// Basic phone validation - allows numbers, spaces, parentheses, hyphens
		String phoneRegex = "^[\\d\\s\\-\\(\\)\\+]+$";
		return phone != null && phone.matches(phoneRegex) && phone.replaceAll("\\D", "").length() >= 10;
	}

	private int calculateProfileStrength(JobSeeker jobSeeker) {
		int strength = 0;

		if (jobSeeker.getFullName() != null && !jobSeeker.getFullName().trim().isEmpty())
			strength += 15;
		if (jobSeeker.getEmail() != null && !jobSeeker.getEmail().trim().isEmpty())
			strength += 15;
		if (jobSeeker.getPhone() != null && !jobSeeker.getPhone().trim().isEmpty())
			strength += 15;
		if (jobSeeker.getLocation() != null && !jobSeeker.getLocation().trim().isEmpty())
			strength += 10;
		if (jobSeeker.getSummary() != null && !jobSeeker.getSummary().trim().isEmpty())
			strength += 20;
		if (jobSeeker.getSkills() != null && !jobSeeker.getSkills().trim().isEmpty())
			strength += 15;

		// Check if resume exists
		Resume resume = resumeService.getResumeByJobSeekerId(jobSeeker.getId());
		if (resume != null)
			strength += 10;

		return Math.min(strength, 100);
	}
}