<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jobportal.model.*" %>
<%
    // Check if user is logged in
    User jobSeeker = (User) session.getAttribute("user");
    if (jobSeeker == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Seeker Dashboard - Job Portal</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --primary: #3f51b5;
            --secondary: #f50057;
            --success: #4caf50;
            --info: #2196f3;
            --warning: #ff9800;
            --light: #f8f9fa;
            --dark: #212529;
            --sidebar-width: 250px;
            --header-height: 70px;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f7fb;
            overflow-x: hidden;
        }
        
        .dashboard-container {
            display: flex;
            min-height: 100vh;
        }
        
        /* Sidebar Styles */
        .sidebar {
            width: var(--sidebar-width);
            background: linear-gradient(180deg, var(--primary) 0%, #283593 100%);
            color: white;
            position: fixed;
            height: 100vh;
            overflow-y: auto;
            transition: all 0.3s;
            z-index: 1000;
        }
        
        .sidebar-header {
            padding: 20px 15px;
            border-bottom: 1px solid rgba(255,255,255,0.1);
            text-align: center;
        }
        
        .sidebar-header h3 {
            margin: 0;
            font-weight: 600;
        }
        
        .sidebar-menu {
            padding: 15px 0;
        }
        
        .sidebar-menu ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        
        .sidebar-menu li {
            margin-bottom: 5px;
        }
        
        .sidebar-menu a {
            display: flex;
            align-items: center;
            padding: 12px 20px;
            color: rgba(255,255,255,0.8);
            text-decoration: none;
            transition: all 0.3s;
        }
        
        .sidebar-menu a:hover, .sidebar-menu a.active {
            background-color: rgba(255,255,255,0.1);
            color: white;
            border-left: 4px solid var(--secondary);
        }
        
        .sidebar-menu i {
            margin-right: 10px;
            font-size: 18px;
            width: 24px;
            text-align: center;
        }
        
        /* Main Content Styles */
        .main-content {
            flex: 1;
            margin-left: var(--sidebar-width);
            padding: 20px;
        }
        
        .header {
            background-color: white;
            padding: 15px 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            margin-bottom: 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .search-bar {
            width: 400px;
            position: relative;
        }
        
        .search-bar input {
            padding-left: 40px;
            border-radius: 30px;
        }
        
        .search-bar i {
            position: absolute;
            left: 15px;
            top: 12px;
            color: #6c757d;
        }
        
        .user-profile {
            display: flex;
            align-items: center;
        }
        
        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: var(--primary);
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            margin-right: 10px;
        }
        
        /* Stats Cards */
        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
            gap: 20px;
            margin-bottom: 25px;
        }
        
        .stat-card {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            display: flex;
            align-items: center;
        }
        
        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
            font-size: 24px;
        }
        
        .stat-icon.applied {
            background-color: rgba(63, 81, 181, 0.1);
            color: var(--primary);
        }
        
        .stat-icon.saved {
            background-color: rgba(245, 0, 87, 0.1);
            color: var(--secondary);
        }
        
        .stat-icon.interview {
            background-color: rgba(76, 175, 80, 0.1);
            color: var(--success);
        }
        
        .stat-icon.profile {
            background-color: rgba(33, 150, 243, 0.1);
            color: var(--info);
        }
        
        .stat-info h3 {
            margin: 0;
            font-size: 24px;
            font-weight: 600;
        }
        
        .stat-info p {
            margin: 0;
            color: #6c757d;
        }
        
        /* Alert Messages */
        .alert {
            border-radius: 10px;
            margin-bottom: 20px;
        }
        
        /* Resume Section */
        .resume-section {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        
        .resume-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        
        .resume-actions {
            display: flex;
            gap: 10px;
        }
        
        .resume-preview {
            border: 1px dashed #dee2e6;
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            margin-bottom: 20px;
            background-color: #f8f9fa;
        }
        
        .resume-preview i {
            font-size: 48px;
            color: #6c757d;
            margin-bottom: 10px;
        }
        
        .resume-upload-form {
            display: none;
            margin-top: 20px;
        }
        
        .resume-edit-form {
            display: none;
            margin-top: 20px;
        }
        
        /* Responsive */
        @media (max-width: 992px) {
            .sidebar {
                width: 70px;
                text-align: center;
            }
            
            .sidebar-header h3, .sidebar-menu span {
                display: none;
            }
            
            .sidebar-menu i {
                margin-right: 0;
                font-size: 20px;
            }
            
            .main-content {
                margin-left: 70px;
            }
            
            .search-bar {
                width: 300px;
            }
        }
        
        @media (max-width: 768px) {
            .sidebar {
                width: 0;
            }
            
            .main-content {
                margin-left: 0;
            }
            
            .header {
                flex-direction: column;
                align-items: flex-start;
            }
            
            .search-bar {
                width: 100%;
                margin-bottom: 15px;
            }
            
            .stats-container {
                grid-template-columns: 1fr;
            }
            
            .resume-header {
                flex-direction: column;
                align-items: flex-start;
            }
            
            .resume-actions {
                margin-top: 15px;
                width: 100%;
                justify-content: space-between;
            }
        }
        
        /* Toggle Button for Mobile */
        .menu-toggle {
            display: none;
            background: none;
            border: none;
            font-size: 24px;
            color: var(--primary);
            margin-right: 15px;
        }
        
        @media (max-width: 768px) {
            .menu-toggle {
                display: block;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-header">
                <h3>JobPortal</h3>
            </div>
            <nav class="sidebar-menu">
                <ul>
                    <li><a href="#" class="active"><i class="fas fa-home"></i> <span>Dashboard</span></a></li>
                    <li><a href="#"><i class="fas fa-search"></i> <span>Find Jobs</span></a></li>
                    <li><a href="#"><i class="fas fa-briefcase"></i> <span>Applied Jobs</span></a></li>
                    <li><a href="#"><i class="fas fa-bookmark"></i> <span>Saved Jobs</span></a></li>
                    <li><a href="#"><i class="fas fa-calendar-check"></i> <span>Interviews</span></a></li>
                    <li><a href="#" id="resume-link"><i class="fas fa-file-alt"></i> <span>Resume</span></a></li>
                    <li><a href="#"><i class="fas fa-bell"></i> <span>Notifications</span></a></li>
                    <li><a href="#"><i class="fas fa-cog"></i> <span>Settings</span></a></li>
                    <li><a href="#"><i class="fas fa-question-circle"></i> <span>Help & Support</span></a></li>
                    <li><a href="<%= request.getContextPath() %>/logout"><i class="fas fa-sign-out-alt"></i> <span>Logout</span></a></li>
                </ul>
            </nav>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Header -->
            <div class="header">
                <button class="menu-toggle">
                    <i class="fas fa-bars"></i>
                </button>
                <div class="search-bar">
                    <i class="fas fa-search"></i>
                    <input type="text" class="form-control" placeholder="Search for jobs, companies, or keywords" id="job-search">
                </div>
                <div class="user-profile">
                    <div class="user-avatar">JS</div>
                    <div>
                        <div class="fw-bold" id="user-name">
                            <%= user.getFullName() != null ? user.getFullName() : "Job Seeker" %>
                        </div>
                        <div class="small text-muted">Job Seeker</div>
                    </div>
                </div>
            </div>

            <!-- Alert Messages -->
            <% if (request.getAttribute("message") != null) { %>
                <div class="alert alert-<%= request.getAttribute("messageType") != null ? request.getAttribute("messageType") : "info" %> alert-dismissible fade show" role="alert">
                    <%= request.getAttribute("message") %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>

            <!-- Validation Errors -->
            <% 
                List<String> errors = (List<String>) request.getAttribute("errors");
                if (errors != null && !errors.isEmpty()) { 
                    for (String error : errors) { %>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <%= error %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
            <%      }
                } 
            %>

            <!-- Stats Cards -->
            <div class="stats-container">
                <div class="stat-card">
                    <div class="stat-icon applied">
                        <i class="fas fa-paper-plane"></i>
                    </div>
                    <div class="stat-info">
                        <h3 id="applied-count">
                            <%= request.getAttribute("appliedJobsCount") != null ? request.getAttribute("appliedJobsCount") : "0" %>
                        </h3>
                        <p>Jobs Applied</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon saved">
                        <i class="fas fa-bookmark"></i>
                    </div>
                    <div class="stat-info">
                        <h3 id="saved-count">
                            <%= request.getAttribute("savedJobsCount") != null ? request.getAttribute("savedJobsCount") : "0" %>
                        </h3>
                        <p>Saved Jobs</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon interview">
                        <i class="fas fa-calendar-alt"></i>
                    </div>
                    <div class="stat-info">
                        <h3 id="interview-count">
                            <%= request.getAttribute("interviewCount") != null ? request.getAttribute("interviewCount") : "0" %>
                        </h3>
                        <p>Interviews</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon profile">
                        <i class="fas fa-user-check"></i>
                    </div>
                    <div class="stat-info">
                        <h3 id="profile-strength">
                            <%= request.getAttribute("profileStrength") != null ? request.getAttribute("profileStrength") + "%" : "0%" %>
                        </h3>
                        <p>Profile Strength</p>
                    </div>
                </div>
            </div>


            <!-- Resume Section -->
            <div class="resume-section" id="resume-section">
                <div class="resume-header">
                    <h4 class="section-title">My Resume</h4>
                    <div class="resume-actions">
                        <button class="btn btn-primary" id="upload-resume-btn">
                            <i class="fas fa-upload"></i> Upload Resume
                        </button>
                        <button class="btn btn-outline-primary" id="edit-resume-btn">
                            <i class="fas fa-edit"></i> Edit Resume
                        </button>
                        <% 
                            Resume resume = (Resume) request.getAttribute("resume");
                            if (resume != null) { 
                        %>
                            <button class="btn btn-danger" id="delete-resume-btn">
                                <i class="fas fa-trash"></i> Delete Resume
                            </button>
                        <% } %>
                    </div>
                </div>
                
                
                <!-- Resume Preview -->
                <% if (resume != null) { %>
                    <div class="resume-preview" id="resume-preview">
                        <i class="fas fa-file-pdf"></i>
                        <h5 id="resume-file-name"><%= resume.getTitle() != null ? resume.getTitle() : "Resume" %></h5>
                        <p class="text-muted" id="resume-upload-date">
                            Uploaded on: <%= resume.getUploadDate() != null ? resume.getUploadDate() : "N/A" %>
                        </p>
                        <p class="text-muted">File Size: <%= resume.getFileSize() > 0 ? String.format("%.2f", resume.getFileSize()) : "0.00" %> MB</p>
                        <a href="<%= resume.getFileUrl() != null ? resume.getFileUrl() : "#" %>" 
                           target="_blank" class="btn btn-success mt-2" 
                           <%= resume.getFileUrl() == null ? "disabled" : "" %>>
                            <i class="fas fa-download"></i> Download Resume
                        </a>
                    </div>
                <% } else { %>
                    <div class="resume-preview" id="resume-preview">
                        <i class="fas fa-file-pdf"></i>
                        <h5 id="resume-file-name">No resume uploaded</h5>
                        <p class="text-muted" id="resume-upload-date">Upload your resume to apply for jobs faster</p>
                    </div>
                <% } %>
                
                <!-- Resume Upload Form -->
                <div class="resume-upload-form" id="resume-upload-form">
                    <form id="uploadForm" action="<%= request.getContextPath() %>/job-seeker?action=uploadResume" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="resume-file" class="form-label">Select Resume File (PDF, DOC, DOCX) - Max 5MB</label>
                            <input class="form-control" type="file" id="resume-file" name="resumeFile" accept=".pdf,.doc,.docx">
                        </div>
                        <div class="mb-3">
                            <label for="resume-title" class="form-label">Resume Title</label>
                            <input type="text" class="form-control" id="resume-title" name="resumeTitle" 
                                   value="<%= request.getParameter("resumeTitle") != null ? request.getParameter("resumeTitle") : "" %>" 
                                   placeholder="e.g., Senior Developer Resume">
                        </div>
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-save"></i> Upload Resume
                            </button>
                            <button type="button" class="btn btn-secondary" id="cancel-upload">
                                Cancel
                            </button>
                        </div>
                    </form>
                </div>
                
                <!-- Resume Edit Form -->
                <div class="resume-edit-form" id="resume-edit-form">
                    <form id="editResumeForm" action="<%= request.getContextPath() %>/job-seeker?action=updateProfile" method="post">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="full-name" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="full-name" name="fullName" 
                                       value="<%= request.getParameter("fullName") != null ? request.getParameter("fullName") : (jobSeeker.getFullName() != null ? jobSeeker.getFullName() : "") %>">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="<%= request.getParameter("email") != null ? request.getParameter("email") : (jobSeeker.getEmail() != null ? jobSeeker.getEmail() : "") %>">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="phone" class="form-label">Phone</label>
                                <input type="tel" class="form-control" id="phone" name="phone" 
                                       value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : (jobSeeker.getPhone() != null ? jobSeeker.getPhone() : "") %>">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="location" class="form-label">Location</label>
                                <input type="text" class="form-control" id="location" name="location" 
                                       value="<%= request.getParameter("location") != null ? request.getParameter("location") : (jobSeeker.getLocation() != null ? jobSeeker.getLocation() : "") %>">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="summary" class="form-label">Professional Summary</label>
                            <textarea class="form-control" id="summary" name="summary" rows="3"><%= request.getParameter("summary") != null ? request.getParameter("summary") : (jobSeeker.getSummary() != null ? jobSeeker.getSummary() : "") %></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="skills" class="form-label">Skills (comma separated)</label>
                            <input type="text" class="form-control" id="skills" name="skills" 
                                   value="<%= request.getParameter("skills") != null ? request.getParameter("skills") : (jobSeeker.getSkills() != null ? jobSeeker.getSkills() : "") %>" 
                                   placeholder="e.g., Java, Spring Boot, MySQL">
                        </div>
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-save"></i> Save Changes
                            </button>
                            <button type="button" class="btn btn-secondary" id="cancel-edit">
                                Cancel
                            </button>
                        </div>
                    </form>
                </div>
                
                <!-- Delete Resume Form (Hidden) -->
                <% if (resume != null) { %>
                    <form id="deleteResumeForm" action="<%= request.getContextPath() %>/job-seeker?action=deleteResume" method="post" style="display: none;">
                        <input type="hidden" name="resumeId" value="<%= resume.getId() %>">
                    </form>
                <% } %>
            </div>

            <!-- Recent Applications -->
            <div class="section-title">Recent Applications</div>
            <div class="job-listings" id="recent-applications">
                <% 
                    List<JobApplication> recentApplications = (List<JobApplication>) request.getAttribute("recentApplications");
                    if (recentApplications != null && !recentApplications.isEmpty()) { 
                %>
                    <% for (JobApplication application : recentApplications) { %>
                        <div class="job-card">
                            <div class="job-header">
                                <div>
                                    <div class="job-title"><%= application.getJobTitle() != null ? application.getJobTitle() : "N/A" %></div>
                                    <div class="job-company"><%= application.getCompanyName() != null ? application.getCompanyName() : "N/A" %></div>
                                </div>
                                <div class="job-type"><%= application.getJobType() != null ? application.getJobType() : "N/A" %></div>
                            </div>
                            <div class="job-details">
                                <div class="job-detail"><i class="fas fa-map-marker-alt"></i> <%= application.getLocation() != null ? application.getLocation() : "N/A" %></div>
                                <div class="job-detail"><i class="fas fa-dollar-sign"></i> <%= application.getSalary() != null ? application.getSalary() : "N/A" %></div>
                                <div class="job-detail"><i class="fas fa-clock"></i> Applied on: <%= application.getApplicationDate() != null ? application.getApplicationDate() : "N/A" %></div>
                            </div>
                            <div class="job-details">
                                <div class="job-detail"><i class="fas fa-info-circle"></i> Status: 
                                    <span class="badge bg-<%= 
                                        application.getStatus() != null ? 
                                        (application.getStatus().equals("Pending") ? "warning" : 
                                         application.getStatus().equals("Accepted") ? "success" : "danger") : "secondary" %>">
                                        <%= application.getStatus() != null ? application.getStatus() : "Unknown" %>
                                    </span>
                                </div>
                            </div>
                        </div>
                    <% } %>
                <% } else { %>
                    <div class="alert alert-info">
                        No recent applications.
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
</body>
</html>