<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.jobportal.model.Application"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Job Applications | Job Portal</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
:root {
	--primary: #2c3e50;
	--secondary: #3498db;
	--success: #27ae60;
	--danger: #e74c3c;
	--warning: #f39c12;
	--info: #17a2b8;
	--light: #ecf0f1;
	--dark: #34495e;
	--white: #ffffff;
	--gray: #95a5a6;
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
	color: #333;
	line-height: 1.6;
}

/* Header */
.header {
	background: rgba(255, 255, 255, 0.95);
	backdrop-filter: blur(10px);
	padding: 1rem 2rem;
	box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
	position: sticky;
	top: 0;
	z-index: 100;
}

.header-content {
	max-width: 1200px;
	margin: 0 auto;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.logo {
	font-size: 1.8rem;
	font-weight: 700;
	color: var(--secondary);
	text-decoration: none;
}

.logo i {
	margin-right: 0.5rem;
}

/* Main Content */
.main-container {
	max-width: 1200px;
	margin: 2rem auto;
	padding: 0 1rem;
}

.page-title {
	text-align: center;
	color: white;
	font-size: 2.5rem;
	margin-bottom: 2rem;
	text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

/* Applications Card */
.applications-card {
	background: var(--white);
	border-radius: 15px;
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
	overflow: hidden;
	margin-bottom: 2rem;
}

.card-header {
	background: linear-gradient(135deg, var(--secondary), var(--primary));
	color: white;
	padding: 1.5rem 2rem;
}

.card-title {
	font-size: 1.5rem;
	font-weight: 600;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.applications-count {
	background: rgba(255, 255, 255, 0.2);
	padding: 0.3rem 0.8rem;
	border-radius: 20px;
	font-size: 0.9rem;
}

/* Table */
.table-container {
	overflow-x: auto;
	padding: 1rem;
}

.applications-table {
	width: 100%;
	border-collapse: collapse;
	min-width: 800px;
}

.applications-table th {
	background: var(--light);
	padding: 1rem;
	text-align: left;
	font-weight: 600;
	color: var(--dark);
	border-bottom: 2px solid #bdc3c7;
}

.applications-table td {
	padding: 1rem;
	border-bottom: 1px solid #ecf0f1;
	vertical-align: middle;
}

.applications-table tr:hover {
	background: #f8f9fa;
	transform: translateY(-1px);
	transition: all 0.2s ease;
}

/* Status Badges */
.status-badge {
	padding: 0.4rem 0.8rem;
	border-radius: 20px;
	font-size: 0.8rem;
	font-weight: 600;
	text-transform: uppercase;
}

.status-pending {
	background: #fff3cd;
	color: #856404;
}

.status-shortlisted {
	background: #d1edff;
	color: #004085;
}

.status-rejected {
	background: #f8d7da;
	color: #721c24;
}

/* Action Buttons */
.action-buttons {
	display: flex;
	gap: 0.5rem;
	flex-wrap: wrap;
}

.btn {
	padding: 0.5rem 1rem;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	font-size: 0.85rem;
	font-weight: 600;
	transition: all 0.3s ease;
	display: inline-flex;
	align-items: center;
	gap: 0.3rem;
	text-decoration: none;
}

.btn-view {
	background: var(--info);
	color: white;
}

.btn-view:hover {
	background: #138496;
	transform: translateY(-2px);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.btn-shortlist {
	background: var(--success);
	color: white;
}

.btn-shortlist:hover {
	background: #218838;
	transform: translateY(-2px);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.btn-reject {
	background: var(--danger);
	color: white;
}

.btn-reject:hover {
	background: #c82333;
	transform: translateY(-2px);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.btn-back {
	background: var(--gray);
	color: white;
	padding: 0.8rem 1.5rem;
}

.btn-back:hover {
	background: #5a6268;
	transform: translateY(-2px);
}

/* No Data */
.no-data {
	text-align: center;
	padding: 3rem;
	color: var(--gray);
}

.no-data i {
	font-size: 3rem;
	margin-bottom: 1rem;
	color: #bdc3c7;
}

/* Modal */
.modal {
	display: none;
	position: fixed;
	z-index: 1000;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.6);
	backdrop-filter: blur(5px);
}

.modal-content {
	background: white;
	margin: 5% auto;
	border-radius: 15px;
	width: 90%;
	max-width: 700px;
	max-height: 80vh;
	overflow-y: auto;
	box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
	animation: modalSlideIn 0.3s ease-out;
}

@
keyframes modalSlideIn {from { opacity:0;
	transform: translateY(-50px) scale(0.9);
}

to {
	opacity: 1;
	transform: translateY(0) scale(1);
}

}
.modal-header {
	background: linear-gradient(135deg, var(--secondary), var(--primary));
	color: white;
	padding: 1.5rem 2rem;
	border-radius: 15px 15px 0 0;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.modal-title {
	font-size: 1.3rem;
	font-weight: 600;
}

.close-btn {
	background: none;
	border: none;
	color: white;
	font-size: 1.5rem;
	cursor: pointer;
	padding: 0.5rem;
	border-radius: 50%;
	transition: background 0.3s ease;
}

.close-btn:hover {
	background: rgba(255, 255, 255, 0.2);
}

.modal-body {
	padding: 2rem;
}

/* Applicant Details */
.applicant-details {
	display: grid;
	gap: 1.5rem;
}

.detail-section {
	background: #f8f9fa;
	padding: 1.5rem;
	border-radius: 10px;
	border-left: 4px solid var(--secondary);
}

.section-title {
	font-size: 1.1rem;
	font-weight: 600;
	color: var(--secondary);
	margin-bottom: 1rem;
	display: flex;
	align-items: center;
	gap: 0.5rem;
}

.detail-item {
	margin-bottom: 0.8rem;
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
}

.detail-label {
	font-weight: 600;
	color: var(--dark);
	min-width: 140px;
}

.detail-value {
	color: var(--dark);
	flex: 1;
	text-align: right;
}

.skills-list {
	display: flex;
	flex-wrap: wrap;
	gap: 0.5rem;
	margin-top: 0.5rem;
}

.skill-tag {
	background: var(--secondary);
	color: white;
	padding: 0.3rem 0.8rem;
	border-radius: 15px;
	font-size: 0.8rem;
}

.resume-link {
	color: var(--secondary);
	text-decoration: none;
	font-weight: 600;
	display: inline-flex;
	align-items: center;
	gap: 0.5rem;
}

.resume-link:hover {
	text-decoration: underline;
}

/* Footer */
.footer {
	background: var(--primary);
	color: white;
	text-align: center;
	padding: 1.5rem;
	margin-top: 2rem;
}

/* Responsive */
@media ( max-width : 768px) {
	.main-container {
		margin: 1rem auto;
	}
	.page-title {
		font-size: 2rem;
	}
	.action-buttons {
		flex-direction: column;
	}
	.btn {
		width: 100%;
		justify-content: center;
	}
	.modal-content {
		width: 95%;
		margin: 10% auto;
	}
	.detail-item {
		flex-direction: column;
		align-items: flex-start;
	}
	.detail-value {
		text-align: left;
		margin-top: 0.3rem;
	}
}
</style>
</head>

<body>
	<!-- Header -->
	<header class="header">
		<div class="header-content">
			<a href="#" class="logo"> <i class="fas fa-briefcase"></i>JobPortal
			</a>
			<div class="header-actions">
				<a href="Recruiter.jsp" class="btn btn-back"> <i
					class="fas fa-arrow-left"></i> Back to Dashboard
				</a>
			</div>
		</div>
	</header>

	<!-- Main Content -->
	<div class="main-container">
		<h1 class="page-title">Job Applications</h1>

		<div class="applications-card">
			<div class="card-header">
				<div class="card-title">
					<span>All Applications</span> <span class="applications-count">
						<%
						List<Application> applications = (List<Application>) request.getAttribute("applications");
						if (applications != null) {
							out.print(applications.size());
						} else {
							out.print(0);
						}
						%> Applications
					</span>
				</div>
			</div>

			<div class="table-container">
				<%
				if (applications != null && !applications.isEmpty()) {
				%>
				<table class="applications-table">
					<thead>
						<tr>
							<th>Application ID</th>
							<th>Job Title</th>
							<th>Applicant Name</th>
							<th>Email</th>
							<th>Location</th>
							<th>Status</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Application app : applications) {
							String statusClass = "status-" + app.getStatus().toLowerCase();
						%>
						<tr>
							<td>#<%=app.getId()%></td>
							<td><strong><%=app.getJobTitle()%></strong></td>
							<td><%=app.getApplicantName()%></td>
							<td><%=app.getApplicantEmail()%></td>
							<td><%=app.getLocation() != null ? app.getLocation() : "N/A"%></td>
							<td><span class="status-badge <%=statusClass%>"> <%=app.getStatus()%>
							</span></td>
							<td>
								<div class="action-buttons">
									<!-- View Button -->
									<button class="btn btn-view"
										onclick="openApplicantModal('<%=app.getId()%>')">
										<i class="fas fa-eye"></i> View
									</button>

									<%
									String status = app.getStatus().toUpperCase();
									if ("PENDING".equals(status)) {
									%>
									<!-- Show shortlist/reject only if still pending -->
									<a
										href="RecruiterServlet?action=manageApplication&type=SHORTLISTED&applicationId=<%=app.getId()%>"
										class="btn btn-shortlist"> <i class="fas fa-check"></i>
										Shortlist
									</a> <a
										href="RecruiterServlet?action=manageApplication&type=REJECTED&applicationId=<%=app.getId()%>"
										class="btn btn-reject"
										onclick="return confirm('Are you sure you want to reject this application?');">
										<i class="fas fa-times"></i> Reject
									</a>
									<%
									} else if ("SHORTLISTED".equals(status)) {
									%>
									<!-- If already shortlisted -->
									<span class="status-badge status-shortlisted">Shortlisted</span>
									<%
									} else if ("REJECTED".equals(status)) {
									%>
									<!-- If already rejected -->
									<span class="status-badge status-rejected">Rejected</span>
									<%
									}
									%>
								</div>
							</td>

						</tr>
						<%
						}
						%>
					</tbody>
				</table>
				<%
				} else {
				%>
				<div class="no-data">
					<i class="fas fa-inbox"></i>
					<h3>No Applications Found</h3>
					<p>There are no job applications to display at the moment.</p>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer class="footer">
		<p>&copy; 2025 JobPortal. All Rights Reserved. | Making Hiring
			Easier</p>
	</footer>

	<!-- Modals -->
	<%
	if (applications != null && !applications.isEmpty()) {
		for (Application app : applications) {
			String statusClass = "status-" + app.getStatus().toLowerCase();
	%>
	<div id="modal_<%=app.getId()%>" class="modal">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title">
					<i class="fas fa-user"></i> Applicant Details -
					<%=app.getApplicantName()%>
				</h3>
				<button class="close-btn" onclick="closeModal('<%=app.getId()%>')">
					<i class="fas fa-times"></i>
				</button>
			</div>
			<div class="modal-body">
				<div class="applicant-details">
					<!-- Application Information -->
					<div class="detail-section">
						<h4 class="section-title">
							<i class="fas fa-info-circle"></i> Application Information
						</h4>
						<div class="detail-item">
							<span class="detail-label">Application ID:</span> <span
								class="detail-value">#<%=app.getId()%></span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Job Title:</span> <span
								class="detail-value"><%=app.getJobTitle()%></span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Status:</span> <span
								class="detail-value"> <span
								class="status-badge <%=statusClass%>"> <%=app.getStatus()%>
							</span>
							</span>
						</div>

						<div class="detail-item">
							<span class="detail-label">Job ID:</span> <span
								class="detail-value"><%=app.getJob_id()%></span>
						</div>
					</div>

					<!-- Applicant Information -->
					<div class="detail-section">
						<h4 class="section-title">
							<i class="fas fa-user-circle"></i> Applicant Information
						</h4>
						<div class="detail-item">
							<span class="detail-label">Full Name:</span> <span
								class="detail-value"><%=app.getApplicantName()%></span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Email:</span> <span
								class="detail-value"><%=app.getApplicantEmail()%></span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Applicant ID:</span> <span
								class="detail-value"><%=app.getApplicantId()%></span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Location:</span> <span
								class="detail-value"><%=app.getLocation() != null ? app.getLocation() : "N/A"%></span>
						</div>
					</div>

					<!-- Professional Details -->
					<div class="detail-section">
						<h4 class="section-title">
							<i class="fas fa-briefcase"></i> Professional Details
						</h4>
						<div class="detail-item">
							<span class="detail-label">Experience:</span> <span
								class="detail-value"> <%=app.getExperienceYears() != null ? app.getExperienceYears() + " years" : "Not specified"%>
							</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Skills:</span> <span
								class="detail-value"> <%
 if (app.getSkills() != null && !app.getSkills().trim().isEmpty()) {
 	String[] skills = app.getSkills().split(",");
 %>
								<div class="skills-list">
									<%
									for (String skill : skills) {
									%>
									<span class="skill-tag"><%=skill.trim()%></span>
									<%
									}
									%>
								</div> <%
 } else {
 %> Not specified <%
 }
 %>
							</span>
						</div>
					</div>
					<!-- Documents -->
					<div class="detail-section">
						<h4 class="section-title">
							<i class="fas fa-file-pdf"></i> Documents
						</h4>
						<div class="detail-item">
							<span class="detail-label">Resume:</span> <span
                            class="detail-value"> <%
							 String resumeLink = app.getResumeLink();
							 if (resumeLink != null && !resumeLink.trim().isEmpty()) {
							 %> <a href="<%=resumeLink%>" class="resume-link"
								target="_blank" download> <i class="fas fa-download"></i>
									Download Resume
							</a> <%
 } else {
 %> No resume uploaded <%
 }
 %>
							</span>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<%
	}
	}
	%>

	<script>
        function openApplicantModal(appId) {
            console.log('Opening modal for application:', appId);
            const modal = document.getElementById('modal_' + appId);
            if (modal) {
                modal.style.display = 'block';
                console.log('Modal found and displayed');
            } else {
                console.error('Modal not found for application:', appId);
            }
        }

        function closeModal(appId) {
            console.log('Closing modal for application:', appId);
            const modal = document.getElementById('modal_' + appId);
            if (modal) {
                modal.style.display = 'none';
            }
        }

        // Close modal when clicking outside
        window.onclick = function(event) {
            if (event.target.classList.contains('modal')) {
                event.target.style.display = 'none';
            }
        }

        // Close modal with Escape key
        document.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                const modals = document.querySelectorAll('.modal');
                modals.forEach(modal => {
                    modal.style.display = 'none';
                });
            }
        });
    </script>
</body>
</html>