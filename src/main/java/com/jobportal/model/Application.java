package com.jobportal.model;

import com.jobportal.audit.Auditable;
import com.jobportal.enums.StatusType;

public class Application extends Auditable {
	private String id;
	private String user_id;
	private String job_id;
	private StatusType statusType ;
	public Application() {
		super();
	}
	public Application(String id, String user_id, String job_id, StatusType statusType) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.job_id = job_id;
		this.statusType = statusType;
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
	public StatusType getStatusType() {
		return statusType;
	}
	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}
	@Override
	public String toString() {
		return "Application [id=" + id + ", user_id=" + user_id + ", job_id=" + job_id + ", statusType=" + statusType
				+ "]";
	}
	
}
