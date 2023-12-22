package com.supplierBHX.repository;

import com.supplierBHX.entity.ZoneDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ZoneDeliveryRepository extends JpaRepository<ZoneDelivery, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ZoneDelivery z WHERE z.quotation.id = :quotationId")
    void deleteZoneDeliveriesById(@Param("quotationId") Integer quotationId);
}
