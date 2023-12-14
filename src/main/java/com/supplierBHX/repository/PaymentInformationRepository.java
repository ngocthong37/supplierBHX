package com.supplierBHX.repository;

import com.supplierBHX.Enum.PaymentInformationType;
import com.supplierBHX.entity.PaymentInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation,Integer> {
    @Query("SELECT p FROM PaymentInformation p WHERE " +
            "(:search IS NULL OR CAST(p.purchaseOrder.id AS string) LIKE %:search%) AND" +
            "(:paymentInformationTypes IS NULL OR p.informationType IN :paymentInformationTypes) AND " +
            "(cast(:startDate as date) IS NULL OR p.createdAt >= :startDate) AND " +
            "(cast(:endDate as date) IS NULL OR p.createdAt <= :endDate) AND " +
            "(p.supplier.id = :supplierId)")
    Page<PaymentInformation> findByFilters(
            @Param("search") String search,
            @Param("paymentInformationTypes") List<PaymentInformationType> paymentInformationTypes,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("supplierId") Integer supplierId, Pageable pageable);
}
