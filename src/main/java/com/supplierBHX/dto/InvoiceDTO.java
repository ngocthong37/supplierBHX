package com.supplierBHX.dto;

import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.Supplier;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDTO {
    private Integer id;
    private String paymentStatus;
    private LocalDate paymentDate;
    private String image;
    private Integer invoiceNumber;
    private Integer purchaseOrderId;
    private Integer supplierId;

}
