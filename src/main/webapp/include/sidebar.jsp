<div class="sidebar" data-background-color="dark">
	<div class="sidebar-logo">
		<!-- Logo Header -->
		<div class="logo-header" data-background-color="dark">
			<a href="index.html" class="logo"> <img
				src="assets/img/job-portal-logo.svg" alt="navbar brand"
				class="navbar-brand" height="20">
			</a>
			<div class="nav-toggle">
				<button class="btn btn-toggle toggle-sidebar">
					<i class="gg-menu-right"></i>
				</button>
				<button class="btn btn-toggle sidenav-toggler">
					<i class="gg-menu-left"></i>
				</button>
			</div>
			<button class="topbar-toggler more">
				<i class="gg-more-vertical-alt"></i>
			</button>
		</div>
		<!-- End Logo Header -->
	</div>
	<div class="sidebar-wrapper scrollbar scrollbar-inner">
		<div class="sidebar-content">
			<ul class="nav nav-secondary">
				<li class="nav-item active"><a data-bs-toggle="collapse"
					href="#dashboard" class="collapsed" aria-expanded="false"> <i
						class="fas fa-home"></i>
						<p>Dashboard</p> <span class="caret"></span>
				</a>
					<div class="collapse" id="dashboard">
						<ul class="nav nav-collapse">
							<li><a href="#"> <span class="sub-item">Recruiter
										Overview</span>
							</a></li>
						</ul>
					</div></li>
				<li class="nav-section"><span class="sidebar-mini-icon">
						<i class="fa fa-ellipsis-h"></i>
				</span>
					<h4 class="text-section">Recruiter Tools</h4></li>
				<li class="nav-item"><a data-bs-toggle="collapse" href="#jobs">
						<i class="fas fa-briefcase"></i>
						<p>Jobs</p> <span class="caret"></span>
				</a>
					<div class="collapse" id="jobs">
						<ul class="nav nav-collapse">
							<li><a href="JobPost.jsp"> <span class="sub-item">Post
										New Job</span>
							</a></li>
							<li><a href="./RecruiterServlet?action=managejob"> <span
									class="sub-item">Manage Jobs</span>
							</a></li>
						</ul>
					</div></li>
				<li class="nav-item"><a data-bs-toggle="collapse"
					href="#applications"> <i class="fas fa-file-alt"></i>
						<p>Applications</p> <span class="caret"></span>
				</a>
					<div class="collapse" id="applications">
						<ul class="nav nav-collapse">
							<li><a href="./RecruiterServlet?action=viewApplications">
									<span class="sub-item">View Applications</span>
							</a></li>

							<li><a href="shortlist-candidates.jsp"> <span
									class="sub-item">Shortlist Candidates</span>
							</a></li>
						</ul>
					</div></li>
				<li class="nav-item"><a href="candidates.jsp"> <i
						class="fas fa-users"></i>
						<p>Candidates</p>
				</a></li>
				<li class="nav-item"><a href="analytics.jsp"> <i
						class="fas fa-chart-bar"></i>
						<p>Analytics</p>
				</a></li>
				<li class="nav-item"><a href="settings.jsp"> <i
						class="fas fa-cog"></i>
						<p>Settings</p>
				</a></li>
			</ul>
		</div>
	</div>
</div>