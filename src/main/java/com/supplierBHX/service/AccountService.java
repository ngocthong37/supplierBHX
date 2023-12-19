package com.supplierBHX.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseObject> findAll() {
        List<Account> accountList = null;
        accountList = accountRepository.findAll();
        if (accountList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", accountList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<Object> updatePassword(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (json == null || json.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("ERROR", "Empty JSON", ""));
            }
            JsonNode jsonObjectUpdate = objectMapper.readTree(json);
            Integer accountId = jsonObjectUpdate.get("accountId") != null ?
                    jsonObjectUpdate.get("accountId").asInt() : -1;
            String enteredPassword = jsonObjectUpdate.get("oldPassword") != null ?
                    jsonObjectUpdate.get("oldPassword").asText() : "";
            String newPassword = jsonObjectUpdate.get("newPassword") != null ?
                    jsonObjectUpdate.get("newPassword").asText() : "";
            Optional<Account> optionalAccount = accountRepository.findById(accountId);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                if (passwordEncoder.matches(enteredPassword, account.getPassword())) {
                    account.setPassword(passwordEncoder.encode(newPassword));
                    accountRepository.save(account);
                    return ResponseEntity.ok(new ResponseObject("SUCCESS", "Password updated", ""));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new ResponseObject("ERROR", "Incorrect old password", ""));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("ERROR", "Account not found", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("ERROR", "An error occurred", e.getMessage()));
        }
    }
}
