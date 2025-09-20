package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jobportal.dao.IUserDao;
import com.jobportal.enums.RoleType;
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

    public User login(String email, String password) {
        sql = "SELECT * FROM users WHERE user_email = ? AND user_password = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
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
}
