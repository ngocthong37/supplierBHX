package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return accountService.findAll();
    }

}
