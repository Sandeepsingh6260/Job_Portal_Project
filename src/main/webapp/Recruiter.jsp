<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Job Seeker Dashboard</title>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
<style>
*{margin:0;padding:0;box-sizing:border-box;font-family:'Poppins',sans-serif;}
body{
    background:linear-gradient(135deg,#1f1c2c,#928dab);
    min-height:100vh;
    color:#fff;
}

/* HEADER */
header{
    display:flex;justify-content:space-between;align-items:center;
    padding:20px 50px;
    backdrop-filter:blur(15px);
    background:rgba(255,255,255,0.05);
    border-bottom:1px solid rgba(255,255,255,0.2);
    box-shadow:0 5px 25px rgba(0,0,0,0.5);
    position:sticky;top:0;z-index:999;
}
header h1{
    font-size:28px;font-weight:700;
    background:linear-gradient(45deg,#00c6ff,#0072ff);
    -webkit-background-clip:text;
    -webkit-text-fill-color:transparent;
}
header input{
    padding:10px 20px;border:none;outline:none;
    border-radius:30px;
    background:rgba(255,255,255,0.1);
    color:#fff;
    box-shadow:0 0 10px rgba(255,255,255,0.3);
    transition:0.3s;
}
header input:focus{box-shadow:0 0 20px #00c6ff;}
header a{
    text-decoration:none;padding:10px 25px;border-radius:30px;
    background:linear-gradient(90deg,#ff416c,#ff4b2b);
    color:#fff;font-weight:700;
    box-shadow:0 0 15px rgba(255,65,108,0.7);
    transition:0.3s;
}
header a:hover{transform:scale(1.1);box-shadow:0 0 25px rgba(255,65,108,1);}

/* CONTAINER */
.container{display:flex;gap:25px;padding:30px;}

/* SIDEBAR */
.sidebar{
    width:260px;
    background:rgba(255,255,255,0.08);
    border-radius:20px;
    padding:30px;
    box-shadow:0 10px 25px rgba(0,0,0,0.5);
    backdrop-filter:blur(20px);
}
.sidebar h2{font-size:22px;margin-bottom:20px;}
.sidebar ul{list-style:none;}
.sidebar ul li{margin:15px 0;}
.sidebar ul li a{
    text-decoration:none;color:#fff;
    display:block;padding:12px 20px;
    border-radius:12px;
    transition:0.3s;
}
.sidebar ul li a:hover{
    background:linear-gradient(90deg,#00c6ff,#0072ff);
    box-shadow:0 0 20px #00c6ff;
}

/* MAIN */
.main{flex:1;}

/* CARDS */
.cards{
    display:grid;grid-template-columns:repeat(3,1fr);
    gap:25px;margin-bottom:35px;
}
.card{
    padding:25px;border-radius:20px;
    background:rgba(255,255,255,0.08);
    backdrop-filter:blur(20px);
    box-shadow:0 10px 25px rgba(0,0,0,0.6);
    transition:0.3s;
    position:relative;overflow:hidden;
}
.card:hover{
    transform:translateY(-10px);
    box-shadow:0 20px 40px rgba(0,0,0,0.8);
}
.card h3{font-size:18px;margin-bottom:10px;}
.card p{font-size:32px;font-weight:700;}

/* APPLICATIONS */
.applications{display:flex;flex-direction:column;gap:20px;}
.application{
    padding:20px;border-radius:18px;
    background:rgba(255,255,255,0.08);
    backdrop-filter:blur(20px);
    display:flex;justify-content:space-between;align-items:center;
    box-shadow:0 10px 25px rgba(0,0,0,0.6);
    transition:0.3s;
}
.application:hover{transform:scale(1.02);box-shadow:0 20px 40px rgba(0,0,0,0.8);}
.application h4{font-size:18px;font-weight:600;}
.status{
    padding:8px 18px;border-radius:30px;
    font-weight:600;text-transform:uppercase;font-size:13px;
}
.pending{background:#facc15;color:#222;}
.shortlisted{background:#22c55e;color:#fff;}
.rejected{background:#ef4444;color:#fff;}
</style>
</head>
<body>

<header>
    <h1>JobPortal</h1>
    <input type="text" placeholder="🔍 Search jobs...">
    <a href="#">Logout</a>
</header>

<div class="container">
    <div class="sidebar">
        <h2>👋 Hi, Manoj</h2>
        <ul>
            <li><a href="#">📄 Profile</a></li>
            <li><a href="#">📑 My Applications</a></li>
            <li><a href="#">⭐ Saved Jobs</a></li>
        </ul>
    </div>

    <div class="main">
        <div class="cards">
            <div class="card">
                <h3>Total Applications</h3>
                <p>25</p>
            </div>
            <div class="card">
                <h3>Shortlisted</h3>
                <p>10</p>
            </div>
            <div class="card">
                <h3>Rejected</h3>
                <p>5</p>
            </div>
        </div>

        <h2 style="margin:15px 0;">📌 Recent Applications</h2>
        <div class="applications">
            <div class="application">
                <div>
                    <h4>Frontend Developer</h4>
                    <p>ABC Tech, Mumbai</p>
                </div>
                <div class="status pending">Pending</div>
            </div>
            <div class="application">
                <div>
                    <h4>Java Developer</h4>
                    <p>XYZ Solutions, Delhi</p>
                </div>
                <div class="status shortlisted">Shortlisted</div>
            </div>
            <div class="application">
                <div>
                    <h4>UI/UX Designer</h4>
                    <p>Creative Minds, Bangalore</p>
                </div>
                <div class="status rejected">Rejected</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
