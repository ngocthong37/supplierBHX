package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return invoiceService.findAll();
    }

    @GetMapping("/findById/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        return invoiceService.findById(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> getFilteredInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String, Object> filters
    ) {

        Pageable pageable = PageRequest.of(page, size);
        return invoiceService.getFilteredInvoices(pageable, filters);
    }
}
