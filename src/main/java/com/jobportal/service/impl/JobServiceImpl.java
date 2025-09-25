package com.jobportal.service.impl;

import com.jobportal.dao.IJobDao;
import com.jobportal.daoimpl.JobDaoImpl;
import com.jobportal.model.Job;
import com.jobportal.service.IJobService;

public class JobServiceImpl implements IJobService {

	IJobDao jobdao = new JobDaoImpl();
	
	

	@Override
	public boolean jobpost(Job job) {
		return jobdao.jobpost(job);
	}

	@Override
	public boolean saveJob(Job job) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getActiveJobsCount(String recruiterId) {
		 return jobdao.getActiveJobsCount(recruiterId);
	}

}
