package com.jobportal.dao;

import com.jobportal.model.Company;


public interface ICompanyDao {
	public Boolean save(Company request);
	public Company getCompanyById(String companyId);
	boolean UpdateCompany(Company company);
	

}
