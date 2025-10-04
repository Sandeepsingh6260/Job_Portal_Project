<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*, com.jobportal.model.*"%>
<%
User jobSeeker = (User) session.getAttribute("user");
if (jobSeeker == null || !"JOB_SEEKER".equals(jobSeeker.getUser_role().name())) {
	response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
	return;
}

String activeTab = request.getAttribute("activeTab") != null ? (String) request.getAttribute("activeTab") : "dashboard";

List<Job> allJobs = (List<Job>) request.getAttribute("allJobs");
Resume resume = (Resume) request.getAttribute("resume");
List<String> appliedJobIds = (List<String>) request.getAttribute("appliedJobIds");

List<Application> applications = (List<Application>) request.getAttribute("applications");
List<Application> recentApplications = (List<Application>) request.getAttribute("recentApplications");

Integer totalApplications = (Integer) request.getAttribute("totalApplications");
Integer pendingApplications = (Integer) request.getAttribute("pendingApplications");
Integer shortlistedApplications = (Integer) request.getAttribute("shortlistedApplications");

String applyJobId = (String) session.getAttribute("applyJobId");

// Static job data for demonstration


Map<String, String> jobTitles = new HashMap<>();
jobTitles.put("job1", "Senior Java Developer");
jobTitles.put("job2", "Frontend React Developer");
jobTitles.put("job3", "Full Stack Engineer");
jobTitles.put("job4", "DevOps Engineer");
jobTitles.put("job5", "Data Analyst");

Map<String, String> companyNames = new HashMap<>();
companyNames.put("job1", "Tech Solutions Inc");
companyNames.put("job2", "Digital Innovations LLC");
companyNames.put("job3", "Software Crafters Ltd");
companyNames.put("job4", "Cloud Systems Corp");
companyNames.put("job5", "Data Insights Co");

%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Job Seeker Dashboard - Job Portal</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome@6.4.0/css/all.min.css"
	rel="stylesheet">
<style>
:root {
	--primary: #3f51b5;
	--secondary: #f50057;
	--success: #4caf50;
	--warning: #ff9800;
	--info: #2196f3;
	--light: #f8f9fa;
	--dark: #212529;
}

body {
	background-color: #f5f7fb;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.dashboard-container {
	display: flex;
	min-height: 100vh;
}

