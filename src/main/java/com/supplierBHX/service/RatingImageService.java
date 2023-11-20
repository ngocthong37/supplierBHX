package com.supplierBHX.service;

import com.supplierBHX.dto.RatingImageDTO;
import com.supplierBHX.dto.RatingProductDTO;
import com.supplierBHX.entity.RatingImage;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.RatingImageRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<ResponseObject> findAll() {
        List<RatingImage> ratingImageList = new ArrayList<RatingImage>();
        ratingImageList = ratingImageRepository.findAll();
        if (!ratingImageList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingImageList.stream().map(ratingImage -> modelMapper.map(ratingImage, RatingImageDTO.class)).toList()));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
