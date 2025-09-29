package com.jobportal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@SuppressWarnings("unchecked")
public class CloudinaryUtil {

    private static Cloudinary cloudinary;

    static {
        try {
            Map<String, Object> config = ObjectUtils.asMap(
                    "cloud_name", "dsvk7uoky", 
                    "api_key", "536281552934685",
                    "api_secret", "6DH4z6LlVncs94sVPqc-qGUiYfI",
                    "secure", true
            );
            cloudinary = new Cloudinary(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Get Cloudinary instance
    public static Cloudinary getCloudinary() {
        return cloudinary;
    }

    public static Map uploadFile(InputStream fileInputStream, String folder, String publicId) throws IOException {
        if (cloudinary == null) throw new IOException("Cloudinary not initialized");

        // Convert InputStream to byte[]
        byte[] fileBytes = fileInputStream.readAllBytes();

        if (publicId == null || publicId.isEmpty()) {
            publicId = "resume_" + UUID.randomUUID();
        }

        // ✅ Always upload resumes as RAW (PDF, DOCX)
        return cloudinary.uploader().upload(
            fileBytes,
            ObjectUtils.asMap(
                "resource_type", "raw",     
                "folder", folder,           
                "public_id", publicId,
                "format", "pdf" ,
                "type", "upload",      
                "overwrite", true

            )
        );
    }


    // ✅ Extract publicId from Cloudinary URL
    public static String extractPublicIdFromUrl(String url) {
        if (url == null || url.isEmpty()) return null;
        try {
            String[] parts = url.split("/upload/");
            if (parts.length < 2) return null;

            String path = parts[1];
            if (path.startsWith("v")) {
                path = path.substring(path.indexOf("/") + 1);
            }
            int lastDot = path.lastIndexOf(".");
            if (lastDot != -1) path = path.substring(0, lastDot);

            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ✅ Generate secure URL
    public static String generateSecureUrl(String publicId) {
        if (cloudinary == null) return null;
        return cloudinary.url().secure(true).generate(publicId);
    }
}
