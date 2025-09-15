<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome | Index</title>
<style>
    body {
        margin: 0;
        padding: 0;
        font-family: "Segoe UI", sans-serif;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background: linear-gradient(135deg, #74ebd5 0%, #9face6 100%);
    }

    .box {
        background: #fff;
        padding: 40px;
        border-radius: 20px;
        text-align: center;
        width: 400px;
        box-shadow: 0px 10px 20px rgba(0,0,0,0.25);
        position: relative;
    }

    .box img {
        width: 80px;
        height: 80px;
        margin-bottom: 20px;
    }

    h1 {
        margin-bottom: 25px;
        color: #333;
    }

    .btn-container {
        display: flex;
        justify-content: space-between;
        gap: 20px;
    }

    .btn {
        flex: 1;
        padding: 14px;
        font-size: 16px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        background: #007bff;
        color: white;
        transition: 0.3s;
        text-decoration: none;
        font-weight: bold;
    }

    .btn:hover {
        transform: scale(1.05);
        background: #0056b3;
    }
</style>
</head>
<body>

<div class="box">
    <!-- Icon Image -->
    <img src="https://cdn-icons-png.flaticon.com/512/295/295128.png" alt="User Icon">
    <h1>Welcome to Job Portal</h1>

    <div class="btn-container">
        <a href="./AuthenticationServlet" class="btn">Login</a>
        <a href="register.jsp" class="btn">Signup</a>
    </div>
</div>

</body>
</html>
