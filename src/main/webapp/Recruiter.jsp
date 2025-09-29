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
		
		<div class="container">
				<div class="page-inner">
					<div
						class="d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4">
						<div>
							<h3 class="fw-bold mb-3">Recruiter Dashboard</h3>
							<h6 class="op-7 mb-2">Manage your job postings and
								applications</h6>
						</div>
						<div class="ms-md-auto py-2 py-md-0">
							<a href="jobpost.jsp" class="btn btn-primary btn-round"> <i
								class="fas fa-plus me-1"></i>Post New Job
							</a>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6 col-md-3">
							<div class="card card-stats card-round">
								<div class="card-body">
									<div class="row align-items-center">
										<div class="col-icon">
											<div
												class="icon-big text-center icon-primary bubble-shadow-small">
												<i class="fas fa-briefcase"></i>
											</div>
										</div>
										<div class="col col-stats ms-3 ms-sm-0">
											<div class="numbers">
												<p class="card-category">Active Jobs</p>
												<h4 class="card-title">45</h4>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="card card-stats card-round">
								<div class="card-body">
									<div class="row align-items-center">
										<div class="col-icon">
											<div
												class="icon-big text-center icon-info bubble-shadow-small">
												<i class="fas fa-user-check"></i>
											</div>
										</div>
										<div class="col col-stats ms-3 ms-sm-0">
											<div class="numbers">
												<p class="card-category">Applications</p>
												<h4 class="card-title">320</h4>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="card card-stats card-round">
								<div class="card-body">
									<div class="row align-items-center">
										<div class="col-icon">
											<div
												class="icon-big text-center icon-success bubble-shadow-small">
												<i class="fas fa-handshake"></i>
											</div>
										</div>
										<div class="col col-stats ms-3 ms-sm-0">
											<div class="numbers">
												<p class="card-category">Hires</p>
												<h4 class="card-title">15</h4>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="card card-stats card-round">
								<div class="card-body">
									<div class="row align-items-center">
										<div class="col-icon">
											<div
												class="icon-big text-center icon-secondary bubble-shadow-small">
												<i class="fas fa-chart-line"></i>
											</div>
										</div>
										<div class="col col-stats ms-3 ms-sm-0">
											<div class="numbers">
												<p class="card-category">Response Rate</p>
												<h4 class="card-title">85%</h4>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
        <jsp:include page="./include/footer.jsp" />	
    </div>
	<jsp:include page="./include/scripts.jsp"></jsp:include>
</body>
</html>