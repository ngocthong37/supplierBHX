package com.supplierBHX.controller;

import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.PaymentResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/paymentResponse")
public class PaymentResponseController {

    @Autowired
    private PaymentResponseService paymentResponseService;

    @GetMapping("/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return paymentResponseService.findAll();
    }

    @GetMapping("/findById/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id){
            return paymentResponseService.findById(id);
    }
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insert(@RequestBody @Validated PaymentResponse paymentResponse, BindingResult result){
        if (!result.hasErrors()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponseService.insert(paymentResponse)));

        }
        else {
            throw new RuntimeException(Objects.requireNonNull(result.getFieldError()).toString());
        }
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateById(@RequestBody @Validated PaymentResponse paymentResponse, BindingResult result,@PathVariable Integer id){
        if (!result.hasErrors()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponseService.update(paymentResponse, id)));

        }
        else {
            throw new RuntimeException(Objects.requireNonNull(result.getFieldError()).toString());
        }
    }


}
