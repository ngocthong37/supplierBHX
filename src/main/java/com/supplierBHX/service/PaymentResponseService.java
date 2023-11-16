package com.supplierBHX.service;

import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.ResponseObject;
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

    public PaymentResponse update(PaymentResponse newPaymentResponse, Integer id) {
         return paymentResponseRepository
                .findById(id)
                .map(
                        paymentR -> {
                            paymentR.setPaymentResponseType(newPaymentResponse.getPaymentResponseType());
                            paymentR.setPaymentResponseStatus(newPaymentResponse.getPaymentResponseStatus());
                            paymentR.setUpdateDate(newPaymentResponse.getUpdateDate());
                            paymentR.setNote(newPaymentResponse.getNote());
                            paymentR.setPurchaseOrder(newPaymentResponse.getPurchaseOrder());
                            paymentR.setAccount(newPaymentResponse.getAccount());
                            paymentR.setUpdater(newPaymentResponse.getUpdater());
                            paymentR.setPaymentStatus(newPaymentResponse.getPaymentStatus());
                            return paymentResponseRepository.save(paymentR);
                        })
                .orElseGet(
                        () -> paymentResponseRepository.save(newPaymentResponse));
    }

    public Object insert(PaymentResponse paymentResponse) {
        return paymentResponseRepository.save(paymentResponse);
    }
}
