package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jobportal.dao.ApplicationDao;
import com.jobportal.enums.StatusType;
import com.jobportal.util.DbConnection;

public class ApplicationDaoImpl implements ApplicationDao{
	
	private Connection con = null;
	private PreparedStatement pst = null;
	private String sql = "";
	
	
	public ApplicationDaoImpl()
	{
		con = DbConnection.getConnection();
	}


	 @Override
	    public int getTotalApplicationsCount(String recruiterId) {
	        String sql = "SELECT COUNT(*) FROM application a " +
	                     "JOIN job j ON a.job_id = j.job_id " +
	                     "WHERE j.user_id = ? AND a.status_flag = true";
	        
	        System.out.println("📋 Executing Total Applications Query: " + sql);
	        System.out.println("📋 Recruiter ID: " + recruiterId);
	        
	        try (PreparedStatement pst = con.prepareStatement(sql)) {
	            pst.setString(1, recruiterId);
	            ResultSet rs = pst.executeQuery();
	            
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                System.out.println("✅ Total Applications Found: " + count);
	                return count;
	            }
	        } catch (SQLException e) {
	            System.out.println("❌ Error in getTotalApplicationsCount: " + e.getMessage());
	            e.printStackTrace();
	        }
	        return 0;
	    }

	    @Override
	    public int getHiresCount(String recruiterId) {
	        String sql = "SELECT COUNT(*) FROM application a " +
	                     "JOIN job j ON a.job_id = j.job_id " +
	                     "WHERE j.user_id = ? AND a.status = 'SHORTLISTED' AND a.status_flag = true";
	        
	        System.out.println("📋 Executing Hires Query: " + sql);
	        System.out.println("📋 Recruiter ID: " + recruiterId);
	        
	        try (PreparedStatement pst = con.prepareStatement(sql)) {
	            pst.setString(1, recruiterId);
	            ResultSet rs = pst.executeQuery();
	            
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                System.out.println("✅ Hires Found: " + count);
	                return count;
	            }
	        } catch (SQLException e) {
	            System.out.println("❌ Error in getHiresCount: " + e.getMessage());
	            e.printStackTrace();
	        }
	        return 0;
	    }

	    @Override
	    public double getResponseRate(String recruiterId) {
	        String totalJobsSql = "SELECT COUNT(*) FROM job WHERE user_id = ? AND status = true";
	        String respondedJobsSql = "SELECT COUNT(DISTINCT j.job_id) FROM job j " +
	                                "JOIN application a ON j.job_id = a.job_id " +
	                                "WHERE j.user_id = ? AND a.status_flag = true";
	        
	        System.out.println(" Calculating Response Rate for Recruiter: " + recruiterId);
	        
	        try (PreparedStatement totalPs = con.prepareStatement(totalJobsSql);
	             PreparedStatement respondedPs = con.prepareStatement(respondedJobsSql)) {
	            
	            totalPs.setString(1, recruiterId);
	            respondedPs.setString(1, recruiterId);
	            
	            ResultSet totalRs = totalPs.executeQuery();
	            ResultSet respondedRs = respondedPs.executeQuery();
	            
	            int totalJobs = 0;
	            int respondedJobs = 0;
	            
	            if (totalRs.next()) totalJobs = totalRs.getInt(1);
	            if (respondedRs.next()) respondedJobs = respondedRs.getInt(1);
	            
	            System.out.println("Total Jobs: " + totalJobs + ", Responded Jobs: " + respondedJobs);
	            
	            if (totalJobs > 0) {
	                double rate = ((double) respondedJobs / totalJobs) * 100;
	                System.out.println(" Response Rate Calculated: " + rate + "%");
	                return rate;
	            }
	        } catch (SQLException e) {
	            System.out.println("Error in getResponseRate: " + e.getMessage());
	            e.printStackTrace();
	        }
	        return 0.0;
	    }
	}