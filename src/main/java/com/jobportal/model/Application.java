package com.jobportal.model;

import com.jobportal.audit.Auditable;

import com.jobportal.enums.StatusType;

public class Application extends Auditable {
		private String id;
		private String user_id;
		private String job_id;
		private String company_id;
		private boolean isDeleted;
		private StatusType statusType ;
		private String JobTitle;
		private String ApplicantId;
		private String ApplicantName;
		private String ApplicantEmail;
		private String ResumeLink;
		private String ExperienceYears;
		private String Skills;
		private String Location;
		

	public Application(String id, String user_id, String job_id, String company_id, boolean isDeleted,
			StatusType statusType, String jobTitle, String applicantId, String applicantName, String applicantEmail,
			String resumeLink, String experienceYears, String skills, String location) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.job_id = job_id;
		this.company_id = company_id;
		this.isDeleted = isDeleted;
		this.statusType = statusType;
		JobTitle = jobTitle;
		ApplicantId = applicantId;
		ApplicantName = applicantName;
		ApplicantEmail = applicantEmail;
		ResumeLink = resumeLink;
		ExperienceYears = experienceYears;
		Skills = skills;
		Location = location;
	
	}

	public Application() {
		super();
	}

	public Application(String id, String user_id, String job_id, String company_id, boolean isDeleted,
			StatusType statusType) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.job_id = job_id;
		this.company_id = company_id;
		this.isDeleted = isDeleted;
		this.statusType = statusType;
	}


	
	public Application(String id, String user_id, String job_id, String company_id, boolean isDeleted,
			StatusType statusType, String jobTitle, String applicantId, String applicantName, String applicantEmail) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.job_id = job_id;
		this.company_id = company_id;
		this.isDeleted = isDeleted;
		this.statusType = statusType;
		JobTitle = jobTitle;
		ApplicantId = applicantId;
		ApplicantName = applicantName;
		ApplicantEmail = applicantEmail;
	}


	
	
	public String getExperienceYears() {
		return ExperienceYears;
	}

	public void setExperienceYears(String experienceYears) {
		ExperienceYears = experienceYears;
	}

	public String getSkills() {
		return Skills;
	}

	public void setSkills(String skills) {
		Skills = skills;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getResumeLink() {
		return ResumeLink;
	}

	public void setResumeLink(String resumeLink) {
		ResumeLink = resumeLink;
	}
	
	public String getApplicantId() {
		return ApplicantId;
	}

	public void setApplicantId(String applicantId) {
		ApplicantId = applicantId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public StatusType getStatusType() {
		return statusType;
	}

	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}

	public String getApplicantName() {
		return ApplicantName;
	}

	public void setApplicantName(String applicantName) {
		ApplicantName = applicantName;
	}

	public String getApplicantEmail() {
		return ApplicantEmail;
	}

	public void setApplicantEmail(String applicantEmail) {
		ApplicantEmail = applicantEmail;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", user_id=" + user_id + ", job_id=" + job_id + ", company_id=" + company_id
				+ ", isDeleted=" + isDeleted + ", statusType=" + statusType + ", JobTitle=" + JobTitle
				+ ", ApplicantId=" + ApplicantId + ", ApplicantName=" + ApplicantName + ", ApplicantEmail="
				+ ApplicantEmail + ", ResumeLink=" + ResumeLink + ", ExperienceYears=" + ExperienceYears + ", Skills="
				+ Skills + ", Location=" + Location + "]";
	}

	
}