.sidebar {
	width: 250px;
	background: linear-gradient(180deg, var(--primary) 0%, #283593 100%);
	color: white;
	position: fixed;
	height: 100vh;
	overflow-y: auto;
	transition: all 0.3s;
	z-index: 1000;
}

.main-content {
	margin-left: 250px;
	flex: 1;
	padding: 20px;
	transition: all 0.3s;
}

.sidebar-header {
	padding: 20px;
	text-align: center;
	border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.sidebar-menu {
	padding: 15px 0;
}

.sidebar-menu a {
	display: flex;
	align-items: center;
	color: white;
	padding: 12px 20px;
	text-decoration: none;
	transition: all 0.3s;
	border-left: 3px solid transparent;
}

.sidebar-menu a:hover, .sidebar-menu a.active {
	background-color: rgba(255, 255, 255, 0.1);
	border-left-color: white;
}

.sidebar-menu a i {
	width: 25px;
	text-align: center;
	margin-right: 10px;
}

.stat-card {
	background: white;
	border-radius: 10px;
	padding: 20px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	margin-bottom: 20px;
	transition: transform 0.3s ease;
	border-left: 4px solid var(--primary);
}

.stat-card:hover {
	transform: translateY(-5px);
}

.job-card {
	background: white;
	border-radius: 10px;
	padding: 20px;
	margin-bottom: 15px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border-left: 4px solid var(--primary);
	transition: all 0.3s ease;
}

.job-card:hover {
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.profile-section {
	background: white;
	border-radius: 10px;
	padding: 20px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	margin-bottom: 20px;
}

.nav-tabs .nav-link.active {
	border-bottom: 3px solid var(--primary);
	font-weight: 600;
}

.header-bar {
	background: white;
	border-radius: 10px;
	padding: 15px 20px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	margin-bottom: 20px;
	display: flex;
	justify-content: between;
	align-items: center;
}

.user-avatar {
	width: 40px;
	height: 40px;
	border-radius: 50%;
	background: var(--primary);
	color: white;
	display: flex;
	align-items: center;
	justify-content: center;
	font-weight: bold;
}

@media ( max-width : 768px) {
	.sidebar {
		width: 100%;
		height: auto;
		position: relative;
	}
	.main-content {
		margin-left: 0;
	}
}

.application-status-pending {
	background-color: #ffeb3b;
	color: #000;
	padding: 4px 10px;
	border-radius: 5px;
	font-weight: 600;
}

.application-status-approved {
	background-color: #4caf50;
	color: white;
	padding: 4px 10px;
	border-radius: 5px;
	font-weight: 600;
}

.application-status-rejected {
	background-color: #f44336;
	color: white;
	padding: 4px 10px;
	border-radius: 5px;
	font-weight: 600;
}

.application-status-unknown {
	background-color: #9e9e9e;
	color: white;
	padding: 4px 10px;
	border-radius: 5px;
	font-weight: 600;
}

.application-status-shortlisted {
	background-color: #2196f3;
	color: white;
	padding: 4px 10px;
	border-radius: 5px;
	font-weight: 600;
}

.status-badge {
	padding: 4px 10px;
	border-radius: 5px;
	font-weight: 600;
	font-size: 0.85em;
}
</style>
</head>
<body>
	<div class="dashboard-container">
		<!-- Sidebar -->
		<div class="sidebar">
			<div class="sidebar-header">
				<h4>
					<i class="fas fa-briefcase me-2"></i>Job Portal
				</h4>
				<small>Job Seeker Dashboard</small>
			</div>

			<div class="sidebar-menu">
				<a href="JobSeekerServlet?action=viewDashboard"
					class="<%="dashboard".equals(activeTab) ? "active" : ""%>"> <i
					class="fas fa-home"></i> Dashboard
				</a> <a href="JobSeekerServlet?action=viewJobs"
					class="<%="jobs".equals(activeTab) ? "active" : ""%>"> <i
					class="fas fa-briefcase"></i> Browse Jobs
				</a> <a href="JobSeekerServlet?action=viewApplications"
					class="<%="applications".equals(activeTab) ? "active" : ""%>">
					<i class="fas fa-file-alt"></i> My Applications
				</a> <a href="JobSeekerServlet?action=viewProfile"
					class="<%="profile".equals(activeTab) ? "active" : ""%>"> <i
					class="fas fa-user"></i> My Profile
				</a> <a href="JobSeekerServlet?action=viewResume"
					class="<%="resume".equals(activeTab) ? "active" : ""%>"> <i
					class="fas fa-file-pdf"></i> My Resume
				</a> <a href="JobSeekerServlet?action=viewSettings"> <i
					class="fas fa-cog"></i> Settings
				</a>
				<hr
					style="border-color: rgba(255, 255, 255, 0.1); margin: 15px 20px;">
				<a href="JobSeekerServlet?action=logout">
				 <i class="fas fa-sign-out-alt"></i> Logout </a>
			</div>
		</div>

		<!-- Main Content -->
		<div class="main-content">
			<!-- Header Bar -->
			<div class="header-bar">
				<div>
					<h4 class="mb-0">
						<%
						if ("dashboard".equals(activeTab)) {
						%>Dashboard<%
						}
						%>
						<%
						if ("jobs".equals(activeTab)) {
						%>Browse Jobs<%
						}
						%>
						<%
						if ("applications".equals(activeTab)) {
						%>My Applications<%
						}
						%>
						<%
						if ("profile".equals(activeTab)) {
						%>My Profile<%
						}
						%>
						<%
						if ("resume".equals(activeTab)) {
						%>My Resume<%
						}
						%>
					</h4>
				</div>
				<div class="d-flex align-items-center">
					<div class="me-3 text-end">
						<div class="fw-bold"><%=jobSeeker.getUser_name()%></div>
						<small class="text-muted">Job Seeker</small>
					</div>
					<div class="user-avatar">
						<%=jobSeeker.getUser_name().substring(0, 1).toUpperCase()%>
					</div>
				</div>
			</div>

			<!-- Alert Messages -->
			<%
			if (session.getAttribute("message") != null) {
			%>
			<div
				class="alert alert-<%=session.getAttribute("messageType") != null ? session.getAttribute("messageType") : "info"%> alert-dismissible fade show"
				role="alert">
				<%=session.getAttribute("message")%>
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
			<%
			session.removeAttribute("message");
			session.removeAttribute("messageType");
			}
			%>

			<!-- Dashboard Content -->
			<%
			if ("dashboard".equals(activeTab)) {
			%>
			<!-- Statistics Cards -->
			<div class="row">
				<div class="col-md-3">
					<div class="stat-card text-center">
						<i class="fas fa-briefcase fa-2x text-primary mb-2"></i>
						<h3><%=allJobs != null ? allJobs.size() : 5%></h3>
						<p class="text-muted">Available Jobs</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="stat-card text-center">
						<i class="fas fa-paper-plane fa-2x text-success mb-2"></i>
						<h3><%=totalApplications != null ? totalApplications : (applications != null ? applications.size() : 0)%></h3>
						<p class="text-muted">Total Applications</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="stat-card text-center">
						<i class="fas fa-clock fa-2x text-warning mb-2"></i>
						<h3><%=pendingApplications != null ? pendingApplications : 0%></h3>
						<p class="text-muted">Pending</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="stat-card text-center">
						<i class="fas fa-user-check fa-2x text-info mb-2"></i>
						<h3><%=shortlistedApplications != null ? shortlistedApplications : 0%></h3>
						<p class="text-muted">Shortlisted</p>
					</div>
				</div>
			</div>

			<!-- Main Content Area -->
			<div class="row mt-4">
			
				<!-- Resume Status -->
				<div class="col-md-6">
					<div class="profile-section">
						<h5 class="mb-3">
							<i class="fas fa-file-alt me-2"></i>Resume Status
							<%
							if (resume != null) {
							%>
							<span class="badge bg-success ms-2">Uploaded</span>
							<%
							} else {
							%>
							<span class="badge bg-warning ms-2">Required</span>
							<%
							}
							%>
						</h5>

						<%
						if (resume != null) {
						%>
						<div class="mb-3">
							<strong>Skills:</strong>
							<p class="mb-2"><%=resume.getSkills() != null ? resume.getSkills() : "Not specified"%></p>
						</div>
						<div class="mb-3">
							<strong>Experience:</strong>
							<p class="mb-2"><%=resume.getExperience_years() > 0 ? resume.getExperience_years() + " years" : "Not specified"%></p>
						</div>
						<div class="d-flex gap-2">
							<a href="JobSeekerServlet?action=downloadResume"
								class="btn btn-primary btn-sm"> <i
								class="fas fa-download me-1"></i>Download Resume
							</a>
							<button class="btn btn-outline-primary btn-sm"
								data-bs-toggle="modal" data-bs-target="#resumeModal">
								<i class="fas fa-edit me-1"></i>Update
							</button>
						</div>
						<%
						} else {
						%>
						<div class="alert alert-warning">
							<i class="fas fa-exclamation-triangle me-2"></i> Upload your
							resume to start applying for jobs.
						</div>
						<button class="btn btn-primary" data-bs-toggle="modal"
							data-bs-target="#resumeModal">
							<i class="fas fa-upload me-1"></i>Upload Resume
						</button>
						<%
						}
						%>
					</div>

					<!-- Quick Actions -->
					<div class="profile-section">
						<h5 class="mb-3">
							<i class="fas fa-bolt me-2"></i>Quick Actions
						</h5>
						<div class="d-grid gap-2">
							<a href="JobSeekerServlet?action=viewJobs"
								class="btn btn-outline-primary"> <i
								class="fas fa-search me-2"></i>Browse Jobs
								
							</a> <a href="JobSeekerServlet?action=viewApplications"
								class="btn btn-outline-success">
								 <i class="fas fa-list me-2"></i>View	Applications </a>
								 
							<button class="btn btn-outline-info" data-bs-toggle="modal"
								data-bs-target="#resumeModal">
								<i class="fas fa-file-upload me-2"></i>
								<%=resume != null ? "Update Resume" : "Upload Resume"%>
							</button>
						</div>
					</div>
				</div>

				<!-- Recent Activity -->
				<div class="col-md-6">
					<div class="profile-section">
						<h5 class="mb-3">
							<i class="fas fa-clock me-2"></i>Recent Applications
						</h5>
						<%
						if (recentApplications != null && !recentApplications.isEmpty()) {
							for (Application recentApp : recentApplications) {
								String jobId = recentApp.getJob_id();
								String jobTitle = jobTitles.getOrDefault(jobId, "Unknown Job");
								String companyName = companyNames.getOrDefault(jobId, "Unknown Company");
								String status = recentApp.getStatusType() != null ? 
									recentApp.getStatusType().name() : "PENDING";
						%>
						<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-2">
							<div>
								<strong><%= jobTitle %></strong> 
								<br> 
								<small class="text-muted"><%= companyName %></small>
								<br>
								<small class="text-muted">Applied on: <%=recentApp.getCreated_at() != null ? recentApp.getCreated_at().toString().substring(0, 10) : "N/A"%></small>
							</div>
							<span class="status-badge application-status-<%=status.toLowerCase()%>">
								<%= status %>
							</span>
						</div>
						<%
							}
						%>
						<a href="JobSeekerServlet?action=viewApplications"
							class="btn btn-outline-primary btn-sm mt-2"> View All
							Applications </a>
						<%
						} else {
						%>
						<p class="text-muted">No recent applications. Start applying
							for jobs!</p>
						<a href="JobSeekerServlet?action=viewJobs"
							class="btn btn-primary btn-sm"> Browse Jobs </a>
						<%
						}
						%>
					</div>

					<!-- Recommended Jobs -->
					<div class="profile-section">
						<h5 class="mb-3">
							<i class="fas fa-star me-2"></i>Recommended Jobs
						</h5>
						<%
						if (allJobs != null && allJobs.size() > 0) {
							int count = 0;
							for (Job job : allJobs) {
								if (count >= 3) break;
								boolean isApplied = appliedJobIds != null && appliedJobIds.contains(job.getId());
						%>
						<div class="job-card">
							<h6 class="fw-bold"><%=job.getTitle()%></h6>
							<p class="text-muted small mb-2">
								<i class="fas fa-map-marker-alt me-1"></i><%=job.getLocation()%>
								| <i class="fas fa-dollar-sign me-1"></i><%=job.getSalary() != null ? job.getSalary() : "Negotiable"%>
							</p>
							<%
							if (!isApplied && resume != null) {
							%>
							<form action="JobSeekerServlet" method="post" class="mt-2">
								<input type="hidden" name="action" value="applyJob"> 
								<input type="hidden" name="jobId" value="<%=job.getId()%>">
								<button type="submit" class="btn btn-success btn-sm">
									<i class="fas fa-paper-plane me-1"></i>Quick Apply
								</button>
							</form>
							<%
							} else if (isApplied) {
							%>
							<span class="badge bg-success">Already Applied</span>
							<%
							} else {
							%>
							<span class="badge bg-warning">Upload Resume to Apply</span>
							<%
							}
							%>
						</div>
						<%
							count++;
							}
						} else {
							// Static demo jobs
							String[] demoJobs = {"Senior Java Developer", "Frontend React Developer", "Full Stack Engineer"};
							String[] demoCompanies = {"Tech Solutions Inc", "Digital Innovations LLC", "Software Crafters Ltd"};
							String[] demoLocations = {"New York, NY", "San Francisco, CA", "Remote"};
							for (int i = 0; i < 3; i++) {
						%>
						<div class="job-card">
							<h6 class="fw-bold"><%= demoJobs[i] %></h6>
							<p class="text-muted small mb-2">
								<i class="fas fa-building me-1"></i><%= demoCompanies[i] %>
								| <i class="fas fa-map-marker-alt me-1"></i><%= demoLocations[i] %>
								| <i class="fas fa-dollar-sign me-1"></i>$80,000 - $120,000
							</p>
							<%
							if (resume != null) {
							%>
							<button class="btn btn-success btn-sm" disabled>
								<i class="fas fa-paper-plane me-1"></i>Apply Now
							</button>
							<%
							} else {
							%>
							<span class="badge bg-warning">Upload Resume to Apply</span>
							<%
							}
							%>
						</div>
						<%
							}
						}
						%>
					</div>
				</div>
			</div>

			<%
			} else if ("jobs".equals(activeTab)) {
			%>
			<!-- Jobs Listing Page -->
			<div class="d-flex justify-content-between align-items-center mb-4">
				<h4>
					<i class="fas fa-briefcase me-2"></i>Available Jobs
				</h4>
				<span class="badge bg-primary"><%=allJobs != null ? allJobs.size() : 5%>
					jobs</span>
			</div>

			<%
			if (allJobs != null && !allJobs.isEmpty()) {
				for (Job job : allJobs) {
					boolean isApplied = appliedJobIds != null && appliedJobIds.contains(job.getId());
			%>
			<div class="job-card">
				<div class="row align-items-center">
					<div class="col-md-8">
						<h5 class="fw-bold text-primary"><%=job.getTitle()%></h5>
						<div class="row text-muted small mb-2">
							<div class="col-auto">
								<i class="fas fa-building me-1"></i><%= companyNames.getOrDefault(job.getId(), "Tech Company") %>
							</div>
							<div class="col-auto">
								<i class="fas fa-map-marker-alt me-1"></i><%=job.getLocation()%>
							</div>
							<div class="col-auto">
								<i class="fas fa-dollar-sign me-1"></i><%=job.getSalary() != null ? job.getSalary() : "Negotiable"%>
							</div>
							<div class="col-auto">
								<i class="fas fa-briefcase me-1"></i><%=job.getJob_type() != null ? job.getJob_type() : "Full-time"%>
							</div>
						</div>
						<p class="mb-3"><%=job.getDescription() != null && job.getDescription().length() > 200 ? 
							job.getDescription().substring(0, 200) + "..." : 
							(job.getDescription() != null ? job.getDescription() : "Great opportunity for skilled professionals.")%></p>
					</div>
					<div class="col-md-4 text-end">
						<%
						if (isApplied) {
						%>
						<span class="badge bg-success mb-2">Already Applied</span>
						<%
						}
						%>
						<form action="JobSeekerServlet" method="post">
							<input type="hidden" name="action" value="applyJob"> 
							<input type="hidden" name="jobId" value="<%=job.getId()%>">
							<button type="submit" class="btn btn-success"
								<%=resume == null || isApplied ? "disabled" : ""%>>
								<i class="fas fa-paper-plane me-1"></i>
								<%=isApplied ? "Applied" : (resume == null ? "Upload Resume First" : "Apply Now")%>
							</button>
						</form>
					</div>
				</div>
			</div>
			<%
				}
			} else {
				// Static demo jobs listing
				String[] staticJobIds = {"job1", "job2", "job3", "job4", "job5"};
				String[] staticJobTitles = {
					"Senior Java Developer", 
					"Frontend React Developer", 
					"Full Stack Engineer",
					"DevOps Engineer", 
					"Data Analyst"
				};
				String[] staticCompanies = {
					"Tech Solutions Inc",
					"Digital Innovations LLC", 
					"Software Crafters Ltd",
					"Cloud Systems Corp", 
					"Data Insights Co"
				};
				String[] staticLocations = {
					"New York, NY", 
					"San Francisco, CA", 
					"Remote",
					"Austin, TX", 
					"Boston, MA"
				};
				String[] staticSalaries = {
					"$100,000 - $140,000",
					"$90,000 - $130,000", 
					"$110,000 - $150,000",
					"$120,000 - $160,000", 
					"$80,000 - $110,000"
				};
				
				for (int i = 0; i < 5; i++) {
					boolean isApplied = appliedJobIds != null && appliedJobIds.contains(staticJobIds[i]);
			%>
			<div class="job-card">
				<div class="row align-items-center">
					<div class="col-md-8">
						<h5 class="fw-bold text-primary"><%= staticJobTitles[i] %></h5>
						<div class="row text-muted small mb-2">
							<div class="col-auto">
								<i class="fas fa-building me-1"></i><%= staticCompanies[i] %>
							</div>
							<div class="col-auto">
								<i class="fas fa-map-marker-alt me-1"></i><%= staticLocations[i] %>
							</div>
							<div class="col-auto">
								<i class="fas fa-dollar-sign me-1"></i><%= staticSalaries[i] %>
							</div>
							<div class="col-auto">
								<i class="fas fa-briefcase me-1"></i>Full-time
							</div>
						</div>
						<p class="mb-3">We are looking for a skilled <%= staticJobTitles[i] %> to join our dynamic team. 
						The ideal candidate will have strong technical skills and a passion for innovation.</p>
					</div>
					<div class="col-md-4 text-end">
						<%
						if (isApplied) {
						%>
						<span class="badge bg-success mb-2">Already Applied</span>
						<%
						}
						%>
						<form action="JobSeekerServlet" method="post">
							<input type="hidden" name="action" value="applyJob"> 
							<input type="hidden" name="jobId" value="<%= staticJobIds[i] %>">
							<button type="submit" class="btn btn-success"
								<%=resume == null || isApplied ? "disabled" : ""%>>
								<i class="fas fa-paper-plane me-1"></i>
								<%=isApplied ? "Applied" : (resume == null ? "Upload Resume First" : "Apply Now")%>
							</button>
						</form>
					</div>
				</div>
			</div>
			<%
				}
			}
			%>

			<%
			} else if ("applications".equals(activeTab)) {
			%>
			<!-- Applications Page -->
			<div class="d-flex justify-content-between align-items-center mb-4">
				<h4>
					<i class="fas fa-file-alt me-2"></i>My Job Applications
				</h4>
				<span class="badge bg-primary"><%=applications != null ? applications.size() : 0%>
					applications</span>
			</div>

			<%
			if (applications != null && !applications.isEmpty()) {
				for (Application app : applications) {
					String jobId = app.getJob_id();
					String jobTitle = jobTitles.getOrDefault(jobId, "Unknown Job");
					String companyName = companyNames.getOrDefault(jobId, "Unknown Company");
					String status = app.getStatusType() != null ? 
						app.getStatusType().name() : "PENDING";
			%>
			<div class="job-card">
				<div class="row align-items-center">
					<div class="col-md-8">
						<h5 class="fw-bold text-primary"><%= jobTitle %></h5>
						<p class="text-muted mb-2">
							<i class="fas fa-building me-1"></i><%= companyName %>
						</p>
						<div class="row text-muted small">
							<div class="col-auto">
								<i class="fas fa-calendar me-1"></i>Applied:
								<%=app.getCreated_at() != null ? app.getCreated_at().toString().substring(0, 10) : "N/A"%>
							</div>
							<div class="col-auto">
								Job ID: <%= jobId.substring(0, 8) %>...
							</div>
						</div>
					</div>
					<div class="col-md-4 text-end">
						<span class="status-badge application-status-<%=status.toLowerCase()%> mb-2">
							<%= status %>
						</span>
						<br>
						<%
						if ("PENDING".equals(status)) {
						%>
						<form action="JobSeekerServlet" method="post" class="d-inline">
							<input type="hidden" name="action" value="withdrawApplication">
							<input type="hidden" name="applicationId"
								value="<%=app.getId()%>">
							<button type="submit" class="btn btn-outline-danger btn-sm mt-2"
								onclick="return confirm('Are you sure you want to withdraw this application?')">
								Withdraw
							</button>
						</form>
						<%
						}
						%>
					</div>
				</div>
			</div>
			<%
				}
			} else {
			%>
			<div class="alert alert-info text-center">
				<i class="fas fa-info-circle me-2"></i>You haven't applied for any
				jobs yet.
				<br>
				<a href="JobSeekerServlet?action=viewJobs" class="btn btn-primary mt-2">
					Browse Available Jobs
				</a>
			</div>
			<%
			}
			%>

			<%
			} else if ("profile".equals(activeTab)) {
			%>
			<!-- Profile Page -->
			<div class="profile-section">
				<h4 class="mb-4">
					<i class="fas fa-user me-2"></i>My Profile
				</h4>

				<div class="row">
					<div class="col-md-6">
						<div class="mb-3">
							<strong>Full Name:</strong>
							<p><%=jobSeeker.getUser_name()%></p>
						</div>
						<div class="mb-3">
							<strong>Email:</strong>
							<p><%=jobSeeker.getUser_email()%></p>
						</div>
						<div class="mb-3">
							<strong>Location:</strong>
							<p><%=jobSeeker.getLocation() != null ? jobSeeker.getLocation() : "Not specified"%></p>
						</div>
					</div>
					<div class="col-md-6">
						<div class="mb-3">
							<strong>Member Since:</strong>
							<p><%=jobSeeker.getCreated_at() != null ? jobSeeker.getCreated_at().toString().substring(0, 10) : "N/A"%></p>
						</div>
						<div class="mb-3">
							<strong>Account Status:</strong>
							<p>
								<span class="badge bg-success">Active</span>
							</p>
						</div>
						<div class="mb-3">
							<strong>User Role:</strong>
							<p>
								<span class="badge bg-primary">Job Seeker</span>
							</p>
						</div>
					</div>
				</div>

				<div class="mt-4">
					<button class="btn btn-primary" data-bs-toggle="modal"
						data-bs-target="#profileModal">
						<i class="fas fa-edit me-1"></i>Edit Profile
					</button>
				</div>
			</div>

			<%
			} else if ("resume".equals(activeTab)) {
			%>
			<!-- Resume Management Page -->
			<div class="profile-section">
				<h4 class="mb-4">
					<i class="fas fa-file-pdf me-2"></i>Resume Management
				</h4>

				<%
				if (resume != null) {
				%>
				<div class="alert alert-success">
					<i class="fas fa-check-circle me-2"></i>Your resume is uploaded and
					ready.
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="mb-3">
							<strong>Skills:</strong>
							<p class="p-2 bg-light rounded"><%=resume.getSkills()%></p>
						</div>
						<div class="mb-3">
							<strong>Experience:</strong>
							<p class="p-2 bg-light rounded"><%=resume.getExperience_years()%>
								years
							</p>
						</div>
					</div>
					<div class="col-md-6">
						<div class="mb-3">
							<strong>Last Updated:</strong>
							<p class="p-2 bg-light rounded"><%=resume.getUpdated_at() != null ? resume.getUpdated_at().toString().substring(0, 10) : "N/A"%></p>
							</div>
						<div class="mb-3">
							<strong>Resume File:</strong>
							<p class="p-2 bg-light rounded">
								<a href="JobSeekerServlet?action=downloadResume"
									class="text-decoration-none"> <i
									class="fas fa-download me-1"></i>Download Resume
								</a>
							</p>
						</div>
					</div>
				</div>

				<div class="d-flex gap-2 mt-4">
					<button class="btn btn-primary" data-bs-toggle="modal"
						data-bs-target="#resumeModal">
						<i class="fas fa-edit me-1"></i>Update Resume
					</button>
					<form action="JobSeekerServlet" method="post">
						<input type="hidden" name="action" value="deleteResume">
						<button type="submit" class="btn btn-danger"
							onclick="return confirm('Are you sure you want to delete your resume?')">
							<i class="fas fa-trash me-1"></i>Delete Resume
						</button>
					</form>
				</div>
				<%
				} else {
				%>
				<div class="alert alert-warning">
					<i class="fas fa-exclamation-triangle me-2"></i> You haven't
					uploaded a resume yet. Upload your resume to start applying for
					jobs.
				</div>

				<div class="text-center py-4">
					<button class="btn btn-primary btn-lg" data-bs-toggle="modal"
						data-bs-target="#resumeModal">
						<i class="fas fa-upload me-2"></i>Upload Your First Resume
					</button>
				</div>
				<%
				}
				%>
			</div>
			<%
			}
			%>
		</div>
	</div>

	<!-- Resume Modal -->
	<div class="modal fade" id="resumeModal" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="fas fa-file-alt me-2"></i>
						<%=resume != null ? "Update Resume" : "Upload Resume"%>
					</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<form action="JobSeekerServlet" method="post"
					enctype="multipart/form-data">
					<div class="modal-body">
						<input type="hidden" name="action"
							value="<%=resume != null ? "updateResume" : "uploadResume"%>">

						<div class="mb-3">
							<label for="skills" class="form-label">Skills *</label>
							<textarea class="form-control" id="skills" name="skills" rows="3"
								placeholder="Enter your skills (e.g., Java, Spring Boot, MySQL, React)"
								required><%=resume != null && resume.getSkills() != null ? resume.getSkills() : ""%></textarea>
						</div>

						<div class="mb-3">
							<label for="experience_years" class="form-label">Years of Experience *</label>
								 <input type="number" class="form-control"
								id="experience_years" name="experience_years" min="0" max="50"
								value="<%=resume != null && resume.getExperience_years() > 0 ? resume.getExperience_years() : ""%>"
								placeholder="Enter your total years of experience" required>
						</div>

						<div class="mb-3">
							<label for="resumeFile" class="form-label">Resume File *</label>
							<input type="file" class="form-control" id="resumeFile"
								name="resumeFile" accept=".pdf,.doc,.docx"
								<%=resume == null ? "required" : ""%>>
							<div class="form-text">Accepted formats: PDF, DOC, DOCX
								(Max 5MB)</div>
							<%
							if (resume != null) {
							%>
							<div class="form-text">Current file will be replaced</div>
							<%
							}
							%>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancel</button>
						<button type="submit" class="btn btn-primary">
							<i class="fas fa-save me-1"></i>
							<%=resume != null ? "Update Resume" : "Upload Resume"%>
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
        // Auto-show modal if resume is required for job application
        <%if (applyJobId != null && resume == null) {%>
            document.addEventListener('DOMContentLoaded', function() {
                var resumeModal = new bootstrap.Modal(document.getElementById('resumeModal'));
                resumeModal.show();
            });
            <%session.removeAttribute("applyJobId");%>
        <%}%>
        
        // File size validation
        document.getElementById('resumeFile')?.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file && file.size > 5 * 1024 * 1024) {
                alert('File size must be less than 5MB');
                e.target.value = '';
            }
        });
    </script>
</body>
</html>