package com.supplierBHX.repository;

import com.supplierBHX.entity.GRNDetail;
import com.supplierBHX.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {

    List<InvoiceDetail> findByInvoiceId(Integer invoiceId);
}
