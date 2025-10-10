package com.jobportal.service;

import java.util.List;

import com.jobportal.model.Job;

public interface IJobService {

	boolean savejob(Job job);

	Job getJobById(String jobId);
	
	boolean deleteJob(String jobId);

	List<Job> getAllJobs();

	boolean updateJob(Job job);

	List<Job> searchJobs(String keyword, String userId);
}
