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

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT i FROM Invoice i WHERE " +
            "((:search IS NULL) OR (CAST(i.invoiceNumber AS string) LIKE %:search%) OR " +
            "(CAST(i.purchaseOrder.id AS string) LIKE %:search%)) AND" +
            "(:paymentStatus IS NULL OR i.paymentStatus IN :paymentStatus) AND " +
            "(cast(:paymentDateFrom as date) IS NULL OR i.paymentDate >= :paymentDateFrom) AND " +
            "(cast(:paymentDateTo as date) IS NULL OR i.paymentDate <= :paymentDateTo)")
    Page<Invoice> findByFilters(
            @Param("search") String search, @Param("paymentStatus") List<PaymentStatus> paymentStatus,
            @Param("paymentDateFrom") LocalDate paymentDateFrom,
            @Param("paymentDateTo") LocalDate paymentDateTo,
            Pageable pageable);
}
