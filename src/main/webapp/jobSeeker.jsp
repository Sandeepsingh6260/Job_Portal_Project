<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Job Seeker Registration</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(135deg, #74ebd5, #ACB6E5);
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        background: #fff;
        padding: 30px 40px;
        border-radius: 12px;
        box-shadow: 0px 8px 20px rgba(0,0,0,0.15);
        width: 400px;
    }

    h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
    }

    label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #444;
    }

    input {
        width: 100%;
        padding: 10px;
        margin-bottom: 18px;
        border: 1px solid #ccc;
        border-radius: 6px;
        outline: none;
        transition: 0.3s;
    }

    input:focus {
        border-color: #74ebd5;
        box-shadow: 0 0 5px rgba(116, 235, 213, 0.7);
    }

    button {
        width: 100%;
        background: #74ebd5;
        color: #333;
        padding: 12px;
        font-size: 16px;
        font-weight: bold;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: 0.3s;
    }

    button:hover {
        background: #ACB6E5;
    }

    .error {
        color: red;
        font-size: 14px;
        margin-bottom: 15px;
        text-align: center;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>Job Seeker Registration</h2>

        <!-- Error message display -->
        <p class="error">
            <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
        </p>

        <form action="./JobSeekerServlet" method="post" enctype="multipart/form-data">
            <label for="user_name">Full Name:</label>
            <input type="text" id="user_name" name="user_name">

            <label for="user_email">Email:</label>
            <input type="email" id="user_email" name="user_email" >

            <label for="user_password">Password:</label>
			<input type="password" id="user_password" name="user_password">
			
			<label for="confirm_password">Confirm Password:</label>
			<input type="password" id="confirm_password" name="confirm_password">


            <label for="resume">Resume (PDF, Max 2MB):</label>
            <input type="file" id="resume" name="resume" accept=".pdf" >

            <label for="skills">Skills:</label>
            <input type="text" id="skills" name="skills">

            <label for="experience_years">Experience (years):</label>
            <input type="number" id="experience_years" name="experience_years" min="0">

            <label for="location">Location:</label>
            <input type="text" id="location" name="location" >

            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>
