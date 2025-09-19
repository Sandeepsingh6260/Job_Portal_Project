package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jobportal.dao.IUserDao;
import com.jobportal.enums.RoleType;
import com.jobportal.model.User;
import com.jobportal.util.DbConnection;

public class UserDaoImpl implements IUserDao {
	private Connection con;
	private PreparedStatement pst;

	public UserDaoImpl() {
		con = DbConnection.getConnection();
	}

	@Override
	public Boolean Register(User user) {
		try {
			String sql = "";
			if(user.getUser_role() == RoleType.JOB_SEEKER)
			{
				System.out.println("seeker");
				sql = "INSERT INTO users (user_id,user_name,user_email,user_password,location,skills,experience,resume_path,user_role) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pst = con.prepareStatement(sql);
				pst.setString(1, user.getUser_id());
				pst.setString(2, user.getUser_name());
				pst.setString(3, user.getUser_email());
				pst.setString(4, user.getUser_password());
				pst.setString(5, user.getLocation());
				pst.setString(6, user.getSkills());
				pst.setString(7, user.getExperience());
				pst.setString(8, user.getResumePath());
				pst.setString(9, user.getUser_role().name());
			} 
			
			else if(user.getUser_role() == RoleType.RECRUITER) 
			{
//				System.out.println("recruiter   "+user.getCompany_id());

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
}
