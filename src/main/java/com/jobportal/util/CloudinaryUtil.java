package com.jobportal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils;



public class CloudinaryUtil {

//    private static Cloudinary cloudinary;
//
//    static {
//        try {
//            Map<String, Object> config = ObjectUtils.asMap(
//                    "cloud_name", "Root",
//                    "api_key", "536281552934685",
//                    "api_secret", "6DH4z6LlVncs94sVPqc-qGUiYfI",
//                    "secure", true
//            );
//            cloudinary = new Cloudinary(config);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Get Cloudinary instance
//    
//    
//    public static Cloudinary getCloudinary() {
//        return cloudinary;
//    }
//
//    // Upload file to Cloudinary with folder and publicId
//    public static Map uploadFile(InputStream fileInputStream, String folder, String publicId) throws IOException {
//        if (cloudinary == null) throw new IOException("Cloudinary not initialized");
//        return cloudinary.uploader().upload(fileInputStream, ObjectUtils.asMap(
//                "resource_type", "auto",
//                "folder", folder,
//                "public_id", publicId
//        ));
//    }
//
//    // Extract publicId from Cloudinary URL (for deletion)
//    
//    
//    public static String extractPublicIdFromUrl(String url) {
//        if (url == null || url.isEmpty()) return null;
//        try {
//            String[] parts = url.split("/upload/");
//            if (parts.length < 2) return null;
//
//            String path = parts[1];
//            // Remove version prefix if exists
//            if (path.startsWith("v")) {
//                path = path.substring(path.indexOf("/") + 1);
//            }
//            // Remove file extension
//            
//            int lastDot = path.lastIndexOf(".");
//            if (lastDot != -1) path = path.substring(0, lastDot);
//
//            return path;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Generate secure URL from publicId
//    
//    
//    public static String generateSecureUrl(String publicId) {
//        if (cloudinary == null) return null;
//        return cloudinary.url().secure(true).generate(publicId);
//    }
}
