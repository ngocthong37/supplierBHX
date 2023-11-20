package com.supplierBHX.repository;

import com.supplierBHX.entity.RatingProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RatingProductRepository extends JpaRepository<RatingProduct, Integer> {
    Page<RatingProduct> findByRatingDateBetweenOrderByRatingDate(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
