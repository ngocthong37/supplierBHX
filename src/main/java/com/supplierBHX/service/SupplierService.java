package com.supplierBHX.service;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.entity.Supplier;
import com.supplierBHX.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierInterface;

    public ResponseEntity<ResponseObject> findAll() {
        Map<String, Object> results = new TreeMap<String, Object>();
        List<Supplier> supplierList = null;
        try {
            supplierList = supplierInterface.findAll();
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        results.put("data", supplierList);
        if (results.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", results));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

}
