package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
