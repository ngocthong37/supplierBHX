package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Account> accounts;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Quotation> quotations;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Problem> problems;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<SupplyCapacity> supplyCapacities;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<PurchaseOrder> purchaseOrders;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<RatingProduct> ratingProducts;

}
