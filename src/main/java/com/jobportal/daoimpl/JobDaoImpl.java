package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.dao.IJobDao;
import com.jobportal.model.Job;
import com.jobportal.util.DbConnection;

public class JobDaoImpl implements IJobDao {

    private Connection con;

    public JobDaoImpl() {
        con = DbConnection.getConnection();
    }

    @Override
    public boolean saveJob(Job job) {
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

    @Override
    public boolean updateJob(Job job) {
        String sql = "UPDATE job SET title=?, description=?, location=?, salary=?, mobile_no=?, experience_required=?, job_type=?, updated_at=NOW() WHERE job_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, job.getTitle());
            ps.setString(2, job.getDescription());
            ps.setString(3, job.getLocation());
            ps.setDouble(4, job.getSalary());
            ps.setString(5, job.getMobile_no());
            ps.setString(6, job.getExperience_required());
            ps.setString(7, job.getJob_type());
            ps.setString(8, job.getId());

            int updated = ps.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteJob(String jobId) {
        String sql = "DELETE FROM job WHERE job_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, jobId);
            int deleted = ps.executeUpdate();
            return deleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Job getJobById(String jobId) {
        String sql = "SELECT * FROM job WHERE job_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, jobId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Job job = new Job();
                job.setId(rs.getString("job_id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setUser_id(rs.getString("user_id"));
                job.setLocation(rs.getString("location"));
                job.setSalary(rs.getDouble("salary"));
                job.setMobile_no(rs.getString("mobile_no"));
                job.setExperience_required(rs.getString("experience_required"));
                job.setJob_type(rs.getString("job_type"));
                return job;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM job ORDER BY created_at DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Job job = new Job();
                job.setId(rs.getString("job_id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setUser_id(rs.getString("user_id"));
                job.setLocation(rs.getString("location"));
                job.setSalary(rs.getDouble("salary"));
                job.setMobile_no(rs.getString("mobile_no"));
                job.setExperience_required(rs.getString("experience_required"));
                job.setJob_type(rs.getString("job_type"));
                jobs.add(job);
            }
            System.out.println("------------------->>>   h"+jobs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
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
