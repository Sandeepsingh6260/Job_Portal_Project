package com.jobportal.service.impl;


import com.jobportal.daoimpl.ApplicationDaoImpl;
import com.jobportal.service.ApplicationService;

public class ApplicationServiceImpl implements ApplicationService{
	
	ApplicationDaoImpl applicationDaoimpl = new ApplicationDaoImpl();
	

	@Override
	public int getTotalApplicationsCount(String recruiterId) {
		return applicationDaoimpl.getTotalApplicationsCount(recruiterId);	}

	@Override
	public int getHiresCount(String recruiterId) {
		return applicationDaoimpl.getHiresCount(recruiterId);
	}

	@Override
	public double getResponseRate(String recruiterId) {
		return applicationDaoimpl.getResponseRate(recruiterId);
	}
	
	

}
