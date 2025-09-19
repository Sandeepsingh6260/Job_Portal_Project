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
<<<<<<< HEAD
	
	private static final long serialVersionUID = 1L;
	IUserService userService ;
=======

	private static final long serialVersionUID = 1L;
	IUserService userService;
>>>>>>> bfaa16c295886a17e0cfed24a69f658f34a4a169
	ICompanyService companyService;

	public AuthenticationServlet() {
		super();
		userService = new UserServiceImpl();
		companyService = new CompanyServiceImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String action = request.getParameter("action");
		if ("signup".equals(action)) {
			signup(request, response);
		}
		else if ("login".equalsIgnoreCase(action)) {
			System.out.println("garima");
			try {
				login(request,response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("user_name");
		String email = request.getParameter("user_email");
		String password = request.getParameter("user_password");
		String location = request.getParameter("location");
		String role = request.getParameter("user_role");

		boolean hasError = false;

		// ==================== Validation =========================
		if (name == null || name.isBlank()) {
			request.setAttribute("nameError", AppConstant.NAME_REQUIRED);
			hasError = true;
		} else if (!AppUtil.isValidName(name)) {
			request.setAttribute("nameError", AppConstant.NAME_NOT_VALID);
			hasError = true;
		}

		if (email == null || email.isBlank()) {
			request.setAttribute("emailError", AppConstant.EMAIL_REQUIRED);
			hasError = true;
		} else if (!AppUtil.isValidEmail(email)) {
			request.setAttribute("emailError", AppConstant.EMAIL_INVALID);
			hasError = true;
		}

		if (password == null || password.isBlank()) {
			request.setAttribute("passwordError", AppConstant.PASSWORD_REQUIRED);
			hasError = true;
		} else if (!AppUtil.isValidPassword(password)) {
			request.setAttribute("passwordError", AppConstant.PASSWORD_NOT_VALID);
			hasError = true;
		}

		if (location == null || location.isBlank()) {
			request.setAttribute("locationError", AppConstant.LOCATION_REQUIRED);
			hasError = true;
		} else if (!AppUtil.isValidLocation(location)) {
			request.setAttribute("locationError", AppConstant.LOCATION_NOT_VALID);
			hasError = true;
		}

		// Job Seeker

		if ("job_seeker".equalsIgnoreCase(role)) {
			String[] skills = request.getParameterValues("skills");
			String exp = request.getParameter("experience");
			String resume = request.getParameter("resume_path");

			if (skills == null || skills.length == 0 || skills[0].isBlank()) {
				request.setAttribute("skillsError", AppConstant.SKILLS_REQUIRED);
				hasError = true;
			}
			if (exp == null || exp.isBlank()) {
				request.setAttribute("experienceError", AppConstant.EXPERIENCE_REQUIRED);
				hasError = true;
			}
			if (resume == null || resume.isBlank()) {
				request.setAttribute("resumeError", AppConstant.RESUME_REQUIRED);
				hasError = true;
			}
		}

		// Recruiter
		if ("recruiter".equalsIgnoreCase(role)) {
			String companyName = request.getParameter("company_name");
			String companyLocation = request.getParameter("company_location");

			if (companyName == null || companyName.isBlank()) {
				request.setAttribute("companyNameError", AppConstant.COMPANY_NAME_REQUIRED);
				hasError = true;
			}
			if (companyLocation == null || companyLocation.isBlank()) {
				request.setAttribute("companyLocationError", AppConstant.COMPANY_LOCATION_REQUIRED);
				hasError = true;
			}
		}

		// अगर error है तो वापस JSP पर भेजो और entered values भी साथ भेजो
		if (hasError) {
			request.setAttribute("user_name_val", name);
			request.setAttribute("user_email_val", email);
			request.setAttribute("location_val", location);
			request.setAttribute("user_role_val", role);
			request.setAttribute("experience_val", request.getParameter("experience"));
			request.setAttribute("company_name_val", request.getParameter("company_name"));
			request.setAttribute("company_location_val", request.getParameter("company_location"));
			request.setAttribute("company_description_val", request.getParameter("company_description"));

			try {
				request.getRequestDispatcher("./auth/Signup.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		// ✅ User Object
		User req = new User();
		req.setUser_id(UUID.randomUUID().toString());
		req.setUser_name(name);
		req.setUser_email(email);
		req.setUser_password(password);
		req.setLocation(location);
		req.setIsDeleted(false);
		req.setUser_role(RoleType.valueOf(role.toUpperCase()));

		if (req.getUser_role() == RoleType.JOB_SEEKER) {
			String[] skillsArray = request.getParameterValues("skills");
			String join = "";
			for (String string : skillsArray) {
				if (string != null&&!string.isBlank()) {
					join = join.concat(string+",");
					System.out.println("===================>   "+join);
				}
			}
			req.setSkills(join.substring(0,join.length()-1));
			req.setExperience(request.getParameter("experience"));
			req.setResumePath(request.getParameter("resume_path"));
		} else if (req.getUser_role() == RoleType.RECRUITER) {
			Company company = new Company();
			company.setCompany_id(UUID.randomUUID().toString());
			company.setCompany_name(request.getParameter("company_name"));
			company.setCompany_description(request.getParameter("company_description"));
			company.setCompany_location(request.getParameter("company_location"));
			company.setIsDeleted(false);
			companyService.save(company);
		}

		User user = userService.signup(req);

		if (user != null) {
			request.setAttribute("successMsg", "Signup successful: " + user.getUser_name());
		} else {
			request.setAttribute("errorMsg", "Signup failed!");
		}

		try {
			request.getRequestDispatcher("./auth/Signup.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    String email = request.getParameter("user_email");
	    String password = request.getParameter("user_password");

	    // Server-side validation
	    if (email == null || email.isBlank()) {
	        request.setAttribute("error", "Email is required!");
	        request.getRequestDispatcher("./auth/login.jsp").forward(request, response);
	        return;
	    }

	    if (password == null || password.isBlank()) {
	        request.setAttribute("error", "Password is required!");
	        request.getRequestDispatcher("./auth/login.jsp").forward(request, response);
	        return;
	    }

	    User user = userService.login(email, password);

	    if (user != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("session", user);

	        if (user.getUser_role() == RoleType.JOB_SEEKER) {
	            response.sendRedirect("jobSeeker.jsp");
	        } else if (user.getUser_role() == RoleType.RECRUITER) {
	            response.sendRedirect("Recruiter.jsp");
	        }
	    } else {
	        request.setAttribute("error", "Invalid Email or Password!");
	        request.getRequestDispatcher("./auth/login.jsp").forward(request, response);
	    }
	}

}
