package com.jobportal.dao;

import com.jobportal.model.Company;
import com.jobportal.payload.request.UserRequest;
import com.jobportal.payload.response.CompnayResponse;

public interface ICompanyDao {
	public Boolean save(Company request);

}
