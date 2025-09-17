package com.jobportal.audit;

import java.sql.Timestamp;

public class Auditable {
	private Timestamp created_at;
	private Timestamp updated_at;
	private Boolean status;
	
	private  Boolean isDeleted;
	public Auditable() {
		super();
	}
	
	public Auditable(Timestamp created_at, Timestamp updated_at, Boolean status, Boolean isDeleted) {
		super();
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.status = status;
		this.isDeleted = isDeleted;
	}
	

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Auditable [created_at=" + created_at + ", updated_at=" + updated_at + ", status=" + status
				+ ", isDeleted=" + isDeleted + "]";
	}
	
}
