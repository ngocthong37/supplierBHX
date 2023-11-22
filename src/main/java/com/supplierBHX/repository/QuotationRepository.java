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
            "(:status IS NULL OR q.status IN :status) AND " +
            "(CAST(:from AS date) IS NULL OR CAST(q.createdAt AS date) >= :from) AND " +
            "(CAST(:to AS date) IS NULL OR CAST(q.createdAt AS date) <= :to)")
    Page<Quotation> findByFilters(
            @Param("status") List<StatusType> status,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable)  ;

}

