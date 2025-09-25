package com.jobportal.dao;

import com.jobportal.model.Job;

public interface IJobDao {
	
	public boolean jobpost(Job job);
	public boolean saveJob(Job job);
	public int getActiveJobsCount(String recruiterId);

}
