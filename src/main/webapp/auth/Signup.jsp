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
		no-repeat center center/cover;
	font-family: "Segoe UI", sans-serif;
}

.frame {
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

/* Error styling */
.error {
	color: red;
	font-size: 13px;
	margin-top: 4px;
}
</style>
</head>
<body>
	<div class="frame">
		<!-- Left side -->
		<div class="left">
			<h1>Register</h1>
			<form id="registrationForm" action="../AuthenticationServlet" method="post">
				<label for="user_name">Name</label> <input type="text"
					id="user_name" name="user_name" required> <label
					for="user_email">Email</label> <input type="email" id="user_email"
					name="user_email" required> <label for="user_password">Password</label>
				<input type="password" id="user_password" name="user_password"
					required minlength="6"> <label for="location">Location</label>
				<input type="text" id="location" name="location"> <label
					for="user_role">Role</label> <select id="user_role"
					name="user_role" required onchange="swapForm()">
					<option value="job_seeker" selected>Job Seeker</option>
					<option value="recruiter">Recruiter</option>
				</select>
		</div>

		<!-- Right side -->
		<div class="right">
			<!-- Job Seeker Fields -->
			<div id="jobSeekerFields" class="form-section">
				<h2>Job Seeker Details</h2>
				<!--   <label for="skills">Skills</label>
        <input type="text" id="skills" name="skills" required> -->
				<label for="skills">Skills</label>
				<div id="skillsContainer">
					<input type="text" name="skills" placeholder="Enter a skill">
				</div>
				<button type="button" onclick="addSkill()">+ Add Skill</button>

				<label for="experience">Experience (years)</label> <input
					type="number" id="experience" name="experience" min="0" required>

				<label for="resume_path">Resume Upload</label> <input type="file"
					id="resume_path" name="resume_path" required>
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
			</form>
		</div>
	</div>

	<script>
		// Show Job Seeker fields by default
		window.onload = function() {
			swapForm();
		}

		function swapForm() {
			const role = document.getElementById("user_role").value;
			const jobSeekerFields = document.getElementById("jobSeekerFields");
			const recruiterFields = document.getElementById("recruiterFields");

			if (role === "job_seeker") {
				jobSeekerFields.style.display = "block";
				recruiterFields.style.display = "none";
			} else {
				recruiterFields.style.display = "block";
				jobSeekerFields.style.display = "none";
			}
		}

		// Basic validation
		function validateForm() {
			const role = document.getElementById("user_role").value;

			if (role === "job_seeker") {
				if (!document.getElementById("skills").value
						|| !document.getElementById("experience").value
						|| !document.getElementById("resume_path").value) {
					alert("Please fill all Job Seeker fields!");
					return false;
				}
			} else if (role === "recruiter") {
				if (!document.getElementById("company_name").value
						|| !document.getElementById("company_location").value) {
					alert("Please fill all Recruiter fields!");
					return false;
				}
			}

			return true; // allow submit
		}
		function addSkill() {
			const container = document.getElementById("skillsContainer");
			const input = document.createElement("input");
			input.type = "text";
			input.name = "skills"; // Same name so server collects into array
			input.placeholder = "Enter another skill";
			container.appendChild(input);
		}
	</script>
</body>
</html>
