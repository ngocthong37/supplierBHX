package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    @GetMapping("supply-capacity/findToCompare/{productId}")
    ResponseEntity<ResponseObject> findToCompare(@PathVariable Integer productId) {
        return supplierService.findToCompareSupplyCapacity(productId);
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

    @GetMapping("quotation/filter")
    public ResponseEntity<ResponseObject> getFilteredQuotations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String, Object> filters
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return supplierService.getFilteredQuotations(pageable, filters);
    }

    @GetMapping("supply-capacity/filter")
    public ResponseEntity<ResponseObject> getFilteredSupply(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String, Object> filters
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return supplierService.getFilteredSupplyCapacity(pageable, filters);
    }

    @PostMapping("quotation/uploadImage")
    List<String> saveImage(@RequestParam("namePath") String namePath, @RequestParam("file") List<MultipartFile> files, @RequestParam("quotationId") Integer quotationId) {
        return supplierService.uploadImage(files, namePath, quotationId);
    }

    @PutMapping("quotation/delete/{quotationId}")
    ResponseEntity<Object>  deleteQuotation(@PathVariable Integer quotationId) {
        return supplierService.deleteQuotation(quotationId);
    }

}
