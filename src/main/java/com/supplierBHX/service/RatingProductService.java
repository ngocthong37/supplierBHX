package com.supplierBHX.service;

import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.RatingProduct;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.RatingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingProductService {

    @Autowired
    private RatingProductRepository ratingProductRepository;

    public ResponseEntity<ResponseObject> findAll() {
        List<RatingProduct> ratingProductList = new ArrayList<RatingProduct>();
        ratingProductList = ratingProductRepository.findAll();
        if (!ratingProductList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingProductList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
