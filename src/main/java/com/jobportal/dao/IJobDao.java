package com.jobportal.dao;

import java.util.List;

import com.jobportal.model.Job;

public interface IJobDao {

	boolean jobpost(Job job);

	boolean saveJob(Job job);

	boolean updateJob(Job job);

	boolean deleteJob(String jobId);

	Job getJobById(String jobId);

	List<Job> getAllJobs();

	List<Job> searchJobs(String keyword, String userId);
}
