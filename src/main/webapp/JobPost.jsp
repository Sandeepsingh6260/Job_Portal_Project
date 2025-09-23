<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Post a Job</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
body {
	margin: 0;
	padding: 0;
	font-family: "Segoe UI", sans-serif;
	background: #f4f7f9;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
}

.container {
	background: #fff;
	padding: 25px;
	width: 600px;
	border-radius: 10px;
	box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
}

h1 {
	text-align: center;
	margin-bottom: 20px;
	font-size: 26px;
	color: #2d3436;
}

label {
	display: block;
	margin-top: 12px;
	font-weight: 600;
	font-size: 14px;
}

input, textarea, select {
	width: 100%;
	padding: 10px 12px;
	margin-top: 6px;
	border-radius: 6px;
	border: 1px solid #ccc;
	font-size: 15px;
	box-sizing: border-box;
}

textarea {
	resize: vertical;
	min-height: 100px;
}

button {
	width: 100%;
	padding: 12px;
	margin-top: 20px;
	border: none;
	border-radius: 8px;
	background: linear-gradient(90deg, #0984e3, #00cec9);
	color: white;
	font-size: 16px;
	font-weight: bold;
	cursor: pointer;
	transition: 0.2s;
}

button:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.back-btn {
	background: #636e72;
	margin-top: 10px;
}

.back-btn:hover {
	background: #2d3436;
}

.error {
	color: #d63031;
	font-size: 13px;
	margin-top: 5px;
}

.success {
	color: #00b894;
	font-size: 13px;
	margin-top: 5px;
}
</style>
</head>
<body>

	<div class="container">
		<h1>Post a New Job</h1>

		<!-- Success / Error messages -->
		<%
		if (session.getAttribute("successMsg") != null) {
		%>
		<div class="success"><%=session.getAttribute("successMsg")%></div>
		<!-- ✅ Back button visible only when job is posted successfully -->
		<form action="recruiter.jsp" method="get">
			<button type="submit" class="back-btn">Back to Dashboard</button>
		</form>
		<%
		session.removeAttribute("successMsg");
		%>
		<%
		}
		%>

		<%
		if (session.getAttribute("errorMsg") != null) {
		%>
		<div class="error"><%=session.getAttribute("errorMsg")%></div>
		<%
		session.removeAttribute("errorMsg");
		%>
		<%
		}
		%>

		<form action="${pageContext.request.contextPath}/RecruitersServlet"
			method="post">
			<input type="hidden" name="action" value="postJob"> <label
				for="title">Job Title</label> <input type="text" id="title"
				name="title"
				value="<%=session.getAttribute("title_val") != null ? session.getAttribute("title_val") : ""%>">

			<label for="description">Description</label>
			<textarea id="description" name="description"><%=session.getAttribute("desc_val") != null ? session.getAttribute("desc_val") : ""%></textarea>

			<label for="location">Location</label> <input type="text"
				id="location" name="location"
				value="<%=session.getAttribute("loc_val") != null ? session.getAttribute("loc_val") : ""%>">

			<label for="salary">Salary (in ₹)</label> <input type="number"
				step="0.01" id="salary" name="salary"
				value="<%=session.getAttribute("salary_val") != null ? session.getAttribute("salary_val") : ""%>">

			<label for="experience_required">Experience Required (years)</label>
			<input type="number" id="experience_required"
				name="experience_required" min="0"
				value="<%=session.getAttribute("exp_val") != null ? session.getAttribute("exp_val") : ""%>">

			<label for="job_type">Job Type</label> <select id="job_type"
				name="job_type">
				<option value="1"
					<%="1".equals(String.valueOf(session.getAttribute("job_type_val"))) ? "selected" : ""%>>Full-Time</option>
				<option value="2"
					<%="2".equals(String.valueOf(session.getAttribute("job_type_val"))) ? "selected" : ""%>>Part-Time</option>
				<option value="3"
					<%="3".equals(String.valueOf(session.getAttribute("job_type_val"))) ? "selected" : ""%>>Internship</option>
				<option value="4"
					<%="4".equals(String.valueOf(session.getAttribute("job_type_val"))) ? "selected" : ""%>>Contract</option>
			</select> <label for="mobile_no">Mobile Number</label> <input type="text"
				id="mobile_no" name="mobile_no"
				value="<%=session.getAttribute("mobile_val") != null ? session.getAttribute("mobile_val") : ""%>">

			<button type="submit">Post Job</button>
		</form>
	</div>

</body>
</html>
