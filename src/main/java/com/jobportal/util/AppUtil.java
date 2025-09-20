package com.jobportal.util;

import java.util.regex.Pattern;

public class AppUtil {

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
}
