package com.jobportal.dao;

import java.util.List;

import com.jobportal.model.Application;

public interface IApplicationDao {

	boolean applyJob(Application application);
	String getIdByjobId(String job_id);
	
	public List<Application> getApplicationsById(String recruiterId);
	
}
