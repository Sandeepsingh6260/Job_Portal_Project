<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Job Portal Registration</title>
<style>
body {
    margin: 0;
    padding: 0;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url("https://images.unsplash.com/photo-1521791136064-7986c2920216?auto=format&fit=crop&w=1350&q=80") no-repeat center/cover;
    font-family: "Segoe UI", sans-serif;
}
.frame {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 15px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
    width: 850px;
    display: flex;
    overflow: hidden;
}
.left, .right { flex: 1; padding: 30px; }
.left { border-right: 1px solid #ddd; }
.right { background: #f9f9f9; }
h1 { text-align: center; margin-bottom: 20px; }
label { display: block; margin-top: 12px; font-weight: 600; }
.error { color: red; font-size: 13px; }
.success { color: green; font-size: 13px; }
</style>
</head>
<body>

<div class="frame">
<form action="${pageContext.request.contextPath}/AuthenticationServlet" method="post" style="width:100%; display:flex;">

    <!-- Left -->
    <div class="left">
        <h1>Register</h1>

        <% if (request.getAttribute("successMsg") != null) { %>
            <div class="success"><%= request.getAttribute("successMsg") %></div>
        <% } %>
        <% if (request.getAttribute("errorMsg") != null) { %>
            <div class="error"><%= request.getAttribute("errorMsg") %></div>
        <% } %>

        <!-- Name -->
        <label for="user_name">Name</label>
        <% if (request.getAttribute("nameError") != null) { %>
            <div class="error"><%= request.getAttribute("nameError") %></div>
        <% } %>
        <input type="text" id="user_name" name="user_name"
               value="<%= request.getAttribute("user_name_val") != null ? request.getAttribute("user_name_val") : "" %>">

        <!-- Email -->
        <label for="user_email">Email</label>
        <% if (request.getAttribute("emailError") != null) { %>
            <div class="error"><%= request.getAttribute("emailError") %></div>
        <% } %>
        <input type="email" id="user_email" name="user_email"
               value="<%= request.getAttribute("user_email_val") != null ? request.getAttribute("user_email_val") : "" %>">

        <!-- Password -->
        <label for="user_password">Password</label>
        <% if (request.getAttribute("passwordError") != null) { %>
            <div class="error"><%= request.getAttribute("passwordError") %></div>
        <% } %>
        <input type="password" id="user_password" name="user_password">

        <!-- Location -->
        <label for="location">Location</label>
        <% if (request.getAttribute("locationError") != null) { %>
            <div class="error"><%= request.getAttribute("locationError") %></div>
        <% } %>
        <input type="text" id="location" name="location"
               value="<%= request.getAttribute("location_val") != null ? request.getAttribute("location_val") : "" %>">

        <!-- Role -->
        <label for="user_role">Role</label>
        <select id="user_role" name="user_role" onchange="swapForm()">
            <option value="job_seeker"
                <%= "job_seeker".equals(request.getAttribute("user_role_val")) ? "selected" : "" %>>Job Seeker</option>
            <option value="recruiter"
                <%= "recruiter".equals(request.getAttribute("user_role_val")) ? "selected" : "" %>>Recruiter</option>
        </select>
    </div>

    <!-- Right -->
    <div class="right">
        <!-- Job Seeker -->
        <div id="jobSeekerFields">
            <label>Skills</label>
            <% if (request.getAttribute("skillsError") != null) { %>
                <div class="error"><%= request.getAttribute("skillsError") %></div>
            <% } %>
            <div id="skillsContainer">
                <input type="text" name="skills">
            </div>
            <button type="button" onclick="addSkill()">+ Add Skill</button>

            <label>Experience</label>
            <% if (request.getAttribute("experienceError") != null) { %>
                <div class="error"><%= request.getAttribute("experienceError") %></div>
            <% } %>
            <input type="number" name="experience"
                   value="<%= request.getAttribute("experience_val") != null ? request.getAttribute("experience_val") : "" %>">

            <label>Resume Upload</label>
            <% if (request.getAttribute("resumeError") != null) { %>
                <div class="error"><%= request.getAttribute("resumeError") %></div>
            <% } %>
            <input type="file" name="resume_path">
        </div>

        <!-- Recruiter -->
        <div id="recruiterFields">
            <label>Company Name</label>
            <% if (request.getAttribute("companyNameError") != null) { %>
                <div class="error"><%= request.getAttribute("companyNameError") %></div>
            <% } %>
            <input type="text" name="company_name"
                   value="<%= request.getAttribute("company_name_val") != null ? request.getAttribute("company_name_val") : "" %>">

            <label>Company Location</label>
            <% if (request.getAttribute("companyLocationError") != null) { %>
                <div class="error"><%= request.getAttribute("companyLocationError") %></div>
            <% } %>
            <input type="text" name="company_location"
                   value="<%= request.getAttribute("company_location_val") != null ? request.getAttribute("company_location_val") : "" %>">

            <label>Company Description</label>
            <textarea name="company_description"><%= request.getAttribute("company_description_val") != null ? request.getAttribute("company_description_val") : "" %></textarea>
        </div>

        <button type="submit" name="action" value="signup">Register</button>
    </div>
</form>
</div>

<script>
    function swapForm() {
        const role = document.getElementById("user_role").value;
        document.getElementById("jobSeekerFields").style.display = (role === "job_seeker") ? "block" : "none";
        document.getElementById("recruiterFields").style.display = (role === "recruiter") ? "block" : "none";
    }
    window.onload = swapForm;

    function addSkill() {
        const c = document.getElementById("skillsContainer");
        const input = document.createElement("input");
        input.type = "text";
        input.name = "skills";
        input.placeholder = "Enter another skill";
        c.appendChild(input);
    }
</script>

</body>
</html>
