package com.supplierBHX.service;

import com.supplierBHX.entity.PaymentResponseDetail;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.PaymentResponseDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentResponseDetailService {

    @Autowired
    private PaymentResponseDetailRepository paymentResponseDetailRepository;

    public ResponseEntity<ResponseObject> findAll() {
        List<PaymentResponseDetail> paymentResponseDetailList = new ArrayList<PaymentResponseDetail>();
        paymentResponseDetailList = paymentResponseDetailRepository.findAll();
        if (!paymentResponseDetailList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponseDetailList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
