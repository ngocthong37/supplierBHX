package com.supplierBHX.controller;

import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/findAll")
    ResponseEntity<ResponseObject> findAll() {
        return accountService.findAll();
    }

    @PutMapping("/change-password")
    public ResponseEntity<Object> updatePassword(
            @RequestBody String json
    ) {
        return ResponseEntity.ok(accountService.updatePassword(json));
    }

}
