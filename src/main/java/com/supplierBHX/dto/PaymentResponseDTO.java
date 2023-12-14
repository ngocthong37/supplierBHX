package com.supplierBHX.dto;

import com.supplierBHX.Enum.PaymentResponseType;
import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.Enum.ResponseStatus;
import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.Product;
import com.supplierBHX.entity.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponseDTO {
    private Integer id;
    private String paymentResponseType;
    private String paymentResponseStatus;
    private LocalDate createdAt;
    private Integer updaterId;
    private Integer creatorId;
    private Double priceOfInvoice;
    private Double quantityOfInvoice;
    private Double priceOfPurchaseOrder;
    private Double quantityOfPurchaseOrder;
    private Integer purchaseOrderId;
    private Integer invoiceId;
    private Product product;
    private Integer accountId;
}
