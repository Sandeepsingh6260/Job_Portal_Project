package com.jobportal.util;

import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;
    
    static {
        // Configure Cloudinary with your credentials
    	
        Map config = ObjectUtils.asMap(
            "cloud_name", "dsvk7uoky",
            "api_key", "536281552934685",
            "api_secret", "6DH4z6LlVncs94sVPqc-qGUiYfI",
            "secure", true
        );
        cloudinary = new Cloudinary(config);
    }
    
    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}