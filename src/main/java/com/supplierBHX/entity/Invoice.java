package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supplierBHX.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Temporal(TemporalType.DATE)
    private LocalDate paymentDate;
    private String image;
    private Integer invoiceNumber;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "purchaseOrder_id")
    private PurchaseOrder purchaseOrder;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


}
