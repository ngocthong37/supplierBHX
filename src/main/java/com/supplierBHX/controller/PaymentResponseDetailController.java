package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.InvoiceService;
import com.supplierBHX.service.PaymentResponseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class PaymentResponseDetailController {
    @Autowired
    private PaymentResponseDetailService paymentResponseDetailService;

    @GetMapping("/paymentResponseDetail/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return paymentResponseDetailService.findAll();
    }
}
