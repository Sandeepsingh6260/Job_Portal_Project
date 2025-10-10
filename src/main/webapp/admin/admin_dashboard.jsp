<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.jobportal.model.User"%>
<%@ page import="com.jobportal.model.User" %>
<%@ page import="com.jobportal.enums.RoleType" %>

<%@ page import="com.jobportal.model.User" %>
<%@ page import="com.jobportal.enums.RoleType" %>

<%
User user = (User) session.getAttribute("user");
if (user == null || user.getUser_role() != RoleType.ADMIN) {
    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    return;
}

response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0); 
%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard - Job Portal</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
	rel="stylesheet">
<style>
:root {
	--primary: #4361ee;
	--secondary: #3a0ca3;
	--success: #4cc9f0;
	--danger: #f72585;
	--warning: #f8961e;
	--dark: #2b2d42;
	--light: #f8f9fa;
	--sidebar: #1e293b;
	--card-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 6px 10px -5px
		rgba(0, 0, 0, 0.04);
	--hover-shadow: 0 20px 40px -10px rgba(0, 0, 0, 0.15);
}

* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	min-height: 100vh;
	line-height: 1.6;
	color: #333;
}

.dashboard-container {
	display: flex;
	min-height: 100vh;
}

/* Enhanced Sidebar */
.sidebar {
	width: 280px;
	background: var(--sidebar);
	color: white;
	padding: 0;
	position: fixed;
	height: 100vh;
	overflow-y: auto;
	box-shadow: 4px 0 20px rgba(0, 0, 0, 0.1);
	z-index: 1000;
}

.sidebar-header {
	padding: 30px 25px;
	background: rgba(255, 255, 255, 0.05);
	border-bottom: 1px solid rgba(255, 255, 255, 0.1);
	text-align: center;
}

.sidebar-header h2 {
	color: white;
	font-size: 1.6rem;
	font-weight: 700;
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 12px;
}

.sidebar-header h2 i {
	color: #4cc9f0;
}

.sidebar-menu {
	list-style: none;
	padding: 20px 0;
}

.sidebar-menu li {
	margin: 8px 15px;
	border-radius: 12px;
	overflow: hidden;
	transition: all 0.3s ease;
}

.sidebar-menu li a {
	color: #cbd5e1;
	text-decoration: none;
	display: flex;
	align-items: center;
	gap: 15px;
	padding: 16px 20px;
	font-size: 15px;
	font-weight: 500;
	transition: all 0.3s ease;
}

.sidebar-menu li i {
	width: 20px;
	text-align: center;
	font-size: 1.1rem;
}

.sidebar-menu li:hover {
	background: rgba(255, 255, 255, 0.1);
	transform: translateX(5px);
}

.sidebar-menu li:hover a {
	color: white;
}

.sidebar-menu li.active {
	background: linear-gradient(135deg, var(--primary), var(--secondary));
	box-shadow: 0 4px 15px rgba(67, 97, 238, 0.3);
}

.sidebar-menu li.active a {
	color: white;
}

/* Enhanced Main Content */
.main-content {
	flex: 1;
	padding: 30px;
	margin-left: 280px;
	width: calc(100% - 280px);
}

.header {
	background: rgba(255, 255, 255, 0.95);
	backdrop-filter: blur(10px);
	padding: 30px;
	border-radius: 20px;
	box-shadow: var(--card-shadow);
	margin-bottom: 30px;
	border: 1px solid rgba(255, 255, 255, 0.2);
}

.header h1 {
	color: var(--dark);
	margin-bottom: 10px;
	font-size: 2.2rem;
	font-weight: 700;
	background: linear-gradient(135deg, var(--primary), var(--danger));
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
}

.header p {
	color: #64748b;
	font-size: 1.1rem;
	font-weight: 500;
}

/* Enhanced Stats Cards */
.stats-container {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
	gap: 25px;
	margin-bottom: 35px;
}

.stat-card {
	background: rgba(255, 255, 255, 0.95);
	backdrop-filter: blur(10px);
	padding: 30px;
	border-radius: 20px;
	box-shadow: var(--card-shadow);
	text-align: center;
	transition: all 0.3s ease;
	border: 1px solid rgba(255, 255, 255, 0.2);
	position: relative;
	overflow: hidden;
}

.stat-card::before {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	height: 4px;
	background: linear-gradient(135deg, var(--primary), var(--success));
}

