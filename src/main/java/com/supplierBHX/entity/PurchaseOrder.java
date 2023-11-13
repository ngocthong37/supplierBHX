package com.supplierBHX.entity;

import com.supplierBHX.Enum.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double total;
    private Date deliveryDate;
    private Date receiveDate;
    private Date createdAt;
    private Double discount;
    private Enum<UnitType> unitType;
    private Integer confirmStatus;
    private Date confirmDate;
    private Date deliveryStatus;
    private Date newDeliveryDate;
    private Date newReceiveDate;
    private Date updatedAt;
    private Integer warehouseId;
    private Integer employeeId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "grn_id")
    private GoodsReceivedNote goodsReceivedNote;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<PurchaseOrderDetail> purchaseOrderDetails;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<ProblemDetail> problemDetails;




}
