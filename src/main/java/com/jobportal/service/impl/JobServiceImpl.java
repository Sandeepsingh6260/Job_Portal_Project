package com.jobportal.service.impl;

import java.util.List;

import com.jobportal.dao.IJobDao;
import com.jobportal.daoimpl.JobDaoImpl;
import com.jobportal.model.Job;
import com.jobportal.service.IJobService;

public class JobServiceImpl implements IJobService {

	IJobDao jobdao = new JobDaoImpl();
	
	public boolean savejob(Job job) {		
		return jobdao.jobpost(job);
	}

	@Override
	public Job getJobById(String jobId) {
		return null;
	}

	@Override
	public boolean deleteJob(String jobId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Job> getAllJobs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateJob(Job job) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
