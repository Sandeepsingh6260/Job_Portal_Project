package com.jobportal.controller;

import java.io.IOException;

import com.jobportal.model.Job;
import com.jobportal.service.IJobService;
import com.jobportal.service.impl.JobServiceImpl;
import com.jobportal.util.AppConstant;
import com.jobportal.util.AppUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class RecruitersServlet
 */
@WebServlet("/RecruitersServlet")
public class RecruiterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecruiterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("postJob".equals(action)) {
			handlePostJob(request, response);
		}
	}

	private void handlePostJob(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		boolean hasError = false;

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

		// Form inputs
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String location = request.getParameter("location");
		String salaryStr = request.getParameter("salary");
		String experience = request.getParameter("experience_required");
		String jobType = request.getParameter("job_type");
		String mobileNo = request.getParameter("mobile_no");

		// Validation
		if (title == null || title.isBlank()) {
			session.setAttribute("titleError", AppConstant.TITLE_REQUIRED);
			hasError = true;
		} else if (!AppUtil.isValidTitle(title)) {
			session.setAttribute("titleError", AppConstant.TITLE_NOT_VALID);
			hasError = true;
		}

		if (description == null || description.isBlank()) {
			session.setAttribute("descriptionError", AppConstant.DESCRIPTION_REQUIRED);
			hasError = true;
		}

		if (location == null || location.isBlank()) {
			session.setAttribute("locationError", AppConstant.LOCATION_REQUIRED);
			hasError = true;
		}

		if (salaryStr == null || salaryStr.isBlank()) {
			session.setAttribute("salaryError", AppConstant.SALARY_REQUIRED);
			hasError = true;
		} else if (!AppUtil.isValidSalary(salaryStr)) {
			session.setAttribute("salaryError", AppConstant.SALARY_NOT_VALID);
			hasError = true;
		}

		if (experience == null || experience.isBlank()) {
			session.setAttribute("experienceError", AppConstant.EXPERIENCE_REQUIRED);
			hasError = true;
		} else if (!AppUtil.isValidExperience(experience)) {
			session.setAttribute("experienceError", AppConstant.EXPERIENCE_NOT_VALID);
			hasError = true;
		}

		if (jobType == null || jobType.isBlank()) {
			session.setAttribute("jobTypeError", AppConstant.JOB_TYPE_REQUIRED);
			hasError = true;
		}

		if (mobileNo == null || mobileNo.isBlank()) {
			session.setAttribute("mobileError", AppConstant.MOBILE_REQUIRED);
			hasError = true;
		} else if (!AppUtil.isValidMobile(mobileNo)) {
			session.setAttribute("mobileError", AppConstant.MOBILE_NOT_VALID);
			hasError = true;
		}

		if (hasError) {
			// Save old inputs to repopulate form
			session.setAttribute("title_val", title);
			session.setAttribute("desc_val", description);
			session.setAttribute("loc_val", location);
			session.setAttribute("salary_val", salaryStr);
			session.setAttribute("exp_val", experience);
			session.setAttribute("job_type_val", jobType);
			session.setAttribute("mobile_val", mobileNo);

			try {
				response.sendRedirect("jobpost.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

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

		// Call Service layer

		IJobService jobService = new JobServiceImpl();
		boolean saved = jobService.savejob(job);

		if (saved) {
			session.setAttribute("successMsg", "Job posted successfully!");
		} else {
			session.setAttribute("errorMsg", "Something went wrong while posting the job.");
		}

		try {
			response.sendRedirect("jobpost.jsp");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
