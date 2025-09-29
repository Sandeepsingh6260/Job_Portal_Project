package com.jobportal.service.impl;

import java.util.List;

import com.jobportal.model.Application;
import com.jobportal.service.IApplicationService;

public class ApplicationServiceImpl implements IApplicationService {

	@Override
	public List<String> getAppliedJobIds(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Application> getApplicationsByUser(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasUserAppliedForJob(String user_id, String jobId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyForJob(Application application) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Application getApplicationById(String applicationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean withdrawApplication(String applicationId) {
		// TODO Auto-generated method stub
		return false;
	}

}
