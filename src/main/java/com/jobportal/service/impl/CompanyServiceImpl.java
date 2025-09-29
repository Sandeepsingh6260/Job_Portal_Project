package com.jobportal.service.impl;

import com.jobportal.dao.ICompanyDao;
import com.jobportal.daoimpl.CompnayDaoImpl;
import com.jobportal.model.Company;
import com.jobportal.service.ICompanyService;

public class CompanyServiceImpl implements ICompanyService {
	public ICompanyDao companyDao;
	public CompanyServiceImpl() {
		super();
		companyDao=new CompnayDaoImpl();
	}

	@Override
	public Boolean save(Company company) {
		
		return companyDao.save(company);
	}
	
	
	@Override
	public Company getCompanyById(String company_id) {
		
		return companyDao.getCompanyById(company_id);
	}

}
