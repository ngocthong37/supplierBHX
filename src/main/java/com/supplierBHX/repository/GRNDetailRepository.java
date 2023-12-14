package com.supplierBHX.repository;

import com.supplierBHX.dto.ProductDTO;
import com.supplierBHX.entity.GRNDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GRNDetailRepository extends JpaRepository<GRNDetail, Integer> {

    List<GRNDetail> findAllGRNDetailByProductId(Integer productId);

    @Query("SELECT new com.supplierBHX.dto.ProductDTO(g.id, g.numberReceived, g.price, g.product.id, g.product.name) " +
            "FROM GRNDetail g WHERE g.goodsReceivedNote.id = :grnId")
    List<ProductDTO> findDataProductToInsert(@Param("grnId") Integer grnId);
}
