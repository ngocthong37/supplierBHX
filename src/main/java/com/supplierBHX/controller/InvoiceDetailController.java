package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/invoiceDetail")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @GetMapping("/findByInvoiceId/{invoiceId}")
    ResponseEntity<ResponseObject> findByInvoiceId(@PathVariable Integer invoiceId){
        return  invoiceDetailService.findByInvoice(invoiceId);
    }


}
