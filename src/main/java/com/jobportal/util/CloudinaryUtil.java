package com.jobportal.util;

import com.cloudinary.Cloudinary;
import org.apache.commons.lang3.ObjectUtils;

public class CloudinaryUtil {

    private static Cloudinary cloudinary;

    static {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "root",
            "api_key", "242116398141651",
            "api_secret", "2KbiyoNDu_Wp-2pulhQ1MZI3RbI"
        ));
    }

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}
