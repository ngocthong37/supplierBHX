package com.supplierBHX.service;

import com.supplierBHX.dto.PaymentResponseDTO;
import com.supplierBHX.dto.PaymentResponseDetailDTO;
import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.PaymentResponseDetail;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.PaymentResponseDetailRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ResponseObject> findAll() {
        List<PaymentResponseDetail> paymentResponseDetailList = new ArrayList<PaymentResponseDetail>();
        paymentResponseDetailList = paymentResponseDetailRepository.findAll();
        if (!paymentResponseDetailList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponseDetailList.stream().map(paymentResponseDetail -> modelMapper.map(paymentResponseDetail, PaymentResponseDetailDTO.class)).toList()));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findByPaymentResponseId(Integer paymentResponseId) {
        List<PaymentResponseDetail> paymentResponseDetailList = new ArrayList<PaymentResponseDetail>();
        paymentResponseDetailList = paymentResponseDetailRepository.findByPaymentResponseId(paymentResponseId);
        if (!paymentResponseDetailList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponseDetailList.stream().map(paymentResponseDetail -> modelMapper.map(paymentResponseDetail, PaymentResponseDetailDTO.class)).toList()));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
