package com.supplierBHX.repository;

public interface EmailServiceRepository {
    String sendMail(String to, String[] cc, String subject, String body);
}
