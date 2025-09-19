<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Job Portal Registration</title>
<style>
body {
<<<<<<< HEAD
	margin: 0;
	padding: 0;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	background:
		url("https://images.unsplash.com/photo-1521791136064-7986c2920216?auto=format&fit=crop&w=1350&q=80")
		no-repeat center center/cover;
	font-family: "Segoe UI", sans-serif;
=======
    margin: 0;
    padding: 0;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url("https://images.unsplash.com/photo-1521791136064-7986c2920216?auto=format&fit=crop&w=1350&q=80") no-repeat center/cover;
    font-family: "Segoe UI", sans-serif;
>>>>>>> bfaa16c295886a17e0cfed24a69f658f34a4a169
}
.frame {
<<<<<<< HEAD
	background: rgba(255, 255, 255, 0.95);
	border-radius: 15px;
	box-shadow: 0px 10px 25px rgba(0, 0, 0, 0.3);
	width: 850px;
	display: flex;
	overflow: hidden;
	animation: fadeIn 1s ease-in-out;
}

.left, .right {
	flex: 1;
	padding: 30px;
}

.left {
	border-right: 1px solid #ddd;
}

.right {
	background: #f9f9f9;
}

h1 {
	text-align: center;
	margin-bottom: 20px;
	color: #333;
}

h2 {
	margin-bottom: 15px;
	color: #444;
}

label {
	display: block;
	margin-top: 12px;
	font-weight: 600;
	color: #222;
}

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
	box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.25);
}

.form-section {
	display: none;
	animation: fadeIn 0.6s ease;
}

@
keyframes fadeIn {from { opacity:0;
	transform: translateY(15px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}
.error {
	color: red;
	font-size: 13px;
	margin-top: 4px;
}

.m .frame {
	display: flex;
=======
    background: rgba(255, 255, 255, 0.95);
    border-radius: 15px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
    width: 850px;
    display: flex;
    overflow: hidden;
>>>>>>> bfaa16c295886a17e0cfed24a69f658f34a4a169
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
<<<<<<< HEAD

	<form id="registrationForm" action="../AuthenticationServlet" method="post"
		class="m" onsubmit="return validateForm()">
=======
>>>>>>> bfaa16c295886a17e0cfed24a69f658f34a4a169

<div class="frame">
<form action="${pageContext.request.contextPath}/AuthenticationServlet" method="post" style="width:100%; display:flex;">

<<<<<<< HEAD
		<div class="frame">
			<!-- Left side -->
			<div class="left">
				<h1>Register</h1>

				<label for="user_name">Name</label> <input type="text"
					id="user_name" name="user_name" required> <label
					for="user_email">Email</label> <input type="email" id="user_email"
					name="user_email" required> <label for="user_password">Password</label>
				<input type="password" id="user_password" name="user_password"
					required minlength="6"> <label for="location">Location</label>
				<input type="text" id="location" name="location"> <label
					for="user_role">Role</label> <select id="user_role"
					name="user_role" required onchange="swapForm()">
					<option value="JOB_SEEKER" selected>Job Seeker</option>
					<option value="RECRUITER">Recruiter</option>
				</select>
			</div>

			<!-- Right side -->
			<div class="right">
				<!-- Job Seeker Fields -->
				<div id="jobSeekerFields" class="form-section">
					<h2>Job Seeker Details</h2>
					<label for="skills">Skills</label>
					<div id="skillsContainer">
						<input type="text" name="skills" placeholder="Enter a skill">
					</div>
					<button type="button" onclick="addSkill()">+ Add Skill</button>

					<label for="experience">Experience (years)</label> <input
						type="number" id="experience" name="experience" min="0"> <label
						for="resume_path">Resume Upload</label> <input type="file"
						id="resume_path" name="resume_path">
				</div>

				<!-- Recruiter Fields -->
				<div id="recruiterFields" class="form-section">
					<h2>Recruiter Details</h2>
					<label for="company_name">Company Name</label> <input type="text"
						id="company_name" name="company_name"> <label
						for="company_location">Company Location</label> <input type="text"
						id="company_location" name="company_location"> <label
						for="company_description">Company Description</label>
					<textarea id="company_description" name="company_description"
						rows="3"></textarea>
				</div>

				<button type="submit" name="action" value="signup">Register</button>
			</div>
		</div>
	</form>

	<script>
// Show correct section on load
window.onload = swapForm;

function swapForm() {
    const role = document.getElementById("user_role").value;
    const jobSeekerFields = document.getElementById("jobSeekerFields");
    const recruiterFields = document.getElementById("recruiterFields");

    if (role === "JOB_SEEKER") {
        jobSeekerFields.style.display = "block";
        recruiterFields.style.display = "none";
    } else {
        jobSeekerFields.style.display = "none";
        recruiterFields.style.display = "block";
=======
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
>>>>>>> bfaa16c295886a17e0cfed24a69f658f34a4a169
    }
    window.onload = swapForm;

<<<<<<< HEAD
// Add another skill input
function addSkill() {
    const container = document.getElementById("skillsContainer");
    const input = document.createElement("input");
    input.type = "text";
    input.name = "skills[]";
    input.placeholder = "Enter another skill";
    container.appendChild(input);
}

// Form validation
function validateForm() {
    const role = document.getElementById("user_role").value;

    if(role === "JOB_SEEKER") {
        const skillsInputs = document.querySelectorAll("#skillsContainer input");
        let skillsFilled = Array.from(skillsInputs).some(input => input.value.trim() !== "");
        if(!skillsFilled || !document.getElementById("experience").value || !document.getElementById("resume_path").value){
            alert("Please fill all Job Seeker fields!");
            return false;
        }
    }
    else
    {
        if(!document.getElementById("company_name").value.trim() || !document.getElementById("company_location").value.trim()){
            alert("Please fill all Recruiter fields!");
            return false;
        }
=======
    function addSkill() {
        const c = document.getElementById("skillsContainer");
        const input = document.createElement("input");
        input.type = "text";
        input.name = "skills";
        input.placeholder = "Enter another skill";
        c.appendChild(input);
>>>>>>> bfaa16c295886a17e0cfed24a69f658f34a4a169
    }
</script>

</body>
</html>