.stat-card:nth-child(2)::before {
	background: linear-gradient(135deg, var(--warning), var(--danger));
}

.stat-card:nth-child(3)::before {
	background: linear-gradient(135deg, var(--success), #4895ef);
}

.stat-card:hover {
	transform: translateY(-8px);
	box-shadow: var(--hover-shadow);
}

.stat-card h3 {
	color: #64748b;
	font-size: 14px;
	margin-bottom: 15px;
	text-transform: uppercase;
	letter-spacing: 1px;
	font-weight: 600;
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 8px;
}

.stat-number {
	font-size: 3rem;
	font-weight: 800;
	color: var(--dark);
	margin-bottom: 10px;
	text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.stat-card p {
	color: #94a3b8;
	font-weight: 500;
}

/* Enhanced Content Section */
.content-section {
	background: rgba(255, 255, 255, 0.95);
	backdrop-filter: blur(10px);
	border-radius: 20px;
	box-shadow: var(--card-shadow);
	padding: 30px;
	margin-bottom: 30px;
	border: 1px solid rgba(255, 255, 255, 0.2);
}

.section-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 25px;
	padding-bottom: 20px;
	border-bottom: 2px solid #e2e8f0;
}

.section-header h2 {
	color: var(--dark);
	font-size: 1.6rem;
	font-weight: 700;
	display: flex;
	align-items: center;
	gap: 12px;
}

.search-box {
	padding: 14px 20px;
	border: 2px solid #e2e8f0;
	border-radius: 12px;
	width: 350px;
	font-size: 14px;
	transition: all 0.3s ease;
	background: rgba(255, 255, 255, 0.8);
}

.search-box:focus {
	outline: none;
	border-color: var(--primary);
	box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.1);
	background: white;
}

/* Enhanced Table Styles */
.data-table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
	font-size: 14px;
	background: white;
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
}

.data-table th, .data-table td {
	padding: 18px 15px;
	text-align: left;
	border-bottom: 1px solid #f1f5f9;
}

.data-table th {
	background: linear-gradient(135deg, var(--primary), var(--secondary));
	color: white;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: 0.5px;
	font-size: 12px;
	border: none;
}

.data-table tr:last-child td {
	border-bottom: none;
}

.data-table tr:hover {
	background: #f8fafc;
	transform: scale(1.01);
	transition: all 0.2s ease;
}

.status-active {
	background: var(--success);
	color: white;
	padding: 8px 16px;
	border-radius: 20px;
	font-size: 12px;
	font-weight: 600;
	display: inline-flex;
	align-items: center;
	gap: 6px;
	box-shadow: 0 4px 10px rgba(76, 201, 240, 0.3);
}

.status-blocked {
	background: var(--danger);
	color: white;
	padding: 8px 16px;
	border-radius: 20px;
	font-size: 12px;
	font-weight: 600;
	display: inline-flex;
	align-items: center;
	gap: 6px;
	box-shadow: 0 4px 10px rgba(247, 37, 133, 0.3);
}

