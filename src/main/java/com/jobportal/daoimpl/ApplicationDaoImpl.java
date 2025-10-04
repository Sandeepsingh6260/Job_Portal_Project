package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.dao.IApplicationDao;
import com.jobportal.model.Application;
import com.jobportal.util.DbConnection;

public class ApplicationDaoImpl implements IApplicationDao {
	
	private String sql="";
	private PreparedStatement pst=null;
	private Connection con=null;
	

	public ApplicationDaoImpl() {
		con=DbConnection.getConnection();
	}

	public boolean applyJob(Application application) {
	    boolean isApplied = false;

	    sql = "INSERT INTO application (application_id, user_id, job_id, company_id, status, isDeleted) "
	            + "VALUES (?, ?, ?, ?, ?, ?)";

	    try {
	        pst = con.prepareStatement(sql);

	        pst.setString(1, application.getId());
	        pst.setString(2, application.getUser_id());
	        pst.setString(3, application.getJob_id());
	        pst.setString(4, application.getCompany_id());
	        
	        String status = application.getStatus();
	        if (status == null || status.trim().isEmpty()) {
	            status = "PENDING";
	        }

	        pst.setString(5, status);
	        pst.setBoolean(6, false);

	        int rows = pst.executeUpdate();
	        if (rows > 0) {
	            isApplied = true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return isApplied;
	}


	public String getIdByjobId(String job_id) {
	    String companyId = null;
	    String sql = "SELECT u.company_id " +
	                 "FROM job j " +
	                 "JOIN users u ON j.user_id = u.user_id " +
	                 "WHERE j.job_id = ?";

	    try {
	        pst = con.prepareStatement(sql);
	        pst.setString(1, job_id);
	        var rs = pst.executeQuery();

	        if (rs.next()) {
	            companyId = rs.getString("company_id");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return companyId;
	}


	public List<Application> getApplicationsById(String recruiterId) {
	    List<Application> applications = new ArrayList<>();

	    sql = "SELECT a.application_id, a.job_id, j.title AS job_title, "
	        + "u.user_id AS applicant_id, u.user_name AS applicant_name, u.user_email AS applicant_email, "
	        + "a.status, a.isDeleted, a.created_at "
	        + "FROM application a "
	        + "JOIN job j ON a.job_id = j.job_id "
	        + "JOIN users u ON a.user_id = u.user_id "
	        + "WHERE j.user_id = ? AND a.isDeleted = false "
	        + "ORDER BY a.created_at DESC";

	    try {
	        pst = con.prepareStatement(sql);
	        pst.setString(1, recruiterId);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next())
	        {
	            Application app = new Application();
	            app.setId(rs.getString("application_id"));
	            app.setJob_id(rs.getString("job_id"));
	            app.setJobTitle(rs.getString("job_title"));
	            app.setApplicantId(rs.getString("applicant_id"));
	            app.setApplicantName(rs.getString("applicant_name"));
	            app.setApplicantEmail(rs.getString("applicant_email"));
	            app.setStatus(rs.getString("status"));
	            app.setDeleted(rs.getBoolean("isDeleted"));

	            applications.add(app);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return applications;
	}

}
