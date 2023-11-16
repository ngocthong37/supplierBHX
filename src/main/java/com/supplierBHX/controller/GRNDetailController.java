package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.GRNDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/grnDetail")
public class GRNDetailController {

    @Autowired
    private GRNDetailService grnDetailService;

    @GetMapping("grnDetailDTOByProductId/{productId}")
    ResponseEntity<ResponseObject> findAllGRNDtailDTOByProductId(@PathVariable Integer productId) {
        return grnDetailService.findAllGRNDtailDTOByProductId(productId);
    }
}
