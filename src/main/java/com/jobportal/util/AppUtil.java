package com.jobportal.util;

import java.util.regex.Pattern;

public class AppUtil {

	

	    public static boolean isValidEmail(String email) {
	        return Pattern.matches(AppConstant.EMAIL_REGEX, email);
	    }

}
