package com.jobportal.dao;

public interface ApplicationDao {

	
	 public int getTotalApplicationsCount(String recruiterId);
	 public int getHiresCount(String recruiterId);
	 public double getResponseRate(String recruiterId);

	}

	

