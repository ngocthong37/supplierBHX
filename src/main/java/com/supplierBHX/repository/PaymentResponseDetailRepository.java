package com.supplierBHX.repository;

import com.supplierBHX.entity.PaymentResponseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentResponseDetailRepository extends JpaRepository<PaymentResponseDetail,Integer> {
    List<PaymentResponseDetail> findByPaymentResponseId(Integer paymentResponseId);
}
