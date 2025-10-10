package com.jobportal.dao;

import java.util.List;
import java.util.Map;

import com.jobportal.enums.StatusType;
import com.jobportal.model.Application;

public interface IApplicationDao {

	boolean applyJob(Application application);
	String getIdByjobId(String job_id);
	
	public List<Application> getApplicationsById(String recruiterId);
	boolean updateApplicationStatus(String applicationId, StatusType status);
	
	int countJobsByRecruiter(String recruiterId);
    int countApplicationsByRecruiter(String recruiterId);
    int countShortlistedByRecruiter(String recruiterId);
    int countRejectedByRecruiter(String recruiterId);
    Map<String, Integer> getDashboardCounts(String recruiterId);
    int countTodaysApplications(String recruiterId);
    List<Application> searchApplications(String keyword, String recruiterId);
    List<Application> searchApplicationsByStatus(String status, String recruiterId);
	
}
