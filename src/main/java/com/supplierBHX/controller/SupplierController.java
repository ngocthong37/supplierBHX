package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/")
public class SupplierController {
    @Autowired
    SupplierService supplierService;


    @GetMapping("supplier/findAll")
    ResponseEntity<ResponseObject> findAll() {
        System.out.println("Come here");
        return supplierService.findAll();
    }

    @PostMapping("create-quotation")
    ResponseEntity<Object> createQuotation(@RequestBody String json) {
        return supplierService.createQuotation(json);
    }

    @PostMapping("create-request-change")
    ResponseEntity<Object> createRequestChangeSupplyCapacity(@RequestBody String json) {
        return supplierService.createRequestChangeSupplyCapacity(json);
    }

    @PutMapping("update-status")
    ResponseEntity<Object> updateStatusQuotation(@RequestBody String json) {
        return supplierService.updateStatus(json);
    }


}
