<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Recruiter Registration</title>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(135deg, #667eea, #764ba2);
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
        box-shadow: 0px 8px 25px rgba(0,0,0,0.2);
        width: 420px;
        animation: fadeIn 0.8s ease-in-out;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(-20px); }
        to { opacity: 1; transform: translateY(0); }
    }

    h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
    }

    label {
        display: block;
        margin-bottom: 6px;
        font-weight: 600;
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
        font-size: 14px;
    }

    input:focus {
        border-color: #667eea;
        box-shadow: 0 0 6px rgba(102,126,234,0.6);
    }

    button {
        width: 100%;
        background: #667eea;
        color: white;
        padding: 12px;
        font-size: 16px;
        font-weight: bold;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: 0.3s;
    }

    button:hover {
        background: #764ba2;
    }

    .error {
        color: red;
        font-size: 14px;
        margin-bottom: 15px;
        text-align: center;
    }

    .success {
        color: green;
        font-size: 14px;
        margin-bottom: 15px;
        text-align: center;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>Recruiter Registration</h2>

        <!-- Messages -->
        <p class="error">
            <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
        </p>
        <p class="success">
            <%= request.getAttribute("successMessage") != null ? request.getAttribute("successMessage") : "" %>
        </p>

        <form action="./RecruiterServlet" method="post">
            <label for="recruiter_name">Full Name:</label>
            <input type="text" id="recruiter_name" name="recruiter_name" required>

            <label for="recruiter_email">Email:</label>
            <input type="email" id="recruiter_email" name="recruiter_email" required>

            <label for="recruiter_password">Password:</label>
            <input type="password" id="recruiter_password" name="recruiter_password" required>

            <label for="confirm_password">Confirm Password:</label>
            <input type="password" id="confirm_password" name="confirm_password" required>

            <label for="company_name">Company Name:</label>
            <input type="text" id="company_name" name="company_name" required>

            <label for="location">Location:</label>
            <input type="text" id="location" name="location" required>

            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>
