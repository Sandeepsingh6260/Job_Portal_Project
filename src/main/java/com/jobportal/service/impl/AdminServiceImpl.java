package com.jobportal.service.impl;

import java.util.List;

import com.jobportal.dao.IAdminDao;
import com.jobportal.daoimpl.AdminDaoImpl;
import com.jobportal.model.User;
import com.jobportal.service.IAdminService;

public class AdminServiceImpl implements IAdminService {
           IAdminDao  adminDao;
           AdminDaoImpl admindaoimpl;
	    
	    public AdminServiceImpl() {
			super();
			adminDao=new AdminDaoImpl();
		}
	
	
	@Override
	public int getTotalJobSeekers() {
		return adminDao.getJobSeekers();
	}

	@Override
	public int getTotalRecruiters() {
		return adminDao.getRecruiters();
	}

	@Override
	public int getActiveJobsCount() {
		return adminDao.getJobsCount();
	}

	@Override
	public int getPendingApprovalsCount() {
		return adminDao.getPendingCount();
	}

	@Override
	public List<User> getAllJobSeekers() {
		return adminDao.getAllSeekersList();
	}

	@Override
	public List<User> getAllRecruiters() {
		return adminDao.getAllRecruitersList();
	}

	@Override
	public boolean activateUser(String userId) {
		return adminDao.updateUserStatus(userId, true);
	}

	@Override
	public boolean blockUser(String userId) {
		return adminDao.updateUserStatus(userId, false);
	}

}
