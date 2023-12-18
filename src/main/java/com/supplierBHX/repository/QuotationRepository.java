package com.supplierBHX.repository;

import com.supplierBHX.Enum.StatusType;
import com.supplierBHX.dto.QuotationDTO;
import com.supplierBHX.entity.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Integer> {

    @Query("SELECT q, p FROM Quotation q " +
            "INNER JOIN ProductImage p ON q.id = p.quotation.id " +
            "WHERE " +
            "(:status IS NULL OR q.status IN :status) " +
            "AND (:from IS NULL OR CAST(q.createdAt AS date) >= :from) " +
            "AND (:to IS NULL OR CAST(q.createdAt AS date) <= :to)")
    Page<Quotation> findByFilters(
            @Param("status") List<StatusType> status,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE ProductImage p SET p.imageUrl = :image where p.quotation.id = :quotationId AND p.id = :id")
    void updateImage(String image, Integer quotationId, Integer id);

    @Query("SELECT q.id, q.productName,q.account.id, q.supplier.id, q.price, q.number, q.employeeId, q.beginDate, q.endDate, q.unitType, q.status, q.createdAt, q.description, q.dateConfirmed, p.imageUrl FROM Quotation q " +
            "INNER JOIN ProductImage p ON q.id = p.quotation.id")
    Page<QuotationDTO> findAllWithImage(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Quotation quotation SET quotation.defaultImageUrl = :imageUrl where quotation.id = :quotationId")
    void updateDefaultImage(String imageUrl, Integer quotationId);

    @Transactional
    @Modifying
    @Query("UPDATE Quotation quotation SET quotation.isRemove = True where quotation.id = :quotationId")
    Integer deleteQuotation(Integer quotationId);

    @Query("SELECT q FROM Quotation q where q.isRemove = False")
    Page<Quotation> findAllWithCondition(Pageable pageable);


}

