package com.jobportal.service;

public interface ApplicationService {

	
	 public int getTotalApplicationsCount(String recruiterId);
	 public int getHiresCount(String recruiterId);
	 public double getResponseRate(String recruiterId);
}
