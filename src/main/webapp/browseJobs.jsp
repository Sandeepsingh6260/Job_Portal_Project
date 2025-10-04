<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.jobportal.model.Job, com.jobportal.model.Resume" %>
<%
    List<Job> allJobs = (List<Job>) request.getAttribute("allJobs");
    Resume resume = (Resume) request.getAttribute("resume");
    List<String> appliedJobIds = (List<String>) request.getAttribute("appliedJobIds");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Browse Jobs</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f9;
        margin: 0;
        padding: 0;
    }

    h2 {
        text-align: center;
        margin-top: 20px;
        color: #333;
    }

    .job-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 20px;
        padding: 20px;
    }

    .job-card {
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        width: 320px;
        padding: 20px;
        transition: transform 0.2s ease;
    }

    .job-card:hover {
        transform: translateY(-5px);
    }

    .job-title {
        font-size: 20px;
        font-weight: bold;
        color: #2b2b2b;
        margin-bottom: 8px;
    }

    .company {
        color: #555;
        font-weight: 500;
        margin-bottom: 10px;
    }

    .details {
        color: #777;
        margin-bottom: 10px;
    }

    .apply-btn {
        display: inline-block;
        padding: 8px 15px;
        background-color: #ff6600;
        color: #fff;
        border: none;
        border-radius: 5px;
        text-decoration: none;
        cursor: pointer;
    }

    .apply-btn:hover {
        background-color: #e65c00;
    }

    .applied-btn {
        display: inline-block;
        padding: 8px 15px;
        background-color: #ccc;
        color: #333;
        border: none;
        border-radius: 5px;
        cursor: not-allowed;
    }
</style>
</head>
<body>

<h2>Available Jobs</h2>

<div class="job-container">
<%
    if (allJobs != null && !allJobs.isEmpty()) {
        for (Job job : allJobs) {
            boolean alreadyApplied = appliedJobIds != null && appliedJobIds.contains(String.valueOf(job.getId()));
%>
    <div class="job-card">
        <div class="job-title"><%= job.getTitle() %></div>
        <div class="company">Company: <%= job.getCompany_name()  %> </div>
        <div class="details">Location: <%= job.getLocation() %></div>
        <div class="details">Salary: ₹<%= job.getSalary() %></div>
        <div class="details">Job Type: <%= job.getJob_type() %></div>
        <div class="details">Description: <%= job.getDescription() %></div>

        <% if (alreadyApplied) { %>
            <button class="applied-btn">Applied</button>
        <% } else if (resume == null) { %>
            <button class="applied-btn">Upload Resume First</button>
        <% } else { %>
            <form action="<%= request.getContextPath() %>/JobSeekerServlet" method="post" style="margin-top:10px;">
                <input type="hidden" name="jobId" value="<%= job.getId() %>">
                <button type="submit" class="apply-btn" name="action" value="applyJob">Apply</button>
            </form>
        <% } %>
    </div>
<%
        }
    } else {
%>
    <p style="text-align:center; color:#777;">No jobs available right now.</p>
<%
    }
%>
</div>

</body>
</html>
