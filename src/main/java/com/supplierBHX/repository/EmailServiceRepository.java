package com.supplierBHX.repository;

import java.util.Map;

public interface EmailServiceRepository {
    String sendMail(String to, String[] cc, String subject, Map<String, Object> model);
}
