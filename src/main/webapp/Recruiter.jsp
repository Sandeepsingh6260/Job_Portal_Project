<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.jobportal.model.User" %>
<%@ page import="com.jobportal.daoimpl.ApplicationDaoImpl" %>
<%@ page import="com.jobportal.model.Application" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>

<%
    // Session Check
    User user = (User) session.getAttribute("session");

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
    response.setHeader("Pragma", "no-cache"); 
    response.setHeader("Expires", "0"); 

    if (user == null) {
        response.sendRedirect("auth/login.jsp");
        return;
    }
    
    // Get dashboard counts from database
    ApplicationDaoImpl applicationDao = new ApplicationDaoImpl();
    Map<String, Integer> counts = applicationDao.getDashboardCounts(user.getUser_id());
    
    int jobCount = counts.getOrDefault("job_count", 0);
    int applicationCount = counts.getOrDefault("application_count", 0);
    int shortlistedCount = counts.getOrDefault("shortlisted_count", 0);
    int rejectedCount = counts.getOrDefault("rejected_count", 0);
    int todayApplications = applicationDao.countTodaysApplications(user.getUser_id());
    
    // Get recent applications
    List<Application> recentApplications = applicationDao.getApplicationsById(user.getUser_id());
%>
    
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Recruiter Dashboard - Job Portal</title>
<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no'
    name='viewport' />
 <jsp:include page="./include/head.jsp" />
  
 </head>
