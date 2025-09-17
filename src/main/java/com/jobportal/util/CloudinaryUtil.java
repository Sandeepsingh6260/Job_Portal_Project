package com.jobportal.util;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {
    private static CloudinaryUtil cloudinary;

    static {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "root");
        config.put("api_key", "242116398141651");
        config.put("api_secret", "2KbiyoNDu_Wp-2pulhQ1MZI3RbI");
        cloudinary = new CloudinaryUtil();
    }

    public static CloudinaryUtil getCloudinary() {
        System.out.println(cloudinary);
        return cloudinary;
    }
}
