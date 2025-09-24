package com.jobportal.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.Map;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;
    
    static {
        // Configure Cloudinary with your credentials
    	
        Map config = ObjectUtils.asMap(
            "cloud_name", "your-cloud-name",
            "api_key", "your-api-key",
            "api_secret", "your-api-secret",
            "secure", true
        );
        cloudinary = new Cloudinary(config);
    }
    
    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}