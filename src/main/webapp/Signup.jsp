<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Choose Your Role</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(135deg, #6dd5ed, #2193b0);
        height: 100vh;
        margin: 0;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .container {
        text-align: center;
        background: #fff;
        padding: 40px;
        border-radius: 15px;
        box-shadow: 0 8px 20px rgba(0,0,0,0.2);
    }
    h1 {
        margin-bottom: 20px;
        color: #333;
    }
    .btn {
        display: inline-block;
        padding: 12px 25px;
        margin: 10px;
        font-size: 18px;
        font-weight: bold;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: 0.3s;
        text-decoration: none;
        color: white;
    }
    .jobseeker {
        background: #28a745;
    }
    .recruiter {
        background: #007bff;
    }
    .btn:hover {
        transform: scale(1.05);
        opacity: 0.9;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Job Portal</h1>
        <p>Select where you want to go:</p>
        <a href="./JobSeeker.jsp" class="btn jobseeker">I am a Job Seeker</a>
        <a href="./Recruiter.jsp" class="btn recruiter">I am a Recruiter</a>
    </div>
</body>
</html>
