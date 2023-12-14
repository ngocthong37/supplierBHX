package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.PaymentInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/paymentInformation")
public class PaymentInformationController {

    @Autowired
    private PaymentInformationService paymentInformationService;

    @GetMapping("/findAll")
    ResponseEntity<ResponseObject> findAll(){ return paymentInformationService.findAll();}

    @GetMapping("/findById/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        return paymentInformationService.findById(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> getFilteredPaymentInformations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String, Object> filters
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return paymentInformationService.getFilteredPaymentInformations(pageable, filters);
    }
}
