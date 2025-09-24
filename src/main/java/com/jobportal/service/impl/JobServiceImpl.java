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
		return jobdao.getJobById(jobId);
	}

	@Override
	public boolean deleteJob(String jobId) {
		// TODO Auto-generated method stub
		return jobdao.deleteJob(jobId);
	}

	@Override
	public List<Job> getAllJobs() {
		return jobdao.getAllJobs();
	}

	@Override
	public boolean updateJob(Job job) {
		return jobdao.updateJob(job);
	}

	
}
