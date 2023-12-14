package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonManagedReference(value = "invoice-supplier")
    @OneToMany(mappedBy = "supplier")
    private List<Invoice> invoices;

    @JsonBackReference(value = "quotation-supplier")
    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<Quotation> quotations;

    @JsonManagedReference(value = "supplier-problem")
    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<Problem> problems;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<SupplyCapacity> supplyCapacities;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<PurchaseOrder> purchaseOrders;

    @JsonManagedReference(value = "supplier-ratingProduct")
    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<RatingProduct> ratingProducts;

    @JsonManagedReference(value = "supplier-goodsReceivedNote")
    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<GoodsReceivedNote> goodsReceivedNotes;

    @JsonManagedReference(value = "supplier-paymentInformations")
    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<PaymentInformation> paymentInformations;

}
