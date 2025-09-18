package com.jobportal.payload.response;

public class CompnayResponse {
private String company_id;
private String compnay_name;
private String company_description;
private String company_location;
public CompnayResponse(String company_id, String compnay_name, String company_description, String company_location) {
	super();
	this.company_id = company_id;
	this.compnay_name = compnay_name;
	this.company_description = company_description;
	this.company_location = company_location;
}
public String getCompany_id() {
	return company_id;
}
public void setCompany_id(String company_id) {
	this.company_id = company_id;
}
public String getCompnay_name() {
	return compnay_name;
}
public void setCompnay_name(String compnay_name) {
	this.compnay_name = compnay_name;
}
public String getCompany_description() {
	return company_description;
}
public void setCompany_description(String company_description) {
	this.company_description = company_description;
}
public String getCompany_location() {
	return company_location;
}
public void setCompany_location(String company_location) {
	this.company_location = company_location;
}
@Override
public String toString() {
	return "CompnayResponse [company_id=" + company_id + ", compnay_name=" + compnay_name + ", company_description="
			+ company_description + ", company_location=" + company_location + "]";
}

}
