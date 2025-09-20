package com.jobportal.model;

import com.jobportal.audit.Auditable;

public class Resume extends Auditable {
	private String resume_id;
	private String file_path;
	private String skills;
	private Integer experience_years;
	private String user_id;
	public Resume() {
		super();
	}
	public Resume(String resume_id, String file_path, String skills, Integer experience_years, String user_id) {
		super();
		this.resume_id = resume_id;
		this.file_path = file_path;
		this.skills = skills;
		this.experience_years = experience_years;
		this.user_id = user_id;
	}
	public String getResume_id() {
		return resume_id;
	}
	public void setResume_id(String resume_id) {
		this.resume_id = resume_id;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public Integer getExperience_years() {
		return experience_years;
	}
	public void setExperience_years(Integer experience_years) {
		this.experience_years = experience_years;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "Resume [resume_id=" + resume_id + ", file_path=" + file_path + ", skills=" + skills
				+ ", experience_years=" + experience_years + ", user_id=" + user_id + "]";
	}
	

}
