<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.jobportal.model.Job"%>
<%
    // Fetch jobs with better null handling
    List<Job> jobs = (List<Job>) request.getAttribute("jobs");
    if (jobs == null) {
        jobs = new ArrayList<>();
        System.out.println("Jobs list is null in JSP - using empty list");
    }

    Job updateJob = (Job) request.getAttribute("updateJob");
    
    // Pagination variables
    int currentPage = 1;
    int recordsPerPage = 5;
    if(request.getParameter("page") != null) {
        try {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
    }

    int startIndex = (currentPage - 1) * recordsPerPage;
    int endIndex = Math.min(startIndex + recordsPerPage, jobs.size());
    int totalPages = (int) Math.ceil(jobs.size() * 1.0 / recordsPerPage);
    
    // Search variables
    String searchKeyword = (String) request.getAttribute("searchKeyword");
    String searchType = (String) request.getAttribute("searchType");
    Integer totalJobs = (Integer) request.getAttribute("totalJobs");
    if (totalJobs == null) {
        totalJobs = jobs.size();
    }
    
    // Debug output
    System.out.println("JSP - Jobs list size: " + jobs.size());
    System.out.println("JSP - Current page: " + currentPage);
    System.out.println("JSP - Total pages: " + totalPages);
    System.out.println("JSP - Search keyword: " + searchKeyword);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Jobs</title>
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script src="assets/js/core/jquery-3.7.1.min.js"></script>
<script src="assets/js/core/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-4">
    <!-- Success/Error Messages -->
    <%
        String successMsg = (String) session.getAttribute("successMsg");
        String errorMsg = (String) session.getAttribute("errorMsg");
        
        if (successMsg != null) {
    %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle"></i> <%= successMsg %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <%
            session.removeAttribute("successMsg");
        }
        
        if (errorMsg != null) {
    %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-circle"></i> <%= errorMsg %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <%
            session.removeAttribute("errorMsg");
        }
    %>

    <div class="card">
        <div class="card-header">
            <div class="d-flex justify-content-between align-items-center">
                <h4 class="card-title mb-0">
                    <i class="fas fa-briefcase me-2"></i>Manage Jobs
                </h4>
                <a href="./Recruiter.jsp" class="btn btn-secondary">
                    <i class="fas fa-arrow-left me-1"></i> Back to Dashboard
                </a>
            </div>
        </div>
        <div class="card-body">
            <!-- Search Form -->
            <form action="RecruiterServlet" method="get" class="mb-4">
                <div class="row">
                    <div class="col-md-8">
                        <div class="input-group">
                            <input type="text" class="form-control" name="keyword" 
                                   placeholder="Search jobs by title, description, location, job type, or experience..." 
                                   value="<%= searchKeyword != null ? searchKeyword : "" %>">
                            <input type="hidden" name="action" value="searchjobs">
                            <button class="btn btn-primary" type="submit">
                                <i class="fas fa-search me-1"></i> Search
                            </button>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="d-flex gap-2">
                            <a href="RecruiterServlet?action=managejob" class="btn btn-outline-secondary">
                                <i class="fas fa-refresh me-1"></i> Clear Search
                            </a>
                            <a href="JobPost.jsp" class="btn btn-success">
                                <i class="fas fa-plus-circle me-1"></i> Post New Job
                            </a>
                        </div>
                    </div>
                </div>
            </form>

            <!-- Search Results Info -->
            <% if (searchKeyword != null && !searchKeyword.trim().isEmpty()) { %>
                <div class="alert alert-info alert-dismissible fade show">
                    <i class="fas fa-info-circle me-2"></i> 
                    Search results for: "<strong><%= searchKeyword %></strong>" 
                    | Found: <strong><%= totalJobs %></strong> job<%= totalJobs != 1 ? "s" : "" %>
                    <a href="RecruiterServlet?action=managejob" class="btn btn-sm btn-outline-info float-end">
                        <i class="fas fa-times me-1"></i> Clear
                    </a>
                </div>
            <% } %>

            <!-- Jobs Count -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h5 class="text-muted">
                    Total Jobs: <span class="badge bg-primary"><%= totalJobs %></span>
                </h5>
                <% if (jobs.size() > 0) { %>
                    <small class="text-muted">
                        Showing <%= startIndex + 1 %> to <%= Math.min(endIndex, jobs.size()) %> of <%= totalJobs %> entries
                    </small>
                <% } %>
            </div>

            <!-- Jobs Table -->
            <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th width="50">Sr. No</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Location</th>
                            <th>Salary</th>
                            <th>Job Type</th>
                            <th>Experience</th>
                            <th>Mobile No</th>
                            <th width="150">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        int srNo = startIndex + 1;
                        for(int i = startIndex; i < endIndex; i++) {
                            Job job = jobs.get(i);
                    %>
                        <tr>
                            <td class="text-center"><%= srNo++ %></td>
                            <td><strong><%= job.getTitle() != null ? job.getTitle() : "N/A" %></strong></td>
                            <td>
                                <%= job.getDescription() != null ? 
                                    job.getDescription().length() > 50 ? 
                                    job.getDescription().substring(0, 50) + "..." : 
                                    job.getDescription() : "No description" %>
                            </td>
                            <td><%= job.getLocation() != null ? job.getLocation() : "N/A" %></td>
                            <td>$<%= String.format("%.2f", job.getSalary()) %></td>
                            <td>
                                <span class="badge bg-info"><%= job.getJob_type() != null ? job.getJob_type() : "N/A" %></span>
                            </td>
                            <td><%= job.getExperience_required() != null ? job.getExperience_required() : "N/A" %></td>
                            <td><%= job.getMobile_no() != null ? job.getMobile_no() : "N/A" %></td>
                            <td>
                                <div class="btn-group btn-group-sm" role="group">
                                    <!-- Update button -->
                                    <form method="get" action="./RecruiterServlet" style="display:inline;">
                                        <input type="hidden" name="job_id" value="<%= job.getId() %>">
                                        <input type="hidden" name="action" value="edit">
                                        <button type="submit" class="btn btn-warning btn-sm" title="Edit Job">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                    </form>

                                    <!-- Delete button -->
                                    <form method="post" action="./RecruiterServlet" style="display:inline;">
                                        <input type="hidden" name="job_id" value="<%= job.getId() %>">
                                        <input type="hidden" name="action" value="delete">
                                        <button type="submit" class="btn btn-danger btn-sm" 
                                                onclick="return confirm('Are you sure you want to delete this job? This action cannot be undone.')" 
                                                title="Delete Job">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    <%
                        }
                        if(jobs.isEmpty()) {
                    %>
                        <tr>
                            <td colspan="9" class="text-center py-4">
                                <div class="alert alert-info mb-0">
                                    <i class="fas fa-info-circle fa-2x mb-3"></i>
                                    <h5>No Jobs Found</h5>
                                    <% if (searchKeyword != null && !searchKeyword.trim().isEmpty()) { %>
                                        <p class="mb-2">No jobs found matching your search criteria.</p>
                                        <a href="RecruiterServlet?action=managejob" class="btn btn-primary btn-sm me-2">
                                            Show All Jobs
                                        </a>
                                    <% } else { %>
                                        <p class="mb-2">You haven't posted any jobs yet.</p>
                                    <% } %>
                                    <a href="JobPost.jsp" class="btn btn-success btn-sm">
                                        <i class="fas fa-plus-circle me-1"></i> Post Your First Job
                                    </a>
                                </div>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>

            <!-- Pagination -->
            <% if (totalPages > 1) { %>
            <nav aria-label="Jobs pagination">
                <ul class="pagination justify-content-center">
                    <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                        <a class="page-link" href="RecruiterServlet?action=<%= searchKeyword != null ? "searchjobs&keyword=" + java.net.URLEncoder.encode(searchKeyword, "UTF-8") : "managejob" %>&page=<%=currentPage-1%>">
                            <i class="fas fa-chevron-left me-1"></i> Previous
                        </a>
                    </li>
                    <% 
                        int startPage = Math.max(1, currentPage - 2);
                        int endPage = Math.min(totalPages, currentPage + 2);
                        
                        if (startPage > 1) {
                    %>
                        <li class="page-item">
                            <a class="page-link" href="RecruiterServlet?action=<%= searchKeyword != null ? "searchjobs&keyword=" + java.net.URLEncoder.encode(searchKeyword, "UTF-8") : "managejob" %>&page=1">1</a>
                        </li>
                        <% if (startPage > 2) { %>
                            <li class="page-item disabled">
                                <span class="page-link">...</span>
                            </li>
                        <% } %>
                    <% } %>
                    
                    <% for(int p = startPage; p <= endPage; p++) { %>
                        <li class="page-item <%= (p == currentPage) ? "active" : "" %>">
                            <a class="page-link" href="RecruiterServlet?action=<%= searchKeyword != null ? "searchjobs&keyword=" + java.net.URLEncoder.encode(searchKeyword, "UTF-8") : "managejob" %>&page=<%=p%>"><%=p%></a>
                        </li>
                    <% } %>
                    
                    <% if (endPage < totalPages) { %>
                        <% if (endPage < totalPages - 1) { %>
                            <li class="page-item disabled">
                                <span class="page-link">...</span>
                            </li>
                        <% } %>
                        <li class="page-item">
                            <a class="page-link" href="RecruiterServlet?action=<%= searchKeyword != null ? "searchjobs&keyword=" + java.net.URLEncoder.encode(searchKeyword, "UTF-8") : "managejob" %>&page=<%=totalPages%>"><%=totalPages%></a>
                        </li>
                    <% } %>
                    
                    <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                        <a class="page-link" href="RecruiterServlet?action=<%= searchKeyword != null ? "searchjobs&keyword=" + java.net.URLEncoder.encode(searchKeyword, "UTF-8") : "managejob" %>&page=<%=currentPage+1%>">
                            Next <i class="fas fa-chevron-right ms-1"></i>
                        </a>
                    </li>
                </ul>
            </nav>
            <% } %>
        </div>
    </div>

    <!-- Update form -->
    <% if(updateJob != null) { %>
    <div class="card mt-4">
        <div class="card-header bg-warning text-dark">
            <h4 class="mb-0">
                <i class="fas fa-edit me-2"></i>Update Job: <%=updateJob.getTitle()%>
            </h4>
        </div>
        <div class="card-body">
            <form method="post" action="./RecruiterServlet">
                <input type="hidden" name="job_id" value="<%=updateJob.getId()%>">
                <input type="hidden" name="action" value="update">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Job Title</label>
                        <input type="text" name="title" class="form-control" value="<%=updateJob.getTitle()%>" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Location</label>
                        <input type="text" name="location" class="form-control" value="<%=updateJob.getLocation()%>" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Job Description</label>
                    <textarea name="description" class="form-control" rows="4" required><%=updateJob.getDescription()%></textarea>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Salary ($)</label>
                        <input type="number" name="salary" step="0.01" class="form-control" value="<%=updateJob.getSalary()%>" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Job Type</label>
                        <select name="job_type" class="form-control" required>
                            <option value="Full-time" <%= "Full-time".equals(updateJob.getJob_type()) ? "selected" : "" %>>Full-time</option>
                            <option value="Part-time" <%= "Part-time".equals(updateJob.getJob_type()) ? "selected" : "" %>>Part-time</option>
                            <option value="Contract" <%= "Contract".equals(updateJob.getJob_type()) ? "selected" : "" %>>Contract</option>
                            <option value="Remote" <%= "Remote".equals(updateJob.getJob_type()) ? "selected" : "" %>>Remote</option>
                            <option value="Internship" <%= "Internship".equals(updateJob.getJob_type()) ? "selected" : "" %>>Internship</option>
                        </select>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Experience Required</label>
                        <input type="text" name="experience_required" class="form-control" 
                               placeholder="e.g., 2-3 years, Fresher, Senior level" 
                               value="<%=updateJob.getExperience_required()%>" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Contact Mobile No</label>
                    <input type="text" name="mobile_no" class="form-control" 
                           placeholder="e.g., +1 234 567 8900" 
                           value="<%=updateJob.getMobile_no()%>" required>
                </div>
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-success">
                        <i class="fas fa-save me-1"></i> Save Changes
                    </button>
                    <a href="./RecruiterServlet?action=managejob" class="btn btn-secondary">
                        <i class="fas fa-times me-1"></i> Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>
    <% } %>
</div>

<script>
// Auto-dismiss alerts after 5 seconds
$(document).ready(function() {
    setTimeout(function() {
        $('.alert').alert('close');
    }, 5000);
    
    // Focus on search input when page loads if there's a search keyword
    <% if (searchKeyword != null && !searchKeyword.trim().isEmpty()) { %>
        $('input[name="keyword"]').focus();
    <% } %>
});
</script>

</body>
</html>