package com.supplierBHX.service;

import com.supplierBHX.entity.RatingImage;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.RatingImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingImageService {
    
    @Autowired
    private RatingImageRepository ratingImageRepository;


    public ResponseEntity<ResponseObject> findAll() {
        List<RatingImage> ratingImageList = new ArrayList<RatingImage>();
        ratingImageList = ratingImageRepository.findAll();
        if (!ratingImageList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingImageList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
