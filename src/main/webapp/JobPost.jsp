<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Post Job | Recruiter</title>
<style>
    body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 0;
        background: #f4f7fb;
    }


    h2 {
        text-align: center;
        margin-top: 20px;
        color: #333;
    }
    

    .container {
        width: 450px;
        margin: 30px auto;
        background: #fff;
        padding: 25px 30px;
        border-radius: 10px;
        box-shadow: 0px 4px 12px rgba(0,0,0,0.1);
    }


    label {
        display: block;
        margin-bottom: 6px;
        font-weight: bold;
        color: #444;
    }

    input[type="text"], textarea, select {
        width: 100%;
        padding: 10px;
        margin-bottom: 12px;
        border: 1px solid #ccc;
        border-radius: 6px;
        outline: none;
        font-size: 14px;
        background: #fafafa;
        transition: border-color 0.3s ease;
    }

    input[type="text"]:focus, textarea:focus, select:focus {
        border-color: #007bff;
        background: #fff;
    }

    textarea {
        resize: none;
        height: 80px;
    }

    .error {
        color: #e74c3c;
        font-size: 13px;
        margin-top: -8px;
        margin-bottom: 10px;
    }

    .success {
        color: #2ecc71;
        font-size: 14px;
        margin-bottom: 12px;
        text-align: center;
        font-weight: bold;
    }

    button {
        width: 100%;
        padding: 12px;
        background: #007bff;
        border: none;
        color: white;
        font-size: 16px;
        border-radius: 6px;
        cursor: pointer;
        transition: background 0.3s ease;
    }

    button:hover {
        background: #0056b3;
    }

    .back-link {
        text-align: center;
        margin-top: 15px;
    }

    .back-link a {
        text-decoration: none;
        color: #007bff;
        font-size: 14px;
    }

    .back-link a:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>
    <h2>Post a Job</h2>

    <div class="container">
        <!-- Success & Error messages -->
        <%
            if (session.getAttribute("successMsg") != null) {
        %>
            <div class="success"><%= session.getAttribute("successMsg") %></div>
        <%
                session.removeAttribute("successMsg");
            }
            if (session.getAttribute("errorMsg") != null) {
        %>
            <div class="error"><%= session.getAttribute("errorMsg") %></div>
        <%
                session.removeAttribute("errorMsg");
            }
        %>

        <form action="./RecruiterServlet" method="post">
            <input type="hidden" name="action" value="jobpost" />

            <!-- Job Title -->
            <label>Job Title:</label>
            <input type="text" name="title" 
                   value="<%= session.getAttribute("title_val") != null ? session.getAttribute("title_val") : "" %>"/>
            <div class="error"><%= session.getAttribute("titleError") != null ? session.getAttribute("titleError") : "" %></div>

            <!-- Description -->
            <label>Description:</label>
            <textarea name="description"><%= session.getAttribute("desc_val") != null ? session.getAttribute("desc_val") : "" %></textarea>
            <div class="error"><%= session.getAttribute("descriptionError") != null ? session.getAttribute("descriptionError") : "" %></div>

            <!-- Location -->
            <label>Location:</label>
            <input type="text" name="location" 
                   value="<%= session.getAttribute("loc_val") != null ? session.getAttribute("loc_val") : "" %>"/>
            <div class="error"><%= session.getAttribute("locationError") != null ? session.getAttribute("locationError") : "" %></div>

            <!-- Salary -->
            <label>Salary:</label>
            <input type="text" name="salary" 
                   value="<%= session.getAttribute("salary_val") != null ? session.getAttribute("salary_val") : "" %>"/>
            <div class="error"><%= session.getAttribute("salaryError") != null ? session.getAttribute("salaryError") : "" %></div>

            <!-- Experience -->
            <label>Experience:</label>
            <input type="text" name="experience_required" 
                   value="<%= session.getAttribute("exp_val") != null ? session.getAttribute("exp_val") : "" %>"/>
            <div class="error"><%= session.getAttribute("experienceError") != null ? session.getAttribute("experienceError") : "" %></div>

            <!-- Job Type -->
            <label>Job Type:</label>
            <select name="job_type">
                <option value="">--Select--</option>
                <option value="Full-Time" <%= "Full-Time".equals(session.getAttribute("job_type_val")) ? "selected" : "" %>>Full-Time</option>
                <option value="Part-Time" <%= "Part-Time".equals(session.getAttribute("job_type_val")) ? "selected" : "" %>>Part-Time</option>
                <option value="Internship" <%= "Internship".equals(session.getAttribute("job_type_val")) ? "selected" : "" %>>Internship</option>
                <option value="Contract" <%= "Contract".equals(session.getAttribute("job_type_val")) ? "selected" : "" %>>Contract</option>
            </select>
            <div class="error"><%= session.getAttribute("jobTypeError") != null ? session.getAttribute("jobTypeError") : "" %></div>

            <!-- Mobile -->
            <label>Mobile:</label>
            <input type="text" name="mobile_no" 
                   value="<%= session.getAttribute("mobile_val") != null ? session.getAttribute("mobile_val") : "" %>"/>
            <div class="error"><%= session.getAttribute("mobileError") != null ? session.getAttribute("mobileError") : "" %></div>

            <button type="submit">Post Job</button>
        </form>

        <div class="back-link">
            <a href="Recruiter.jsp">← Back to Dashboard</a>
        </div>
    </div>

<%
    // remove field-specific errors after displaying (to avoid showing again on refresh)
    session.removeAttribute("titleError");
    session.removeAttribute("descriptionError");
    session.removeAttribute("locationError");
    session.removeAttribute("salaryError");
    session.removeAttribute("experienceError");
    session.removeAttribute("jobTypeError");
    session.removeAttribute("mobileError");
%>
</body>
</html>
