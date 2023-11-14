package com.supplierBHX.entity;


import com.supplierBHX.Enum.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SupplyCapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private Double number;
    private Double mass;
    private Enum<UnitType> unitType;
    private Date beginDate;
    private Date endDate;
    private String description;
    private String warehouseDelivery;
    private Integer status;
    private Date dateConfirmed;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
