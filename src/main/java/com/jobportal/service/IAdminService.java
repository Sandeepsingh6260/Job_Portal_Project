package com.jobportal.service;

import java.util.List;

import com.jobportal.model.User;

public interface IAdminService {

	int getTotalJobSeekers();
	int getTotalRecruiters();
	int getActiveJobsCount();
	int getPendingApprovalsCount();
	List<User> getAllJobSeekers();
	List<User> getAllRecruiters();
	boolean activateUser(String userId);
	boolean blockUser(String userId);

}
