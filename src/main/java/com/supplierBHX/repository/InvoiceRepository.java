package com.supplierBHX.repository;

import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value = "SELECT i FROM Invoice i WHERE " +
            "(:paymentStatus IS NULL OR i.paymentStatus IN :paymentStatus) AND " +
            "(CAST(:paymentDateFrom AS date) IS NULL OR i.paymentDate >= CAST(:paymentDateFrom AS date)) AND " +
            "(CAST(:paymentDateFrom AS date) IS NULL OR i.paymentDate <= CAST(:paymentDateTo AS date))")
    Page<Invoice> findByFilters(
            @Param("paymentStatus") List<PaymentStatus> paymentStatus,
            @Param("paymentDateFrom") LocalDate paymentDateFrom,
            @Param("paymentDateTo") LocalDate paymentDateTo,
            Pageable pageable);


}
