package com.supplierBHX.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class StorageService {
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqqhlhhr9",
                "api_key", "788139511972494",
                "api_secret", "TVQm_6LL5d3zNouU0ouIFrQRDfE"));
        return cloudinary;
    }

    // Check file type: image png, jpg, jpeg, bmp
    public boolean isImage(MultipartFile file) {
        return Arrays.asList(new String[] {"image/png","image/jpg","image/jpeg","image/bmp"})
                .contains(Objects.requireNonNull(file.getContentType()).trim().toLowerCase());
    }

    // Upload image to cloudinary
    public List<String> uploadImages(List<MultipartFile> files, String namePath) {
        List<String> imageUrls = new ArrayList<>();
        try {
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                // Tạo chuỗi ngẫu nhiên
                String randomString = UUID.randomUUID().toString();
                // Thêm chuỗi ngẫu nhiên vào namePath
                String updatedNamePath = namePath + "/" + randomString;
                Map uploadResult = this.cloudinary().uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "public_id", updatedNamePath)
                );
                String imageUrl = (String) uploadResult.get("secure_url");
                imageUrls.add(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageUrls;
    }
}