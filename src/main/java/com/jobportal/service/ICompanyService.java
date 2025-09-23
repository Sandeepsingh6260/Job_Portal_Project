package com.jobportal.service;

import com.jobportal.model.Company;

public interface ICompanyService {
	public Boolean save(Company company);

	public Company getCompanyById(String company_id);
}
