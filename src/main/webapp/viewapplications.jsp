<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.jobportal.model.Application" %>
<%@ page import="com.jobportal.model.User, com.jobportal.model.Job" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Applications</title>

<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 0;
        background: #f4f4f4;
    }

    h1 {
        text-align: center;
        color: #333;
        margin-top: 30px;
    }

    .container {
        width: 90%;
        margin: 30px auto;
        background: #fff;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        padding: 20px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        padding: 12px;
        text-align: center;
        border-bottom: 1px solid #ddd;
    }

    th {
        background: #007bff;
        color: white;
        text-transform: uppercase;
    }

    tr:hover {
        background: #f1f1f1;
    }

    .btn {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        color: white;
    }

    .btn-shortlist {
        background: #28a745;
    }

    .btn-reject {
        background: #dc3545;
    }

    .btn-back {
        display: inline-block;
        margin-top: 20px;
        background: #6c757d;
        color: white;
        padding: 10px 18px;
        border-radius: 4px;
        text-decoration: none;
    }

    .btn-back:hover {
        background: #5a6268;
    }

    .no-data {
        text-align: center;
        color: #888;
        padding: 20px;
    }
</style>
</head>

<body>

<h1>All Job Applications</h1>

<div class="container">

    <%
        List<Application> applications = (List<Application>) request.getAttribute("applications");
        if (applications != null && !applications.isEmpty()) {
    %>

    <table>
        <thead>
            <tr>
                <th>Application ID</th>
                <th>Job Title</th>
                <th>Applicant Name</th>
                <th>Applicant Email</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            for (Application app : applications) {
        %>
            <tr>
                <td><%= app.getId() %></td>
                <td><%= app.getJobTitle() %></td>
                <td><%= app.getApplicantName() %></td>
                <td><%= app.getApplicantEmail() %></td>
                <td><%= app.getStatus() %></td>
                <td>
                    <form action="ApplicationServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="shortlist">
                        <input type="hidden" name="applicationId" value="<%= app.getId() %>">
                        <button class="btn btn-shortlist" type="submit">Shortlist</button>
                    </form>
                    <form action="ApplicationServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="reject">
                        <input type="hidden" name="applicationId" value="<%= app.getId() %>">
                        <button class="btn btn-reject" type="submit">Reject</button>
                    </form>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <% } else { %>
        <p class="no-data">No applications found.</p>
    <% } %>

    <div style="text-align:center;">
        <a href="Recruiter.jsp" class="btn-back">← Back to Dashboard</a>
    </div>

</div>

</body>
</html>
