<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome | Job Portal</title>
<style>
    * { margin: 0; padding: 0; box-sizing: border-box; font-family: "Segoe UI", sans-serif; }

    body {
        background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        color: #333;
    }

    /* Navbar */
    header {
        background: rgba(255, 255, 255, 0.95);
        padding: 15px 50px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0px 4px 10px rgba(0,0,0,0.1);
        position: sticky;
        top: 0;
        z-index: 1000;
    }

    header h2 {
        color: #007bff;
        font-weight: bold;
    }

    nav a {
        margin: 0 15px;
        text-decoration: none;
        color: #333;
        font-weight: 600;
        transition: 0.3s;
    }
    nav a:hover {
        color: #007bff;
    }

    /* Hero Section */
    .hero {
        height: 90vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        text-align: center;
        color: #fff;
        background: url("https://images.unsplash.com/photo-1521791136064-7986c2920216?auto=format&fit=crop&w=1350&q=80") no-repeat center center/cover;
        position: relative;
    }
    .hero::after {
        content: "";
        position: absolute;
        top: 0; left: 0;
        width: 100%; height: 100%;
        background: rgba(0,0,0,0.5);
    }
    .hero-content {
        position: relative;
        z-index: 2;
    }
    .hero h1 {
        font-size: 48px;
        margin-bottom: 15px;
    }
    .hero p {
        font-size: 18px;
        margin-bottom: 25px;
    }
    .btn {
        padding: 12px 25px;
        border-radius: 8px;
        text-decoration: none;
        font-size: 16px;
        font-weight: bold;
        background: linear-gradient(90deg, #4facfe, #00f2fe);
        color: #fff;
        transition: 0.3s;
    }
    .btn:hover {
        transform: translateY(-2px);
        box-shadow: 0px 6px 15px rgba(0,0,0,0.3);
    }

    /* Sections */
    section {
        padding: 60px 80px;
        text-align: center;
        background: #fff;
    }
    section:nth-child(even) {
        background: #f9f9f9;
    }
    section h2 {
        font-size: 32px;
        margin-bottom: 20px;
        color: #007bff;
    }
    section p {
        max-width: 800px;
        margin: auto;
        font-size: 16px;
        line-height: 1.6;
    }

    /* Footer */
    footer {
        background: #222;
        color: #fff;
        text-align: center;
        padding: 20px;
    }
</style>
</head>
<body>

<!-- Navbar -->
<header>
    <h2>JobPortal</h2>
    <nav>
        <a href="#services">Services</a>
        <a href="#about">About</a>
        <a href="#contact">Contact</a>
        <a href="./auth/login.jsp">Login</a>
        <a href="./auth/Signup.jsp">Signup</a>
    </nav>
</header>

<!-- Hero Section -->
<section class="hero">
    <div class="hero-content">
        <h1>Welcome to Job Portal</h1>
        <p>Find your dream job or hire the best talent with ease.</p>
        <a href="./auth/Signup.jsp" class="btn">Get Started</a>
    </div>
</section>

<!-- Services Section -->
<section id="services">
    <h2>Our Services</h2>
    <p>We connect job seekers and recruiters. Upload resumes, apply for jobs, and track applications. Recruiters can post jobs, search resumes, and manage applicants efficiently.</p>
</section>

<!-- About Section -->
<section id="about">
    <h2>About Us</h2>
    <p>Job Portal is a platform built to make hiring and job hunting easier. Whether you're a fresh graduate looking for opportunities or a recruiter seeking top talent, we simplify the process with modern tools.</p>
</section>

<!-- Contact Section -->
<section id="contact">
    <h2>Contact Us</h2>
    <p>Email: support@jobportal.com | Phone: +91 98765 43210</p>
</section>

<!-- Footer -->
<footer>
    <p>&copy; 2025 Job Portal. All Rights Reserved.</p>
</footer>

</body>
</html>
