package com.supplierBHX.repository;

import com.supplierBHX.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    @Query("SELECT p FROM ProductImage p WHERE p.quotation.id = :quotationId")
    List<ProductImage> findByQuotationId(Integer quotationId);
}
