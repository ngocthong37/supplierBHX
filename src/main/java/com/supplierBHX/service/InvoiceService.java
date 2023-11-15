package com.supplierBHX.service;

import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.Invoice;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public ResponseEntity<ResponseObject> findAll() {
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        invoiceList = invoiceRepository.findAll();
        if (!invoiceList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", invoiceList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
