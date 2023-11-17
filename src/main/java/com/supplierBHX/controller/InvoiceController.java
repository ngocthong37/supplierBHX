package com.supplierBHX.controller;

import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
