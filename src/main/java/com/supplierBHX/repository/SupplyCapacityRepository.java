package com.supplierBHX.repository;

import com.supplierBHX.entity.SupplyCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface SupplyCapacityRepository extends JpaRepository<SupplyCapacity, Integer> {

}