.btn {
	padding: 10px 20px;
	border: none;
	border-radius: 10px;
	cursor: pointer;
	font-size: 12px;
	margin: 3px;
	transition: all 0.3s ease;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: 0.5px;
	display: inline-flex;
	align-items: center;
	gap: 6px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.btn-activate {
	background: linear-gradient(135deg, var(--success), #38b2ac);
	color: white;
}

.btn-block {
	background: linear-gradient(135deg, var(--danger), #e53e3e);
	color: white;
}

.action-buttons {
	display: flex;
	gap: 8px;
	flex-wrap: wrap;
}

/* Enhanced Tabs */
.tabs {
	display: flex;
	border-bottom: 2px solid #e2e8f0;
	margin-bottom: 25px;
	gap: 5px;
}

.tab {
	padding: 16px 32px;
	cursor: pointer;
	border-bottom: 3px solid transparent;
	transition: all 0.3s ease;
	font-weight: 600;
	color: #64748b;
	border-radius: 10px 10px 0 0;
	display: flex;
	align-items: center;
	gap: 8px;
}

.tab:hover {
	color: var(--primary);
	background: rgba(67, 97, 238, 0.05);
}

.tab.active {
	border-bottom: 3px solid var(--primary);
	color: var(--primary);
	background: rgba(67, 97, 238, 0.1);
}

.tab-content {
	display: none;
	animation: fadeIn 0.5s ease;
}

.tab-content.active {
	display: block;
}

@
keyframes fadeIn {from { opacity:0;
	transform: translateY(10px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}

/* Enhanced Error Message */
.error-message {
	background: linear-gradient(135deg, var(--danger), #e53e3e);
	color: white;
	padding: 20px;
	border-radius: 15px;
	margin-bottom: 25px;
	text-align: center;
	box-shadow: 0 8px 25px rgba(247, 37, 133, 0.3);
	border: 1px solid rgba(255, 255, 255, 0.2);
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 12px;
	font-weight: 600;
}

/* Enhanced No Data Message */
.no-data {
	text-align: center;
	padding: 60px 40px;
	color: #94a3b8;
	font-size: 16px;
}

.no-data i {
	font-size: 3rem;
	margin-bottom: 15px;
	color: #cbd5e1;
}

/* Loading Animation */
.loading {
	display: inline-block;
	width: 16px;
	height: 16px;
	border: 2px solid rgba(255, 255, 255, 0.3);
	border-radius: 50%;
	border-top-color: white;
	animation: spin 1s ease-in-out infinite;
}

@
keyframes spin {to { transform:rotate(360deg);
	
}

}

/* Responsive Design */
@media ( max-width : 1200px) {
	.sidebar {
		width: 250px;
	}
	.main-content {
		margin-left: 250px;
		width: calc(100% - 250px);
	}
}

@media ( max-width : 768px) {
	.dashboard-container {
		flex-direction: column;
	}
	.sidebar {
		position: relative;
		width: 100%;
		height: auto;
	}
	.main-content {
		margin-left: 0;
		width: 100%;
		padding: 20px;
	}
	.stats-container {
		grid-template-columns: 1fr;
	}
	.section-header {
		flex-direction: column;
		gap: 15px;
		align-items: flex-start;
	}
	.search-box {
		width: 100%;
	}
	.action-buttons {
		flex-direction: column;
	}
	.tabs {
		flex-direction: column;
	}
	.tab {
		text-align: center;
		justify-content: center;
	}
}
</style>
</head>
<body>
	<div class="dashboard-container">
		<!-- Enhanced Sidebar -->
		<div class="sidebar">
			<div class="sidebar-header">
				<h2>
					<i class="fas fa-crown"></i> Admin Portal
				</h2>
			</div>
			<ul class="sidebar-menu">
				<li class="active"><a
					href="${pageContext.request.contextPath}/admin/dashboard"> <i
						class="fas fa-chart-line"></i>Dashboard
				</a></li>
				<li><a href="#jobseekers" onclick="switchTab('jobseekers')">
						<i class="fas fa-users"></i>Job Seekers
				</a></li>
				<li><a href="#recruiters" onclick="switchTab('recruiters')">
						<i class="fas fa-building"></i>Recruiters
				</a></li>
				<li><a
					href="${pageContext.request.contextPath}/AdminServlet?action=logout">
						<i class="fas fa-sign-out-alt"></i>Logout
				</a></li>
			</ul>
		</div>

		<!-- Main Content -->
		<div class="main-content">
			<div class="header">
				<h1>
					<i class="fas fa-tachometer-alt"></i> Admin Dashboard
				</h1>
				<p>Welcome back, Admin! Here's your comprehensive overview.</p>
			</div>

			<!-- Error Message -->
			<%
			String error = (String) request.getAttribute("error");
			if (error != null && !error.trim().isEmpty()) {
			%>
			<div class="error-message">
				<i class="fas fa-exclamation-triangle"></i>
				<%=error%>
			</div>
			<%
			}
			%>

			<!-- Statistics Cards -->
			<div class="stats-container">
				<div class="stat-card">
					<h3>
						<i class="fas fa-user-graduate"></i> Total Job Seekers
					</h3>
					<div class="stat-number">
						<%=request.getAttribute("totalJobSeekers") != null ? request.getAttribute("totalJobSeekers") : "0"%>
					</div>
					<p>Active job seekers in system</p>
				</div>
				<div class="stat-card">
					<h3>
						<i class="fas fa-briefcase"></i> Total Recruiters
					</h3>
					<div class="stat-number">
						<%=request.getAttribute("totalRecruiters") != null ? request.getAttribute("totalRecruiters") : "0"%>
					</div>
					<p>Active recruiting partners</p>
				</div>
				<div class="stat-card">
					<h3>
						<i class="fas fa-tasks"></i> Active Jobs
					</h3>
					<div class="stat-number">
						<%=request.getAttribute("activeJobs") != null ? request.getAttribute("activeJobs") : "0"%>
					</div>
					<p>Currently listed positions</p>
				</div>
			</div>

			<!-- Tabs for Job Seekers and Recruiters -->
			<div class="content-section">
				<div class="tabs">
					<div class="tab active" onclick="switchTab('jobseekers')">
						<i class="fas fa-users"></i> Job Seekers
					</div>
					<div class="tab" onclick="switchTab('recruiters')">
						<i class="fas fa-building"></i> Recruiters
					</div>
				</div>

				<!-- Job Seekers Tab -->
				<div id="jobseekers-tab" class="tab-content active">
					<div class="section-header">
						<h2>
							<i class="fas fa-user-graduate"></i> Manage Job Seekers
						</h2>
						<input type="text" class="search-box"
							placeholder="🔍 Search job seekers by name, email, or location..."
							onkeyup="searchTable(this, 'jobseekers-table')">
					</div>

					<table class="data-table" id="jobseekers-table">
						<thead>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Email</th>
								<th>Location</th>
								<th>Status</th>
								<th>Registration Date</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<%
							List<User> jobSeekers = (List<User>) request.getAttribute("jobSeekers");
							if (jobSeekers != null && !jobSeekers.isEmpty()) {
								for (User seeker : jobSeekers) {
							%>
							<tr>
								<td><strong>#<%=seeker.getUser_id()%></strong></td>
								<td><%=seeker.getUser_name() != null ? seeker.getUser_name() : "N/A"%></td>
								<td><%=seeker.getUser_email() != null ? seeker.getUser_email() : "N/A"%></td>
								<td><i class="fas fa-map-marker-alt"></i> <%=seeker.getLocation() != null ? seeker.getLocation() : "N/A"%></td>
								<td><span
									class="status-<%=seeker.isStatus() ? "active" : "blocked"%>">
										<i
										class="fas <%=seeker.isStatus() ? "fa-check" : "fa-ban"%>"></i>
										<%=seeker.isStatus() ? "ACTIVE" : "BLOCKED"%>
								</span></td>
								<td><%=seeker.getCreated_at() != null ? seeker.getCreated_at() : "N/A"%></td>
								<td>
									<div class="action-buttons">
										<%
										if (!seeker.isStatus()) {
										%>
										<button class="btn btn-activate"
											onclick="activateUser('<%=seeker.getUser_id()%>', 'jobseeker')">
											<i class="fas fa-play"></i>Activate
										</button>
										<%
										} else {
										%>
										<button class="btn btn-block"
											onclick="blockUser('<%=seeker.getUser_id()%>', 'jobseeker')">
											<i class="fas fa-pause"></i>Block
										</button>
										<%
										}
										%>
									</div>
								</td>
							</tr>
							<%
							}
							} else {
							%>
							<tr>
								<td colspan="7" class="no-data"><i
									class="fas fa-user-slash"></i>
									<h3>No Job Seekers Found</h3>
									<p>There are currently no job seekers registered in the
										system.</p></td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>

				<!-- Recruiters Tab -->
				<div id="recruiters-tab" class="tab-content">
					<div class="section-header">
						<h2>
							<i class="fas fa-building"></i> Manage Recruiters
						</h2>
						<input type="text" class="search-box"
							placeholder="🔍 Search recruiters by company, name, or email..."
							onkeyup="searchTable(this, 'recruiters-table')">
					</div>

					<table class="data-table" id="recruiters-table">
						<thead>
							<tr>
								<th>ID</th>
								<th>Company</th>
								<th>Contact Person</th>
								<th>Email</th>
								<th>Location</th>
								<th>Status</th>
								<th>Jobs Posted</th>
								<th>Registration Date</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<%
							List<User> recruiters = (List<User>) request.getAttribute("recruiters");
							if (recruiters != null && !recruiters.isEmpty()) {
								for (User recruiter : recruiters) {
							%>
							<tr>
								<td><strong>#<%=recruiter.getUser_id()%></strong></td>
								<td><%=recruiter.getCompany_name() != null ? recruiter.getCompany_name() : "N/A"%></td>
								<td><%=recruiter.getUser_name() != null ? recruiter.getUser_name() : "N/A"%></td>
								<td><%=recruiter.getUser_email() != null ? recruiter.getUser_email() : "N/A"%></td>
								<td><i class="fas fa-map-marker-alt"></i> <%=recruiter.getLocation() != null ? recruiter.getLocation() : "N/A"%></td>
								<td><span
									class="status-<%=recruiter.isStatus() ? "active" : "blocked"%>">
										<i
										class="fas <%=recruiter.isStatus() ? "fa-check" : "fa-ban"%>"></i>
										<%=recruiter.isStatus() ? "ACTIVE" : "BLOCKED"%>
								</span></td>
								<td><span
									style="background: #4895ef; color: white; padding: 6px 12px; border-radius: 15px; font-size: 12px; font-weight: 600;">
										<%=recruiter.getJobs_posted() > 0 ? recruiter.getJobs_posted() : "0"%>
								</span></td>
								<td><%=recruiter.getCreated_at() != null ? recruiter.getCreated_at() : "N/A"%></td>
								<td>
									<div class="action-buttons">
										<%
										if (!recruiter.isStatus()) {
										%>
										<button class="btn btn-activate"
											onclick="activateUser('<%=recruiter.getUser_id()%>', 'recruiter')">
											<i class="fas fa-play"></i>Activate
										</button>
										<%
										} else {
										%>
										<button class="btn btn-block"
											onclick="blockUser('<%=recruiter.getUser_id()%>', 'recruiter')">
											<i class="fas fa-pause"></i>Block
										</button>
										<%
										}
										%>
									</div>
								</td>
							</tr>
							<%
							}
							} else {
							%>
							<tr>
								<td colspan="9" class="no-data"><i class="fas fa-building"></i>
									<h3>No Recruiters Found</h3>
									<p>There are currently no recruiters registered in the
										system.</p></td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script>
        // Tab switching functionality
        function switchTab(tabName) {
            // Hide all tab contents
            document.querySelectorAll('.tab-content').forEach(tab => {
                tab.classList.remove('active');
            });
            
            // Remove active class from all tabs
            document.querySelectorAll('.tab').forEach(tab => {
                tab.classList.remove('active');
            });
            
            // Show selected tab content
            document.getElementById(tabName + '-tab').classList.add('active');
            
            // Add active class to clicked tab
            event.target.classList.add('active');
        }

        // Search functionality
        function searchTable(input, tableId) {
            const searchTerm = input.value.toLowerCase();
            const table = document.getElementById(tableId);
            const rows = table.querySelectorAll('tbody tr');
            
            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                if (text.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }

        // Activate user function
        function activateUser(userId, userType) {
            if (confirm('Are you sure you want to activate this ' + userType + '?')) {
                const button = event.target;
                const originalText = button.innerHTML;
                button.innerHTML = '<div class="loading"></div> Processing...';
                button.disabled = true;

                fetch('${pageContext.request.contextPath}/AdminServlet', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'action=manageUser&userType=' + userType + '&userId=' + userId + '&manageAction=activate'
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert(data.message);
                        location.reload();
                    } else {
                        alert('Error: ' + data.message);
                        button.innerHTML = originalText;
                        button.disabled = false;
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error activating user. Please try again.');
                    button.innerHTML = originalText;
                    button.disabled = false;
                });
            }
        }

        // Block user function
        function blockUser(userId, userType) {
            if (confirm('Are you sure you want to block this ' + userType + '?')) {
                const button = event.target;
                const originalText = button.innerHTML;
                button.innerHTML = '<div class="loading"></div> Processing...';
                button.disabled = true;

                fetch('${pageContext.request.contextPath}/AdminServlet', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'action=manageUser&userType=' + userType + '&userId=' + userId + '&manageAction=block'
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert(data.message);
                        location.reload();
                    } else {
                        alert('Error: ' + data.message);
                        button.innerHTML = originalText;
                        button.disabled = false;
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error blocking user. Please try again.');
                    button.innerHTML = originalText;
                    button.disabled = false;
                });
            }
        }

        // Initialize page
        document.addEventListener('DOMContentLoaded', function() {
            console.log('Admin dashboard loaded successfully');
        });
    </script>
</body>
</html>