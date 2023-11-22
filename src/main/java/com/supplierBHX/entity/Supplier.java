package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String supplierName;
    private String address;
    private String phoneNumber;
    private String dateOfBirth;
    private Boolean sex;
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<Account> accounts;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<Invoice> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<Quotation> quotations;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<Problem> problems;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<SupplyCapacity> supplyCapacities;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<PurchaseOrder> purchaseOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<RatingProduct> ratingProducts;
}
