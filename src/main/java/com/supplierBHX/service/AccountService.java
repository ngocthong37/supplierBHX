package com.supplierBHX.service;

import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public ResponseEntity<ResponseObject> findAll() {
        Map<String, Object> results = new TreeMap<String, Object>();
        List<Account> accountList = null;
        accountList = accountRepository.findAll();
        results.put("data", accountList);
        if (results.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", results));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }

    }

}