<body>
        <jsp:include page="./include/header.jsp"></jsp:include>
    <div class="wrapper">
        <!-- Sidebar -->
        <jsp:include page="./include/sidebar.jsp" />
        <!-- End Sidebar -->
        
        
        <!-- Main Content -->
        <div class="main-panel">
            <div class="content">
                <div class="page-inner">
                    <div class="page-header">
                        <h4 class="page-title">Recruiter Dashboard</h4>
                        <div class="btn-group btn-group-page-header ml-auto">
                            <button type="button" class="btn btn-light btn-round btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fa fa-ellipsis-h"></i>
                            </button>
                            <div class="dropdown-menu">
                                <div class="arrow"></div>
                                <a class="dropdown-item" href="JobPost.jsp">Post New Job</a>
                                <a class="dropdown-item" href="./RecruiterServlet?action=managejob">Manage Jobs</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="./RecruiterServlet?action=viewApplications">View Applications</a>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Dashboard Stats -->
                    <div class="row">
                        <div class="col-sm-6 col-md-3">
                            <div class="card card-stats card-round">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-icon">
                                            <div class="icon-big text-center icon-primary bubble-shadow-small">
                                                <i class="fas fa-briefcase"></i>
                                            </div>
                                        </div>
                                        <div class="col col-stats ml-3 ml-sm-0">
                                            <div class="numbers">
                                                <p class="card-category">Job Posts</p>
                                                <h4 class="card-title"><%= jobCount %></h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-3">
                            <div class="card card-stats card-round">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-icon">
                                            <div class="icon-big text-center icon-info bubble-shadow-small">
                                                <i class="fas fa-file-alt"></i>
                                            </div>
                                        </div>
                                        <div class="col col-stats ml-3 ml-sm-0">
                                            <div class="numbers">
                                                <p class="card-category">Applications</p>
                                                <h4 class="card-title"><%= applicationCount %></h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-3">
                            <div class="card card-stats card-round">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-icon">
                                            <div class="icon-big text-center icon-success bubble-shadow-small">
                                                <i class="fas fa-user-check"></i>
                                            </div>
                                        </div>
                                        <div class="col col-stats ml-3 ml-sm-0">
                                            <div class="numbers">
                                                <p class="card-category">Shortlisted</p>
                                                <h4 class="card-title"><%= shortlistedCount %></h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-3">
                            <div class="card card-stats card-round">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-icon">
                                            <div class="icon-big text-center icon-danger bubble-shadow-small">
                                                <i class="fas fa-user-times"></i>
                                            </div>
                                        </div>
                                        <div class="col col-stats ml-3 ml-sm-0">
                                            <div class="numbers">
                                                <p class="card-category">Rejected</p>
                                                <h4 class="card-title"><%= rejectedCount %></h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Recent Activity and Quick Actions -->
                    <div class="row">
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-header">
                                    <div class="card-head-row">
                                        <h4 class="card-title">Recent Applications</h4>
                                        <div class="card-tools">
                                            <a href="./RecruiterServlet?action=viewApplications" class="btn btn-info btn-border btn-round btn-sm">
                                                View All
                                                <span class="btn-label">
                                                    <i class="fa fa-arrow-right"></i>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped mt-3">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Candidate</th>
                                                    <th scope="col">Position</th>
                                                    <th scope="col">Applied Date</th>
                                                    <th scope="col">Status</th>
                                                    <th scope="col">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    int displayCount = 0;
                                                    for(Application app : recentApplications) {
                                                        if(displayCount >= 5) break;
                                                        displayCount++;
                                                %>
                                                <tr>
                                                    <td><%= app.getApplicantName() != null ? app.getApplicantName() : "N/A" %></td>
                                                    <td><%= app.getJobTitle() != null ? app.getJobTitle() : "N/A" %></td>
                                                    <td>
                                                        <%
                                                            if(app.getCreated_at() != null) {
                                                                String createdDate = app.getCreated_at().toString().substring(0, 10);
                                                        %>
                                                                <%= createdDate %>
                                                        <%
                                                            } else {
                                                        %>
                                                                N/A
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <td>
                                                        <span class="badge 
                                                            <% if("SHORTLISTED".equals(app.getStatus())) { %>
                                                                badge-success
                                                            <% } else if("REJECTED".equals(app.getStatus())) { %>
                                                                badge-danger
                                                            <% } else { %>
                                                                badge-warning
                                                            <% } %>">
                                                            <%= app.getStatus() != null ? app.getStatus() : "PENDING" %>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <a href="./RecruiterServlet?action=viewApplications" class="btn btn-primary btn-sm">Review</a>
                                                    </td>
                                                </tr>
                                                <%
                                                    }
                                                    
                                                    if(displayCount == 0) {
                                                %>
                                                <tr>
                                                    <td colspan="5" class="text-center">No applications found</td>
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
                        
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Quick Actions</h4>
                                </div>
                                <div class="card-body">
                                    <div class="d-grid gap-2">
                                        <a href="JobPost.jsp" class="btn btn-primary btn-lg btn-block">
                                            <i class="fas fa-plus-circle"></i> Post New Job
                                        </a>
                                        <a href="./RecruiterServlet?action=managejob" class="btn btn-secondary btn-lg btn-block">
                                            <i class="fas fa-tasks"></i> Manage Jobs
                                        </a>
                                        <a href="./RecruiterServlet?action=viewApplications" class="btn btn-info btn-lg btn-block">
                                            <i class="fas fa-file-alt"></i> View Applications
                                        </a>
                              
                                    </div>
                                </div>
                            </div>
                            
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Job Post Statistics</h4>
                                </div>
                                <div class="card-body">
                                    <div id="jobStatsChart">
                                        <div class="text-center p-4">
                                            <i class="fas fa-chart-pie fa-3x text-muted mb-3"></i>
                                            <p class="text-muted">Job statistics chart would appear here</p>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Active Jobs</span>
                                            <span class="font-weight-bold"><%= jobCount %></span>
                                        </div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Applications Today</span>
                                            <span class="font-weight-bold"><%= todayApplications %></span>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <span>Conversion Rate</span>
                                            <span class="font-weight-bold">
                                                <%
                                                    double conversionRate = 0;
                                                    if(applicationCount > 0) {
                                                        conversionRate = ((double) shortlistedCount / applicationCount) * 100;
                                                    }
                                                %>
                                                <%= String.format("%.1f", conversionRate) %>%
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        
        <!-- End Main Content -->
        
        <jsp:include page="./include/footer.jsp" /> </div>   
    </div>
    <jsp:include page="./include/scripts.jsp"></jsp:include>
</body>
</html>