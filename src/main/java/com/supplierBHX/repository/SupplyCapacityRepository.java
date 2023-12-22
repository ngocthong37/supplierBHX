package com.supplierBHX.repository;

import com.supplierBHX.Enum.StatusType;
import com.supplierBHX.entity.SupplyCapacity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository

public interface SupplyCapacityRepository extends JpaRepository<SupplyCapacity, Integer> {
    @Query("SELECT s FROM SupplyCapacity s WHERE " +
            "((:search IS NULL) OR (CAST(s.product.name AS string) LIKE %:search%)) " +
            "AND (:status IS NULL OR s.status IN :status) " +
            "AND (CAST(:from AS date) IS NULL OR CAST(s.createdAt AS date) >= :from) " +
            "AND (CAST(:to AS date) IS NULL OR CAST(s.createdAt AS date) <= :to)")

    Page<SupplyCapacity> findByFilters(
            @Param("status") List<StatusType> statusList,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("search") String search,
            Pageable pageable);
    @Query("SELECT s FROM SupplyCapacity s " +
            "WHERE (s.product.id, s.createdAt) IN " +
            "(SELECT s2.product.id, MAX(s2.createdAt) " +
            " FROM SupplyCapacity s2 " +
            " GROUP BY s2.product.id)")
    Page<SupplyCapacity> findLatestByProductId(Pageable pageable);


    @Query("SELECT s FROM SupplyCapacity s " +
            "WHERE s.product.id = :productId " +
            "ORDER BY s.createdAt DESC " +
            "LIMIT 2")
    List<SupplyCapacity> findToCompare(@Param("productId") Integer productId);






}
