package com.supplierBHX.controller;

import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.RatingFeedback;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.PaymentResponseService;
import com.supplierBHX.service.RatingFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/ratingFeedback")
public class RatingFeedbackController {

    @Autowired
    private RatingFeedbackService ratingFeedbackService;

    @GetMapping("/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return ratingFeedbackService.findAll();
    }

    @GetMapping("/findById/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id){
            return ratingFeedbackService.findById(id);
    }
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insert(@RequestBody @Validated RatingFeedback ratingFeedback, BindingResult result){
        if (!result.hasErrors()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingFeedbackService.insert(ratingFeedback)));

        }
        else {
            throw new RuntimeException(Objects.requireNonNull(result.getFieldError()).toString());
        }
    }



}
