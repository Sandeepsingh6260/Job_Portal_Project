<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.jobportal.model.User"%>
<%@ page import="com.jobportal.model.Company"%>
<%
User user = (User) session.getAttribute("session");
if (user == null) {
	response.sendRedirect("auth/login.jsp");
	return;
}
%>
<%
Company company = (Company) session.getAttribute("companySession");
if (company == null) {
	response.sendRedirect("auth/login.jsp");
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Profile</title>
<jsp:include page="./include/head.jsp" />
<style>
:root {
	--primary: #6a11cb;
	--primary-light: #7d3cff;
	--secondary: #2575fc;
	--accent: #ff6b6b;
	--light: #f8f9fa;
	--dark: #343a40;
	--gray: #6c757d;
	--success: #28a745;
	--border-radius: 15px;
	--box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
	--transition: all 0.3s ease;
}

body {
	font-family: 'Poppins', sans-serif;
	margin: 0;
	padding: 0;
	min-height: 100vh;
}


/* Main container */
.main-content {
	margin-left: 250px; /* Sidebar width */
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 90vh;
	padding: 30px;
	transition: var(--transition);
}





/* Profile Container */
.profile-container {
	display: flex;
	max-width: 1000px;
	width: 100%;
	background: white;
	border-radius: var(--border-radius);
	box-shadow: var(--box-shadow);
	overflow: hidden;
	transition: var(--transition);
	position: relative;
}

.profile-container:hover {
	transform: translateY(-5px);
	box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
}

/* Close button for entire profile */
.profile-close {
	position: absolute;
	top: 15px;
	right: 20px;
	background: none;
	border: none;
	color: var(--accent);
	font-size: 28px;
	cursor: pointer;
	transition: var(--transition);
	z-index: 10;
	width: 35px;
	height: 35px;
	display: flex;
	align-items: center;
	justify-content: center;
	border-radius: 50%;
	background: rgba(255, 255, 255, 0.9);
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.profile-close:hover {
	background: var(--accent);
	color: white;
	transform: rotate(90deg) scale(1.1);
}

/* Profile Sidebar */
.profile-sidebar {
	flex: 0 0 300px;
	background: linear-gradient(145deg, var(--primary), var(--secondary));
	color: white;
	padding: 40px 30px;
	text-align: center;
	position: relative;
	overflow: hidden;
}

.profile-sidebar::before {
	content: '';
	position: absolute;
	top: -50px;
	right: -50px;
	width: 150px;
	height: 150px;
	border-radius: 50%;
	background: rgba(255, 255, 255, 0.1);
}

.profile-sidebar::after {
	content: '';
	position: absolute;
	bottom: -80px;
	left: -80px;
	width: 200px;
	height: 200px;
	border-radius: 50%;
	background: rgba(255, 255, 255, 0.05);
}

.profile-avatar {
	width: 120px;
	height: 120px;
	border-radius: 50%;
	border: 4px solid rgba(255, 255, 255, 0.3);
	object-fit: cover;
	margin: 0 auto 20px;
	position: relative;
	z-index: 2;
	transition: var(--transition);
}

.profile-sidebar:hover .profile-avatar {
	transform: scale(1.05);
	border-color: rgba(255, 255, 255, 0.5);
}

.profile-name {
	font-size: 24px;
	font-weight: 600;
	margin-bottom: 5px;
	position: relative;
	z-index: 2;
}

.profile-role {
	font-size: 14px;
	opacity: 0.9;
	margin-bottom: 25px;
	position: relative;
	z-index: 2;
}

.profile-stats {
	display: flex;
	justify-content: space-around;
	margin: 30px 0;
	position: relative;
	z-index: 2;
}

.stat-item {
	text-align: center;
}

.stat-value {
	font-size: 24px;
	font-weight: 600;
	display: block;
}

.stat-label {
	font-size: 12px;
	opacity: 0.8;
}

.edit-profile-btn {
	background: rgba(255, 255, 255, 0.2);
	color: white;
	border: 1px solid rgba(255, 255, 255, 0.3);
	padding: 10px 20px;
	border-radius: 30px;
	cursor: pointer;
	font-weight: 500;
	transition: var(--transition);
	position: relative;
	z-index: 2;
	width: 80%;
}

.edit-profile-btn:hover {
	background: rgba(255, 255, 255, 0.3);
	transform: translateY(-2px);
}

/* Profile Content */
.profile-content {
	flex: 1;
	padding: 40px;
}

.section-title {
	font-size: 22px;
	font-weight: 600;
	margin-bottom: 25px;
	color: var(--dark);
	position: relative;
	padding-bottom: 10px;
}

.section-title::after {
	content: '';
	position: absolute;
	bottom: 0;
	left: 0;
	width: 50px;
	height: 3px;
	background: linear-gradient(to right, var(--primary), var(--secondary));
	border-radius: 3px;
}

.info-grid {
	display: grid;
	grid-template-columns: repeat(2, 1fr);
	gap: 20px;
	margin-bottom: 30px;
}

.info-item {
	background: var(--light);
	padding: 15px;
	border-radius: 10px;
	transition: var(--transition);
}

.info-item:hover {
	transform: translateY(-3px);
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

.info-label {
	font-size: 12px;
	color: var(--gray);
	margin-bottom: 5px;
}

.info-value {
	font-size: 16px;
	font-weight: 500;
	color: var(--dark);
}

/* Company Description Styling */
.company-description {
	grid-column: 1/-1; /* Span across both columns */
	background: var(--light);
	padding: 20px;
	border-radius: 10px;
	margin-bottom: 20px;
}

.company-description .info-label {
	font-size: 14px;
	font-weight: 600;
	color: var(--dark);
	margin-bottom: 10px;
}

.company-description .info-value {
	font-size: 14px;
	line-height: 1.6;
	color: var(--gray);
}

/* Edit Form */
.edit-form-container {
	display: none;
	max-width: 1000px;
	width: 100%;
	background: white;
	border-radius: var(--border-radius);
	box-shadow: var(--box-shadow);
	overflow: hidden;
	animation: fadeIn 0.5s ease;
	position: relative;
}

@
keyframes fadeIn {from { opacity:0;
	transform: translateY(20px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}
.form-header {
	background: linear-gradient(145deg, var(--primary), var(--secondary));
	color: white;
	padding: 25px 30px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.form-title {
	font-size: 22px;
	font-weight: 600;
}

.close-form {
	background: none;
	border: none;
	color: white;
	font-size: 24px;
	cursor: pointer;
	transition: var(--transition);
	padding: 0;
	width: 30px;
	height: 30px;
	display: flex;
	align-items: center;
	justify-content: center;
	border-radius: 50%;
}

.close-form:hover {
	background-color: rgba(255, 255, 255, 0.1);
	transform: rotate(90deg);
}

.form-body {
	padding: 30px;
}

.form-grid {
	display: grid;
	grid-template-columns: repeat(2, 1fr);
	gap: 20px;
}

.form-group {
	margin-bottom: 20px;
}

.form-label {
	display: block;
	margin-bottom: 8px;
	font-weight: 500;
	color: var(--dark);
}

.form-input {
	width: 100%;
	padding: 12px 15px;
	border: 1px solid #e0e0e0;
	border-radius: 8px;
	font-size: 14px;
	transition: var(--transition);
}

.form-input:focus {
	border-color: var(--primary-light);
	box-shadow: 0 0 0 3px rgba(106, 17, 203, 0.1);
	outline: none;
}

/* Password field specific styling */
.password-field {
	position: relative;
}

.password-toggle {
	position: absolute;
	right: 15px;
	top: 50%;
	transform: translateY(-50%);
	background: none;
	border: none;
	color: var(--gray);
	cursor: pointer;
	font-size: 14px;
}

.password-toggle:hover {
	color: var(--primary);
}

/* Textarea Styling */
.form-textarea {
	width: 100%;
	padding: 12px 15px;
	border: 1px solid #e0e0e0;
	border-radius: 8px;
	font-size: 14px;
	font-family: 'Poppins', sans-serif;
	resize: vertical;
	min-height: 100px;
	transition: var(--transition);
}

.form-textarea:focus {
	border-color: var(--primary-light);
	box-shadow: 0 0 0 3px rgba(106, 17, 203, 0.1);
	outline: none;
}

/* Full width form group */
.form-group-full {
	grid-column: 1/-1;
}

/* Password section styling */
.password-section {
	grid-column: 1/-1;
	border-top: 2px solid var(--light);
	padding-top: 20px;
	margin-top: 10px;
}

.password-section-title {
	font-size: 18px;
	font-weight: 600;
	color: var(--dark);
	margin-bottom: 15px;
	padding-bottom: 10px;
	border-bottom: 1px solid var(--light);
}

.password-note {
	font-size: 12px;
	color: var(--gray);
	margin-top: 5px;
	font-style: italic;
}

.form-actions {
	display: flex;
	justify-content: flex-end;
	gap: 15px;
	margin-top: 30px;
}

.btn {
	padding: 12px 25px;
	border: none;
	border-radius: 8px;
	font-weight: 500;
	cursor: pointer;
	transition: var(--transition);
}

.error-msg {
	color: red;
	font-size: 12px;
	margin-top: 5px;
}

.btn-primary {
	background: linear-gradient(145deg, var(--primary), var(--secondary));
	color: white;
}

.btn-primary:hover {
	transform: translateY(-2px);
	box-shadow: 0 5px 15px rgba(106, 17, 203, 0.3);
}

.btn-secondary {
	background: #f8f9fa;
	color: var(--gray);
	border: 1px solid #e0e0e0;
}

.btn-secondary:hover {
	background: #e9ecef;
}

/* Profile Closed State */
.profile-closed {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	text-align: center;
	padding: 60px 30px;
	background: white;
	border-radius: var(--border-radius);
	box-shadow: var(--box-shadow);
	max-width: 500px;
	margin: 0 auto;
	animation: fadeIn 0.5s ease;
}

.profile-closed h3 {
	color: var(--dark);
	margin-bottom: 15px;
	font-size: 24px;
}

.profile-closed p {
	color: var(--gray);
	margin-bottom: 25px;
	font-size: 16px;
}

.reopen-profile-btn {
	background: linear-gradient(145deg, var(--primary), var(--secondary));
	color: white;
	border: none;
	padding: 12px 30px;
	border-radius: 30px;
	font-weight: 500;
	cursor: pointer;
	transition: var(--transition);
} 
.error {
    color: red;
    font-size: 12px;
    margin-top: 5px;
}


.reopen-profile-btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 5px 15px rgba(106, 17, 203, 0.3);
}

.simple-email {
	padding: 12px 15px;
	font-size: 14px;
	color: var(--dark);
	background-color: #f8f9fa;
	border-radius: 8px;
	border: 1px solid #e0e0e0;
}

.email-display {
	padding: 12px 15px;
	font-size: 14px;
	color: var(--dark);
	background-color: #f8f9fa;
	border-radius: 8px;
	border: 1px solid #e0e0e0;
	font-weight: 500;
}
/* Responsive */
@media ( max-width : 992px) {
	.main-content {
		margin-left: 0;
		padding: 20px;
	}
	.profile-container, .edit-form-container {
		max-width: 100%;
	}
	.profile-sidebar {
		flex: 0 0 250px;
	}
}

@media ( max-width : 768px) {
	.profile-container {
		flex-direction: column;
	}
	.profile-sidebar {
		flex: none;
	}
	.info-grid, .form-grid {
		grid-template-columns: 1fr;
	}
	.form-actions {
		flex-direction: column;
	}
	.btn {
		width: 100%;
	}
	.profile-close {
		top: 10px;
		right: 15px;
	}
	.company-description {
		grid-column: 1;
	}
}
</style>

<script>


document.addEventListener('DOMContentLoaded', function() {
    <% if (session.getAttribute("editFormOpen") != null) { %>
        document.getElementById('profileContainer').style.display = 'none';
        document.getElementById('editFormContainer').style.display = 'block';
        <% session.removeAttribute("editFormOpen"); %>
    <% } %>
});

	




function showEditForm() {
    document.getElementById('profileContainer').style.display = 'none';
    document.getElementById('editFormContainer').style.display = 'block';
}

function cancelEdit() {
    document.getElementById('editFormContainer').style.display = 'none';
    document.getElementById('profileContainer').style.display = 'flex';
}

function closeProfile() {
    // Hide both profile and edit form
    document.getElementById('profileContainer').style.display = 'none';
    document.getElementById('editFormContainer').style.display = 'none';
    
    // Show the closed state message
    document.getElementById('profileClosed').style.display = 'flex';
}

function reopenProfile() {
    // Show the profile again
    document.getElementById('profileClosed').style.display = 'none';
    document.getElementById('profileContainer').style.display = 'flex';
}

// Password toggle functionality
function togglePassword(fieldId, button) {
    const passwordField = document.getElementById(fieldId);
    const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordField.setAttribute('type', type);
    
    // Toggle eye icon
    button.innerHTML = type === 'password' ? '👁' : '👁‍🗨';
}

// Password validation
function validatePassword() {
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const passwordError = document.getElementById('passwordError');
    
    if (newPassword !== confirmPassword) {
        passwordError.textContent = 'Passwords do not match';
        return false;
    }
    
    if (newPassword.length > 0 && newPassword.length < 6) {
        passwordError.textContent = 'Password must be at least 6 characters long';
        return false;
    }
    
    passwordError.textContent = '';
    return true;
}

// Form submission validation
function validateForm() {
    const oldPassword = document.getElementById('oldPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    
    // If any password field is filled, all must be filled
    if (oldPassword || newPassword || confirmPassword) {
        if (!oldPassword) {
            alert('Please enter your current password');
            return false;
        }
        if (!newPassword) {
            alert('Please enter new password');
            return false;
        }
        if (!confirmPassword) {
            alert('Please confirm your new password');
            return false;
        }
        if (!validatePassword()) {
            return false;
        }
    }
    
    return true;
}

// Add some interactive elements
document.addEventListener('DOMContentLoaded', function() {
    // Add animation to stats
    const statValues = document.querySelectorAll('.stat-value');
    statValues.forEach(stat => {
        const target = parseInt(stat.getAttribute('data-target'));
        let current = 0;
        const increment = target / 50;
        
        const updateStat = () => {
            if (current < target) {
                current += increment;
                stat.textContent = Math.ceil(current);
                setTimeout(updateStat, 20);
            } else {
                stat.textContent = target;
            }
        };
        
        updateStat();
    });
    
    // Add form validation on submit
    const form = document.getElementById('editFormContainer');
    form.addEventListener('submit', function(e) {
        if (!validateForm()) {
            e.preventDefault();
        }
    });
});
</script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="./include/sidebar.jsp" />
		<jsp:include page="./include/header.jsp" />

		<div class="main-content">
			<!-- Profile Closed State (Initially Hidden) -->


			<!-- Profile Container -->
			<div class="profile-container" id="profileContainer">
				<!-- Close button for entire profile -->
				<button class="profile-close" onclick="closeProfile()"
					title="Close Profile">&times;</button>

				<!-- Profile Sidebar -->
				<div class="profile-sidebar">
					<img src="assets/img/profile.jpg" alt="Profile"
						class="profile-avatar">
					<h2 class="profile-name"><%=user.getUser_name()%></h2>
					<p class="profile-role"><%=user.getUser_role()%></p>

					

					<button class="edit-profile-btn" onclick="showEditForm()">
						Edit Profile</button>
				</div>

				<!-- Profile Content -->
				<div class="profile-content">
					<h3 class="section-title">Personal Information</h3>

					<div class="info-grid">
						<div class="info-item">
							<div class="info-label">Email</div>
							<div class="info-value"><%=user.getUser_email()%></div>
						</div>

						<div class="info-item">
							<div class="info-label">Location</div>
							<div class="info-value"><%=user.getLocation()%></div>
						</div>

						<div class="info-item">
							<div class="info-label">Role</div>
							<div class="info-value"><%=user.getUser_role()%></div>
						</div>

						<%
						if (user.getUser_role() == com.jobportal.enums.RoleType.RECRUITER) {
						%>
						<div class="info-item">
							<div class="info-label">Company</div>
							<div class="info-value"><%=company.getCompany_name()%></div>
						</div>

						<div class="info-item">
							<div class="info-label">Company Location</div>
							<div class="info-value"><%=company.getCompany_location()%></div>
						</div>

						<div class="info-item">
							<div class="info-label">Phone</div>
							<div class="info-value"><%=company.getMobile()%></div>
						</div>

						<!-- Company Description Section -->
						<div class="company-description">
							<div class="info-label">Company Description</div>
							<div class="info-value"><%=company.getCompany_description() != null ? company.getCompany_description() : ""%></div>
						</div>
						<%
						}
						%>
					</div>
				</div>
			</div>

			<!-- Edit Form Container -->
			<form class="edit-form-container" id="editFormContainer"
				action="${pageContext.request.contextPath}/AuthenticationServlet"
				method="post" onsubmit="return validateForm()">
				<div class="form-header">
					<h3 class="form-title">Edit Profile</h3>
					<button type="button" class="close-form" onclick="cancelEdit()">&times;</button>
				</div>

				<div class="form-body">
					<div class="form-grid">
						<div class="form-group">
							<label class="form-label">Full Name</label> <input type="text"
								class="form-input" name="user_name"
								value="<%=user.getUser_name()%>" required>
						</div>

						<div class="form-group">
							<label class="form-label">Email</label> <input type="hidden"
								name="email" />
							<div class="email-display">
								<%=user.getUser_email()%>
							</div>

						</div>

						<div class="form-group">
							<label class="form-label">Location</label> <input type="text"
								class="form-input" name="location"
								value="<%=user.getLocation()%>" required>
						</div>

						<%
						if (user.getUser_role() == com.jobportal.enums.RoleType.RECRUITER && company != null) {
						%>
						<div class="form-group">
							<label class="form-label">Company Name</label> <input type="text"
								class="form-input" name="company_name"
								value="<%=company.getCompany_name()%>">
						</div>

						<div class="form-group">
							<label class="form-label">Company Location</label> <input
								type="text" class="form-input" name="company_location"
								value="<%=company.getCompany_location()%>">
						</div>

						<div class="form-group">
							<label class="form-label">Phone</label> <input type="text"
								class="form-input" name="phoneNo"
								value="<%=company.getMobile()%>">
						</div>

						<!-- Company Description Textarea -->
						<div class="form-group form-group-full">
							<label class="form-label">Company Description</label>
							<textarea class="form-textarea" name="company_description"
								placeholder="Enter company description"><%=company.getCompany_description() != null ? company.getCompany_description() : ""%></textarea>
						</div>
						<%
						}
						%>

						<!-- Password Change Section -->
						<div class="password-section">
							<div class="password-section-title">Change Password</div>
							<div class="form-grid">
								<div class="form-group">
									<label class="form-label">Current Password</label>
									<div class="password-field">
										<input type="password" class="form-input" id="oldPassword"
											name="old_password" placeholder="Enter current password">
										<button type="button" class="password-toggle"
											onclick="togglePassword('oldPassword', this)">👁</button>
									</div>
									<%
									if (session.getAttribute("passwordInvalidError") != null) {
									%>
									<div class="error"><%=session.getAttribute("passwordInvalidError")%></div>
									<%
									session.removeAttribute("passwordInvalidError");
									}
									%>
									<div class="password-note">Leave blank if you don't want
										to change password</div>
								</div>

								<div class="form-group">
									<label class="form-label">New Password</label>
									<div class="password-field">
										<input type="password" class="form-input" id="newPassword"
											name="new_password" placeholder="Enter new password">
										<button type="button" class="password-toggle"
											onclick="togglePassword('newPassword', this)">👁</button>
									</div>
									<!-- New Password Error -->
									<%
									if (session.getAttribute("passwordInvalidError1") != null) {
									%>
									<div class="error"><%=session.getAttribute("passwordInvalidError1")%></div>
									<%
									session.removeAttribute("passwordInvalidError1");
									}
									%>


								</div>

								<div class="form-group">
									<label class="form-label">Confirm New Password</label>
									<div class="password-field">
										<input type="password" class="form-input" id="confirmPassword"
											name="confirm_password" placeholder="Confirm new password"
											onkeyup="validatePassword()">
										<button type="button" class="password-toggle"
											onclick="togglePassword('confirmPassword', this)">👁</button>
									</div>
									<!-- Confirm Password Error -->
									<%
									if (session.getAttribute("passwordError") != null) {
									%>
									<div class="error"><%=session.getAttribute("passwordError")%></div>
									<%
									session.removeAttribute("passwordError");
									}
									%>

								</div>





							</div>
						</div>
					</div>

					<div class="form-actions">
						<button type="button" class="btn btn-secondary"
							onclick="cancelEdit()">Cancel</button>
						<button type="submit" class="btn btn-primary" name="action"
							value="update">Save Changes</button>
					</div>
				</div>
			</form>
		</div>

		<jsp:include page="./include/footer.jsp" />
	</div>
	<jsp:include page="./include/scripts.jsp"></jsp:include>
	
	
</body>
</html>