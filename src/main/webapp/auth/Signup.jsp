<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Job Portal Registration</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>
/* ===== Base Style ===== */
body {
    margin: 0;
    padding: 0;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url("https://www.webmediatricks.com/uploaded_files/product/1703848338.jpg") no-repeat center/cover;
    font-family: "Segoe UI", sans-serif;
    color: #333;
}

/* ===== Glassmorphism Card ===== */
.frame {
    background: rgba(255, 255, 255, 0.15);
    backdrop-filter: blur(2px);
    border-radius: 16px;
    padding: 25px;
    width: 380px;
    transition: all 0.5s ease;
    box-shadow: 0 10px 35px rgb(0 0 0);
    overflow: hidden;
}

.frame.recruiter-active {
    width: 900px;
    display: flex;
    padding: 0;
}

.left, .right {
    flex: 1;
    padding: 25px;
}

.right {
    display: none;
    background: rgba(255, 255, 255, 0.95);
    border-left: 1px solid #ddd;
    border-radius: 0 16px 16px 0;
}

h1, h2 {
    text-align: center;
    margin-bottom: 20px;
    font-weight: 700;
}
h1 { font-size: 28px; color: white; }
h2 { font-size: 22px; color: #636e72; }

/* ===== Form Elements ===== */
label {
    display: block;
    margin-top: 12px;
    font-weight: 600;
    font-size: 14px;
    /* color:white; */
}

input, select, textarea {
    width: 100%;
    padding: 10px 12px;
    margin-top: 6px;
    border-radius: 8px;
    border: 1px solid #ccc;
    font-size: 15px;
    transition: all 0.3s ease;
    box-sizing: border-box;
}

input:focus, select:focus, textarea:focus {
    border-color: #0984e3;
    box-shadow: 0 0 8px rgba(9, 132, 227, 0.4);
    outline: none;
}

/* ===== Buttons ===== */
button {
    width: 100%;
    padding: 12px;
    margin-top: 15px;
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
    box-shadow: 0 6px 14px rgba(0, 0, 0, 0.25);
}

.register-btn { background: linear-gradient(90deg, #0984e3, #00cec9); }

/* ===== Links ===== */
a.login-link {
    color: #0984e3;
    text-decoration: none;
    font-weight: bold;
}
a.login-link:hover { text-decoration: underline; }

/* ===== Messages ===== */
.error { color: #d63031; font-size: 13px; margin-top: 4px; }
.success { color: #00b894; font-size: 13px; margin-top: 4px; }

/* ===== Responsive ===== */
@media screen and (max-width: 992px) {
    .frame.recruiter-active {
        flex-direction: column;
        width: 95%;
    }
    .right {
        display: block;
        border-left: none;
        border-top: 1px solid #ddd;
        border-radius: 0 0 16px 16px;
    }
}

@media screen and (max-width: 480px) {
    .frame {
        width: 95%;
        padding: 15px;
    }
    h1 { font-size: 22px; }
}
</style>
</head>
<body>

<form id="registrationForm"
    action="${pageContext.request.contextPath}/AuthenticationServlet"
    method="post">

    <div class="frame" id="frameBox">
        <!-- Left Side -->
        <div class="left">
            <h1 >Create an Account</h1>

            <% if (session.getAttribute("successMsg") != null) { %>
                <div class="success"><%= session.getAttribute("successMsg") %></div>
                <% session.removeAttribute("successMsg"); %>
            <% } %>

            <% if (session.getAttribute("errorMsg") != null) { %>
                <div class="error"><%= session.getAttribute("errorMsg") %></div>
                <% session.removeAttribute("errorMsg"); %>
            <% } %>

            <label for="user_name">Full Name</label>
            <input type="text" id="user_name" name="user_name"
                value="<%= session.getAttribute("user_name_val") != null ? session.getAttribute("user_name_val") : "" %>">

            <label for="user_email">Email</label>
            <input type="email" id="user_email" name="user_email"
                value="<%= session.getAttribute("user_email_val") != null ? session.getAttribute("user_email_val") : "" %>">

            <label for="user_password">Password</label>
            <input type="password" id="user_password" name="user_password">

            <label for="confirm_password">Confirm Password</label>
            <input type="password" id="confirm_password" name="confirm_password">

            <label for="location">Location</label>
            <input type="text" id="location" name="location"
                value="<%= session.getAttribute("location_val") != null ? session.getAttribute("location_val") : "" %>">

            <label for="user_role">Register As</label>
            <select id="user_role" name="user_role" onchange="swapForm()">
                <option value="JOB_SEEKER"
                    <%= "JOB_SEEKER".equals(session.getAttribute("user_role_val")) ? "selected" : "" %>>Job Seeker</option>
                <option value="RECRUITER"
                    <%= "RECRUITER".equals(session.getAttribute("user_role_val")) ? "selected" : "" %>>Recruiter</option>
            </select>

            <button type="submit" name="action" value="signup" class="register-btn">Register</button>
            <p style="font-size: 14px; margin-top: 8px; text-align:center;">
                Already have an account? <a href="login.jsp" class="login-link">Login</a>
            </p>
        </div>

        <!-- Right Side (Recruiter Fields) -->
        <div class="right" id="recruiterFields">
            <h2>Company Details</h2>

            <label>Company Name</label>
            <input type="text" id="company_name" name="company_name">

            <label>Company Location</label>
            <input type="text" id="company_location" name="company_location">

            <label>Company Description</label>
            <textarea id="company_description" name="company_description" rows="4"></textarea>

            <button type="submit" name="action" value="signup" class="register-btn">Register</button>
        </div>
    </div>
</form>

<script>
function swapForm() {
    const frame = document.getElementById("frameBox");
    const recruiterFields = document.getElementById("recruiterFields");
    const role = document.getElementById("user_role").value;

    if (role === "RECRUITER") {
        frame.classList.add("recruiter-active");
        recruiterFields.style.display = "block";
    } else {
        frame.classList.remove("recruiter-active");
        recruiterFields.style.display = "none";
    }
}
window.onload = swapForm;
</script>

</body>
</html>
