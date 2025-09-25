package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jobportal.dao.IJobDao;
import com.jobportal.model.Job;
import com.jobportal.util.DbConnection;


public class JobDaoImpl implements IJobDao {
	
	
	private Connection con = null;
	private PreparedStatement pst = null;
	private String sql = "";
	
	
	public JobDaoImpl()
	{
		con = DbConnection.getConnection();
	}
	
	
	
	@Override
	public boolean jobpost(Job job) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean saveJob(Job job) {
		 sql = "INSERT INTO job (job_id, title, description, user_id, location, salary, experience_required, job_type, mobile_no, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try  {
        
        pst.setString(1, job.getId());
        pst.setString(2, job.getTitle());
        pst.setString(3, job.getDescription());
        pst.setString(4, job.getUser_id());
        pst.setString(5, job.getLocation());
        pst.setDouble(6, job.getSalary());
        pst.setString(7, job.getExperience_required());
        pst.setString(8, job.getJob_type());
        pst.setString(9, job.getMobile_no());
        pst.setBoolean(10, true);
        
        return pst.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
	
	@Override
	public int getActiveJobsCount(String recruiterId) {
		sql = "SELECT COUNT(*) FROM job WHERE user_id = ? AND status = true";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, recruiterId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
	}
	
	


