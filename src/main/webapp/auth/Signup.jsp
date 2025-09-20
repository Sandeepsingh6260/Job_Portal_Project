<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    background:
        url("https://images.unsplash.com/photo-1521791136064-7986c2920216?auto=format&fit=crop&w=1350&q=80")
        no-repeat center/cover;
    font-family: "Segoe UI", sans-serif;
}

/* frame box */
.frame {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 15px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
    width: 350px;
    transition: all 0.4s ease;
    overflow: hidden;
    padding: 20px;
}

.frame.recruiter-active {
    width: 850px;
    display: flex;
    padding: 0;
}

.left, .right {
    flex: 1;
    padding: 25px;
}

.right {
    display: none;
    background: #f9f9f9;
    border-left: 1px solid #ddd;
}

h1 { text-align: center; margin-bottom: 15px; color: #333; }
h2 { margin-bottom: 15px; color: #444; }

label {
    display: block;
    margin-top: 8px;
    font-weight: 600;
    font-size: 14px;
    color: #222;
}

input, select, textarea {
    width: 100%;
    padding: 8px 10px;
    margin-top: 4px;
    border-radius: 6px;
    border: 1px solid #ccc;
    font-size: 14px;
    transition: all 0.3s ease;
    box-sizing: border-box;
}

input:focus, select:focus, textarea:focus {
    border-color: #4facfe;
    box-shadow: 0 0 5px rgba(79, 172, 254, 0.6);
    outline: none;
}

.button-group {
    display: flex;
    justify-content: center;
    flex-direction: column;
    margin-top: 15px;
    gap: 6px;
    text-align: center;
}

button {
    width: 100%;
    padding: 10px;
    border: none;
    border-radius: 6px;
    color: white;
    font-size: 15px;
    font-weight: bold;
    cursor: pointer;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
}

button:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.25);
}

.register-btn { background: linear-gradient(90deg, #00b894, #00cec9); }
.register-btn:hover { background: #019874; }

a.login-link { color: #0984e3; text-decoration: none; font-weight: bold; }
a.login-link:hover { text-decoration: underline; }

.error { color: red; font-size: 13px; margin-top: 4px; }
.success { color: green; font-size: 13px; margin-top: 4px; }
</style>
</head>
<body>



<form id="registrationForm"
    action="${pageContext.request.contextPath}/AuthenticationServlet"
    method="post">

    <div class="frame" id="frameBox">
        <!-- Left side -->
        <div class="left">
            <h1>Register</h1>

            <% if (session.getAttribute("successMsg") != null) { %>
                <div class="success"><%= session.getAttribute("successMsg") %></div>
                <% session.removeAttribute("successMsg"); %>
            <% } %>

            <% if (session.getAttribute("errorMsg") != null) { %>
                <div class="error"><%= session.getAttribute("errorMsg") %></div>
                <% session.removeAttribute("errorMsg"); %>
            <% } %>

            <label for="user_name">Name</label>
            <input type="text" id="user_name" name="user_name"
                value="<%= session.getAttribute("user_name_val") != null ? session.getAttribute("user_name_val") : "" %>">
            <% if (session.getAttribute("nameError") != null) { %>
                <div class="error"><%= session.getAttribute("nameError") %></div>
                <% session.removeAttribute("nameError"); %>
            <% } %>

            <label for="user_email">Email</label>
            <input type="email" id="user_email" name="user_email"
                value="<%= session.getAttribute("user_email_val") != null ? session.getAttribute("user_email_val") : "" %>">
            <% if (session.getAttribute("emailError") != null) { %>
                <div class="error"><%= session.getAttribute("emailError") %></div>
                <% session.removeAttribute("emailError"); %>
            <% } %>

            <label for="user_password">Password</label>
            <input type="password" id="user_password" name="user_password">
            <% if (session.getAttribute("passwordError") != null) { %>
                <div class="error"><%= session.getAttribute("passwordError") %></div>
                <% session.removeAttribute("passwordError"); %>
            <% } %>

            <label for="confirm_password">Confirm Password</label>
            <input type="password" id="confirm_password" name="confirm_password">
            <% if (session.getAttribute("confirmPasswordError") != null) { %>
                <div class="error"><%= session.getAttribute("confirmPasswordError") %></div>
                <% session.removeAttribute("confirmPasswordError"); %>
            <% } %>

            <label for="location">Location</label>
            <input type="text" id="location" name="location"
                value="<%= session.getAttribute("location_val") != null ? session.getAttribute("location_val") : "" %>">
            <% if (session.getAttribute("locationError") != null) { %>
                <div class="error"><%= session.getAttribute("locationError") %></div>
                <% session.removeAttribute("locationError"); %>
            <% } %>

            <label for="user_role">Role</label>
            <select id="user_role" name="user_role" onchange="swapForm()">
                <option value="JOB_SEEKER"
                    <%= "JOB_SEEKER".equals(session.getAttribute("user_role_val")) ? "selected" : "" %>>Job Seeker</option>
                <option value="RECRUITER"
                    <%= "RECRUITER".equals(session.getAttribute("user_role_val")) ? "selected" : "" %>>Recruiter</option>
            </select>

            <div id="jobSeekerButton" class="button-group">
                <button type="submit" name="action" value="signup" class="register-btn">Register</button>
                <p style="font-size: 13px; color: #555;">
                    Already have an account? <a href="login.jsp" class="login-link">Login</a>
                </p>
            </div>
        </div>

        <!-- Right side (Recruiter fields only) -->
        <div class="right" id="recruiterFields">
            <h2>Recruiter Details</h2>

            <label>Company Name</label>
            <input type="text" id="company_name" name="company_name"
                value="<%= session.getAttribute("company_name_val") != null ? session.getAttribute("company_name_val") : "" %>">
            <% if (session.getAttribute("companyNameError") != null) { %>
                <div class="error"><%= session.getAttribute("companyNameError") %></div>
                <% session.removeAttribute("companyNameError"); %>
            <% } %>

            <label>Company Location</label>
            <input type="text" id="company_location" name="company_location"
                value="<%= session.getAttribute("company_location_val") != null ? session.getAttribute("company_location_val") : "" %>">
            <% if (session.getAttribute("companyLocationError") != null) { %>
                <div class="error"><%= session.getAttribute("companyLocationError") %></div>
                <% session.removeAttribute("companyLocationError"); %>
            <% } %>

            <label>Company Description</label>
            <textarea id="company_description" name="company_description"><%= session.getAttribute("company_description_val") != null ? session.getAttribute("company_description_val") : "" %></textarea>

            <div class="button-group">
                <button type="submit" name="action" value="signup" class="register-btn">Register</button>
                <p style="font-size: 13px; color: #555;">
                    Already have an account? <a href="login.jsp" class="login-link">Login</a>
                </p>
            </div>
        </div>
    </div>
</form>

<script>
function swapForm() {
    const role = document.getElementById("user_role").value;
    const frame = document.getElementById("frameBox");
    const recruiterFields = document.getElementById("recruiterFields");
    const jobSeekerButton = document.getElementById("jobSeekerButton");

    if (role === "RECRUITER") {
        frame.classList.add("recruiter-active");
        recruiterFields.style.display = "block";
        jobSeekerButton.style.display = "none";
    } else {
        frame.classList.remove("recruiter-active");
        recruiterFields.style.display = "none";
        jobSeekerButton.style.display = "flex";
    }
}
window.onload = swapForm;
</script>
</body>
</html>
