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


    @GetMapping("quotation/findAll")
    ResponseEntity<ResponseObject> findAllQuotation() {
        return supplierService.findAllQuotation();
    }

    @GetMapping("quotation/findById/{id}")
    ResponseEntity<ResponseObject> findQuotationById(@PathVariable Integer id) {
        return supplierService.findQuotationById(id);
    }

    @GetMapping("supply-capacity/findById/{id}")
    ResponseEntity<ResponseObject> findSupplyCapacityById(@PathVariable Integer id) {
        return supplierService.findSupplyCapacityById(id);
    }

    @GetMapping("supply-capacity/findAll")
    ResponseEntity<ResponseObject> findAllSupplyCapacity() {
        return supplierService.findAllSupplyCapacity();
    }


    @PostMapping("quotation/create")
    ResponseEntity<Object> createQuotation(@RequestBody String json) {
        return supplierService.createQuotation(json);
    }

    @PostMapping("supply-capacity/create")
    ResponseEntity<Object> createRequestChangeSupplyCapacity(@RequestBody String json) {
        return supplierService.createRequestChangeSupplyCapacity(json);
    }

    @PutMapping("quotation/update-status")
    ResponseEntity<Object> updateStatusQuotation(@RequestBody String json) {
        return supplierService.updateStatus(json);
    }

    @PutMapping("supply-capacity/update-status")
    ResponseEntity<Object> updateStatusSupplyCapacity(@RequestBody String json) {
        return supplierService.updateSupplyCapacityStatus(json);
    }
}
