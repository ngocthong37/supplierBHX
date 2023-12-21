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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;

    @JsonManagedReference(value ="supplyCapacity-product")
    @OneToMany(mappedBy = "product")
    private List<SupplyCapacity> supplyCapacity;

    @JsonManagedReference(value ="paymentResponse-product")
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<PaymentResponse> paymentResponses;

    @JsonManagedReference(value ="product-ratingProduct")
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<RatingProduct> ratingProducts;


}
