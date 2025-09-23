package com.jobportal.service.impl;

import com.jobportal.dao.IJobDao;
import com.jobportal.daoimpl.JobDaoImpl;
import com.jobportal.model.Job;
import com.jobportal.service.IJobService;

public class JobServiceImpl implements IJobService {

	IJobDao jobdao = new JobDaoImpl();
	
	public boolean savejob(Job job) {		
		return jobdao.jobpost(job);
	}

}
