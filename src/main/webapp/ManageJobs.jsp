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
    
    // Debug output
    
    System.out.println("JSP - Jobs list size: " + jobs.size());
    System.out.println("JSP - Current page: " + currentPage);
    System.out.println("JSP - Total pages: " + totalPages);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Jobs</title>
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
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
            <%= successMsg %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <%
            session.removeAttribute("successMsg");
        }
        
        if (errorMsg != null) {
    %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= errorMsg %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <%
            session.removeAttribute("errorMsg");
        }
    %>

    <div class="d-flex justify-content-between align-items-center">
        <h3>Manage Jobs</h3>
        <!-- Back Button -->
        <a href="Recruiter.jsp" class="btn btn-secondary">Back to Dashboard</a>
    </div>
    
    <table class="table table-bordered table-striped mt-3">
        <thead>
            <tr>
                <th>Sr. No</th>
                <th>Title</th>
                <th>Description</th>
                <th>Location</th>
                <th>Salary</th>
                <th>Job Type</th>
                <th>Experience</th>
                <th>Mobile No</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <%
            int srNo = startIndex + 1;
            for(int i = 0; i < jobs.size(); i++) {
                Job job = jobs.get(i);
        %>
            <tr>
                <td><%= srNo++ %></td>
                <td><%= job.getTitle() %></td>
                <td><%= job.getDescription() != null ? job.getDescription().length() > 50 ? 
                      job.getDescription().substring(0, 50) + "..." : job.getDescription() : "" %></td>
                <td><%= job.getLocation() %></td>
                <td>$<%= String.format("%.2f", job.getSalary()) %></td>
                <td><%= job.getJob_type() %></td>
                <td><%= job.getExperience_required() %></td>
                <td><%= job.getMobile_no() %></td>
                <td>
                    <!-- Update button -->
                    <form method="get" action="./RecruiterServlet" style="display:inline;">
                        <input type="hidden" name="job_id" value="<%=job.getId()%>">
                        <input type="hidden" name="action" value="edit">
                        <button type="submit" class="btn btn-sm btn-warning">Update</button>
                    </form>

                    <!-- Delete button -->
                    <form method="post" action="./RecruiterServlet" style="display:inline;">
                        <input type="hidden" name="job_id" value="<%=job.getId()%>">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit" class="btn btn-sm btn-danger" 
                                onclick="return confirm('Are you sure you want to delete this job?')">Delete</button>
                    </form>
                </td>
            </tr>
        <%
            }
            if(jobs.isEmpty()) {
        %>
            <tr>
                <td colspan="9" class="text-center">
                    <div class="alert alert-info">
                        No Jobs Found. <a href="JobPost.jsp" class="alert-link">Post your first job!</a>
                    </div>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <!-- Pagination -->
    <% if (totalPages > 1) { %>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                <a class="page-link" href="RecruiterServlet?action=managejob&page=<%=currentPage-1%>">Previous</a>
            </li>
            <% for(int p = 1; p <= totalPages; p++) { %>
                <li class="page-item <%= (p == currentPage) ? "active" : "" %>">
                    <a class="page-link" href="RecruiterServlet?action=managejob&page=<%=p%>"><%=p%></a>
                </li>
            <% } %>
            <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                <a class="page-link" href="RecruiterServlet?action=managejob&page=<%=currentPage+1%>">Next</a>
            </li>
        </ul>
    </nav>
    <% } %>

    <!-- Update form -->
    <% if(updateJob != null) { %>
    <div class="card mt-5">
        <div class="card-header">
            <h4 class="mb-0">Update Job: <%=updateJob.getTitle()%></h4>
        </div>
        <div class="card-body">
            <form method="post" action="./RecruiterServlet">
                <input type="hidden" name="job_id" value="<%=updateJob.getId()%>">
                <input type="hidden" name="action" value="update">
                <div class="row">
                    <div class="col-md-6 mb-2">
                        <label class="form-label">Title</label>
                        <input type="text" name="title" class="form-control" value="<%=updateJob.getTitle()%>" required>
                    </div>
                    <div class="col-md-6 mb-2">
                        <label class="form-label">Location</label>
                        <input type="text" name="location" class="form-control" value="<%=updateJob.getLocation()%>" required>
                    </div>
                </div>
                <div class="mb-2">
                    <label class="form-label">Description</label>
                    <textarea name="description" class="form-control" rows="3" required><%=updateJob.getDescription()%></textarea>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-2">
                        <label class="form-label">Salary</label>
                        <input type="number" name="salary" step="0.01" class="form-control" value="<%=updateJob.getSalary()%>" required>
                    </div>
                    <div class="col-md-4 mb-2">
                        <label class="form-label">Job Type</label>
                        <select name="job_type" class="form-control" required>
                            <option value="Full-time" <%= "Full-time".equals(updateJob.getJob_type()) ? "selected" : "" %>>Full-time</option>
                            <option value="Part-time" <%= "Part-time".equals(updateJob.getJob_type()) ? "selected" : "" %>>Part-time</option>
                            <option value="Contract" <%= "Contract".equals(updateJob.getJob_type()) ? "selected" : "" %>>Contract</option>
                            <option value="Remote" <%= "Remote".equals(updateJob.getJob_type()) ? "selected" : "" %>>Remote</option>
                        </select>
                    </div>
                    <div class="col-md-4 mb-2">
                        <label class="form-label">Experience Required</label>
                        <input type="text" name="experience_required" class="form-control" value="<%=updateJob.getExperience_required()%>" required>
                    </div>
                </div>
                <div class="mb-2">
                    <label class="form-label">Mobile No</label>
                    <input type="text" name="mobile_no" class="form-control" value="<%=updateJob.getMobile_no()%>" required>
                </div>
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-success">Save Changes</button>
                    <a href="./RecruiterServlet?action=managejob" class="btn btn-secondary">Cancel</a>
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
});
</script>

</body>
</html>