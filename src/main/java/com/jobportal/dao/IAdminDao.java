package com.jobportal.dao;

import java.util.List;

import com.jobportal.model.User;

public interface IAdminDao {

	int getJobSeekers();

	int getRecruiters();

	int getJobsCount();

	int getPendingCount();

	List<User> getAllSeekersList();

	List<User> getAllRecruitersList();

	boolean updateUserStatus(String userId, boolean status);

}
