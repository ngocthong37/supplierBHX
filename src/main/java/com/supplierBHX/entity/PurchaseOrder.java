package com.supplierBHX.entity;

import com.supplierBHX.Enum.UnitType;
import com.supplierBHX.Enum.UtilConstString;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.EntityGraph;

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
    private Double VAT;

    @Enumerated(EnumType.STRING)
    private UtilConstString.ConfirmedStatus confirmStatus;
    private Date confirmDate;

    @Enumerated(EnumType.STRING)
    private UtilConstString.DeliveryStatus deliveryStatus;

    private Date newDeliveryDate;
    private Date newReceiveDate;
    private Date updatedAt;
    private Integer warehouseId;
    private Integer employeeId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<PurchaseOrderDetail> purchaseOrderDetails;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<ProblemDetail> problemDetails;
}
