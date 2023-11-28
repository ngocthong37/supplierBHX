package com.supplierBHX.entity;

import com.supplierBHX.Enum.ResponseStatus;
import com.supplierBHX.Enum.PaymentResponseType;
import com.supplierBHX.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PaymentResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private PaymentResponseType paymentResponseType;
    @Enumerated(EnumType.STRING)
    private ResponseStatus paymentResponseStatus;
    private LocalDate createdAt;
    private Integer updaterId;
    private Integer creatorId;
    private Double priceOfInvoice;
    private Double quantityOfInvoice;
    private Double priceOfPurchaseOrder;
    private Double quantityOfPurchaseOrder;

    @ManyToOne
    @JoinColumn(name = "purchaseOrder_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


}
