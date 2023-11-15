package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.PaymentResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class PaymentResponseController {

    @Autowired
    private PaymentResponseService paymentResponseService;

    @GetMapping("/paymentResponse/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return paymentResponseService.findAll();
    }

    @GetMapping("/paymentResponse/findById/")
    ResponseEntity<ResponseObject> findById()
}
