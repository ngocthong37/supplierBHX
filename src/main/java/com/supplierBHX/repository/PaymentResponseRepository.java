package com.supplierBHX.repository;

import com.supplierBHX.entity.PaymentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentResponseRepository extends JpaRepository<PaymentResponse, Integer> {
    List<PaymentResponse> findByPurchaseOrderId(Integer purchaseOrderId);
//    @Query("SELECT p FROM PaymentResponse p WHERE " +
//            "(:#{#filters['property1']} IS NULL OR p.property1 = :#{#filters['property1']}) AND " +
//            "(:#{#filters['property2']} IS NULL OR p.property2 = :#{#filters['property2']})")
//    Page<PaymentResponse> findByFilters(Map<String, String> filters, Pageable pageable);
}
