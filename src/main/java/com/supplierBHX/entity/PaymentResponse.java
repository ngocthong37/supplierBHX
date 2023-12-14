package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Double price;
    private Double quantity;
    private Double adjustedPrice;
    private Double adjustedQuantity;

    @JsonBackReference(value ="paymentInformation-paymentResponse")
    @ManyToOne
    @JoinColumn(name = "paymentInformation_id")
    private PaymentInformation paymentInformation;

    @JsonBackReference(value ="paymentResponse-product")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
