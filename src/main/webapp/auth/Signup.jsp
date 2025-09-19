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
    animation: fadeIn 1s ease-in-out;
}
.left, .right { flex: 1; padding: 30px; }
.left { border-right: 1px solid #ddd; }
.right { background: #f9f9f9; }
h1 { text-align: center; margin-bottom: 20px; color: #333; }
h2 { margin-bottom: 15px; color: #444; }
label { display: block; margin-top: 12px; font-weight: 600; color: #222; }
input, select, textarea {
    width: 100%;
    padding: 10px 12px;
    margin-top: 5px;
    border-radius: 8px;
    border: 1px solid #ccc;
    font-size: 14px;
    transition: all 0.3s ease;
}
input:focus, select:focus, textarea:focus {
    border-color: #4facfe;
    box-shadow: 0 0 6px rgba(79, 172, 254, 0.6);
    outline: none;
}
button {
    margin-top: 20px;
    width: 100%;
    padding: 12px;
    background: linear-gradient(90deg, #4facfe, #00f2fe);
    border: none;
    border-radius: 8px;
    color: white;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
}
button:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.25);
}
.form-section { display: none; animation: fadeIn 0.6s ease; }
.error { color: red; font-size: 13px; margin-top: 4px; }
.success { color: green; font-size: 13px; font-size: 13px; }
</style>
</head>
<body>

<form id="registrationForm" action="${pageContext.request.contextPath}/AuthenticationServlet" method="post" class="m">

    <div class="frame">
        <!-- Left side -->
        <div class="left">
            <h1>Register</h1>

            <% if (request.getAttribute("successMsg") != null) { %>
                <div class="success"><%= request.getAttribute("successMsg") %></div>
            <% } %>
            <% if (request.getAttribute("errorMsg") != null) { %>
                <div class="error"><%= request.getAttribute("errorMsg") %></div>
            <% } %>

            <label for="user_name">Name</label>
            <% if (request.getAttribute("nameError") != null) { %>
                <div class="error"><%= request.getAttribute("nameError") %></div>
            <% } %>
            <input type="text" id="user_name" name="user_name"
                   value="<%= request.getAttribute("user_name_val") != null ? request.getAttribute("user_name_val") : "" %>">

            <label for="user_email">Email</label>
            <% if (request.getAttribute("emailError") != null) { %>
                <div class="error"><%= request.getAttribute("emailError") %></div>
            <% } %>
            <input type="email" id="user_email" name="user_email"
                   value="<%= request.getAttribute("user_email_val") != null ? request.getAttribute("user_email_val") : "" %>">

            <label for="user_password">Password</label>
            <% if (request.getAttribute("passwordError") != null) { %>
                <div class="error"><%= request.getAttribute("passwordError") %></div>
            <% } %>
            <input type="password" id="user_password" name="user_password">

            <label for="location">Location</label>
            <% if (request.getAttribute("locationError") != null) { %>
                <div class="error"><%= request.getAttribute("locationError") %></div>
            <% } %>
            <input type="text" id="location" name="location"
                   value="<%= request.getAttribute("location_val") != null ? request.getAttribute("location_val") : "" %>">

            <label for="user_role">Role</label>
            <select id="user_role" name="user_role" onchange="swapForm()">
                <option value="JOB_SEEKER"
                    <%= "JOB_SEEKER".equals(request.getAttribute("user_role_val")) ? "selected" : "" %>>Job Seeker</option>
                <option value="RECRUITER"
                    <%= "RECRUITER".equals(request.getAttribute("user_role_val")) ? "selected" : "" %>>Recruiter</option>
            </select>
        </div>

        <!-- Right side -->
        <div class="right">
            <!-- Job Seeker Fields -->
            <div id="jobSeekerFields">
                <h2>Job Seeker Details</h2>

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
                <input type="number" id="experience" name="experience"
                       value="<%= request.getAttribute("experience_val") != null ? request.getAttribute("experience_val") : "" %>">

                <label>Resume Upload</label>
                <% if (request.getAttribute("resumeError") != null) { %>
                    <div class="error"><%= request.getAttribute("resumeError") %></div>
                <% } %>
                <input type="file" id="resume_path" name="resume_path">
            </div>

            <!-- Recruiter Fields -->
            <div id="recruiterFields">
                <h2>Recruiter Details</h2>

                <label>Company Name</label>
                <% if (request.getAttribute("companyNameError") != null) { %>
                    <div class="error"><%= request.getAttribute("companyNameError") %></div>
                <% } %>
                <input type="text" id="company_name" name="company_name"
                       value="<%= request.getAttribute("company_name_val") != null ? request.getAttribute("company_name_val") : "" %>">

                <label>Company Location</label>
                <% if (request.getAttribute("companyLocationError") != null) { %>
                    <div class="error"><%= request.getAttribute("companyLocationError") %></div>
                <% } %>
                <input type="text" id="company_location" name="company_location"
                       value="<%= request.getAttribute("company_location_val") != null ? request.getAttribute("company_location_val") : "" %>">

                <label>Company Description</label>
                <textarea id="company_description" name="company_description"><%= request.getAttribute("company_description_val") != null ? request.getAttribute("company_description_val") : "" %></textarea>
            </div>

            <button type="submit" name="action" value="signup">Register</button>
        </div>
    </div>
</form>

<script>
function swapForm() {
    const role = document.getElementById("user_role").value;
    document.getElementById("jobSeekerFields").style.display = (role === "JOB_SEEKER") ? "block" : "none";
    document.getElementById("recruiterFields").style.display = (role === "RECRUITER") ? "block" : "none";
}
window.onload = swapForm;

function addSkill() {
    const container = document.getElementById("skillsContainer");
    const input = document.createElement("input");
    input.type = "text";
    input.name = "skills";
    input.placeholder = "Enter another skill";
    container.appendChild(input);
}
</script>

</body>
</html>
