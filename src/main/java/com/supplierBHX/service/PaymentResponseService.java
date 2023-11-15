package com.supplierBHX.service;

import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.PaymentResponseDetail;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.PaymentResponseDetailRepository;
import com.supplierBHX.repository.PaymentResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentResponseService {

    @Autowired
    private PaymentResponseRepository paymentResponseRepository;

    public ResponseEntity<ResponseObject> findAll() {
        List<PaymentResponse> paymentResponselList = new ArrayList<PaymentResponse>();
        paymentResponselList = paymentResponseRepository.findAll();
        if (!paymentResponselList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponselList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findById(Integer id) {
        Optional<PaymentResponse> paymentResponse = paymentResponseRepository.findById(id);
        if (paymentResponse.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponse));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }

    }
}
