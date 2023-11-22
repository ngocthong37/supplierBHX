package com.supplierBHX.repository;

import com.supplierBHX.entity.PurchaseOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    @EntityGraph(attributePaths = {"purchaseOrderDetails"})
    Optional<PurchaseOrder> findByIdAndSupplier_Id(Integer orderId, Integer supplierId);

    Page<PurchaseOrder> findAllBySupplier_Id(Integer supplierId, Pageable pageable);

}
