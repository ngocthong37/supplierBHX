package com.supplierBHX.controller;

import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.PaymentResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
//    @GetMapping("/findAllFilter")
//    ResponseEntity<ResponseObject> findAllFilter(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) Map<String, String> filters
//    ) {
//        Pageable pageable = PageRequest.of(page, size);
//        return paymentResponseService.findAllFilter(pageable, filters);
//    }

    @GetMapping("/findById/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id){
            return paymentResponseService.findById(id);
    }

    @GetMapping("/findByPurchaseOrderId/{purchaseOrderId}")
    ResponseEntity<ResponseObject> findByPurchaseOrderId(@PathVariable Integer purchaseOrderId){
        return paymentResponseService.findByPurchaseOrderId(purchaseOrderId);
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

//    @PutMapping("/update/{id}")
//    ResponseEntity<ResponseObject> updateById(@RequestBody @Validated PaymentResponse paymentResponse, BindingResult result,@PathVariable Integer id){
//        if (!result.hasErrors()){
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponseService.update(paymentResponse, id)));
//
//        }
//        else {
//            throw new RuntimeException(Objects.requireNonNull(result.getFieldError()).toString());
//        }
//    }


}
