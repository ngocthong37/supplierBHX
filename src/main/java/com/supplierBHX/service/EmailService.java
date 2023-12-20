package com.supplierBHX.service;

import com.supplierBHX.repository.Imp.EmailServiceRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class EmailService {
    @Autowired
    EmailServiceRepositoryImp emailServiceRepositoryImp;

    public String sendMail(String to, String[] cc, String subject, Map<String, Object> model) {
        return emailServiceRepositoryImp.sendMail(to, cc, subject, model);
    }
}
