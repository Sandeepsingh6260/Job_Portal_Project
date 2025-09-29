package com.jobportal.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jobportal.dao.ICompanyDao;
import com.jobportal.model.Company;
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
	    sql = "INSERT INTO company (company_id, company_name, company_description, company_location, status, phoneNo) " +
	                 "VALUES (?, ?, ?, ?, ?, ?)";
	    try {
	        pst = con.prepareStatement(sql);
	        pst.setString(1, request.getCompany_id());
	        pst.setString(2, request.getCompany_name());
	        pst.setString(3, request.getCompany_description());
	        pst.setString(4, request.getCompany_location());
	        pst.setBoolean(5, true);
	        pst.setString(6, request.getMobile());

	        int rows = pst.executeUpdate();
	        return rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	@Override
    public Company getCompanyById(String companyId) {
        sql = "SELECT * FROM company WHERE company_id = ?";
        Company company = null;
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, companyId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                company = new Company();
                company.setCompany_id(rs.getString("company_id"));
                company.setCompany_name(rs.getString("company_name"));
                company.setCompany_description(rs.getString("company_description"));
                company.setCompany_location(rs.getString("company_location"));
                company.setMobile(rs.getString("phoneNo"));
                company.setIsDeleted(!rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }
	
	@Override
    public boolean UpdateCompany(Company company) {
        String sql = "UPDATE company SET company_name=?, company_description=?, company_location=?, phoneNo=? WHERE company_id=?";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, company.getCompany_name());
            pst.setString(2, company.getCompany_description());
            pst.setString(3, company.getCompany_location());
            pst.setString(4, company.getMobile());
            pst.setString(5, company.getCompany_id());

            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
