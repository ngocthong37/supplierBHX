package com.supplierBHX.repository;

import com.supplierBHX.entity.Problem;
import com.supplierBHX.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

    Optional<Problem> findByIdAndSupplier_Id(Integer orderId, Integer supplierId);

    Page<Problem> findAllBySupplier_Id(Integer supplierId, Pageable pageable);

}
