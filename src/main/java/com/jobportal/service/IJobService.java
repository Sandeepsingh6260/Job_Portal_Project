package com.jobportal.service;

import com.jobportal.model.Job;

public interface IJobService {

	
	public boolean jobpost(Job job);
	public boolean saveJob(Job job);
	public int getActiveJobsCount(String recruiterId);

}
