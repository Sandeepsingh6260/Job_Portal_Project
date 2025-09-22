package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jobportal.dao.IJobDao;
import com.jobportal.model.Job;
import com.jobportal.util.DbConnection;

public class JobDaoImpl implements IJobDao {
	 private Connection con=null;
	

	 public JobDaoImpl() {
		super();
		 con = DbConnection.getConnection();
		 }

	 public boolean jobpost(Job job) {
	        String sql = "INSERT INTO job (job_id, title, description, user_id, location, salary, mobile_no, experience_required, job_type, created_at, updated_at, status) "
	                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), ?)";

	        try (PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, job.getId());
	            ps.setString(2, job.getTitle());
	            ps.setString(3, job.getDescription());
	            ps.setString(4, job.getUser_id());
	            ps.setString(5, job.getLocation());
	            ps.setDouble(6, job.getSalary());
	            ps.setString(7, job.getMobile_no());
	            ps.setString(8, job.getExperience_required()); 
	            ps.setString(9, job.getJob_type());           
	            ps.setBoolean(10, true);                      

	            int inserted = ps.executeUpdate();
	            return inserted > 0;

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

}
