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

public class PurchaseOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private Double price;
    private Double VAT;
    private Double discount;
    private Double number;
    private Double mass;
    private Integer unit;
    private Double newNumber;
    private Double newMass;

    @ManyToOne
    @JoinColumn(name = "purchaser_order_id")
    private PurchaseOrder purchaseOrder;

}
