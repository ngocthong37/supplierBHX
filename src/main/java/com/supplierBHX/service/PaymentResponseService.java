package com.supplierBHX.service;

import com.supplierBHX.dto.PaymentResponseDTO;
import com.supplierBHX.dto.RatingFeedbackDTO;
import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.PaymentResponseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentResponseService {

    @Autowired
    private PaymentResponseRepository paymentResponseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ResponseObject> findAll() {
        List<PaymentResponse> paymentResponselList = new ArrayList<PaymentResponse>();
        paymentResponselList = paymentResponseRepository.findAll();
        if (!paymentResponselList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponselList.stream().map(paymentResponse -> modelMapper.map(paymentResponse, PaymentResponseDTO.class)).toList()));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findById(Integer id) {
        Optional<PaymentResponse> paymentResponse = paymentResponseRepository.findById(id);
        if (paymentResponse.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponse.stream().map(paymentResponse1 -> modelMapper.map(paymentResponse1, PaymentResponseDTO.class)).collect(Collectors.toList())));
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

    public ResponseEntity<ResponseObject> findByPurchaseOrderId(Integer purchaseOrderId) {
        List<PaymentResponse> paymentResponselList = new ArrayList<PaymentResponse>();
        paymentResponselList = paymentResponseRepository.findByPurchaseOrderId(purchaseOrderId);
        if (!paymentResponselList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentResponselList.stream().map(paymentResponse -> modelMapper.map(paymentResponse, PaymentResponseDTO.class)).toList()));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
