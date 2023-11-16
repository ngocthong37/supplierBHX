package com.supplierBHX.repository;

import com.supplierBHX.entity.WarehouseDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseDeliveryRepository extends JpaRepository<WarehouseDelivery, Integer> {
}
