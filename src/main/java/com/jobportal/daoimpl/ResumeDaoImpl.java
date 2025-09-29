package com.jobportal.daoimpl;

import java.sql.*;
import java.util.*;

import com.jobportal.dao.IResumeDao;
import com.jobportal.model.Resume;
import com.jobportal.util.DbConnection;

public class ResumeDaoImpl implements IResumeDao {

    private Connection con;

    public ResumeDaoImpl() {
        con = DbConnection.getConnection();
    }
    
    
    @Override
    public Boolean saveResume(Resume resume) {
        String sql = "INSERT INTO resume (resume_id, user_id, file_path, skills, experience_years, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, resume.getResume_id());
            ps.setString(2, resume.getUser_id());
            ps.setString(3, resume.getFile_path());
            ps.setString(4, resume.getSkills());
            ps.setInt(5, resume.getExperience_years());
            ps.setBoolean(6, resume.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error saving resume: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Update existing resume
    
    
    @Override
    public Boolean updateResume(Resume resume) {
        String sql = "UPDATE resume SET file_path=?, skills=?, experience_years=?, status=? WHERE resume_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, resume.getFile_path());
            ps.setString(2, resume.getSkills());
            ps.setInt(3, resume.getExperience_years());
            ps.setBoolean(4, resume.getStatus());
            ps.setString(5, resume.getResume_id());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error updating resume: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Delete resume by ID
    
    
    @Override
    public Boolean deleteResume(String resumeId) {
        String sql = "DELETE FROM resume WHERE resume_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, resumeId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error deleting resume: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Get resume by user ID
    @Override
    public Resume getResumeByUserId(String userId) {
        String sql = "SELECT * FROM resume WHERE user_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractResume(rs);
            }
        } catch (Exception e) {
            System.err.println("Error getting resume by user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Get resume by resume ID
    @Override
    public Resume getResumeById(String resumeId) {
        String sql = "SELECT * FROM resume WHERE resume_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, resumeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractResume(rs);
            }
        } catch (Exception e) {
            System.err.println("Error getting resume by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Get all resumes
    @Override
    public List<Resume> getAllResumes() {
        List<Resume> list = new ArrayList<>();
        String sql = "SELECT * FROM resume ORDER BY created_at DESC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractResume(rs));
            }
        } catch (Exception e) {
            System.err.println("Error getting all resumes: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Get resumes by skills (LIKE search)
    @Override
    public List<Resume> getResumesBySkills(String skills) {
        List<Resume> list = new ArrayList<>();
        String sql = "SELECT * FROM resume WHERE skills LIKE ? ORDER BY experience_years DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + skills + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractResume(rs));
            }
        } catch (Exception e) {
            System.err.println("Error getting resumes by skills: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Get resumes by experience range
    @Override
    public List<Resume> getResumesByExperience(int minYears, int maxYears) {
        List<Resume> list = new ArrayList<>();
        String sql = "SELECT * FROM resume WHERE experience_years BETWEEN ? AND ? ORDER BY experience_years DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, minYears);
            ps.setInt(2, maxYears);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractResume(rs));
            }
        } catch (Exception e) {
            System.err.println("Error getting resumes by experience: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Update resume status (active/inactive)
    @Override
    public Boolean updateResumeStatus(String resumeId, Boolean status) {
        String sql = "UPDATE resume SET status=? WHERE resume_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setString(2, resumeId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error updating resume status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Get total count of resumes
    @Override
    public Integer getResumesCount() {
        String sql = "SELECT COUNT(*) FROM resume";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error getting resume count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ Get recent resumes (limit)
    @Override
    public List<Resume> getRecentResumes(int limit) {
        List<Resume> list = new ArrayList<>();
        String sql = "SELECT * FROM resume ORDER BY created_at DESC LIMIT ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractResume(rs));
            }
        } catch (Exception e) {
            System.err.println("Error getting recent resumes: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 🔹 Helper method: map ResultSet → Resume object
    private Resume extractResume(ResultSet rs) throws SQLException {
        Resume r = new Resume();
        r.setResume_id(rs.getString("resume_id"));
        r.setUser_id(rs.getString("user_id"));
        r.setFile_path(rs.getString("file_path"));
        r.setSkills(rs.getString("skills"));
        r.setExperience_years(rs.getInt("experience_years"));
        r.setStatus(rs.getBoolean("status"));
        return r;
    }
}
