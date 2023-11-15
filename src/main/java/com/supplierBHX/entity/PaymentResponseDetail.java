package com.supplierBHX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PaymentResponseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private Double priceOfInvoice;
    private Double adjustedPrice;
    private Double quantityOfInvoice;
    private Double adjustedQuantity;

    @ManyToOne
    @JoinColumn(name = "paymentResponse_id")
    private PaymentResponse paymentResponse;


}
