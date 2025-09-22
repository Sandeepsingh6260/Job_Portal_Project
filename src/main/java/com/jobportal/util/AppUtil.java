package com.jobportal.util;

import java.util.regex.Pattern;

public class AppUtil {

	
	// ================= user validation ==================
	
    public static boolean isValidName(String name) {
        return name != null && Pattern.matches(AppConstant.NAME_REGEX, name);
    }

    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(AppConstant.EMAIL_REGEX, email);
    }

    public static boolean isValidPassword(String password) {
        return password != null && Pattern.matches(AppConstant.PASSWORD_REGEX, password);
    }

    public static boolean isValidLocation(String location) {
        return location != null && Pattern.matches(AppConstant.LOCATION_REGEX, location);
    }
    
    public static boolean isValidMobile(String mobile) {
        return mobile != null && Pattern.matches(AppConstant.MOBILE_REGEX, mobile);
    }
    
    // ===== Job validations =====
    
    public static boolean isValidTitle(String title) {
        return title != null && Pattern.matches(AppConstant.TITLE_REGEX, title);
    }

    public static boolean isValidJobLocation(String location) {
        return location != null && Pattern.matches(AppConstant.LOCATION_REGEX, location);
    }

    public static boolean isValidSalary(String salary) {
        return salary != null && Pattern.matches(AppConstant.SALARY_REGEX, salary);
    }

    public static boolean isValidExperience(String experience) {
        return experience != null && Pattern.matches(AppConstant.EXPERIENCE_REGEX, experience);
    }

    public static boolean isValidJobType(String jobType) {
        if (jobType == null) return false;
        try {
            int jt = Integer.parseInt(jobType);
            return jt >= 1 && jt <= 4; 
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
