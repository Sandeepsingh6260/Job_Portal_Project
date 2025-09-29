package com.jobportal.util;

public class AppConstant {

    // =================== Error Messages ===================
    public static final String NAME_REQUIRED = "User Name is Required!";
    public static final String NAME_NOT_VALID = "User Name is Not Valid!";

    public static final String EMAIL_REQUIRED = "User Email is Required!";
    public static final String EMAIL_INVALID = "User Email is Invalid!";

    public static final String PASSWORD_REQUIRED = "Password is Required!";
    public static final String PASSWORD_NOT_VALID = "Password must be 8-20 chars, contain uppercase, lowercase, digit and special char!";
    public static final String INVALID_PASSWORD = " Invalid Password!";


    

    public static final String SKILLS_REQUIRED = "At least one skill is Required!";
    public static final String RESUME_REQUIRED = "Resume Upload is Required!";

    public static final String COMPANY_NAME_REQUIRED = "Company Name is Required!";
    public static final String COMPANY_LOCATION_REQUIRED = "Company Location is Required!";

        public static final String TITLE_REQUIRED = "Job title is required!";
        public static final String TITLE_NOT_VALID = "Job title is not valid! Only 2-100 characters, letters, numbers, spaces, basic symbols.";

        public static final String DESCRIPTION_REQUIRED = "Job description is required!";

        public static final String LOCATION_REQUIRED = "Job location is required!";
        public static final String LOCATION_NOT_VALID = "Job location is not valid! Only 2-100 characters, letters, numbers, spaces, basic symbols.";

        public static final String SALARY_REQUIRED = "Salary is required!";
        public static final String SALARY_NOT_VALID = "Salary must be a positive number!";

        public static final String EXPERIENCE_REQUIRED = "Experience is required!";
        public static final String EXPERIENCE_NOT_VALID = "Experience must be a numeric value!";

        public static final String JOB_TYPE_REQUIRED = "Job type is required!";

        public static final String MOBILE_REQUIRED = "Mobile number is required!";
        public static final String MOBILE_NOT_VALID = "Mobile number is invalid! Must be 10 digits, starting with 6-9.";

        // =================== Regex Patterns ===================
        public static final String TITLE_REGEX = "^[A-Za-z0-9 .,-]{2,100}$";      

        public static final String SALARY_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";   
        public static final String EXPERIENCE_REGEX = "^[0-9]+$";                 
        public static final String MOBILE_REGEX = "^[6-9][0-9]{9}$";              
    
        public static final String NAME_REGEX = "^[A-Za-z ]{2,50}$";
        public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        public static final String PASSWORD_REGEX =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$";
        public static final String LOCATION_REGEX = "^[A-Za-z0-9 ,.-]{2,100}$";
   
   
   
}
