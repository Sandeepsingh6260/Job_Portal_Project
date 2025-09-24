package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jobportal.dao.IUserDao;
import com.jobportal.enums.RoleType;
import com.jobportal.model.Company;
import com.jobportal.model.User;
import com.jobportal.util.DbConnection;

public class UserDaoImpl implements IUserDao {
    private Connection con;
    private PreparedStatement pst;
    private String sql = "";
   
    public UserDaoImpl() {
        con = DbConnection.getConnection();
      
    }

    @Override
    public Boolean Register(User user) {
        try {
            if (user.getUser_role() == RoleType.JOB_SEEKER) {
                System.out.println("seeker");
                sql = "INSERT INTO users (user_id,user_name,user_email,user_password,location,user_role) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, user.getUser_id());
                pst.setString(2, user.getUser_name());
                pst.setString(3, user.getUser_email());
                pst.setString(4, user.getUser_password());
                pst.setString(5, user.getLocation());
                pst.setString(6, user.getUser_role().name());
            } else if (user.getUser_role() == RoleType.RECRUITER) {
            	
                sql = "INSERT INTO users (user_id,user_name,user_email,user_password,location,user_role,company_id) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, user.getUser_id());
                pst.setString(2, user.getUser_name());
                pst.setString(3, user.getUser_email());
                pst.setString(4, user.getUser_password());
                pst.setString(5, user.getLocation());
                pst.setString(6, user.getUser_role().name());
                pst.setString(7, user.getCompany_id());
            }
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean isEmailExits(String user_email) {
        sql = "SELECT user_email FROM users WHERE user_email=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, user_email);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean getPassword(String user_email, String user_password) {
        sql = "SELECT user_password FROM users WHERE user_email = ? AND user_password = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, user_email);
            pst.setString(2, user_password);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(String email) {
        sql = "SELECT * FROM users WHERE user_email = ? ";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_email(rs.getString("user_email"));
                user.setUser_password(rs.getString("user_password"));
                user.setLocation(rs.getString("location"));
                user.setUser_role(RoleType.valueOf(rs.getString("user_role")));
                user.setCompany_id(rs.getString("company_id"));
                
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean UpdateUserAndCompany(User user, Company company) {
        try {
            // Update User
             sql = "UPDATE users SET user_name=?, user_password=?, location=? WHERE user_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, user.getUser_name());
            pst.setString(2, user.getUser_password()); // Already BCrypt encoded
            pst.setString(3, user.getLocation());
            pst.setString(4, user.getUser_id());
            int userUpdate = pst.executeUpdate();
            System.out.println("User update count: " + userUpdate);

            // Update Company if RECRUITER
            if (user.getUser_role() == RoleType.RECRUITER && company != null) {
                sql = "UPDATE company SET company_name=?, company_description=?, company_location=?, phoneNo=? WHERE company_id=?";
                pst = con.prepareStatement(sql);
                pst.setString(1, company.getCompany_name());
                pst.setString(2, company.getCompany_description());
                pst.setString(3, company.getCompany_location());
                pst.setString(4, company.getMobile());
                pst.setString(5, company.getCompany_id());
                int companyUpdate = pst.executeUpdate();
                System.out.println("Company update count: " + companyUpdate);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // यहां exception देखो
            return false;
        }
    }

    @Override
    public boolean UpdateUser(User user) {
        String sql = "UPDATE users SET user_name=?, user_password=?, location=? WHERE user_id=?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, user.getUser_name());
            pst.setString(2, user.getUser_password()); // पहले से encoded password
            pst.setString(3, user.getLocation());
            pst.setString(4, user.getUser_id());

            int rows = pst.executeUpdate();
            System.out.println("User updated rows: " + rows);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean UpdateCompany(Company company) {
        String sql = "UPDATE company SET company_name=?, company_description=?, company_location=?, phoneNo=? WHERE company_id=?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, company.getCompany_name());
            pst.setString(2, company.getCompany_description());
            pst.setString(3, company.getCompany_location());
            pst.setString(4, company.getMobile());
            pst.setString(5, company.getCompany_id());

            int rows = pst.executeUpdate();
            System.out.println("Company updated rows: " + rows);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




	

}
