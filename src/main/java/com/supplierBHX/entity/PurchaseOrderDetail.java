package com.supplierBHX.entity;

import com.supplierBHX.Enum.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.EntityGraph;

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
    private String productName;
    private Double unitPrice;
    private Double VAT;
    private Double discount;
    private Double quantity;
    private Double mass;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    private Double newQuantity;
    private Double newMass;
    private Double finalAmount;

    @ManyToOne
    @JoinColumn(name = "purchaser_order_id")
    private PurchaseOrder purchaseOrder;

}
