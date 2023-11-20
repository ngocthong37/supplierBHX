package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.RatingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/v1/ratingProduct")
public class RatingProductController {

    @Autowired
    private RatingProductService ratingProductService;

    @GetMapping("/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return ratingProductService.findAll();
    }

    @GetMapping("/findByDateRangeAndSort")
    public ResponseEntity<ResponseObject> findByDateRangeAndSort(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ratingProductService.findByDateRangeAndSort(startDate, endDate, page, size);
    }
    @GetMapping("/findById/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        return ratingProductService.findById(id);
    }

}
