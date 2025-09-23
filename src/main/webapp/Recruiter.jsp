<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Recruiter Dashboard - Job Portal</title>
<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no'
	name='viewport' />
 <jsp:include page="./include/head.jsp" />
  
 </head>
<body>
	<div class="wrapper">
		<!-- Sidebar -->
		<jsp:include page="./include/sidebar.jsp" />
		<!-- End Sidebar -->
		
		<jsp:include page="./include/header.jsp"></jsp:include>
		
			 <table border="1" cellspacing="0" cellpadding="10" 
           style="width:80%; margin:20px auto; border-collapse:collapse; text-align:left;">
        <caption style="font-size:1.5em; font-weight:bold; margin-bottom:10px;">
            Employee List
        </caption>
        <thead>
            <tr style="background-color:#1d7af3; color:white;">
                <th>ID</th>
                <th>Name</th>
                <th>Department</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <tr style="background-color:#f2f2f2;">
                <td>101</td>
                <td>Manoj Lodhi</td>
                <td>IT</td>
                <td>manoj@example.com</td>
            </tr>
            <tr>
                <td>102</td>
                <td>Rahul Sharma</td>
                <td>HR</td>
                <td>rahul@example.com</td>
            </tr>
            <tr style="background-color:#f2f2f2;">
                <td>103</td>
                <td>Neha Verma</td>
                <td>Finance</td>
                <td>neha@example.com</td>
            </tr>
        </tbody>
    </table>

        <jsp:include page="./include/footer.jsp" />	
    </div>
	<jsp:include page="./include/scripts.jsp"></jsp:include>
</body>
</html>
