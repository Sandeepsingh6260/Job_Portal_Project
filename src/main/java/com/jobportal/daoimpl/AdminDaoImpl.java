package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.dao.IAdminDao;
import com.jobportal.enums.RoleType;
import com.jobportal.model.User;
import com.jobportal.util.DbConnection;

public class AdminDaoImpl implements IAdminDao {

    private String sql = "";
    private PreparedStatement pst = null;
    private Connection con = null;
    private ResultSet rs = null;

    public AdminDaoImpl() {
        con = DbConnection.getConnection();
    }

    @Override
    public int getJobSeekers() {
        sql = "SELECT COUNT(*) FROM users WHERE user_role = 'JOB_SEEKER' AND status = true AND isDeleted = false";
        return getCount(sql);
    }

    @Override
    public int getRecruiters() {
        sql = "SELECT COUNT(*) FROM users WHERE user_role = 'RECRUITER' AND status = true AND isDeleted = false";
        return getCount(sql);
    }

    @Override
    public int getJobsCount() {
        sql = "SELECT COUNT(*) FROM job WHERE status = 1";
        return getCount(sql);
    }

    @Override
    public int getPendingCount() {
        sql = "SELECT COUNT(*) FROM users WHERE status = 0 AND isDeleted = false";
        return getCount(sql);
    }

    private int getCount(String sql) {
        int count = 0;
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return count;
    }

    @Override
    public List<User> getAllSeekersList() {
        List<User> jobSeekers = new ArrayList<>();
        sql = "SELECT user_id, user_name, user_email, location, user_role, created_at, status " +
              "FROM users WHERE user_role = 'JOB_SEEKER' AND isDeleted = false ORDER BY created_at DESC";

        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                jobSeekers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return jobSeekers;
    }

    @Override
    public List<User> getAllRecruitersList() 
    {
        List<User> recruiters = new ArrayList<>();
        sql = "SELECT u.user_id, u.user_name, u.user_email, u.location, u.user_role, " +
              "u.created_at, u.status, c.company_name, COUNT(j.job_id) AS jobs_posted " +
              "FROM users u " +
              "LEFT JOIN company c ON u.company_id = c.company_id " +
              "LEFT JOIN job j ON j.user_id = u.user_id AND j.isDeleted = false " + 
              "WHERE u.user_role = 'RECRUITER' AND u.isDeleted = false " +
              "GROUP BY u.user_id, u.user_name, u.user_email, u.location, u.user_role, " +
              "u.created_at, u.status, c.company_name " +
              "ORDER BY u.created_at DESC";

        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                user.setCompany_name(rs.getString("company_name"));
                user.setJobs_posted(rs.getInt("jobs_posted"));
                recruiters.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return recruiters;
    }

    @Override
    public boolean updateUserStatus(String userId, boolean status) {
        boolean isUpdated = false;
        sql = "UPDATE users SET status = ? WHERE user_id = ? AND isDeleted = false";

        try {
            pst = con.prepareStatement(sql);
            pst.setBoolean(1, status);
            pst.setString(2, userId);

            int rowsAffected = pst.executeUpdate();
            isUpdated = rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return isUpdated;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUser_id(rs.getString("user_id"));
        user.setUser_name(rs.getString("user_name"));
        user.setUser_email(rs.getString("user_email"));
        user.setLocation(rs.getString("location"));

        // Safely handle role conversion
        String roleStr = rs.getString("user_role");
        try {
            user.setUser_role(RoleType.valueOf(roleStr));
        } catch (IllegalArgumentException e) {
            user.setUser_role(RoleType.JOB_SEEKER);
        }

        user.setCreated_at(rs.getTimestamp("created_at"));
        user.setStatus(rs.getBoolean("status"));
        return user;
    }

    // Fixed resource cleanup method
    
    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (pst != null) {
                pst.close();
                pst = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}