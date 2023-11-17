package com.supplierBHX.service;

import com.supplierBHX.repository.Imp.EmailServiceRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    EmailServiceRepositoryImp emailServiceRepositoryImp;

    public String sendMail(String to, String[] cc, String subject, String body) {
        return emailServiceRepositoryImp.sendMail(to, cc, subject, body);
    }
}
