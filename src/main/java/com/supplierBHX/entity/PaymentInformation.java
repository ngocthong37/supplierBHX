package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.supplierBHX.Enum.PaymentInformationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PaymentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private PaymentInformationType informationType;
    private Integer informationId;
    private String image;
    private LocalDate createdAt;

    @JsonBackReference(value = "supplier-paymentInformations")
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @JsonBackReference(value = "purchaseOrder-paymentInformations")
    @ManyToOne
    @JoinColumn(name = "purchaseOrder_id")
    private PurchaseOrder purchaseOrder;

    @JsonManagedReference(value ="paymentInformation-paymentResponse")
    @JsonIgnore
    @OneToMany(mappedBy = "paymentInformation")
    private List<PaymentResponse> paymentResponses;

}
