package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.RatingImageService;
import com.supplierBHX.service.RatingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/ratingImage")
public class RatingImageController {

    @Autowired
    private RatingImageService ratingImageService;

    @GetMapping("/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return ratingImageService.findAll();
    }

    @GetMapping("/findByRatingProductId/{ratingProductId}")
    ResponseEntity<ResponseObject> findByRatingProductId(@PathVariable Integer ratingProductId){
        return ratingImageService.findByRatingProductId(ratingProductId);
    }
}
