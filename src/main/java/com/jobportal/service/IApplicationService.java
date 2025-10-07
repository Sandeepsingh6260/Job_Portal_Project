package com.jobportal.service;

import java.util.List;

import com.jobportal.enums.StatusType;
import com.jobportal.model.Application;

public interface IApplicationService {

	List<String> getAppliedJobIds(String user_id);

	List<Application> getApplicationsByUser(String user_id);

	boolean hasUserAppliedForJob(String user_id, String jobId);

	boolean applyForJob(Application application);

	Application getApplicationById(String applicationId);

	boolean withdrawApplication(String applicationId);
	
	String getCompanyIdByJobId(String job_id);

	boolean updateApplicationStatus(String appId, StatusType type);

}
