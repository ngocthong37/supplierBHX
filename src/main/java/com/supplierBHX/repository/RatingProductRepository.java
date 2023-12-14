package com.supplierBHX.repository;

import com.supplierBHX.Enum.PaymentInformationType;
import com.supplierBHX.entity.RatingProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RatingProductRepository extends JpaRepository<RatingProduct, Integer> {
    Page<RatingProduct> findByRatingDateBetweenOrderByRatingDate(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<RatingProduct> findByProduct_NameContainingIgnoreCaseAndRatingDateBetweenOrderByRatingDate(
            String productName, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT r FROM RatingProduct r WHERE " +
            "(:search IS NULL OR CAST(r.product.name AS string) LIKE %:search%) AND" +
            "(cast(:startDate as date) IS NULL OR r.ratingDate >= :startDate) AND " +
            "(cast(:endDate as date) IS NULL OR r.ratingDate <= :endDate) AND " +
            "(r.supplier.id = :supplierId) AND " +
            "(:minRatingScore IS NULL OR r.ratingScore >= :minRatingScore) AND " +
            "(:maxRatingScore IS NULL OR r.ratingScore <= :maxRatingScore)")
    Page<RatingProduct> findByFilters(
            @Param("search") String search,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("supplierId") Integer supplierId,
            @Param("minRatingScore") Float minRatingScore,
            @Param("maxRatingScore") Float maxRatingScore,
            Pageable pageable);
}