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
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double number;
    private Double price;
    private LocalDate beginDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusType status;
    private LocalDate dateConfirmed;
    private Integer employeeId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String productName;
    private String defaultImageUrl;
    private String reasonDecline;
    private boolean isRemove;

    @JsonBackReference(value = "quotation-supplier")
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @JsonBackReference(value = "account-quotation")
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "quotation",  cascade = CascadeType.ALL)
    private List<ZoneDelivery> zoneDeliveryList;

    @OneToMany(mappedBy = "quotation",  cascade = CascadeType.ALL)
    private List<ProductImage> productImageList;

}
