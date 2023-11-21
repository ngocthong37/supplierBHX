package com.supplierBHX.repository;

import com.supplierBHX.entity.ZoneDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneDeliveryRepository extends JpaRepository<ZoneDelivery, Integer> {

}
