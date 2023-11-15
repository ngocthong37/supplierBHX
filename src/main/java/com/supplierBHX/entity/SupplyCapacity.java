package com.supplierBHX.entity;


import com.supplierBHX.Enum.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
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
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String description;
    private String warehouseDelivery;
    private Integer status;
    private LocalDate dateConfirmed;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer employeeId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
