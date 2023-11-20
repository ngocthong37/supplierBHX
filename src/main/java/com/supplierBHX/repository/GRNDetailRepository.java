package com.supplierBHX.repository;

import com.supplierBHX.dto.GRNDetailDTO;
import com.supplierBHX.entity.GRNDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GRNDetailRepository extends JpaRepository<GRNDetail, Integer> {
//    @Query(value = "SELECT " +
//            "gd.id AS id, " +
//            "grn.id AS goodsReceivedNoteID, " +
//            "gd.product_id AS productId, " +
//            "gd.quality_product AS qualityProduct, " +
//            "gd.status_product AS statusProduct, " +
//            "gd.price AS price, " +
//            "gd.number_inpo AS numberInPO, " +
//            "gd.number_received AS numberReceived, " +
//            "gd.number_damage AS numberDamage, " +
//            "gd.number_missing AS numberMissing, " +
//            "grn.created_at AS createdAt " +
//            "FROM procurement.GRNDetail gd " +
//            "INNER JOIN procurement.goods_received_note grn ON gd.grn_id = grn.id " +
//            "WHERE gd.product_id = :productId", nativeQuery = true)
    List<GRNDetail> findAllGRNDetailByProductId(Integer productId);
}
