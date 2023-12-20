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
    @Query(value = "SELECT sc.* FROM supply_capacity sc " +
            "JOIN (SELECT s.product_id AS productId, MAX(s.created_at) AS latestCreatedAt " +
            "      FROM supply_capacity s " +
            "      GROUP BY s.product_id) latest_sc " +
            "ON sc.product_id = latest_sc.productId AND sc.created_at = latest_sc.latestCreatedAt", nativeQuery = true)
    Page<SupplyCapacity> findLatestByProductId(Pageable pageable);

    @Query(value = "SELECT sc.* FROM supply_capacity sc " +
            "JOIN (SELECT s.product_id AS productId, s.created_at AS latestCreatedAt " +
            "      FROM supply_capacity s " +
            "      WHERE s.product_id = :productId " +
            "      ORDER BY s.created_at DESC " +
            "      LIMIT 2) latest_sc " +
            "ON sc.product_id = latest_sc.productId AND sc.created_at = latest_sc.latestCreatedAt", nativeQuery = true)
    List<SupplyCapacity> findToCompare(@Param("productId") Integer productId);




}
