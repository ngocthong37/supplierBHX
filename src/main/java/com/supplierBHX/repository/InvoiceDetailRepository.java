package com.supplierBHX.repository;

import com.supplierBHX.dto.ProductDTO;
import com.supplierBHX.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {

    List<InvoiceDetail> findByInvoiceId(Integer invoiceId);

    @Query("SELECT new com.supplierBHX.dto.ProductDTO(i.id, i.quantity, i.price, i.product.id, i.product.name) " +
            "FROM InvoiceDetail i WHERE i.invoice.id = :invoiceId")
    List<ProductDTO> findDataProductToInsert(@Param("invoiceId") Integer invoiceId);
}
