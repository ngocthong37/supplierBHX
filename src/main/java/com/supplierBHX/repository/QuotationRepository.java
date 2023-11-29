package com.supplierBHX.repository;

import com.supplierBHX.Enum.StatusType;
import com.supplierBHX.entity.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Integer> {

    @Query("SELECT q FROM Quotation q WHERE " +
            "((:search IS NULL) OR (CAST(q.product.name AS string) LIKE %:search%)) " +
            "AND ((:status IS NULL) OR (q.status IN :status)) " +
            "AND ((:from IS NULL) OR (CAST(q.createdAt AS date) >= :from)) " +
            "AND ((:to IS NULL) OR (CAST(q.createdAt AS date) <= :to))")

    Page<Quotation> findByFilters(
            @Param("status") List<StatusType> status,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("search") String search,
            Pageable pageable)  ;

}

