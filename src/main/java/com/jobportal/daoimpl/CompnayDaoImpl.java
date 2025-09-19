package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import com.jobportal.dao.ICompanyDao;
import com.jobportal.model.Company;
import com.jobportal.payload.request.UserRequest;
import com.jobportal.payload.response.CompnayResponse;
import com.jobportal.util.DbConnection;

public class CompnayDaoImpl  implements ICompanyDao{
	private String sql="";
	private PreparedStatement pst=null;
	private Connection con=null;
	

	public CompnayDaoImpl() {
		con=DbConnection.getConnection();
	}


	@Override
	public Boolean save(Company request) {
	    sql = "INSERT INTO company (company_id, company_name, company_description, company_location, status) " +
	                 "VALUES (?, ?, ?, ?, ?)";
	    try {
	        pst = con.prepareStatement(sql);
	        pst.setString(1, request.getCompany_id());
	        pst.setString(2, request.getCompany_name());
	        pst.setString(3, request.getCompany_description());
	        pst.setString(4, request.getCompany_location());
	        pst.setBoolean(5, true);   

	        int rows = pst.executeUpdate();
	        return rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
