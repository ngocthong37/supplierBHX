package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.supplierBHX.Enum.StatusType;
import com.supplierBHX.Enum.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SupplyCapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double number;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    private LocalDate beginDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private StatusType status;
    private LocalDate dateConfirmed;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer employeeId;

    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @JsonIgnore
    @OneToMany(mappedBy = "supplyCapacity")
    private List<WarehouseDelivery> warehouseDeliveries;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonManagedReference(value ="supplyCapacity-product")
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
