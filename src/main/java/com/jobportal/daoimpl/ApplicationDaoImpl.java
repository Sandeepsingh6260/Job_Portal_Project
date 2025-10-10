package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jobportal.dao.IApplicationDao;
import com.jobportal.enums.StatusType;
import com.jobportal.model.Application;
import com.jobportal.util.DbConnection;

public class ApplicationDaoImpl implements IApplicationDao {
    
    private String sql = "";
    private PreparedStatement pst = null;
    private Connection con = null;
    
    public ApplicationDaoImpl() {
        con = DbConnection.getConnection();
    }

    // Your existing methods
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
            + "a.status, a.isDeleted, a.created_at, "
            + "r.experience_years, r.skills, r.file_path, "
            + "u.location " 
            + "FROM application a "
            + "JOIN job j ON a.job_id = j.job_id "
            + "JOIN users u ON a.user_id = u.user_id "
            + "LEFT JOIN resume r ON a.user_id = r.user_id " 
            + "WHERE j.user_id = ? AND a.isDeleted = false "
            + "ORDER BY a.created_at DESC";

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getString("application_id"));
                app.setJob_id(rs.getString("job_id"));
                app.setJobTitle(rs.getString("job_title"));
                app.setApplicantId(rs.getString("applicant_id"));
                app.setApplicantName(rs.getString("applicant_name"));
                app.setApplicantEmail(rs.getString("applicant_email"));
                app.setStatus(rs.getString("status"));
                app.setDeleted(rs.getBoolean("isDeleted"));
                app.setExperienceYears(rs.getString("experience_years"));
                app.setSkills(rs.getString("skills"));
                app.setResumeLink(rs.getString("file_path"));
                app.setLocation(rs.getString("location"));
                app.setCreated_at(rs.getTimestamp("created_at"));
                
                applications.add(app);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public boolean updateApplicationStatus(String applicationId, StatusType status) {
        boolean isUpdated = false;
        String sql = "UPDATE application SET status = ? WHERE application_id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);    
            pst.setString(1, status.toString());
            pst.setString(2, applicationId);

            int rows = pst.executeUpdate();
            isUpdated = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    // New count methods
    public int countJobsByRecruiter(String recruiterId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS job_count FROM job WHERE user_id = ? AND isDeleted = false AND status = true";
        
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("job_count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countApplicationsByRecruiter(String recruiterId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS application_count " +
                     "FROM application a " +
                     "JOIN job j ON a.job_id = j.job_id " +
                     "WHERE j.user_id = ? AND a.isDeleted = false";
        
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("application_count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countShortlistedByRecruiter(String recruiterId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS shortlisted_count " +
                     "FROM application a " +
                     "JOIN job j ON a.job_id = j.job_id " +
                     "WHERE j.user_id = ? AND a.status = 'SHORTLISTED' AND a.isDeleted = false";
        
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("shortlisted_count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countRejectedByRecruiter(String recruiterId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS rejected_count " +
                     "FROM application a " +
                     "JOIN job j ON a.job_id = j.job_id " +
                     "WHERE j.user_id = ? AND a.status = 'REJECTED' AND a.isDeleted = false";
        
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("rejected_count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countTodaysApplications(String recruiterId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS today_count " +
                     "FROM application a " +
                     "JOIN job j ON a.job_id = j.job_id " +
                     "WHERE j.user_id = ? AND DATE(a.created_at) = CURDATE() AND a.isDeleted = false";
        
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("today_count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Map<String, Integer> getDashboardCounts(String recruiterId) {
        Map<String, Integer> counts = new HashMap<>();
        
        String sql = "SELECT " +
                     "(SELECT COUNT(*) FROM job WHERE user_id = ? AND isDeleted = false AND status = true) AS job_count, " +
                     "(SELECT COUNT(*) FROM application a JOIN job j ON a.job_id = j.job_id WHERE j.user_id = ? AND a.isDeleted = false) AS application_count, " +
                     "(SELECT COUNT(*) FROM application a JOIN job j ON a.job_id = j.job_id WHERE j.user_id = ? AND a.status = 'SHORTLISTED' AND a.isDeleted = false) AS shortlisted_count, " +
                     "(SELECT COUNT(*) FROM application a JOIN job j ON a.job_id = j.job_id WHERE j.user_id = ? AND a.status = 'REJECTED' AND a.isDeleted = false) AS rejected_count";
        
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            pst.setString(2, recruiterId);
            pst.setString(3, recruiterId);
            pst.setString(4, recruiterId);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                counts.put("job_count", rs.getInt("job_count"));
                counts.put("application_count", rs.getInt("application_count"));
                counts.put("shortlisted_count", rs.getInt("shortlisted_count"));
                counts.put("rejected_count", rs.getInt("rejected_count"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counts;
    }

    @Override
    public List<Application> searchApplications(String keyword, String recruiterId) {
        List<Application> applications = new ArrayList<>();
        
        sql = "SELECT a.application_id, a.job_id, j.title AS job_title, " +
              "u.user_id AS applicant_id, u.user_name AS applicant_name, u.user_email AS applicant_email, " +
              "a.status, a.isDeleted, a.created_at, " +
              "r.experience_years, r.skills, r.file_path, " +
              "u.location " +
              "FROM application a " +
              "JOIN job j ON a.job_id = j.job_id " +
              "JOIN users u ON a.user_id = u.user_id " +
              "LEFT JOIN resume r ON a.user_id = r.user_id " +
              "WHERE j.user_id = ? AND a.isDeleted = false " +
              "AND (u.user_name LIKE ? OR j.title LIKE ? OR u.user_email LIKE ? " +
              "OR r.skills LIKE ? OR u.location LIKE ?) " +
              "ORDER BY a.created_at DESC";
        
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            String searchPattern = "%" + keyword + "%";
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            pst.setString(4, searchPattern);
            pst.setString(5, searchPattern);
            pst.setString(6, searchPattern);
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getString("application_id"));
                app.setJob_id(rs.getString("job_id"));
                app.setJobTitle(rs.getString("job_title"));
                app.setApplicantId(rs.getString("applicant_id"));
                app.setApplicantName(rs.getString("applicant_name"));
                app.setApplicantEmail(rs.getString("applicant_email"));
                app.setStatus(rs.getString("status"));
                app.setDeleted(rs.getBoolean("isDeleted"));
                app.setExperienceYears(rs.getString("experience_years"));
                app.setSkills(rs.getString("skills"));
                app.setResumeLink(rs.getString("file_path"));
                app.setLocation(rs.getString("location"));
                app.setCreated_at(rs.getTimestamp("created_at"));
                
                applications.add(app);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return applications;
    }
    
    
    
    
    @Override
    public List<Application> searchApplicationsByStatus(String status, String recruiterId) {
        List<Application> applications = new ArrayList<>();
        
        sql = "SELECT a.application_id, a.job_id, j.title AS job_title, " +
              "u.user_id AS applicant_id, u.user_name AS applicant_name, u.user_email AS applicant_email, " +
              "a.status, a.isDeleted, a.created_at, " +
              "r.experience_years, r.skills, r.file_path, " +
              "u.location " +
              "FROM application a " +
              "JOIN job j ON a.job_id = j.job_id " +
              "JOIN users u ON a.user_id = u.user_id " +
              "LEFT JOIN resume r ON a.user_id = r.user_id " +
              "WHERE j.user_id = ? AND a.status = ? AND a.isDeleted = false " +
              "ORDER BY a.created_at DESC";
        
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, recruiterId);
            pst.setString(2, status);
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getString("application_id"));
                app.setJob_id(rs.getString("job_id"));
                app.setJobTitle(rs.getString("job_title"));
                app.setApplicantId(rs.getString("applicant_id"));
                app.setApplicantName(rs.getString("applicant_name"));
                app.setApplicantEmail(rs.getString("applicant_email"));
                app.setStatus(rs.getString("status"));
                app.setDeleted(rs.getBoolean("isDeleted"));
                app.setExperienceYears(rs.getString("experience_years"));
                app.setSkills(rs.getString("skills"));
                app.setResumeLink(rs.getString("file_path"));
                app.setLocation(rs.getString("location"));
                app.setCreated_at(rs.getTimestamp("created_at"));
                
                applications.add(app);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return applications;
    }
}