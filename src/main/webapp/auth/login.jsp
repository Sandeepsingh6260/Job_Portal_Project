<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Job Portal Login</title>
<style>
/* Your existing CSS remains the same */
body {
    margin: 0;
    padding: 0;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url("https://images.pexels.com/photos/3184465/pexels-photo-3184465.jpeg") no-repeat center center/cover;
    font-family: "Segoe UI", sans-serif;
}

.frame {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 15px;
    box-shadow: 0px 10px 25px rgba(0, 0, 0, 0.3);
    width: 400px;
    padding: 35px 30px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

h1 {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
    font-size: 28px;
    font-weight: 700;
}

form label {
    display: block;
    margin-top: 15px;
    font-weight: 600;
    color: #222;
    text-align: left;
    width: 100%;
}

input {
    width: 100%;
    padding: 12px 14px;
    margin-top: 5px;
    border-radius: 8px;
    border: 1px solid #ccc;
    font-size: 14px;
    transition: all 0.3s ease;
}

input:focus {
    border-color: #4facfe;
    box-shadow: 0 0 6px rgba(79, 172, 254, 0.6);
    outline: none;
}

button {
    margin-top: 25px;
    padding: 12px 40px;
    background: linear-gradient(90deg, #4facfe, #00f2fe);
    border: none;
    border-radius: 25px;
    color: white;
    font-size: 15px;
    font-weight: bold;
    cursor: pointer;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
}

button:hover {
    transform: translateY(-2px);
    box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.25);
}

.extra-links {
    margin-top: 20px;
    text-align: center;
    font-size: 14px;
    width: 100%;
}

.extra-links a {
    color: #4facfe;
    text-decoration: none;
    margin: 0 10px;
    transition: 0.3s;
    font-weight: 500;
}

.extra-links a:hover {
    color: #00f2fe;
}

.error {
    color: red;
    font-size: 13px;
    margin-top: 5px;
}
</style>
</head>
<body>

<form action="${pageContext.request.contextPath}/AuthenticationServlet" method="post">
    <div class="frame">
        <h1>Login</h1>

        <!-- Hidden dummy fields to prevent autofill -->
        
        <input type="text" style="display:none">
        <input type="password" style="display:none">

        <!-- Email -->
        <label for="user_email">Email</label>
        <input type="email" id="user_email" name="user_email" 
               placeholder="Enter your email" 
               value="<%= session.getAttribute("user_email_val") != null ? session.getAttribute("user_email_val") : "" %>">
        <div class="error">
            <%= session.getAttribute("emailError") != null ? session.getAttribute("emailError") : "" %>
        </div>

        <!-- Password -->
        <label for="user_password">Password</label>
        <input type="password" id="user_password" name="user_password" placeholder="Enter your password">
        <div class="error">
            <%= session.getAttribute("passwordError") != null ? session.getAttribute("passwordError") : "" %>
        </div>

        <!-- Invalid login -->
        <div class="error">
            <%= session.getAttribute("loginError") != null ? session.getAttribute("loginError") : "" %>
        </div>

        <button type="submit" name="action" value="login">Login</button>

        <div class="extra-links">
            <a href="#">Forgot Password?</a> 
            <a href="./Signup.jsp">Sign Up</a>
        </div>
    </div>
</form>

</body>
</html>