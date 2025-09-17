package com.jobportal.model;

import com.jobportal.audit.Auditable;

public class Job extends Auditable {
private String id;
private String title;
private String description;
private String user_id;
private String location;
private Double salary;
	public Job() {
		
	}
	public Job(String id, String title, String description, String user_id, String location, Double salary) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.user_id = user_id;
		this.location = location;
		this.salary = salary;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Job [id=" + id + ", title=" + title + ", description=" + description + ", user_id=" + user_id
				+ ", location=" + location + ", salary=" + salary + "]";
	}

}
